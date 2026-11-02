
package frc.robot.subsystems.Flywheel;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;


public interface FlywheelIO {    
    @AutoLog
    public static class FlywheelIOInputs {
        public boolean flywheelConnected = false;
        public Voltage fLywheelVoltage = Volts.of(magnitude:0);
        public AngularVelocity flywheelAngularVelocity = RadiansPerSecond.of(magnitude:0);
        public Temperature flywheelTemperature = Celsius.of(magnitude = 0);
        public Current flywheelCurrent = Amps.of(magnitude = 0);
    }

    public default void updateInputs(FlywheelIOInputs inputs) {}
    public default void setVoltage(Voltage voltage) {}
    
    public default void stop() {}
    
    public default void setBreakMode(boolean enabled) {}



}
