package subsystems.TanishFlyWheel;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

public interface FlywheelIO {
     @AutoLog 
     public static class FlywheelIOInputs { 
        //motor 1
        public boolean Motor1FlywheelConnected = false; 
        public Voltage Motor1flywheelVoltage= Volts.of(0.0);
        public AngularVelocity Motor1flywheelAngularVelocity = RadiansPerSecond.of(0.0);
        public Current Motor1flywheelCurrent = Amps.of(0.0);
        public Temperature Motor1flyTemperature= Celsius.of(0.0);  
        //motor2 
        public boolean Motor2FlywheelConnected = false; 
        public Voltage Motor2flywheelVoltage= Volts.of(0.0);
        public AngularVelocity Motor2flywheelAngularVelocity = RadiansPerSecond.of(0.0);
        public Current Motor2flywheelCurrent = Amps.of(0.0);
        public Temperature Motor2flyTemperature= Celsius.of(0.0); 
     }

     public default void updateInputs(FlywheelIOInputs inputs) {}
     public default void setVoltage(Voltage voltage) {}
     public default void stop() {} 
     public default void setBreakMode(boolean enabled) {} 
     public default void setPID(double kP, double kI, double kD) {}
     public default void setFF(double kS, double kV) {}
}
