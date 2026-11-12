package frc.robot.subsystems.RollerIO;

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

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.Constants;

public class RollerIOSparkMax implements RollerIO {
    private SparkMax rollerMotor;

    private SparkMaxConfig rollerConfig;

    private ArmFeedforward feedforward;

    public RollerIOSparkMax() {
        rollerMotor = new SparkMax(0, MotorType.kBrushless);

        rollerConfig = new SparkMaxConfig();

        rollerConfig
            .idleMode(IdleMode.kBrake)
            .voltageCompensation(12.0)
            .smartCurrentLimit(60)
            .inverted(false);

        rollerConfig
            .encoder
            .positionConversionFactor(2* Math.PI*1/18)
            .velocityConversionFactor((2* Math.PI*1/18) / 60.0);
        rollerConfig
            .closedLoop
            .pid(1, 2, 3);

        rollerConfig = new SparkMaxConfig();
        rollerConfig.follow(rollerMotor,true);

        rollerMotor.configure(rollerConfig, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);

        feedforward = new ArmFeedforward(3, 4, 5);
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
    public void setFF(double ks, double kv, double kg) {
      feedforward.setKs(ks);
      feedforward.setKv(kv);
      feedforward.setKg(kg);
    }
    
}
