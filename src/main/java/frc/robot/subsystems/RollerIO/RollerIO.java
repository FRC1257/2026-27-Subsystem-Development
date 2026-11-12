package frc.robot.subsystems.RollerIO;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

public interface RollerIO {

    @AutoLog
    public static class RollerIOInputs {
        public boolean rollerConnected = false;
        public Voltage rollerVoltage = Volts.of(0.0);
        public Temperature rollerTemperature = Celsius.of(0.0);
        public AngularVelocity rollerVelocity = RadiansPerSecond.of(0.0);
        public Current rollerCurrent = Amps.of(0.0);
    }

    default void updateInputs(RollerIOInputs inputs) {}

    default void runVoltage(Voltage volts) {}

    default void setVoltage(Voltage volts) {}

    default void stop() {}

    default void setBreakMode(boolean enabled) {}

    default void setPID(double kp, double ki, double kd) {}

    default void setFF(double kv, double ks, double kg) {}


}
