package frc.robot.subsystems.Roller;

/*Steps for Hardware Implementation
 * STEP 1 - imports
 * STEP 2 - define objects
 * STEP 3 Constructor
 * STEP 4 - inputs
 * STEP - overriding methods
 */


import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

//implementing units - the actual values WE are going to use when coding

import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

//imports from REVLib (revrobotics); you can read what the imports are and can infer why they are needed


import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constants;


public class RollerIOSparkMax implements RollerIO {
  private SparkMax rollerMotor;
  private SparkMaxConfig rollerMotorConfig;
  private SimpleMotorFeedforward rollerFeedforward;
  private RelativeEncoder rollerEncoder;


  public RollerIOSparkMax() {
    rollerMotor = new SparkMax(0, MotorType.kBrushless);
    rollerMotorConfig = new SparkMaxConfig();
    rollerEncoder = rollerMotor.getEncoder();

    rollerMotorConfig
        .idleMode(IdleMode.kBrake)
        .voltageCompensation(12.0)
        .smartCurrentLimit(60)
        .inverted(false);

    rollerMotorConfig
      .encoder
      .positionConversionFactor(2* Math.PI*1/18)
      .velocityConversionFactor((2* Math.PI*1/18) / 60.0);

    rollerMotorConfig
      .closedLoop
      .pid(1, 2, 3);

    rollerMotor.configure(rollerMotorConfig, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters); 

    rollerFeedforward = new SimpleMotorFeedforward(1,2,3);
  }


  
  @Override
  public void updateInputs(RollerIOInputs inputs) {
    inputs.rollerMotorVelocity = RadiansPerSecond.of(rollerMotor.getEncoder().getVelocity());
    inputs.rollerMotorVoltage = Volts.of(rollerMotor.getAppliedOutput() *12);
    inputs.rollerMotorAngle = Radians.of(rollerMotor.getEncoder().getPosition());

    inputs.rollerMotorCurrent = Amps.of(rollerMotor.getOutputCurrent());
    inputs.rollerMotorConnected = rollerMotor.getLastError() == REVLibError.kOk;

  }

  @Override
  public void runVoltage(Voltage volts) {
    rollerMotor.setVoltage(volts);
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
    public void setFF(double ks, double kv, double ka) {
      rollerFeedforward.setKs(ks);
      rollerFeedforward.setKv(kv);
      rollerFeedforward.setKa(ka);
    }
}