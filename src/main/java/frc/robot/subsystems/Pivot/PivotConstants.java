package frc.robot.subsystems.Pivot;


import static org.wpilib.units.Units.RadiansPerSecondPerSecond;


import org.wpilib.units.measure.Angle;
import org.wpilib.units.measure.AngularAcceleration;
import org.wpilib.units.measure.AngularVelocity;
import static org.wpilib.units.Units.Radians;
import static org.wpilib.units.Units.RadiansPerSecond;


public class PivotConstants {
    public static final AngularVelocity MAX_ANGULAR_VELOCITY = RadiansPerSecond.of(3.0);
    public static final AngularAcceleration MAX_ANGULAR_ACCELERATION = RadiansPerSecondPerSecond.of(8.0);
    public static final Angle ANGLE1 = Radians.of(1.6);
    public static final Angle ANGLE2 = Radians.of(0.0);




}


