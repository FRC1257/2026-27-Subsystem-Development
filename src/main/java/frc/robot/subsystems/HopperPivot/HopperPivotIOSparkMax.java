package frc.robot.subsystems.HopperPivot;
import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark. SparkAbsoluteEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkBaseConfig. IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure. AngularVelocity;
import frc.robot.Constants;

public class HopperPivotIOSparkMax implements HopperPivotIO {
 
    private SparkMax leftMotor;
    private SparkMax rightMotor;
    
    private SparkMaxConfig leftConfig;
    private SparkMaxConfig rightConfig;

    private ArmFeedforward feedforward;

    public HopperPivotIOSparkMax(){
        leftMotor = new SparkMax(0, null);
        rightMotor = new SparkMax(0, null);

        leftConfig = new SparkMaxConfig();
        rightConfig = new SparkMaxConfig();

        leftConfig
        .idleMode(IdleMode.kBrake)
        .voltageCompensation(12.0)
        .smartCurrentLimit(60)
        .inverted(false);
    leftConfig
      .encoder
      .positionConversionFactor(2* Math.PI*1/18)
      .velocityConversionFactor((2* Math.PI*1/18) / 60.0);
    leftConfig
      .closedLoop
      .pid(0, 0, 0);

    rightConfig = new SparkMaxConfig();
    rightConfig.apply(leftConfig);
    rightConfig.follow(leftMotor,true);

 leftMotor.configure(leftConfig, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters); 
    rightMotor.configure(rightConfig, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters); 

    feedforward = new ArmFeedforward(1,2,3);

    }

}
