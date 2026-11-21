//William Zhang made this


package frc.robot.subsystems.PIDRoller;


import static org.wpilib.units.Units.Amps;
import static org.wpilib.units.Units.Volts;
import static org.wpilib.units.Units.Celsius;


import java.util.function.Supplier;


import org.littletonrobotics.junction.Logger;


import org.wpilib.math.filter.Debouncer;
import org.wpilib.math.filter.Debouncer.DebounceType;
import org.wpilib.units.measure.Voltage;
import org.wpilib.units.measure.Current;
import org.wpilib.units.measure.Temperature;
import org.wpilib.util.Alert;
import org.wpilib.util.Alert.Level;
import org.wpilib.command2.Command;
import org.wpilib.command2.SubsystemBase;
import frc.robot.Robot;
import frc.robot.util.Units.UnitUtil;


public class PIDRoller extends SubsystemBase {


    private final PIDRollerIO io;
    private PIDRollerIOInputsAutoLogged inputs = new PIDRollerIOInputsAutoLogged();


    private final Debouncer connectedDebouncer =
        new Debouncer(0.5, DebounceType.kFalling);
    private final Alert disconnected;




    public PIDRoller(PIDRollerIO io) {
        this.io = io;


        disconnected = new Alert("PID ROLLER MOTOR DISCONNECTED", Level.HIGH);
    }


    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs(getName(), inputs);


        disconnected.set(!connectedDebouncer.calculate(inputs.PIDRollerConnected));


        Robot.batteryLogger.reportCurrentUsage(
            getName(),
            inputs.PIDRollerCurrent.in(Amps)
        );
    }


    private Command runVoltage(Supplier<Voltage> voltage) {
        return run(() -> io.setVoltage(UnitUtil.clamp(voltage.get(), Volts.of(-12.0), Volts.of(12.0))));
    }
   
    public Command runPIDRoller() {
        return runVoltage(() -> PIDRollerConstants.PIDROLLER_VOLTAGE).withName("PIDRoller/ON");
    }


    public Command stopPIDRoller() {
        return runVoltage(() -> Volts.of(0.0)).withName("PIDRoller/OFF");
    }
   
}
THis tooo
// William Zhang made this


package frc.robot.Mechanisms.PIDRoller;


import static org.wpilib.units.Units.Amps;
import static org.wpilib.units.Units.Celsius;
import static org.wpilib.units.Units.RadiansPerSecond;
import static org.wpilib.units.Units.Volts;


import org.littletonrobotics.junction.AutoLog;


import org.wpilib.units.measure.AngularVelocity;
import org.wpilib.units.measure.Current;
import org.wpilib.units.measure.Temperature;
import org.wpilib.units.measure.Voltage;


public interface PIDRollerIO {


    @AutoLog
    public static class PIDRollerIOInputs {
        public boolean PIDRollerConnected = false;
        public Voltage PIDRollerVoltage = Volts.of(0.0);
        public AngularVelocity PIDRollerAngularVelocity = RadiansPerSecond.of(0.0);
        public Current PIDRollerCurrent = Amps.of(0.0);
        public Temperature PIDRollerTemperature = Celsius.of(0.0);
    }


    default void updateInputs(PIDRollerIOInputs inputs) {}


    default void setVoltage(Voltage voltage) {}
   
    default void stop() {}
   
    default void setBreakMode(boolean enabled) {}
}