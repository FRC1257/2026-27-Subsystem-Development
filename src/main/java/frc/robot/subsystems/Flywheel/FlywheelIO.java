package frc.robot.subsystems.Flywheel;
import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;
import org.littletonrobotics.junction.AutoLog;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
public interface FlywheelIO{
@AutoLog
public static class FlywheelIOInputs {
public boolean leaderConnected = false;
public boolean followerConnected = false;

public Voltage flyWheelVoltage = Volts.of(0.0);
// public Voltage leaderVoltage = Volts.of(0.0);
// public Voltage followerVoltage = Volts.of(0.0);
//commented out assuming that the follower is set to follow the leader

public AngularVelocity flyWheelVelocity =  RadiansPerSecond.of(0.0);
// public AngularVelocity leaderVelocity =  RadiansPerSecond.of (0.0);
// public AngularVelocity followerVelocity =  RadiansPerSecond.of (0.0);
//commented out assuming that the follower is set to follow the leader

public Current leaderCurrent = Amps.of(0.0);
public Current followerCurrent = Amps.of(0.0);

public Temperature leaderTemperature = Celsius.of(0.0);
public Temperature followerTemperature = Celsius.of(0.0);
}


default void updateInputs (FlywheelIOInputs inputs) {}

default void setVoltage (Voltage voltage) {}

default void setVelocity (AngularVelocity angularVelocity) {}

default void stop() {}

default void setPID (double kP, double kI, double kD) {}

default void setFF(double kS, double kV) {}

default void setBrakeMode (boolean enabled){}



}