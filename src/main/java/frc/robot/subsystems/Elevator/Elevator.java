package frc.robot.mechanisms.Elevator;

import static org.wpilib.units.Units.PerSecond;
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
import frc.robot.mechanisms.Elevator.ElevatorIO.ElevatorIOInputs;

public class Elevator extends Mechanism {
    
    private final ElevatorIO io;
    private ElevatorIOInputs inputs = new ElevatorIOInputs();

    private TrapezoisProfile profile;
    private TrapezoidProfile.State goal - new TrapezoidProfile.State();
    private LinearVelocity goalVelocity = MetersPerSecond.zero();

    public Elevator(ElevatorIO io) {
        this.io = io;
        
        Scheduler.getDefault().addPeriodic(() -> {
            io.updateInputs(inputs);
        });
    }

    public Trigger atGoalVelocity() {
        return new Trigger(() -> inputs.leaderVelocityRadsPerSec.isNear(goalVelocity, ElevatorConstants.VELOCITY_TOLERANCE));
    }

    private NeedsNameBuilderStage runVoltageCommand(Supplier<Voltage> volts) {
        return runRepeatedly(() -> io.setVoltage(volts.get()));
    }

    private NeedsNameBuilderStage runVelocityCommand(Supplier<LinearVelocity> velocity) {
        return runRepeatedly(() -> {
            goalVelocity = velocity.get();
            io.setVelocity(velocity.get());
        });
    }
    
    public Command runHeight1() {
        return runAngleCommand(() -> IntakePivotConstants.HEIGHT_1)
                .named("IntakePivot/IntakeAngle");
    }
    public Command runHeight2() {
        return runAngleCommand(() -> IntakePivotConstants.HEIGHT_2)
                .named("IntakePivot/StowAngle");
    }
}

// Zafar did this because he works hard

    
