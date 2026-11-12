package frc.robot.subsystems.roller;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.units.measure.Voltage;

import static edu.wpi.first.units.Units.*;

public class RollerIOSparkMax implements RollerIO {
    private SparkMax motor;
    private AbsoluteEncoder encoder;
    private SimpleMotorFeedforward ff;

    public RollerIOSparkMax() {
        this.motor=new SparkMax(0, SparkLowLevel.MotorType.kBrushless); //temp ID
        SparkMaxConfig config=new SparkMaxConfig();

        config
                .idleMode(SparkBaseConfig.IdleMode.kBrake)
                .voltageCompensation(12)
                .smartCurrentLimit(60)
                .inverted(false);
        config
                .encoder
                .positionConversionFactor(2* Math.PI*1/18)
                .velocityConversionFactor((2* Math.PI*1/18) / 60.0); //may need change depending on subsystem ig
        config
                .closedLoop
                .pid(0, 0, 0);
        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        encoder=motor.getAbsoluteEncoder();

        ff=new SimpleMotorFeedforward(0,0,0);
    }

    @Override
    public void updateInputs(RollerIOInputs inputs) {
        inputs.voltage=Volts.of(motor.getOutputCurrent()*12);
        inputs.velocity=RadiansPerSecond.of(encoder.getVelocity());
        inputs.current=Amp.of(motor.getOutputCurrent());
        inputs.motorConnected=motor.getLastError()== REVLibError.kOk;
        inputs.angle=Radians.of(encoder.getPosition());
    }

    @Override
    public void runVoltage(Voltage voltage) {
        motor.setVoltage(voltage);
    }

    @Override
    public void runVelocity(Velocity velocity) {
        motor.getClosedLoopController().setSetpoint(
                velocity.magnitude(),
                SparkBase.ControlType.kVelocity,
                ClosedLoopSlot.kSlot0,
                ff.calculate(velocity.magnitude())
        );
    }

    @Override
    public void stop() {
        motor.stopMotor();
    }

    @Override
    public void setPID(double kP, double kI, double kD) {
        motor.configure(
                new SparkMaxConfig().apply(
                        new ClosedLoopConfig()
                                .p(kP)
                                .i(kI)
                                .d(kD)
                ),
                ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters
        );
    }

    @Override
    public void setFF(double ks, double kv, double ka) {
        ff.setKs(ks);
        ff.setKv(kv);
        ff.setKa(ka);
    }
}
