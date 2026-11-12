package frc.robot.subsystems.RollerSystem;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;
import static edu.wpi.first.units.Units.Celsius;

import org.littletonrobotics.junction.AutoLog;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

public interface RollerSystemIO {
   
   @AutoLog
    public class RollerSystemIOInputs {
     
        public Voltage RollerSystemVoltage = Volts.of(0.0);
       
        public AngularVelocity RollerSystemAngularVelocity = RadiansPerSecond.of(0);
        
        public Temperature RollerSystemTemperature = Celsius.of(0.0);

        public Angle motorAngle = Radians.of(0.0)

        public Current motorCurrent = Amps.of(0.0)

    }

    public void updateInputs(RollerSystemIOInputs inputs) {}
    
    public void setVelocity(RollerSystemAngularVelocity velocityRadsPerSec)
   
    public void setVoltage(Voltage voltage) {}
    
    public void setPID(double kp, double ki, double kd) {}

    public void setFF(double ks, double kv, double ka, double kg) {}
    
    public void stop{} {}

}