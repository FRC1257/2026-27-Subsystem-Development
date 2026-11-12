package frc.robot.subsystems.Roller;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;
import static edu.wpi.first.units.Units.Celsius;

import org.littletonrobotics.junction.AutoLog;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
//import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

public interface RollerIO {

  @AutoLog
  public static class RollerIOInputs {
public boolean rollerConnected = true;

public Voltage rollerVoltage = Volts.of(0.0);

public AngularVelocity rollerVelocity =  RadiansPerSecond.of(0.0);

public Current rollerCurrent = Amps.of(0.0);

//public Temperature rollerTemperature = Celsius.of(0.0);
}

  default void updateInputs(RollerIOInputs inputs) {}

  default void runVoltage(Voltage volts) {}

  default void setPID(double kp, double ki, double kd) {}

  default void setFF(double ks, double kv, double ka) {}

  default void stop() {}

  //setbrakemode?

  default void setVelocity(AngularVelocity angularVelocity) {}


}