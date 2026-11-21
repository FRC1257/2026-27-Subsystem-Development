package frc.robot.Mechanisms.Elevator;

import static org.wpilib.units.Units.FeetPerSecond;
import java.util.function.Supplier;
import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.NeedsNameBuilderStage;
import org.wpilib.command3.Scheduler;
import org.wpilib.command3.Trigger;
import org.wpilib.math.filter.Debouncer;
import org.wpilib.math.filter.Debouncer.DebounceType;
import org.wpilib.units.measure.LinearVelocity;
import org.wpilib.units.measure.Voltage;

import frc.robot.Mechanisms.Elevator.ElevatorIO.ElevatorIOInputs;

public class Elevator extends Mechanism {
    
    private final ElevatorIO io;
    private ElevatorIOInputs inputs = new ElevatorIOInputs();
    //Holy spelling errors
    private TrapezoidalProfile profile;
    private TrapezoidProfile.State goal = new TrapezoidProfile.State();
    private LinearVelocity goalVelocity = FeetPerSecond.zero(); //why feet per second

    public Elevator(ElevatorIO io) {
        this.io = io;
        
        Scheduler.getDefault().addPeriodic(() -> {
            io.updateInputs(inputs);
        });
    }

    public Trigger atGoalVelocity() {
        return new Trigger(() -> inputs.leaderVelocityRadsPerSec.isNear(goalVelocity, ElevatorConstants.VELOCITY_TOLERANCE));
    }

    public Trigger isJammed() {
        return new Trigger(
            () -> inputs.leaderVelocityRadsPerSec.lte(ElevatorConstants.ELEVATOR_JAMMED_VELOCITY) 
            && inputs.leaderCurrentAmps.gte(ElevatorConstants.ELEVATOR_JAMMED_CURRENT)
        ).debounce(ElevatorConstants.ELEVATOR_JAMMED_TIME, DebounceType.kRising);
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

    public Command runEject() {
        return runVelocityCommand(() -> ElevatorConstants.EJECT_VELOCITY)
                .named("Elevator/Eject");
    }

    public Command runIntake() {
        return runVelocityCommand(() -> ElevatorConstants.INTAKE_VELOCITY)
                .named("Elevator/Intake");
    }

    public Command runIdle() {
        return runVelocityCommand(() -> ElevatorConstants.IDLE_VELOCITY)
                .named("Eleavtor/Idle");
    }

    public Command runUnjam() {
        return runVelocityCommand(() -> ElevatorConstants.UNJAM_VELOCITY)
                .until(isJammed().negate())
                .withPriority(Command.HIGHEST_PRIORITY)
                .named("Elevator/Unjam");
    }
        
}

// Zafar did this becayse he works hard