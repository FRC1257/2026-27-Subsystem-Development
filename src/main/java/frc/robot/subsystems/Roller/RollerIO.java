package frc.robot.subsystems.Roller;
//importing packages
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
    public static class RollerIOInputs{
        //data
        boolean rollerConnected = false;
        Voltage rollerVoltage = Volts.of(0.0);
        Angle rollerAngle = Radians.of(0.0);
        AngularVelocity rollerVelocity = RadiansPerSecond.of(0.0);
        Current rollerCurrent = Amps.of(0.0); 
    }
    public default void updateInputs(RollerIOInputs inputs) {}

    public default void runVelocity(AngularVelocity velocityRadsPerSec) {}

    public default void runVoltage(Voltage voltage) {}

    public default void runAngle(double angle, double velocity) {}

    public default void stop() {}

    public default void setPID(double kp, double ki, double kd) {}

    public default void setFF(double ks, double kv) {}
}
