package frc.robot.subsystems.flywheel;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import org.littletonrobotics.junction.AutoLog;

import static edu.wpi.first.units.Units.*;

public interface FlyWheelIO {
    @AutoLog
    public static class FlyWheelIOInputs {
        public Voltage leftFlyWheelVoltage=Volts.of(0.0);
        public Voltage rightFlyWheelVoltage=Volts.of(0);

        public AngularVelocity rightFlyWheelVelocity=RadiansPerSecond.of(0);
        public AngularVelocity leftFlyWheelVelocity=RadiansPerSecond.of(0);

        public Temperature rightFlyWheelTemp=Celsius.of(0);
        public Temperature leftFlyWheelTemp=Celsius.of(0);

        public boolean rightConnected=false;
        public boolean leftConnected=false;
    }

    default void updateInputs(FlyWheelIOInputs inputs) {}

    default void runVoltage(Voltage volts) {}

    default void runVelocity(AngularVelocity velocity) {}

    default void setPID(double kp, double ki, double kd) {}

    default void setFF(double ks, double kv, double kg) {}

    default void stop() {}
}
