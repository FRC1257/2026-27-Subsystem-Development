package frc.robot.subsystems.Roller;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

public interface RollerIO {
    @AutoLog
    public static class RollerIOInputs {
        boolean motorConnected = false;
        Voltage motorVoltage = Volts.of(0.0);
        Angle motorAngle = Radians.of(0.0);
        AngularVelocity motorVelocity = RadiansPerSecond.of(0.0); 
        Current motorCurrent = Amps.of(0.0);
    }
    default void updateInputs(RollerIOInputs inputs) {}
    default void setVoltage(Voltage volts) {}
    default void setVelocity(AngularVelocity velocityRadsPerSec) {}
    default void setPID(double kp, double ki, double kd) {}
    default void setFF(double ks, double kv) {}
    default void stop() {}
}
