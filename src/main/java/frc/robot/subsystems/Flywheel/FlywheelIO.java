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
        public boolean flywheel1Connected = false;
        public Voltage flywheel1Voltage = Volts.of(0);
        public AngularVelocity flywheel1AngularVelocity = RadiansPerSecond.of(0);
        public Current flywheel1Current = Amps.of(0);
        public Temperature flywheel1Temperature = Celsius.of(0.0);

        public boolean flywheel2Connected = false;
        public Voltage flywheel2Voltage = Volts.of(0);
        public AngularVelocity flywheel2AngularVelocity = RadiansPerSecond.of(0);
        public Current flywheel2Current = Amps.of(0);
        public Temperature flywheel2Temperature = Celsius.of(0.0);
    }
    default void updateInputs(FlywheelIOInputs inputs) {}

    default void setVoltage(Voltage voltage) {}

    default void stop() {}

    default void setBreakMode(boolean enabled) {}

    default void setPID(double kp, double ki, double kd) {}

    default void setFF(double kv, double ks, double kg) {}
}