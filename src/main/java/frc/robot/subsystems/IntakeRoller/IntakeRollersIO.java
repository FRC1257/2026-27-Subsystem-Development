//sam's code
package frc.robot.subsystems.IntakeRoller;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

public interface IntakeRollersIO {
    
    public static class IntakeRollersIOInputs {
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
    }

    public default void updateInputs(IntakeRollersIOInputs inputs) {}

    public default void setVoltage(Voltage volts) {}

    public default void stop() {}
}