package frc.robot.subsystems.hopper;

import edu.wpi.first.units.measure.*;
import org.littletonrobotics.junction.AutoLog;

import static edu.wpi.first.units.Units.*;

public interface HopperPivotIO {
    @AutoLog
    public static class HopperPivotIOInputs {
        boolean leftPivotConnected=false;
        Voltage leftPivotVoltage=Volts.of(0);
        Angle leftPivotAngle=Radians.of(0);
        AngularVelocity leftPivotVelocity=RadiansPerSecond.of(0);
        Current leftPivotCurrent=Amps.of(0);

        boolean rightPivotConnected=false;
        Voltage rightPivotVoltage=Volts.of(0);
        Angle rightPivotAngle=Radians.of(0);
        AngularVelocity rightPivotVelocity=RadiansPerSecond.of(0);
        Current rightPivotCurrent=Amps.of(0);
    }

    default void updateInputs(HopperPivotIOInputs inputs) {}

    default void runVoltage(Voltage voltage) {}

    default void runAngle(Angle angle, AngularVelocity velocity) {}

    default void setPID(double kp, double ki, double kd) {}

    default void setFF(double ks, double kv, double kg) {}

    default void stop() {}
}
