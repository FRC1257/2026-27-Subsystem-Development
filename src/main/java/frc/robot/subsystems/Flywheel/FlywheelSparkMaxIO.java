package frc.robot.subsystems.Flywheel;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.FeedForwardConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;



public class FlywheelSparkMaxIO implements FlywheelIO {
    private final SparkFlex motor;
    private final SparkFlex followerMotor;

    private final RelativeEncoder encoder;
    private final RelativeEncoder followerEncoder;

    private final SparkFlexConfig config;
    private final SparkFlexConfig followerConfig;

    private final SparkClosedLoopController controller;
    private final SimpleMotorFeedforward feedforward;

    public FlywheelSparkMaxIO() {
        motor = new SparkFlex(FlywheelConstants.flywheelMotorCANID, SparkFlex.MotorType.kBrushless);
        followerMotor = new SparkFlex(FlywheelConstants.flywheelFollowerMotorCANID, SparkFlex.MotorType.kBrushless);

        encoder = motor.getEncoder();
        followerEncoder = followerMotor.getEncoder();

        config = new SparkFlexConfig();

        config.smartCurrentLimit(60)
        .idleMode(IdleMode.kCoast)
        .voltageCompensation(10)
        .inverted(false);

        config.closedLoop.pid(FlywheelConstants.flywheelKP, FlywheelConstants.flywheelKI, FlywheelConstants.flywheelKD);


        followerConfig = new SparkFlexConfig();
        followerConfig.apply(config);
        followerConfig.follow(motor, true);

        controller = motor.getClosedLoopController();
        feedforward = new SimpleMotorFeedforward(FlywheelConstants.flywheelKS, FlywheelConstants.flywheelKV);

        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        followerMotor.configure(followerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    }

    @Override
    public void updateInputs(FlywheelIOInputs inputs) {
        inputs.leftmotorConnected = motor.getLastError() == REVLibError.kCANDisconnected;
        inputs.leftmotorVoltage = Volts.of(motor.getAppliedOutput() * motor.getBusVoltage());
        inputs.leftmotorAngle = Radians.of(motor.getEncoder().getPosition());
        inputs.leftmotorVelocity = RadiansPerSecond.of(motor.getEncoder().getVelocity());
        inputs.leftmotorCurrent = Amps.of(motor.getOutputCurrent());

        inputs.rightmotorConnected = followerMotor.getLastError() == REVLibError.kCANDisconnected;
        inputs.rightmotorVoltage = Volts.of(followerMotor.getAppliedOutput() * followerMotor.getBusVoltage());
        inputs.rightmotorAngle = Radians.of(followerMotor.getEncoder().getPosition());
        inputs.rightmotorVelocity = RadiansPerSecond.of(followerMotor.getEncoder().getVelocity());
        inputs.rightmotorCurrent = Amps.of(followerMotor.getOutputCurrent());
    }

    @Override
    public void runVoltage(Voltage volts) {
        motor.setVoltage(volts);
    }

    @Override
    public void runAngle(double angle, double velocity) {
        // something idk what it is
    }

    @Override
    public void setPID(double kp, double ki, double kd) {
        config.closedLoop.pid(kp, ki, kd);
        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    @Override
    public void setFF(double ks, double kv, double kg) {
        feedforward.setKs(ks);
        feedforward.setKv(kv);
        //dunno if this is correct
    }

    @Override
    public void stop() {
        motor.stopMotor();
    }
    

}
