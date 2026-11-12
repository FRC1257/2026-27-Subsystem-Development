package frc.robot.subsystems.roller;

import edu.wpi.first.units.measure.*;
import org.littletonrobotics.junction.AutoLog;

import static edu.wpi.first.units.Units.*;

public interface RollerIO {
    @AutoLog
    public static class RollerIOInputs {
        boolean motorConnected=false;
        Current current=Amps.of(0);
        AngularVelocity velocity=RadiansPerSecond.of(0);
        Angle angle=Radians.of(0);
        Voltage voltage=Volts.of(0);

    }

    default void updateInputs(RollerIOInputs inputs) {}

    default void runVoltage(Voltage voltage) {}

    default void runVelocity(Velocity velocity) {}

    default void stop() {}

    default void setPID(double kP, double kI, double kD) {}

    default void setFF(double ks, double kv, double ka) {}
}
