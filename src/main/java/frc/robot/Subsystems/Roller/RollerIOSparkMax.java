package frc.robot.Subsystems.Roller;

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

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.Constants;

public class RollerIOSparkMax implements RollerIO {
  private SparkMax motor;
  private SparkMaxConfig motorConfig;
  private SimpleMotorFeedforward feedforward;

  public RollerIOSparkMax() {
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

    feedforward = new SimpleMotorFeedforward(3, 4, 5);
  }
  
  @Override
  public void updateInputs(RollerIOInputs inputs) {
    inputs.RollerVelocity = RadiansPerSecond.of(motor.getEncoder().getVelocity());
    inputs.RollerVoltage = Volts.of(motor.getAppliedOutput() *12);
    inputs.RollerCurrent = Amps.of(motor.getOutputCurrent());
    inputs.RollerConnected = motor.getLastError() == REVLibError.kOk;
  }

  @Override
  public void runVoltage(Voltage volts) {
    motor.setVoltage(volts);
   }

  @Override
  public void runVelocity(double velocity) {
    motor.getClosedLoopController().setSetpoint(
        velocity,
        ControlType.kVelocity,
        ClosedLoopSlot.kSlot0,
        feedforward.calculate(velocity)
    );
  }

  @Override
  public void stop() {
    motor.stopMotor();
   }

   @Override
    public void setBrakeMode(boolean enabled) {
        config.idleMode(enabled ? IdleMode.kBrake : IdleMode.kCoast);
        motor.configure(
            config, com.revrobotics.ResetMode.kNoResetSafeParameters, com.revrobotics.PersistMode.kNoPersistParameters);
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
 
  @Override
    public void setFF(double ks, double kv, double kg) {
      feedforward.setKs(ks);
      feedforward.setKv(kv);
      feedforward.setKg(kg);
    }
}