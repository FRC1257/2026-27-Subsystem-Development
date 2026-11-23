--package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.Constants;

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

public class RollerIOSparkMax implements RollerIO {
    private final SparkMax rollerMotor;
    
    private final SparkAbsoluteEncoder rollerEncoder;
    // can't rlly make object for encoder
    private final SparkMaxConfig rollerConfig;

    private SimpleMotorFeedforward rollerFeedforward;

    public RollerIOSparkMax() {

        rollerMotor = new SparkMax(0, MotorType.kBrushless);
        rollerConfig = new SparkMaxConfig();

        rollerConfig
            .smartCurrentLimit(60)
            .idleMode(IdleMode.kBrake)
            .voltageCompensation(12.0)
            .inverted(false);

        rollerConfig
            .rollerEncoder 
            .positionConversionFactor(1.0)
            .velocityConversionFactor(1.0);
        
        rollerConfig
            .closedLoop;
            .pid(0,1,2);

        rollerMotor.configure(rollerConfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        
        feedforward = new SimpleMotorFeedforward(10,50,90); //you named it rollerFF
    }    


    @Override
    public void runVoltage(Voltage volts) { //called setVoltage 
      rollerMotor.setVoltage(volts);
      
    }

    @Override
    public void setVelocity(AngularVelocity angularVelocity) {
        double feedforwardVolts = feedforward.calculate(angularVelocity.in(RadiansPerSecond));
        rollerMotor.getClosedLoopController().setSetpoint(angularVelocity.in(RadiansPerSecond), ControlType.kVelocity, ClosedLoopSlot.kSlot0, feedforwardVolts);
        //only works in RPM not RadiansPerSec
    }

    @Override
    public void stop() {
        rollerMotor.stopMotor();
    }

    @Override
    public void setPID(double kP, double kI, double kD) {
      rollerMotor.configure(
        new SparkMaxConfig()
          .apply(new ClosedLoopConfig()
            .p(kP)
            .i(kI)
            .d(kD)),
        ResetMode.kNoResetSafeParameters,
        PersistMode.kPersistParameters);
    }

    @Override 
    public void setFF(double kS, double kV, double kA) {
     feedforward.setKs(ks);
     feedforward.setKv(kv);
     feedforward.setKa(kA);
}

    @Override
    public void setBreakMode(boolean enabled) {
        config.IdleMode(enabled ? IdleMode.kBrake : IdleMode.kCoast);
        motor.configure(
            config, com.revrobotics.ResetMode.kNoResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
}

}

//23/30