package frc.robot.subsystems.hopper;

import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.*;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;

import static edu.wpi.first.units.Units.*;

public class HopperPivotIOSparkMax implements HopperPivotIO {
    private final SparkMax leftMotor; // <-- this is main
    private final SparkMax rightMotor; // <-- this follow

    private final RelativeEncoder leftEncoder;
    private final RelativeEncoder rightEncoder;

    private final SparkClosedLoopController controller;
    private final ArmFeedforward feedforward;

    public HopperPivotIOSparkMax() {
        //temp ids
        leftMotor=new SparkMax(0, SparkLowLevel.MotorType.kBrushless);
        rightMotor=new SparkMax(1, SparkLowLevel.MotorType.kBrushless);
        leftEncoder= leftMotor.getEncoder();
        rightEncoder=rightMotor.getEncoder();

        SparkMaxConfig leftConfig=new SparkMaxConfig();

        leftConfig
                .idleMode(SparkBaseConfig.IdleMode.kBrake)
                .voltageCompensation(12)
                .smartCurrentLimit(60)
                .inverted(false);

        leftConfig.encoder.positionConversionFactor(3);


        leftConfig.closedLoop.pid(0,0,0);

        SparkMaxConfig rightConfig=new SparkMaxConfig();
        rightConfig.apply(leftConfig);
        rightConfig.follow(leftMotor,true);

        controller=leftMotor.getClosedLoopController();
        feedforward=new ArmFeedforward(0,0,0); //temp

        leftMotor.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        rightMotor.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        //IDK other stuff later
    }
    @Override
    public void updateInputs(HopperPivotIOInputs inputs) {
        inputs.leftPivotConnected=leftMotor.getLastError()== REVLibError.kOk;
        inputs.rightPivotConnected=rightMotor.getLastError()== REVLibError.kOk;

        inputs.leftPivotVoltage=Volts.of(leftMotor.getAppliedOutput()*12);
        inputs.rightPivotVoltage=Volts.of(rightMotor.getAppliedOutput()*12);

        inputs.leftPivotAngle=Radians.of(leftEncoder.getPosition());
        inputs.rightPivotAngle=Radians.of(rightEncoder.getPosition());

        inputs.leftPivotVelocity=RadiansPerSecond.of(leftEncoder.getVelocity());
        inputs.rightPivotVelocity=RadiansPerSecond.of(rightEncoder.getVelocity());

        inputs.leftPivotCurrent=Amps.of(leftMotor.getOutputCurrent());
        inputs.rightPivotCurrent=Amps.of(rightMotor.getOutputCurrent());
    }

    @Override
    public void runVoltage(Voltage voltage) {
        leftMotor.setVoltage(voltage);
    }

    @Override
    public void runAngle(Angle angle, AngularVelocity velocity) {
        controller.setSetpoint(angle.in(Radians), SparkBase.ControlType.kPosition, ClosedLoopSlot.kSlot0,feedforward.calculate(angle.in(Radians),velocity.in(RadiansPerSecond)));

    }

    @Override
    public void setPID(double kp, double ki, double kd) {
        leftMotor.configure(new SparkMaxConfig()
                .apply(new ClosedLoopConfig()
                        .p(kp)
                        .i(ki)
                        .d(kd)),
                ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters
        );
    }

    @Override
    public void setFF(double ks, double kv, double kg) {
        feedforward.setKs(ks);
        feedforward.setKv(ks);
        feedforward.setKg(kg);
    }

    @Override
    public void stop() {}
}
