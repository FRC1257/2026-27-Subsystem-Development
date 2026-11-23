// William "The Silly" Zhang made this
package frc.robot.subsystems.PIDRoller;


import static org.wpilib.units.Units.Amps;
import static org.wpilib.units.Units.Celsius;
import static org.wpilib.units.Units.RadiansPerSecond;
import static org.wpilib.units.Units.Volts;


import org.littletonrobotics.junction.AutoLog;


import org.wpilib.units.measure.AngularVelocity;
import org.wpilib.units.measure.Current;
import org.wpilib.units.measure.Temperature;
import org.wpilib.units.measure.Voltage;


public interface PIDRollerIO {


    @AutoLog
    public static class PIDRollerIOInputs {
        public boolean PIDRollerConnected = false;
        public Voltage PIDRollerVoltage = Volts.of(0.0);
        public AngularVelocity PIDRollerAngularVelocity = RadiansPerSecond.of(0.0);
        public Current PIDRollerCurrent = Amps.of(0.0);
        public Temperature PIDRollerTemperature = Celsius.of(0.0);
    }


    default void updateInputs(PIDRollerIOInputs inputs) {}


    default void setVoltage(Voltage voltage) {}
   
    default void stop() {}
   
    default void setBreakMode(boolean enabled) {}
}