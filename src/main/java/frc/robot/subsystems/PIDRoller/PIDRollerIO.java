// MADE BY LOGAN CORTER

package frc.robot.subsystems.PIDroller;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;
import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

public interface PIDrollerIO{

    @AutoLog
    public static class PIDrollerIOInputs {
     
        public boolean PIDrollerConnected = false;
        public Angle PIDrollerLeaderAngle = Radians.of(0.0);
        public AngularVelocity PIDrollerLeaderAngularVelocity = RadiansPerSecond.of(0.0);
        public Voltage PIDrollerLeaderVoltage = Volts.of(0.0);
        public Current PIDrollerLeaderCurrent = Amps.of(0.0);
        public Temperature PIDrollerLeaderTemperature = Celsius.of(0.0);
   
       
        public boolean PIDrollerFollowerConnected = false;
        public Angle PIDrollerFollowerAngle = Radians.of(0.0);
        public AngularVelocity PIDrollerFollowerAngularVelocity = RadiansPerSecond.of(0.0);
        public Voltage PIDrollerFollowerVoltage = Volts.of(0.0);
        public Current PIDrollerFollowerCurrent = Amps.of(0.0);
        public Temperature PIDrollerFollowerTemperature = Celsius.of(0.0);


    }

   
    public default void updateInputs(PIDrollerIOInputs inputs) {}
    public default void setVelocity(AngularVelocity velocityRadsPerSec) {}
    public default void setVoltage(Voltage volts) {}
    public default void stop() {}

    public default void setPID(double kp, double ki, double kd) {}

    public default void setFF(double ks, double kv) {}


}
