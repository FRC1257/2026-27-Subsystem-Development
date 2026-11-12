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

public interface FlywheelIO { 

    @AutoLog
    public static class FlywheelIOInputs {
        boolean leftmotorConnected = false;
        Voltage leftmotorVoltage = Volts.of(0.0);
        Angle leftmotorAngle = Radians.of(0.0);
        AngularVelocity leftmotorVelocity = RadiansPerSecond.of(0.0);
        Current leftmotorCurrent = Amps.of(0.0);

        boolean rightmotorConnected = false;
        Voltage rightmotorVoltage = Volts.of(0.0);
        Angle rightmotorAngle = Radians.of(0.0);
        AngularVelocity rightmotorVelocity = RadiansPerSecond.of(0.0);
        Current rightmotorCurrent = Amps.of(0.0);
    }

    default void updateInputs(FlywheelIOInputs inputs) {}

    default void runVoltage(Voltage volts) {}

    default void runAngle(double angle, double velocity) {}

    default void setPID(double kp, double ki, double kd) {}
    
    default void setFF(double ks, double kv, double kg) {}

    default void stop() {}



}