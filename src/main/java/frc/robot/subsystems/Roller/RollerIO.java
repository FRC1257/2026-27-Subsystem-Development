package frc.robot.subsystems.Roller;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

public interface RollerIO {
    
    @AutoLog
    public static class RollerIOInputs{
        boolean rollerConnected = false;
        Voltage rollerVoltage = Volts.of(0);
        Current rollerCurrent = Amps.of(0);
    }

    default void updateInputs(RollerIOInputs inputs) {}

    default void runVoltage(Voltage volts) {}

    default void setPID(double kp, double ki, double kd) {}

    default void setFF(double ks, double kv, double kg) {}

    default void stop() {}
}

