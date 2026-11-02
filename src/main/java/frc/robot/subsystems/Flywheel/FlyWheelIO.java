package frc.robot.subsystems.Flywheel;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

public interface FlyWheelIO {

    @AutoLog
    public static class FlywheelIOInputs {
        boolean motoroneConnected = false;
       Voltage motoroneVoltage = Volts.of(0.0);
       Angle motoroneAngle = Radians.of(0.0);
         AngularVelocity motoroneAngularVelocity = RadiansPerSecond.of(0.0);
         Current motoroneCurrent = Amps.of(0.0);

        boolean motortwoConnected = false;
         Voltage motortwoVoltage = Volts.of(0.0);
         Angle motortwoAngle = Radians.of(0.0);
         AngularVelocity motortwoAngularVelocity = RadiansPerSecond.of(0.0);
         Current motortwoCurrent = Amps.of(0.0);
    }

    default void updateInputs(FlywheelIOInputs inputs) {}

    default void runVoltage(Voltage volts) {}

    default void runAngle(double angle, double velocity, double acceleration) {}

    default void setPID(double kP, double kI, double kD) {}

    default void setFF(double kS, double kV, double kA) {}

    default void stop() {}
}