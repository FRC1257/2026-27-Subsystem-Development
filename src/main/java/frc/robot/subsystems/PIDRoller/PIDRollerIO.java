package frc.robot.subsystems.PIDRoller;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;
import static edu.wpi.first.units.Units.Celsius;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure.Temperature;
                                                                                                                                        
public interface PIDRollerIO {

    @AutoLog
    public static class PIDRollerIOInputs { 
        public boolean firstMotorConnected = false;
        public Voltage firstMotorVoltage = Volts.of(0.0);
        public AngularVelocity firstMotorAngularVelocity = RadiansPerSecond.of(0.0);
        public Current firstMotorCurrent = Amps.of(0.0);
        public Angle firstMotorAngle = Radians.of(0.0);
        public Temperature firstMotorTemperature = Celsius.of(0.0);
       
        public boolean secondMotorConnected = false;
        public Voltage secondMotorVoltage = Volts.of(0.0);
        public AngularVelocity secondMotorAngularVelocity = RadiansPerSecond.of(0.0);
        public Current secondMotorCurrent = Amps.of(0.0);
        public Angle secondMotorAngle = Radians.of(0.0);
        public Temperature secondMotorTemperature = Celsius.of(0.0);
    }

    default void updateInputs(PIDRollerIOInputs inputs) {}

    default void runVoltage(Voltage volts) {}

    default void setVelocity(AngularVelocity velocity) {}

    default void setPID(double kp, double ki, double kd) {}

    default void setFF(double ks, double kv) {}

    default void stop() {}
}

