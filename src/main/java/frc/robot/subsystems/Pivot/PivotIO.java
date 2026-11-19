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
    public boolean leftpivotConnected = false;
    public Voltage leftpivotVoltage = Volts.of(0.0);
    public Angle leftpivotAngle = Radians.of(0.0);
    public AngularVelocity leftpivotVelocity = RadiansPerSecond.of(0.0);
    public Current leftpivotCurrent = Amps.of(0.0);
    public Temperature leftTemperature = Celsius.of(0.0);


    public boolean rightpivotConnected = false;
    public Voltage rightpivotVoltage = Volts.of(0.0);
    public Angle rightpivotAngle = Radians.of(0.0);
    public AngularVelocity rightpivotVelocity = RadiansPerSecond.of(0.0);
    public Current rightpivotCurrent = Amps.of(0.0);
    public Temperature rightTemperature = Celsius.of(0.0);
  }


  default void updateInputs(PivotIOInputs inputs) {}


  default void runVoltage(Voltage volts) {}


  default void runAngle(double angle, double velocity) {}


  default void setPID(double kp, double ki, double kd) {}


  default void setFF(double ks, double kv, double kg) {}


  default void stop() {}


  default void setVoltage(Voltage volts) {}
 
  default void setAngle(Angle angle, AngularVelocity velocity) {}


}
