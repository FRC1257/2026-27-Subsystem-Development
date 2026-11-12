package frc.robot.subsystems.RollerSystem.RollerSystemIO;
import com.revrobotics.RelativeEncoder;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkBaseConfig;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constants;

public class RollerIOSparkMax implements RollerIO {

    private final SparkFlex motor;

    private final RelativeEncoder encoder;

    private final SparkFlexConfig rollerConfig;

    private final SparkClosedLoopController controller;
    private SimpleMotorFeedforward feedforward;

    public RollerIOSparkMax() {

        motor = new SparkFlex(RollerConstants.ROLLER_MOTOR_ID, SparkFlex.MotorType.kBrushless);
        
        encoder = motor.getEncoder();

        rollerConfig = new SparkFlexConfig();

        rollerConfig
            .smartCurrentLimit(60)
            .idleMode(IdleMode.kCoast)
            .voltageCompensation(12.0)
            .inverted(true);

        rollerConfig
            .encoder
            .positionConversionFactor(Math.PI * 2 * 32 / 34)
            .velocityConversionFactor(((Math.PI * 2) * 32 / 34) / 60).uvwMeasurementPeriod(10)
    .uvwAverageDepth(2)
    .quadratureAverageDepth(8)
    .quadratureMeasurementPeriod(24);

    rollerConfig
        .closedLoop
        .pid(RollerConstants.ROLLER_KP, RollerConstants.ROLLER_KI, RollerConstants.ROLLER_KD);

    controller = motor.getClosedLoopController();
    feedforward = new SimpleMotorFeedforward(RollerConstants.ROLLER_KS, RollerConstants.ROLLER_KV);

    motor.configure(rollerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
}
}
