package frc.robot.subsystems.Roller;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;



public interface RollerIO {
    
    @AutoLog 
    public static class RollerIOInputs {
        public boolean rollerConnected = false;
        public Voltage rollerVoltage = Volts.of(0.0);
        public AngularVelocity rollerVelocity = RadiansPerSecond.of(0.0);
        public Current rollerCurrent = Amps.of(0.0);
        public Angle rollerAngle = Radians.of(0.0);
    }

    public default void updateInputs(RollerIOInputs inputs) {}
    
    public default void runVoltage(Voltage volts) {}

    public default void runVelocity(AngularVelocity velocity) {}

    public default void stop() {}

    public default void setPID(double kP, double kI, double kD) {}
    
    public default void setFF(double kS, double kV, double kA) {}

    public default void setBrakeMode(boolean enabled) {}

    public default void runAngle(double angle) {}







}