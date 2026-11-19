//Ning
package frc.robot.subsystems.Elevator;

import static org.wpilib.units.Units.Amps;
import static org.wpilib.units.Units.Celsius;
import static org.wpilib.units.Units.Meters;
import static org.wpilib.units.Units.MetersPerSecond;
import static org.wpilib.units.Units.Volts;

import org.wpilib.units.measure.Current;
import org.wpilib.units.measure.Distance;
import org.wpilib.units.measure.LinearVelocity;
import org.wpilib.units.measure.Temperature;
import org.wpilib.units.measure.Voltage;

public interface ElevatorIO {

    public static class ElevatorIOInputs {
        public boolean leaderConnected = false;
        public Distance leaderPositionMeters = Meters.zero();
        public LinearVelocity leaderVelocityMetersPerSec = MetersPerSecond.zero();
        public Voltage leaderAppliedVolts = Volts.zero();
        public Current leaderCurrentAmps = Amps.zero();
        public Temperature leaderTemperatureCelsius = Celsius.zero();

        public boolean followerConnected = false;
        public Distance followerPositionMeters = Meters.zero();
        public LinearVelocity followerVelocityMetersPerSec = MetersPerSecond.zero();
        public Voltage followerAppliedVolts = Volts.zero();
        public Current followerCurrentAmps = Amps.zero();
        public Temperature followerTemperatureCelsius = Celsius.zero();
    }

    public default void updateInputs(ElevatorIOInputs inputs) {}

    public default void setPosition(Distance position) {}

    public default void setVoltage(Voltage volts) {}

    public default void stop() {}

    public default void resetPosition(Distance position) {}

    public default void setBrakeMode(boolean brakeEnabled) {}

    public default void setPID(double kP, double kI, double kD) {}

    public default void setFF(double kS, double kG, double kV, double kA) {}
}