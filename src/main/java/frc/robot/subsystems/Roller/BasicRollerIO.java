package frc.robot.subsystems.Roller;


import static org.wpilib.units.Units.Amps;
import static org.wpilib.units.Units.Celsius;
import static org.wpilib.units.Units.RadiansPerSecond;
import static org.wpilib.units.Units.Volts;


import org.littletonrobotics.junction.AutoLog;


import org.wpilib.units.measure.Temperature;
import org.wpilib.units.measure.AngularVelocity;
import org.wpilib.units.measure.Current;
import org.wpilib.units.measure.Voltage;


public interface BasicRollerIO {


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
// Avyan made this



