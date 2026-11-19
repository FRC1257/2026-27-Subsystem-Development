//RyanNg
package frc.robot.subsystems.PIDRoller;

import static org.wpilib.units.Units.Amps;
import static org.wpilib.units.Units.RadiansPerSecond;
import static org.wpilib.units.Units.Seconds;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Time;

public class PIDRollerConstants {
    public static final AngularVelocity VELOCITY_TOLERANCE = RadiansPerSecond.of(2);
    public static final AngularVelocity IDLE_VELOCITY = RadiansPerSecond.of(0);
    public static final AngularVelocity EJECT_VELOCITY = RadiansPerSecond.of(-10);
    public static final AngularVelocity INTAKE_VELOCITY = RadiansPerSecond.of(10);
    public static final AngularVelocity UNJAM_VELOCITY = RadiansPerSecond.of(-15);
    public static final AngularVelocity PIDROLLER_JAMMED_VELOCITY = RadiansPerSecond.of(10);
    public static final Current PIDROLLER_JAMMED_CURRENT = Amps.of(20);
    public static final Time PIDROLLER_JAMMED_TIME = Seconds.of(0.5);
}
