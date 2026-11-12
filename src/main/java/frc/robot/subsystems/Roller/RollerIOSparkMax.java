package frc.robot.subsystems.Roller;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;
import static edu.wpi.first.units.Units.Celsius;

import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.ResetMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.Constants;

public class RollerIOSparkMax implements RollerIO {
  private SparkMax motor;
  private SparkMaxConfig config;

  private SimpleMotorFeedforward feedforward;

  public RollerIOSparkMax() {

    motor = new SparkMax(1, MotorType.kBrushless);
    config = new SparkMaxConfig();

    config
        .idleMode(IdleMode.kBrake)
        .voltageCompensation(12.0)
        .smartCurrentLimit(60)
        .inverted(false);

        //electrical configs, current limit so things don't go on fire, inverted or not (mirrored or not)
        // voltage compensation = how much we can give it
        // idle mode - what should it do when it does nothing (stay still, don't resist movement)
    config
      .encoder
      .positionConversionFactor(2* Math.PI*1/18)
      .velocityConversionFactor((2* Math.PI*1/18) / 60.0);
    config
      .closedLoop
      .pid(1, 2, 3);

    /* config
     * you need to figure out what each config needs for specific subsystem; changes with each subsystem
     * Encoder - usually for conversion factors + encoder specific conversions
     * closed loop - defined PID values + other controllers that might be in the closed loop system
     */

    
    motor.configure(config, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters); 
    
    feedforward = new SimpleMotorFeedforward(3, 4, 5);
    
  }


  
  @Override
  public void updateInputs(HopperPivotIOInputs inputs) {
    inputs.rollerVelocity = RadiansPerSecond.of(motor.getEncoder().getVelocity());
    inputs.rollerVoltage = Volts.of(motor.getAppliedOutput() *12);
    inputs.rollerAngle = Radians.of(motor.getEncoder().getPosition());
    

    inputs.rollerCurrent = Amps.of(motor.getOutputCurrent());
   

    inputs.rollerConnected = motor.getLastError() == REVLibError.kOk;

  }

  @Override
  public void runVoltage(Voltage volts) {
    motor.setVoltage(volts);
    //simple since REVLib's method for this is literally js setVoltage
  }

  @Override
  public void runAngle(double angle, double velocity) {
    motor.getClosedLoopController().setSetpoint(
      angle,
      ControlType.kPosition,
      ClosedLoopSlot.kSlot0,
      feedforward.calculate(angle, velocity)
    );

    /* What the heck is happening?
     * 1st - get the closed loop controller
     * setSetpoint = REVLib method for how to get the motors to reach a setpoint
     * What are we giving this method?
     * angle - parameter - distance the pivot should go
     * control type - defining that the setpoint is a position variable and not something else
     * closed loop slot - can have various slots with various different sets of pid values; not something we do so we keep it at Slot 0 (the first one)
     * feedforward - calculates the voltage necessary to do this whole task taking into account the angle we give + the velocity we give it
     */

  }


  @Override
  public void stop() {
    motor.stopMotor();
    
    //simple since REVLib's method for this is literally js stopMotor
  }

  @Override
    public void setPID(double kP, double kI, double kD) {
      motor.configure(
        new SparkMaxConfig()
          .apply(new ClosedLoopConfig()
            .p(kP)
            .i(kI)
            .d(kD)),
        ResetMode.kNoResetSafeParameters,
        PersistMode.kPersistParameters);
    }
    //available in documentation; telling the motor's config to reset its closed loop with THESE PID values rather than what we defined earlier

  @Override
    public void setFF(double ks, double kv, double kg) {
      feedforward.setKs(ks);
      feedforward.setKv(kv);
      feedforward.setKg(kg);
    }

}