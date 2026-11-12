package frc.robot.subsystems.Roller;
//import, define, constructor, inputs, override methods
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

//imports from REVLib (revrobotics); you can read what the imports are and can infer why they are needed


import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.Constants;
public class RollerIOSparkMax implements RollerIO{
    private SparkMax motor;
    
    private SparkMaxConfig motorConfig;

    private ArmFeedforward feedForward;

    public RollerIOSparkMax(){
        motor = new SparkMax(0, MotorType.kBrushless);
        motorConfig = new SparkMaxConfig();
        motorConfig
        .idleMode(IdleMode.kBrake)
        .voltageCompensation(12.0)
        .smartCurrentLimit(60)
        .inverted(false);

        motorConfig
        .encoder
        .positionConversionFactor(2* Math.PI*1/18)
        .velocityConversionFactor((2* Math.PI*1/18) / 60.0);
        motorConfig
        .closedLoop
        .pid(1, 2, 3);
        
        motor.configure(motorConfig, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters); 
        feedForward = new ArmFeedforward(3, 4, 5);
    }
    @Override
    public void updateInputs(RollerIOInputs inputs){
        inputs.rollerConnected = motor.getLastError() == REVLibError.kOk;
        inputs.rollerCurrent = Amps.of(motor.getOutputCurrent());
        inputs.rollerAngle = Radians.of(motor.getEncoder().getPosition());
        inputs.rollerVoltage = Volts.of(motor.getAppliedOutput()*12);
        inputs.rollerVelocity = RadiansPerSecond.of(motor.getEncoder().getVelocity());
    }

    @Override
    public void runVoltage(Voltage volts){
        motor.setVoltage(volts);
    }

    @Override
    public void runAngle(double angle, double velocity){
        motor.getClosedLoopController().setSetpoint(
        angle,
        ControlType.kPosition,
        ClosedLoopSlot.kSlot0,
        feedForward.calculate(angle, velocity)
        );
    }
    @Override
    public void stop(){
        motor.stopMotor();
    }
    @Override
    public void setPID(double kp, double ki, double kd){
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
      feedForward.setKs(ks);
      feedForward.setKv(kv);
    }
    @Override
    public void runVelocity(AngularVelocity velocityRadsPerSec) {
        //ngl i could not figure this out lol
    }
    }


