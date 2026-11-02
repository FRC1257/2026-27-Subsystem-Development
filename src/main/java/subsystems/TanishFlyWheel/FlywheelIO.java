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
        public boolean FlywheelConnected = false; 
        public Voltage flywheelVoltage= Volts.of(0.0);
        public AngularVelocity flywheelAngularVelocity = RadiansPerSecond.of(0.0);
        public Current flywheelCurrent = Amps.of(0.0);
        public Temperature flyTemperature= Celsius.of(0.0); 
     }

     public default void updateInputs(FlywheelIOInputs inputs) {}
     public default void setVoltage(Voltage voltage) {}
     public default void stop() {} 
     public default void setBreakMode(boolean enabled) {} 
     public default void setPID(double kP, double kI, double kD) {}
     public default void setFF(double kS, double kV) {}
}
