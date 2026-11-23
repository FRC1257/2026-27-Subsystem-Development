//William Zhang made this


package frc.robot.subsystems.PIDRoller;


import static org.wpilib.units.Units.Amps;
import static org.wpilib.units.Units.Volts;
import static org.wpilib.units.Units.Celsius;
import static org.wpilib.units.Units.RadiansPerSecond;


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

    public Command runVoltage(Supplier<Voltage> voltage) {
        return run(() -> io.setVoltage(UnitUtil.clamp(voltage.get(), Volts.of(-12.0), Volts.of(12.0))));
    }
   
    public Command runVelocity

    public Command runPIDRoller() {
        return runVoltage(() -> PIDRollerConstants.PIDROLLER_VOLTAGE).withName("PIDRoller/ON");
    }

    public Command runVelocity() {
        return runVelocityCommand(() -> AngularVelocity.of(0));
                .named("/PIDRoller/RunVelocity");
    }

    public Command stopPIDRoller() {
        return runVoltage(() -> Volts.of(0.0)).withName("PIDRoller/OFF");
    }
   public Trigger atGoalVelocity() {
		return new Trigger(() -> inputs.leaderVelocityRadsPerSec.isNear(goalVelocity, PIDRollerConstants.VELOCITY_TOLERANCE));
    }

    public Trigger isJammed() {
	    return new Trigger(
		    () -> inputs.leaderVelocityRadsPerSec.lte(PIDROLLER_JAMMED_VELOCITY)
		    && inputs.leaderCurrentAmps.gte(PIDRollerConstants.PIDROLLER_JAMMED_CURRENT)
	    ).debounce(PIDRollerConstants.PIDROLLER_JAMMED_TIME, DebounceType.kRising);
    }
// William Zhang made this