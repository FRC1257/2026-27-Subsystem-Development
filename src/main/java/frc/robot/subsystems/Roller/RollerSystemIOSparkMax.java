package frc.robot.subsystems.RollerSystem.RollerSystemIO;

import static edu.wpifist.units.Units.Amps;
import static edu.wpi.first.units.UNits.Celsius;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResistMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobtics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revroboitcs.spark.config.SparkBaseConfig;
import com.revroboitcs.spark.config.SparkFlexConfig;
import com.revroboitcs.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.Constant


public class RollerSystemIOSparkMax implements RollerSystemIo {
    private final SparkFlex motor;
    private final SparkFlexConfig config;


    public RollerSystemIOSparkMax() {
        motor = new SparkFlex(0, SparkFlex.MotorType.kBrushless);
        config = new SparkFlexConfig();
       
       
        config
            .idleMode(IdleMode.kBrake)

        config
            .encoder
                .positionConversionFactor(2* Math.PI * 1/18)
                .velocityConversionFactor((2* Math.PI*1/18)*60)

        config
            .ClosedLoopConfig
            .pid(1,3,4)

        motor.configure.(config, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);

        feedforward = new FeedForward(1,2,3);
    }
}