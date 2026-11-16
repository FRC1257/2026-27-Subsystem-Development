
package frc.robot.subsystems.RollerSubsystem;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;

public interface RollerSubsystemio {
    @AutoLog
    public static class RollerSubsystemIOInputs {
        boolean rollerConnected = false;
        Voltage rollerVoltage = Volts.of(0.0
        Angle rollerAngle = Radians.of(0.0);
        AngularVelocity rollerAngularVelocity = RadiansPerSecond.of(0.0);
        edu.wpi.first.units.measure.Current rollerCurrent = Amps.of(0.0);
        edu.wpi.first.units.measure.Voltage rollerVoltage = Volts.of(0.0);
        edu.wpi.first.units.measure.Angle rollerAngle = Radians.of(0.0);
        edu.wpi.first.units.measure.AngularVelocity rollerAngularVelocity = RadiansPerSecond.of(0.0);
        edu.wpi.first.units.measure.Current rollerCurrent = Amps.of(0.0);

        
}

    boolean Connected = false;
    edu.wpi.first.units.measure.Current Current = null;
    edu.wpi.first.units.measure.Voltage Voltage = null;
    edu.wpi.first.units.measure.AngularVelocity Velocity = null;
    AngularVelocity rollerAngularVelocity = null;
    edu.wpi.first.units.measure.Voltage rollerVoltage = null;
    edu.wpi.first.units.measure.Current rollerCurrent = null;
    boolean rollerConnected = false;

default void updateInputs(RollerSubsystemio inputs) {}

default void runVoltage(Voltage volts) {}

default void runAngle(double angle, double velocity) {}

default void setPID(double kP, double kI, double kD) {

default void setFF(double kS, double kV, double kA) {}

default void stop() {}

}