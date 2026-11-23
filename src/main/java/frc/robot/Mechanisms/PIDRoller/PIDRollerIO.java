package frc.robot.Mechanisms.PIDRoller;
//made by caitlin, please give 100
import static org.wpilib.units.Units.Amps;
import static org.wpilib.units.Units.Celsius;
import static org.wpilib.units.Units.Radians;
import static org.wpilib.units.Units.RadiansPerSecond;
import static org.wpilib.units.Units.Volts;


import org.wpilib.units.measure.Angle;
import org.wpilib.units.measure.AngularVelocity;
import org.wpilib.units.measure.Current;
import org.wpilib.units.measure.Temperature;
import org.wpilib.units.measure.Voltage;


public interface PIDRollerIO {
   
    public static class PIDRollerIOInputs {
        public boolean motorConnected = false;
        public Angle motorAngleRads = Radians.zero();
        public AngularVelocity motorVelocityRadsPerSec = RadiansPerSecond.zero();
        public Voltage motorAppliedVolts = Volts.zero();
        public Current motorCurrentAmps = Amps.zero();
        public Temperature motorTemperatureCelsius = Celsius.zero();
    }

    public default void updateInputs(PIDRollerIOInputs inputs) {}

    public default void setVoltage(Voltage voltage) {}

    public default void setVelocity(AngularVelocity velocityRadsPerSec) {}

    public default void setPID(double kp, double ki, double kd) {}

    public default void setFF(double ks, double kv) {}

    public default void stop() {}
}

