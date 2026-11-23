package frc.robot.subsystems.Roller;

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

import edu.wpi.first.math.controller.ArmFeedfoward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.robots.constants;

public class RollerIOSparkMax implements RollerIO {
    private SparkMax roller;
    
    private SparkMaxConfig rollerconfig;
    
    private ArmFeedfowards feedFoward; //needs to be simple feedforward
    public RollerIOSparkMax(){
        roller = new SparkMax(0, MotorType.kBrushless);
        
        rollerconfig = new SparkMaxConfig();

        rollerconfig
        .idleMode(IdleMode.kBrake)
        .voltageCompensation(10.0);
        rollerconfig
            .encoder
                .positionConversionFactor(3);
        rollerconfig
            .ClosedLoop //closed loop controller
            .pid(1,2,3);
        
    roller.configure(rollerconfig, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParamaters);
    
    feedFoward = new ArmFeedfoward(3,4,4);
    }
}

//21/30 
// implementations not finished