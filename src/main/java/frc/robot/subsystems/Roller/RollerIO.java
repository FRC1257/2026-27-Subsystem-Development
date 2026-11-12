package frc.robot.subsystems.Roller;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

public interface RollerIO {

    @AutoLog 
    public static class RollerIOInputs { 
        public boolean rollerconnected= false; 
        public Voltage rollerVoltage = Volts.of(0.0); 
        public AngularVelocity rollerAngularVelocity = RadiansPerSecond.of(0.0); 
        public Current rollerCurrent= Amps.of(0.0);
        public Temperature rollerTemperature= Celsius.of(0.0);
    }  

    default void updateInputs(RollerIOInputs inputs){} 
    default void setVoltage(Voltage voltage){}
    default void setPID(double kp, double ki, double kd) {} 
    default void setFF(double ks, double kv, double kg) {} 
    default void stop(){}
    

    
} 