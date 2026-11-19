package frc.robot.mechanisms.elevator;

import org.wpilib.units.measure.LinearVelocity;
import org.wpilib.units.measure.Voltage;
import org.wpilib.units.measure.Current;
import org.wpilib.units.measure.Temperature;
import org.wpilib.units.measure.Angle;
import org.wpilib.units.measure.AngularAcceleration;
import org.wpilib.units.measure.Distance;


import static org.wpilib.units.Units.MetersPerSecond;
import static org.wpilib.units.Units.Volts;
import static org.wpilib.units.Units.Amps;
import static org.wpilib.units.Units.Celsius;
import static org.wpilib.units.Units.Meters;



public interface ElevatorIO{
    public static class ElevatorIOInputs {
        public boolean leftConnected=false;
        public LinearVelocity leftVelocity=MetersPerSecond.of(0);
        public Voltage leftVoltage=Volts.of(0);
        public Current leftCurrent=Amps.of(0);
        public Distance leftAngle=Meters.of(0);
        public Temperature leftTemp=Celsius.of(0);
    }

    default void updateInputs(ElevatorIOInputs inputs) {}

    default void setAngle(Angle angle) {}

    default void stop() {}

    default void setMotionProfile(LinearVelocity velocity, AngularAcceleration accel) {}

    default void setPID(double kp, double ki, double kd) {}

    default void runMeters(Distance height) {}

    default Distance getHeight() {return Meters.of(0);}
    
}
