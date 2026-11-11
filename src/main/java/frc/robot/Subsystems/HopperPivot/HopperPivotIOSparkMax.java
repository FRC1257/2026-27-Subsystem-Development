/*Steps for Hardware Implementation
 * STEP 1 - imports
 * STEP 2 - define objects
 * STEP 3 Constructor
 * STEP 4 - inputs
 * STEP - overriding methods
 */

package frc.robot.Subsystems.HopperPivot;
/*
 * notice this package is based on the way the file is organized in the code; changes from person to person
 */
import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

//implementing units - the actual values WE are going to use when coding

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

//WPILib imports for controllers + measures (different from units)
// measure tells what form of measurement we are using; subtle diffferent

public class HopperPivotIOSparkMax implements HopperPivotIO {
//ALWAYS IMPLEMENT IO FILE
  private SparkMax leftMotor;
  private SparkMax rightMotor;
    
  private SparkMaxConfig leftConfig;
  private SparkMaxConfig rightConfig;

  private ArmFeedforward feedforward;
// all objects necessary from configs, encoders, motors, controllers


  public HopperPivotIOSparkMax() {
    leftMotor = new SparkMax(0, MotorType.kBrushless);
    rightMotor = new SparkMax(1, MotorType.kBrushless);
    //define each motor with CAN ID, either brushless or brushed
    leftConfig = new SparkMaxConfig();

    leftConfig
        .idleMode(IdleMode.kBrake)
        .voltageCompensation(12.0)
        .smartCurrentLimit(60)
        .inverted(false);

        //electrical configs, current limit so things don't go on fire, inverted or not (mirrored or not)
        // voltage compensation = how much we can give it
        // idle mode - what should it do when it does nothing (stay still, don't resist movement)
    leftConfig
      .encoder
      .positionConversionFactor(2* Math.PI*1/18)
      .velocityConversionFactor((2* Math.PI*1/18) / 60.0);
    leftConfig
      .closedLoop
      .pid(1, 2, 3);

    /* config
     * you need to figure out what each config needs for specific subsystem; changes with each subsystem
     * Encoder - usually for conversion factors + encoder specific conversions
     * closed loop - defined PID values + other controllers that might be in the closed loop system
     */

    rightConfig = new SparkMaxConfig();
    rightConfig.apply(leftConfig);
    rightConfig.follow(leftMotor,true);
    //when you have two motors, you MUST have two configs but you can set it to follow the other config so you're not rewriting stuff
    //this one is inverted due to directions of motors on actual robot

    leftMotor.configure(leftConfig, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters); 
    rightMotor.configure(rightConfig, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters); 
    /* Configure each motor with config
     * tell each motor to actually implement its config
     * fancy stuff afterwards = persisting parameters = telling the sparkmax to do the configuration NOW the minute it turns on
     */
    feedforward = new ArmFeedforward(3, 4, 5);
    //define feedforward object with necessary values
    
  }


  
  @Override
  public void updateInputs(HopperPivotIOInputs inputs) {
    inputs.leftpivotVelocity = RadiansPerSecond.of(leftMotor.getEncoder().getVelocity());
    inputs.leftpivotVoltage = Volts.of(leftMotor.getAppliedOutput() *12);
    inputs.rightpivotVelocity = RadiansPerSecond.of(rightMotor.getEncoder().getVelocity());
    inputs.rightpivotVoltage = Volts.of(rightMotor.getAppliedOutput()*12);
    inputs.leftpivotAngle = Radians.of(leftMotor.getEncoder().getPosition());
    inputs.rightpivotAngle = Radians.of(rightMotor.getEncoder().getPosition());

    inputs.leftpivotCurrent = Amps.of(leftMotor.getOutputCurrent());
    inputs.rightpivotCurrent = Amps.of(rightMotor.getOutputCurrent());

    inputs.leftpivotConnected = leftMotor.getLastError() == REVLibError.kOk;
    inputs.rightpivotConnected = rightMotor.getLastError() == REVLibError.kOk;

    /*update inputs
     * use getters provided by REVLib + units from WPILib to actually collect the data we want from the motors
     * gets stored in Advantage Kit
     */

  }

  @Override
  public void runVoltage(Voltage volts) {
    leftMotor.setVoltage(volts);
    //simple since REVLib's method for this is literally js setVoltage
  }

  @Override
  public void runAngle(double angle, double velocity) {
    leftMotor.getClosedLoopController().setSetpoint(
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
    leftMotor.stopMotor();
    rightMotor.stopMotor();
    //simple since REVLib's method for this is literally js stopMotor
  }

  @Override
    public void setPID(double kP, double kI, double kD) {
      leftMotor.configure(
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
    //easy enough; js setters
}