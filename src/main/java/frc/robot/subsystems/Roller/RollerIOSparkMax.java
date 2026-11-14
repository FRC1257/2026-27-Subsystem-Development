package frc.robot.subsystems.Roller;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.units.measure.Voltage;


public class RollerIOSparkMax implements RollerIO {
    private SparkMax motor;
    
    private SparkMaxConfig config;

    private SimpleMotorFeedforward feedforward;

    public RollerIOSparkMax() {
        motor = new SparkMax(RollerConstants.motorCANID, MotorType.kBrushless); 

        config = new SparkMaxConfig();

        config
            .idleMode(IdleMode.kCoast) // coast bc it shouldnt matter if it moves or not i think
            .voltageCompensation(12)
            .smartCurrentLimit(60)
            .inverted(false);

        config
            .encoder
            .positionConversionFactor(2* Math.PI*1/18)
            .velocityConversionFactor((2* Math.PI*1/18) / 60.0);

            //^ from hopperpivotiosparkmax   im assuming that it is just to make the numbers look right when recorded

        config
            .closedLoop
            .pid(RollerConstants.rollerKP, RollerConstants.rollerKI, RollerConstants.rollerKD);
            
        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        feedforward = new SimpleMotorFeedforward(RollerConstants.rollerKS, RollerConstants.rollerKV, RollerConstants.rollerKA);

    }

    @Override
    public void updateInputs(RollerIOInputs inputs) {
        inputs.motorConnected = motor.getLastError() == REVLibError.kOk;
        inputs.motorVoltage = Volts.of(motor.getAppliedOutput() * 12);
        inputs.motorAngle = Radians.of(motor.getEncoder().getPosition());
        inputs.motorVelocity = RadiansPerSecond.of(motor.getEncoder().getVelocity());
        inputs.motorCurrent = Amps.of(motor.getOutputCurrent());

    }

    @Override
    public void runVoltage(Voltage volts) {
        motor.setVoltage(volts);
    }

    @Override
    public void runAngle(double angle, double velocity) {
        motor.getClosedLoopController().setSetpoint(
            angle,
            ControlType.kPosition,
            ClosedLoopSlot.kSlot0,
            feedforward.calculate(angle, velocity)
        );
    }

    @Override
    public void setPID(double kp, double ki, double kd) {
        motor.configure(
            new SparkMaxConfig().apply(new ClosedLoopConfig().p(kp).i(ki).d(kd)), 
            ResetMode.kNoResetSafeParameters, 
            PersistMode.kPersistParameters
        );
    }

    @Override
    public void setFF(double ks, double kv, double ka) {
        feedforward.setKs(ks);
        feedforward.setKv(kv);
        feedforward.setKa(ka);
    }

    @Override
    public void stop() {
        motor.stopMotor();
    }
}

