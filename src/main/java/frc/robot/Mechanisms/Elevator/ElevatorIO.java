package frc.robot.Mechanisms.Elevator;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

public interface ElevatorIO {
    @AutoLog
    public static class ElevatorIOInputs {
        boolean leftmotorConnected = false;
        Voltage leftmotorVoltage = Volts.of(0);
        Angle leftmotorAngle = Radians.of(0);
        LinearVelocity leftmotorLinearVelocity = MetersPerSecond.of(0);
        Current leftmotorCurrent = Amps.of(0);
        

        boolean rightmotorConnected = false;
        Voltage rightmotorVoltage = Volts.of(0);
        Angle rightmotorAngle = Radians.of(0);
        LinearVelocity rightmotorLinearVelocity = MetersPerSecond.of(0);
        Current rightmotorCurrent = Amps.of(0);
    }


    default void updateInputs (ElevatorIOInputs inputs) {}
    default void runVoltage(Voltage volts) {}
    default void runAngle(double angle, double velocity) {}
    default void setPID(double kp, double ki, double kd) {}
    default void setFF(double ks, double kv, double kg) {}
    default void stop() {}
}
// Zafar Did this and worked very diligently




