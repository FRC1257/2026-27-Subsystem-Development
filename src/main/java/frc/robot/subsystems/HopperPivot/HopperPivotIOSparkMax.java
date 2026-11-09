package frc.robot.subsystems.HopperPivot;
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
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.Constants;

public class HopperPivotIOSparkMax implements HopperPivotIO {
    private SparkMax motor1;
    private SparkMax motor2;
    
    private SparkMaxConfig config1;
    private SparkMaxConfig config2;

    private ArmFeedforward feedforward;

    public HopperPivotIOSparkMax() {
        motor1 = new SparkMax(HopperPivotConstants.HOPPER_PIVOT_1_ID, MotorType.kBrushless);
        motor2 = new SparkMax(HopperPivotConstants.HOPPER_PIVOT_2_ID, MotorType.kBrushless);
        config1 = new SparkMaxConfig();
        config1.idleMode(IdleMode.kBrake).voltageCompensation(12.0).smartCurrentLimit(60).inverted(false);
        config1.encoder.positionConversionFactor(2).velocityCon3ersionFactor(4);
        config1.closedLoop.pid(HopperPivotConstants.HOPPER_PIVOT_KP, HopperPivotConstants.HOPPER_PIVOT_KI, HopperPivotConstants.HOPPER_PIVOT_KD);
        config2 = new SparkMaxConfig();
        config2.apply(config1);
        config2.follow(motor1,true);
        motor1.configure(config1, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters); 
        motor2.configure(config2, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters); 
        feedforward = new ArmFeedforward(HopperPivotConstants.HOPPER_PIVOT_KS, HopperPivotConstants.HOPPER_PIVOT_KA, HopperPivotConstants.HOPPER_PIVOT_KG, HopperPivotConstants.HOPPER_PIVOT_KV);

    }

}
