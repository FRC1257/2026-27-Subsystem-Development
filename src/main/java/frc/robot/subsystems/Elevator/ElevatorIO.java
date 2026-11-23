package frc.robot.subsystems.Elevator;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;
//Arjun made this

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

public interface ElevatorIO{
    @AutoLog
    public static class ElevatorIOInputs {
        boolean leftpivotConnected = false;
        Voltage leftpivotVoltage = Volts.of(0);
        Distance leftpivotheight = Meters.of(0);
        Current leftpivotCurrent = Amps.of(0);

        boolean rightpivotConnected = false;
        Voltage rightpivotVoltage = Volts.of(0);
        Distance rightpivotheight = Meters.of(0);
        Current rightpivotCurrent = Amps.of(0);
    }

   

    default void updateInputs(ElevatorIOInputs inputs) {}
    default void runVoltage(Voltage volts) {}
    default void runAngle(double angle,double velocity) {}
    default void setPID(double kp, double ki, double kd) {}
    default void setFF(double ks, double kv, double kg) {}
    default void stop() {}
}
