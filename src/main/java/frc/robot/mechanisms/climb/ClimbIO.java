package frc.robot.mechanisms.climb;

import static org.wpilib.units.Units.Amps;
import static org.wpilib.units.Units.Celsius;
import static org.wpilib.units.Units.Radians;
import static org.wpilib.units.Units.MetersPerSecond;
import static org.wpilib.units.Units.Volts;
import static edu.wpi.first.units.Units.Meters;

import org.wpilib.units.measure.Angle;
import org.wpilib.units.measure.LinearVelocity;
import org.wpilib.units.measure.Current;
import org.wpilib.units.measure.Temperature;
import org.wpilib.units.measure.Voltage;
import edu.wpi.first.units.measure.Distance;

public interface ClimbIO {

    @Autolog
    public class ClimbIOInputs {
        public boolean climbConnected = false;
        public Voltage climbVoltage = Volts.zero();
        public Angle climbAngle = Radians.zero();
        public Distance climbDistance = Meters.zero();
        public LinearVelocity climbVelocity = MetersPerSecond.zero();
        public Temperature climbTemperature = Celsius.zero();
    }

    public default void updateInputs(ClimbIOInputs inputs) {}

    public default void setVoltage(Voltage volts) {}

    public default void setVelocity(LinearVelocity velocity) {}
    
    public default void setPosition(Distance meters) {}

    public default void stop() {}
}
