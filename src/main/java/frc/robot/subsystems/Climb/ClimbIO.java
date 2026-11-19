//MADE BY STEVEN ROSARIO

package frc.robot.subsystems.Climb;
import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;
import static edu.epi.first.units.Units.Celsius;
import org.littletonrobotics.junction.AutoLog;


import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure.Temperature;

public interface ClimbIO {
    @AutoLog
    public static class ClimbIOInputs{
        boolean climbConnected = false;
        Voltage climbVoltage = Volts.of(0.0);
        Current climbCurrent = Amps.of(0.0);
        Temperature climbTemperature = Celsius.of(0.0);
    }
    public default void updateInputs(ClimbIOInputs inputs) {}
    public default void runVoltage(Voltage voltage) {}
    public default void runAngle(double angle, double velocity) {}
    public default void runVelocity(double angle, double velocity) {}
    public default void runTemperature(Temperature temperature) {}
    public default void stop() {}

    public default void setPID(double kp, double ki, double kd) {}

    public default void setFF(double ks, double kv, double ka, double kg) {}
}
