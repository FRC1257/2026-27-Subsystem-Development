import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;
import static edu.wpi.first.units.TemperatureUnit;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure.AngularVelocity;
import frc.robot.Constants;


public interface RollerIO {


    @AutoLog 
    public static class RollerIOInputs {
        public boolean rollerConnected = false;
        public Voltage rollerVoltage = Volts.of(0.0);
        public AngularVelocity rollerVelocity = RadiansPerSecond.of(0.0);
        public Temperature rollerTemperature = Celsius.of(0.0);
    }

    public default void updateInputs(RollerIOInputs inputs) {}
    
    public default void setVoltage(Voltage volts) {}

    public default void setVelocity(AngularVelocity velocity) {}

    public default void stop() {}

    public default void setPID(double kP, double kI, double kD) {}
    
    public default void setFF(double kS, double kV, double kA) {}

    public default void setBreakMode(boolean enabled) {}







}