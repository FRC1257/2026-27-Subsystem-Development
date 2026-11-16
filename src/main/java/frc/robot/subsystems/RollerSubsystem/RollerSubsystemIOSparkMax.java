package frc.robot.subsystems.RollerSubsystem;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.Constants;

public class RollerSubsystemIOSparkMax implements RollerSubsystemio {
    private SparkMax Motor;

    private SparkMaxConfig Config;

    private ArmFeedforward feedforward;

    public RollerSubsystemIOSparkMax() {
        Motor = new SparkMax((int) 0.0, MotorType.kBrushless);
    

        Config = new SparkMaxConfig();
        
        
        Config
        .idleMode (IdleMode.kBrake)
        .voltageCompensation(12.0);
        Config
            .encoder
                .positionConversionFactor(3);
        Config
            .closedLoop
                .pid(1,2,3);
        Config = new SparkMaxConfig();
        Config.follow(Motor, true);

        Config.idleMode(IdleMode.kBrake);

        Motor.configure(Config, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);


        feedforward = new ArmFeedforward(3,4,4);

            
    }

    @Override
    public void updateInputs(RollerSubsystemio inputs) {
        inputs.rollerAngularVelocity = RadiansPerSecond.of(Motor.getEncoder().getVelocity());
        inputs.rollerVoltage = Volts.of(Motor.getAppliedOutput() * 12);
        inputs.rollerCurrent = Amps.of(Motor.getOutputCurrent());
        SparkBase rollerMotor;
        inputs.rollerConnected = rollerMotor.getLastError() == REVLibError.kOk; 
}
    @Override 
    public void runAngularVelocity(AngularVelocity velocity) {
        Motor.set(ControlType.kVelocity, velocity.get(RadiansPerSecond));
    }
    @Override
    pubklic void runrollerVoltage(Voltage volts) {
        Motor.set(ControlType.kDutyCycle, volts.get(Volts) / 12.0);
    }
    @Override
    public void setPID(double kP, double kI, double kD) {