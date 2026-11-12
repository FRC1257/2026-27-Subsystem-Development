package frc.robot.subsystems.Roller;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.units.measure.Voltage;

import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase.ControlType;
//import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
//import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;


public class RollerIOSparkMax implements RollerIO {
  private SparkMax rollerMotor;
    
  private SparkMaxConfig rollerMotorConfig;

  private SimpleMotorFeedforward feedforward;

public RollerIOSparkMax(){
 rollerMotor = new SparkMax(0, MotorType.kBrushless);
    rollerMotorConfig = new SparkMaxConfig();

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

      feedforward = new SimpleMotorFeedforward(0, 0, 0);
}


  @Override
  public void updateInputs(RollerIOInputs inputs) {
    inputs.rollerVelocity = RadiansPerSecond.of(rollerMotor.getEncoder().getVelocity());
    inputs.rollerVoltage = Volts.of(rollerMotor.getAppliedOutput() *12);

    inputs.rollerCurrent = Amps.of(rollerMotor.getOutputCurrent());
    
    inputs.rollerConnected = rollerMotor.getLastError() == REVLibError.kOk;

  }

  @Override
  public void runVoltage(Voltage volts) {
    rollerMotor.setVoltage(volts);
    
  }



@Override
    public void setVelocity(AngularVelocity angularVelocity) {
        double feedforwardVolts = feedforward.calculate(angularVelocity.in(RadiansPerSecond));
        rollerMotor.getClosedLoopController().setSetpoint(angularVelocity.in(RadiansPerSecond), ControlType.kVelocity, ClosedLoopSlot.kSlot0, feedforwardVolts);
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
      feedforward.setKs(ks);
      feedforward.setKv(kv);
      feedforward.setKa(ka);
    }

}