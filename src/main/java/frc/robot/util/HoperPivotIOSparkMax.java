package frc.robot.subsystems.Hopper.HopperPivotIO;

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

public class HopperPivotIOSparkMax implements HopperPivotIO {
    private SparkMax leftMotor;
    private SparkMax rightMotor;

    private SparkMaxConfig leftConfig;
    priavte SparkMaxConfig rightConfig;

    private ArmFeedforward feedforward;
    leftMotor = newSparkMax(0, MotorType.kBrushless)

    

    leftConfig
    .idleMode(IdleMode.kBrake)
  
    leftConfig
        .encoder
            .positionConversionFactor(3)
    leftConfig
        .ClosedLoopConfig
        .pid(1,2,3)

    rightConfig = new SparkMaxConfig();
    rightConfig.apply(leftConfig);
    rightConfig.follow(leftMotor,true);

leftMotor.configure.(leftConfig, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);
rightMotor.configure.(rightConfig, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters);

feedforward = new ArmFeedforward(3,4,4)
}