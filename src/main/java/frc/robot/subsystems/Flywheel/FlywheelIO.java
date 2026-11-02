package frc.robot.subsystems.Flywheel;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.Autolog;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

public interface FlywheelIO {

    @Autolog
    public static class FlywheelIOInputs { 
        public boolean firstmotorConnected = false;
        public Voltage firstmotorVoltage = Volts.of(magnitude:0,0);
        public AngularVelocity firstmotorAngularVelocity = RadiansPerSecond.or(magnitude:0,0);
        public Current firstmotorCurrent = Amps.of(magnitude:0,0);
        public Angle firstmotorAngle = Radians.of(magnitude:0,0);
       
        public boolean secondmotorConnected = false;
        public Voltage secondmotorVoltage = Volts.of(magnitude:0,0);
        public AngularVelocity secondmotorAngularVelocity = RadiansPerSecond.or(magnitude:0,0);
        public Current secondmotorCurrent = Amps.of(magnitude:0,0);
        public Angle secondmotorAngle = Radians.of(magnitude:0,0);
    }
    default void updateInputs(FlywheelIOInputs inputs) {}

    default void runVoltage(Voltage volts) {}

    default void runAngle(double angle, double velocity) {}

    default void setPID(double kp, double ki, double kd) {}

    default void setFF(double ks, double kv, double kg) {}

    default void stop() {}
}
