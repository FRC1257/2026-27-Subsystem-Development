package frc.robot.subsystems.Flywheel;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;
import static edu.wpi.first.units.Units.Volts;
import static edu.wpi.first.units.Units.RadiansPerSecond;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

public interface FlywheelIO {
    @AutoLog
    public static class FlywheelIOInputs {
        public boolean flywheelConnected = false;
        public Voltage flywheelVoltage = Volts.of(0);
        public AngularVelocity flywheelAngularVelocity = RadiansPerSecond.of(0);
        public Current flywheelCurrent = Amps.of(0);
        public Temperature flywheelTemperature = Celsius.of(0.0);
    }
    default void updateInputs(FlywheelIOInputs inputs) {}

    default void setVoltage(Voltage voltage) {}

    default void stop() {}

    default void setBreakMode(boolean enabled) {}
}