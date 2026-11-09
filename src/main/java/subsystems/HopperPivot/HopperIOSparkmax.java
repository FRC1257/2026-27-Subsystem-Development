package frc.robot.subsystems.HopperPivot; 

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;
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
import edu.wpi.first.math.trajectory. TrapezoidProfile;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure. AngularVelocity;
import frc.robot.Constants;
import subsystems.HopperPivot.HopperPivotIO;

public class HopperIOSparkmax implements HopperPivotIO {
    private SparkMax leftmotor; 
    private SparkMax rightmotor; 
    
    private SparkMaxConfig leftConfig;
    private SparkMaxConfig rightConfig;   

    private ArmFeedforward feedforward;

    public HopperIOSparkmax(){ 
        leftConfig= new SparkMaxConfig(); 

        leftConfig 
             .idleMode(IdleMode.kBrake); 
        leftConfig 
             .encoder 
                .positionConversionFactor(3); 
        leftConfig 
             .closedLoop
             .pid(1,2,3); 

        rightConfig= new SparkMaxConfig();
        rightConfig.apply(leftConfig); 
        rightConfig.follow(leftmotor,true); 

        leftmotor.configure(leftConfig, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters); 
        rightmotor.configure(rightConfig,com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters); 

        feedforward = new ArmFeedforward(3, 4, 4); 
    }

}
