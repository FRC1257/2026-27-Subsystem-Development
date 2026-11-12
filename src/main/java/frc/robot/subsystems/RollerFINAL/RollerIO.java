package frc.robot.subsystems.RollerFINAL;

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
        public boolean OneRollerConnected = false;
        public Voltage OneRollerVoltage = Volts.of(0);
        public AngularVelocity OneRollerVelocity = RadiansPerSecond.of(0);
        public Current OneRollerCurrent = Amps.of(0);
        public Temperature OneRollerTemperature = Celsius.of(0);
    }

    default void updateInputs(RollerIOInputs inputs) {}
    default void setVoltage(Voltage voltage) {}
    default void runVoltage(Voltage volts) {}

    default void stop() {}
    public default void setPID(double kp, double ki, double kd) {}
    public default void setFF(double ks, double kv, double kg) {}
    default void setBreakMode(boolean enabled) {}
}
