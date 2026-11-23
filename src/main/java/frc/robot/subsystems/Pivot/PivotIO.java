package frc.robot.subsystems.Pivot;
//Arman
import static org.wpilib.units.Units.Amps;
import static org.wpilib.units.Units.Celsius;
import static org.wpilib.units.Units.Radians;
import static org.wpilib.units.Units.RadiansPerSecond;
import static org.wpilib.units.Units.Volts;


import org.littletonrobotics.junction.AutoLog;
import org.wpilib.units.measure.Angle;
import org.wpilib.units.measure.AngularVelocity;
import org.wpilib.units.measure.Current;
import org.wpilib.units.measure.Temperature;
import org.wpilib.units.measure.Voltage;


public interface PivotIO {

  @AutoLog
  public static class PivotIOInputs {
    public boolean leftPivotConnected = false;
    public Voltage leftPivotVoltage = Volts.zero();
    public Angle leftPivotAngle = Radians.zero();
    public AngularVelocity leftPivotVelocity = RadiansPerSecond.zero();
    public Current leftPivotCurrent = Amps.zero();
    public Temperature leftPivotTemperature = Celsius.zero();

    public boolean rightPivotConnected = false;
    public Voltage rightPivotVoltage = Volts.zero();
    public Angle rightPivotAngle = Radians.zero();
    public AngularVelocity rightPivotVelocity = RadiansPerSecond.zero();
    public Current rightPivotCurrent = Amps.zero();
    public Temperature rightPivotTemperature = Celsius.zero();
  }

  default void updateInputs(PivotIOInputs inputs) {}
  
  default void setVoltage(Voltage volts) {}

  default void setAngle(Angle angle, AngularVelocity velocity) {}

  default void setPID(double kp, double ki, double kd) {}

  default void setFF(double ks, double kv, double kg) {}

  default void stop() {}
}

