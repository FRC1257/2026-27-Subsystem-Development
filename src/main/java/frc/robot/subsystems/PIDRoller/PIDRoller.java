//RyanNg
package frc.robot.mechanisms.PIDRoller;

import java.util.function.Supplier;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.NeedsNameBuilderStage;
import org.wpilib.command3.Scheduler;
import org.wpilib.units.measure.Voltage;
import org.wpilib.command3.Trigger;
import org.wpilib.math.filter.Debouncer;
import org.wpilib.math.filter.Debouncer.DebounceType;
import org.wpilib.units.measure.AngularVelocity;
import frc.robot.mechanisms.PIDRoller.PIDRollerIO.PIDRollerIOInputs;
import frc.robot.subsystems.Kicker.KickerConstants;
import static org.wpilib.units.Units.RadiansPerSecond;

public class PIDRoller extends Mechanism {
    private final PIDRollerIO io;
    private PIDRollerIOInputs inputs = new PIDRollerIOInputs();
    private AngularVelocity goalVelocity = RadiansPerSecond.of(0);
    public PIDRoller(PIDRollerIO io) {
        this.io = io;
        Scheduler.getDefault().addPeriodic(() -> {
            io.updateInputs(inputs);
        });
    }
    private NeedsNameBuilderStage runVoltageCommand(Supplier<Voltage> volts) {
        return runRepeatedly(() -> io.setVoltage(volts.get()));
    }
    private NeedsNameBuilderStage runVelocityCommand(Supplier<AngularVelocity> velocity) {
        return runRepeatedly(() -> {
            goalVelocity = velocity.get();
            io.setVelocity(velocity.get());
        });
    }
    public Trigger isJammed() {
        return new Trigger(
            () -> inputs.PIDRollerVelocity.lte(PIDRollerConstants.PIDROLLER_JAMMED_VELOCITY) 
            && inputs.PIDRollerCurrent.gte(PIDRollerConstants.PIDROLLER_JAMMED_CURRENT)
        ).debounce(PIDRollerConstants.PIDROLLER_JAMMED_TIME, DebounceType.kRising);
    }

    public Command runEject() {
        return runVelocityCommand(() -> PIDRollerConstants.EJECT_VELOCITY)
                .named("PIDRoller/Eject");
    }

    public Command runIntake() {
        return runVelocityCommand(() -> PIDRollerConstants.INTAKE_VELOCITY)
                .named("PIDRoller/Intake");
    }

    public Command runIdle() {
        return runVelocityCommand(() -> PIDRollerConstants.IDLE_VELOCITY)
                .named("PIDRoller/Idle");
    }

    public Command runUnjam() {
        return runVelocityCommand(() -> PIDRollerConstants.UNJAM_VELOCITY)
                .until(isJammed().negate())
                .withPriority(Command.HIGHEST_PRIORITY)
                .named("PIDRoller/Unjam");
    }
}
