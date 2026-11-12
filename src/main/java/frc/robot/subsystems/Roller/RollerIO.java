package frc.robot.subsystems.Roller;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;
import static edu.wpi.first.units.Units.Volts;
import static edu.wpi.first.units.Units.RadiansPerSecond;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
//import frc.robot.subsystems.Roller.RollerIO.RollerIOInputs;

public interface RollerIO {
        public static class RollerIOInputs {
        public boolean rollerConnected = false;
        public AngularVelocity rollerAngularVelocity = RadiansPerSecond.of(0.0);
        public Voltage rollerVoltage = Volts.of(0.0);
        public Current rollerCurrent = Amps.of(0.0);
        public Temperature rollerTemperature = Celsius.of(0.0);
    
    }

    public default void updateInputs(RollerIOInputs inputs) {}

    public default void setVelocity(AngularVelocity velocityRadsPerSec) {}

    public default void setVoltage(Voltage voltage) {}

    public default void runVoltage(Voltage volts) {}
    public default void stop() {}

    public default void setPID(double kp, double ki, double kd) {}

    public default void setFeedforward(double ks, double kv) {}
}

//Over the past two-three meetings, we’ve covered IO files, hardware implementations, 
//and have worked through all the various mannerisms you must follow when programming 
//on a repository on your personal computer. We’re gonna put that to the test now.
//You are going to make a roller subsystem. This is a check for understanding of the IO 
//file and the hardware implementation parts of a subsystem. You will have a timed one 
//hour to code and are required to push whatever you have when the time is up. Unless 
//there are technical difficulties that I have been made aware of beforehand, any issues 
//with pushing code will result in a 0.
