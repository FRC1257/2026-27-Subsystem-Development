package frc.robot.subsystems.Flywheel;

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
        public boolean FlywheelPersonConnected = false;
        public Voltage FlywheelPersonVoltage = Volts.of(0);
        public AngularVelocity FlywheelPersonAngularVelocity = RadiansPerSecond.of(0);
        public Current FlywheelPersonCurrent = Amps.of(0);
        public Temperature FlywheelPersonTemperature = Celsius.of(0);

        public boolean FlywheelObjectConnected = false;
        public Voltage FlywheelObjectVoltage = Volts.of(0);
        public AngularVelocity FlywheelObjectAngularVelocity = RadiansPerSecond.of(0);
        public Current FlywheelObjectCurrent = Amps.of(0);
        public Temperature FlywheelObjectTemperature = Celsius.of(0);
    }

    default void updateInputs(FlywheelIOInputs inputs) {}

    default void setVoltage(Voltage voltage) {}

    default void stop() {}

    public default void setPID(double kP, double kI, double kD) {}

    public default void setFF(double kS, double kV) {}

    default void setBreakMode(boolean enabled) {}
}
