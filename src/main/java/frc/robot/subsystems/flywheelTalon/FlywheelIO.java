package frc.robot.subsystems.flywheelTalon;

import edu.wpi.first.units.measure.*;
import org.littletonrobotics.junction.AutoLog;

import static edu.wpi.first.units.Units.*;

public interface FlywheelIO {
    @AutoLog
    public static class FlywheelIOInputs {
        boolean leftConnected=false;
        AngularVelocity leftVelocity=RadiansPerSecond.of(0);
        Voltage leftVoltage=Volts.of(0);
        Current leftCurrent=Amps.of(0);
        Temperature leftTemp=Celsius.of(0);

        boolean rightConnected=false;
        AngularVelocity rightVelocity=RadiansPerSecond.of(0);
        Voltage rightVoltage=Volts.of(0);
        Current rightCurrent=Amps.of(0);
        Temperature rightTemp=Celsius.of(0);
    }

    default void updateInputs(FlywheelIOInputs inputs) {}

    default void runVoltage(Voltage voltage) {}

    default void runVelocity(AngularVelocity velocity) {}

    default void stop() {}

    default void setCurrentLimit(Current current) {}

    default void setBreakMode(BreakMode breakMode) {}

    default void setPID(double kp, double ki, double kd) {}

    default void setFF(double ks, double kv, double ka) {}

    public static enum BreakMode { //why not
        BREAK,
        COAST
    }
}
