//RyanNg
package frc.robot.subsystems.PIDRoller;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Volts;
import static edu.wpi.first.units.Units.RadiansPerSecond;


public interface PIDRollerIO {
    @AutoLog
    public class PIDRollerIOInputs {
        boolean PIDRollerConnected = false;
        Voltage PIDRollerVoltage = Volts.of(0.0);
        Angle PIDRollerAngle = Radians.of(0.0);
        AngularVelocity PIDRollerVelocity = RadiansPerSecond.of(0.0); 
        Current PIDRollerCurrent = Amps.of(0.0);
        Temperature PIDRollerTemperature = Celsius.of(0.0);

        boolean PIDRollerFollowerConnected = false;
        Voltage PIDRollerFollowerVoltage = Volts.of(0.0);
        Angle PIDRollerFollowerAngle = Radians.of(0.0);
        AngularVelocity PIDRollerFollowerVelocity = RadiansPerSecond.of(0.0); 
        Current PIDRollerFollowerCurrent = Amps.of(0.0);
        Temperature PIDRollerFollowerTemperature = Celsius.of(0.0);
    }
    public default void updateInputs(PIDRollerIOInputs inputs) {}

    public default void setVoltage(Voltage voltage) {}

    public default void setVelocity(AngularVelocity velocityRadsPerSec) {}

    default void setPID(double kp, double ki, double kd) {}

    default void setFF(double ks, double kv) {}
  
    public default void stop() {}
}
