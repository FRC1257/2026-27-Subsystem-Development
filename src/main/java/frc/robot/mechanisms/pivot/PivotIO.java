package frc.robot.subsystems.pivot;

import static org.wpilib.units.Units.Amps;
import static org.wpilib.units.Units.Celsius;
import static org.wpilib.units.Units.Radians;
import static org.wpilib.units.Units.RadiansPerSecond;
import static org.wpilib.units.Units.Volts;

import org.littletonrobotics.junction.Autolog;

import org.wpilib.units.measure.Angle;
import org.wpilib.units.measure.AngularVelocity;
import org.wpilib.units.measure.Current;
import org.wpilib.units.measure.Temperature;
import org.wpilib.units.measure.Voltage;

public interface PivotIO {
    
    @Autolog 
    public static class PivotIOInputs {
       public boolean PivotConnected=false; 
       public Voltage PivotVoltage= Volts.of(0.0);
       public AngularVelocity PivotVelocity = RadiansPerSecond.of(0.0);
       public Current PivotCurrent = Amps.of(0.0); 
       public Temperature PivotTemperature = Celsius.of(0.0);
    } 

    public default void updateInputs(PivotIOInputs inputs){}
    
    public default void setVoltage(Voltage voltage){}

    public default void setAngle(Angle angle){}

    public default void stop(){} 
    
    public default void setPID(double kP, double kI, double kD){} 
    
    public default void setFF(double kS, double kV){}
    
}
