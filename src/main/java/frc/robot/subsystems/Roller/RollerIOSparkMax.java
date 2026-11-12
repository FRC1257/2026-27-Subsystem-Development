package frc.robot.subsystems.Roller;

import static edu.wpi.first.units.Units.Amps;
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
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constants;


public class RollerIOSparkMax implements RollerIO {
    private SparkMax motor;
    private SparkMaxConfig config;
    private SimpleMotorFeedforward feedforward;

    public RollerIOSparkMax() {
    motor = new SparkMax(0, MotorType.kBrushless);
    config = new SparkMaxConfig();

    config
        .idleMode(IdleMode.kBrake)
        .voltageCompensation(12.0)
        .smartCurrentLimit(60)
        .inverted(false);
    config
      .encoder
      .positionConversionFactor(2* Math.PI*1/18)
      .velocityConversionFactor((2* Math.PI*1/18) / 60.0);
    config
      .closedLoop
      .pid(1, 2, 3);

    motor.configure(config, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters); 

    feedforward = new SimpleMotorFeedforward(3, 4, 5);    
  }


    @Override
    public void updateInputs(RollerIOInputs inputs) {
        inputs.rollerVoltage = Volts.of(motor.getAppliedOutput() *12);

        inputs.rollerCurrent = Amps.of(motor.getOutputCurrent());

        inputs.rollerConnected = motor.getLastError() == REVLibError.kOk;
    }

    @Override
    public void runVoltage(Voltage volts) {
        motor.setVoltage(volts);
    }

    @Override
    public void stop() {
        motor.stopMotor();
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
    }
}
