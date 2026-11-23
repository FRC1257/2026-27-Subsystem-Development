//made by Caitlin
package frc.robot.Mechanisms.Elevator;

import static org.wpilib.units.Units.Amps;
import static org.wpilib.units.Units.Celsius;
import static org.wpilib.units.Units.Radians;
import static org.wpilib.units.Units.RadiansPerSecond;
import static org.wpilib.units.Units.Volts;

import org.wpilib.units.measure.Angle;
import org.wpilib.units.measure.AngularVelocity;
import org.wpilib.units.measure.Current;
import org.wpilib.units.measure.Temperature;
import org.wpilib.units.measure.Voltage;

public interface ElevatorIO {
    
    public static class ElevatorIOInputs {
        public boolean leaderConnected = false;
        public Angle leaderAngleRads = Radians.zero();
        public AngularVelocity leaderVelocityRadsPerSec = RadiansPerSecond.zero();
        public Voltage leaderAppliedVolts = Volts.zero();
        public Current leaderCurrentAmps = Amps.zero();
        public Temperature leaderTemperatureCelsius = Celsius.zero();

        public boolean followerConnected = false;
        public Angle followerAngleRads = Radians.zero();
        public AngularVelocity followerVelocityRadsPerSec = RadiansPerSecond.zero();
        public Voltage followerAppliedVolts = Volts.zero();
        public Current followerCurrentAmps = Amps.zero();
        public Temperature followerTemperatureCelsius = Celsius.zero();
        
        public double setpointMeters = 0.0;
    }

    public default void updateInputs(ElevatorIOInputs inputs) {}

    public default void setSetpoint(double setpoint) {}

    public default void goToSetpoint() {}

    public default void setVoltage(Voltage volts) {}

    public default void setAngle(Angle angle, AngularVelocity velocity) {}

    public default void setPID(double kP, double kI, double kD) {}

    public default void setFF(double kS, double kG, double kV, double kA) {}

    public default void setBrakeMode(boolean brakeEnabled) {}


    public default void stop() {}
}
