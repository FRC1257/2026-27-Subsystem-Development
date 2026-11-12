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
        boolean rollerConnected = false;
        Voltage rollerVoltage = Volts.of(0.0);
        Angle rollerAngle = Radians.of(0.0);
        AngularVelocity rollerAngularVelocity = RadiansPerSecond.of(0.0);
        Current rollerCurrent = Amps.of(0.0);
        public AngularVelocity rollerVelocity;
       
    }

    default void updateInputs(RollerIOInputs inputs) {}

    default void runVoltage(Voltage volts) {}

    default void runAngle(double angle, double velocity) {}

    default void setPID(double kP, double kI, double kD) {}

    default void setFF(double kS, double kV, double kA) {}

    default void stop() {}
}
