package frc.robot.subsystems.Roller;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.Constants;

public class RollerIOSparkMax implements RollerIO {
    private SparkMax motor;
    private SparkMaxConfig motorConfig;
    private SimpleMotorFeedforward feedforward;
    public RollerIOSparkMax() {
        motor = new SparkMax(RollerConstants.ROLLER_MOTOR_ID, MotorType.kBrushless);
        motorConfig = new SparkMaxConfig();
        motorConfig.idleMode(IdleMode.kBrake)
            .voltageCompensation(12.0)
            .smartCurrentLimit(60)
            .inverted(false);
        motorConfig
            .encoder
            .positionConversionFactor(2* Math.PI*1/18)
            .velocityConversionFactor((2* Math.PI*1/18) / 60.0);
        motorConfig
            .closedLoop
            .pid(RollerConstants.p, RollerConstants.i, RollerConstants.d);
        motor.configure(motorConfig, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
        feedforward = new SimpleMotorFeedforward(RollerConstants.ks, RollerConstants.kv);
    }
    @Override
    public void updateInputs(RollerIOInputs inputs) {
        inputs.motorVelocity = RadiansPerSecond.of(motor.getEncoder().getVelocity());
        inputs.motorVoltage = Volts.of(motor.getAppliedOutput() *12);
        inputs.motorAngle = Radians.of(motor.getEncoder().getPosition());
        inputs.motorCurrent = Amps.of(motor.getOutputCurrent());
        inputs.motorConnected = motor.getLastError() == REVLibError.kOk;
    }
    @Override
    public void runVoltage(Voltage volts) {
        motor.setVoltage(volts);
    }
    @Override
    public void setPID(double kp, double ki, double kd) {
        motor.configure(
            new SparkMaxConfig()
                .apply(new ClosedLoopConfig()
                .p(kp)
                .i(ki)
                .d(kd)),
            ResetMode.kNoResetSafeParameters,
            PersistMode.kPersistParameters);
    }
    @Override
    public void setFF(double ks, double kv) {
        feedforward.setKs(ks);
        feedforward.setKv(kv);
    }
    @Override
    public void stop() {
        motor.stopMotor();
    }
}

