package frc.robot.subsystems.Roller;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.Autolog;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
                                                                                                                                        
public interface RollerIO {

    @Autolog
    public static class RollerIOInputs { 
        public boolean motorConnected = false;
        public Voltage motorVoltage = Volts.of(magnitude:0,0);
        public AngularVelocity motorAngularVelocity = RadiansPerSecond.or(magnitude:0,0);
        public Current motorCurrent = Amps.of(magnitude:0,0);
        public Angle motorAngle = Radians.of(magnitude:0,0);
    }

    default void updateInputs(RollerIOInputs inputs) {}

    default void runVoltage(Voltage volts) {}

    default void runAngle(double angle, double velocity) {}

    default void setPID(double kp, double ki, double kd) {}

    default void setFF(double ks, double kv, double kg) {}

    default void stop() {}
}
