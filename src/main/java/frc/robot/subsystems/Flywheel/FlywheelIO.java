package frc.robot.subsystems.Flywheel;
import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;
import org.littletonrobotics.junction.AutoLog;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

public interface FlywheelIO {
    @AutoLog
    public static class FlywheelIOInputs {
        public boolean flywheel1Connected = false;
        public Angle flywheel1Angle  = Radians.of(0.0);
        public AngularVelocity flywheel1AngularVelocity = RadiansPerSecond.of(0.0);
        public Voltage flywheel1Voltage = Volts.of(0.0);
        public Current flywheel1Current = Amps.of(0.0);
        public Temperature flywheel1Temp = Celsius.of(0.0);
        public boolean flywheel2Connected = false;
        public Angle flywheel2Angle  = Radians.of(0.0);
        public AngularVelocity flywheel2AngularVelocity = RadiansPerSecond.of(0.0);
        public Voltage flywheel2Voltage = Volts.of(0.0);
        public Current flywheel2Current = Amps.of(0.0);
        public Temperature flywheel2Temp = Celsius.of(0.0);
    }
    public default void updateInputs(FlywheelIOInputs inputs) {}

    public default void setVelocity(AngularVelocity velocityRadsPerSec) {}

    public default void setVoltage(Voltage voltage) {}

    public default void stop() {}

    public default void setPID(double kp, double ki, double kd) {}

    public default void setFF(double ks, double kv) {}
}
