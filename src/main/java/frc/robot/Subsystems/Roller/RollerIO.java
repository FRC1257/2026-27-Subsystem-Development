package frc.robot.Subsystems.Roller;

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
    boolean RollerConnected = false;
    Voltage RollerVoltage = Volts.of(0.0);
    AngularVelocity RollerVelocity = RadiansPerSecond.of(0.0); 
    Current RollerCurrent = Amps.of(0.0);
  }

  default void updateInputs(RollerIOInputs inputs) {}

  default void runVoltage(Voltage volts) {} 

  default void runVelocity(double velocity) {}

  default void setPID(double kp, double ki, double kd) {}

  default void setFF(double ks, double kv, double kg) {}

  default void stop() {}

  default void setBrakeMode(boolean enabled) {}
}