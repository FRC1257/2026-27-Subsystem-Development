//RyanNg
package frc.robot.subsystems.Climb;
import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure.Distance;
import frc.robot.subsystems.Climb.ClimbIO.ClimbIOInputs;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Volts;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Meter;

public interface ClimbIO {
    @AutoLog
    public class ClimbIOInputs {
        boolean ClimbConnected = false;
        Distance setpointMeters = Meter.of(0.0);
        Distance positionMeters = Meter.of(0.0);
        Voltage ClimbVoltage = Volts.of(0.0);
        Angle ClimbAngle = Radians.of(0.0);
        AngularVelocity ClimbVelocity = RadiansPerSecond.of(0.0); 
        Current ClimbCurrent = Amps.of(0.0);
        Temperature ClimbTemperature = Celsius.of(0.0);
    }
    public default void updateInputs(ClimbIOInputs inputs) {}

    public default void setVoltage(Voltage voltage) {}

    public default void setSetpoint(double setpoint) {}

    default void setPID(double kp, double ki, double kd) {}

    default void setFF(double ks, double kv) {}
  
    public default void stop() {}
}
