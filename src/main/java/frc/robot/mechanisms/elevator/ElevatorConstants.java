package frc.robot.mechanisms.elevator;
import org.wpilib.units.measure.Distance;
import static org.wpilib.units.Units.Meters;
import org.wpilib.units.measure.LinearVelocity;
import org.wpilib.units.measure.LinearAcceleration;
import static org.wpilib.units.Units.MetersPerSecondPerSecond;
import static org.wpilib.units.Units.MetersPerSecond;



public class ElevatorConstants {
    public static Distance pos1Height=Meters.of(0.5);
    public static Distance pos2Height=Meters.of(1.0);

    public static LinearAcceleration maxAccel=MetersPerSecondPerSecond.of(10);
    public static LinearVelocity maxVel=MetersPerSecond.of(10); //idk what to use as values

}
