package frc.robot.subsystems.Pivot.PivotIO;


import static org.wpilib.units.Units.Amps;
import static org.wpilib.units.Units.Celsius;
import static org.wpilib.units.Units.RadiansPerSecond;
import static org.wpilib.units.Units.Volts;


import org.littletonrobotics.junction.AutoLog;


import org.wpilib.units.measure.AngularVelocity;
import org.wpilib.units.measure.Current;
import org.wpilib.units.measure.Temperature;
import org.wpilib.units.measure.Voltage;


public interface PivotIO {


   @AutoLog
   public static class PivotIOInputs {
     public boolean pivotConnected = false;
     public Voltage pivotVoltage = Volts.of(0.0);
     public AngularVelocity pivotVelocity = RadiansPerSecond.of(0.0);
     public Current pivotCurrent = Amps.of(0.0);
     public Temperature pivotTemperature = Celsius.of(0.0);


     public boolean pivotFollowerConnected = false;
     public Voltage pivotFollowerVoltage = Volts.of(0.0);
     public AngularVelocity pivotFollowerVelocity = RadiansPerSecond.of(0.0);
     public Current pivotFollowerCurrent = Amps.of(0.0);
     public Temperature pivotFollowerTemperature = Celsius.of(0.0);


 }


 public default void updateInputs(PivotIOInputs inputs) {}


 public default void setVoltage(Voltage voltage) {}
  public default void stop() {}
}



