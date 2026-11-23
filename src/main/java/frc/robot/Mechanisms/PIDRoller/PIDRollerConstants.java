package frc.robot.Mechanisms.PIDRoller;

import org.wpilib.units.measure.Voltage;
import org.wpilib.units.measure.AngularVelocity;
import org.wpilib.units.measure.Current;
import org.wpilib.units.measure.Time;
import static org.wpilib.units.Units.Volts;
import static org.wpilib.units.Units.Amps;
import static org.wpilib.units.Units.Seconds;
import static org.wpilib.units.Units.RadiansPerSecond;//maybe rpm
// made by caitlin, please give 100

public class PIDRollerConstants {
    //random constants for PIDRoller
    public static final Voltage INTAKE_VOLTAGE = Volts.of(5.0);
    public static final Voltage EJECT_VOLTAGE = Volts.of(-5.0);
    public static final Voltage IDLE_VOLTAGE = Volts.of(0.0);
    public static final AngularVelocity PIDROLLER_JAMMED_VELOCITY = RadiansPerSecond.of(10.0);
    public static final Current PIDROLLER_JAMMED_CURRENT = Amps.of(20.0);
    public static final Time PIDROLLER_JAMMED_TIME = Seconds.of(1.0);
}


