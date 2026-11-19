//Ning
package frc.robot.subsystems.Elevator;

import static org.wpilib.units.Units.Meters;
import static org.wpilib.units.Units.MetersPerSecond;
import static org.wpilib.units.Units.Volts;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.NeedsNameBuilderStage;
import org.wpilib.command3.Scheduler;
import org.wpilib.command3.Trigger;
import org.wpilib.math.trajectory.TrapezoidProfile;
import org.wpilib.units.measure.Distance;
import org.wpilib.units.measure.LinearVelocity;
import org.wpilib.units.measure.Voltage;

import frc.robot.subsystems.Elevator.ElevatorIO.ElevatorIOInputs;

public class Elevator extends Mechanism {

    private final ElevatorIO io;
    private final ElevatorIOInputs inputs = new ElevatorIOInputs();
    private static final Distance HEIGHT_1 = Meters.of(ElevatorConstants.HEIGHT_1);
    private static final Distance HEIGHT_2 = Meters.of(ElevatorConstants.HEIGHT_2);

    private final TrapezoidProfile profile = new TrapezoidProfile(
            new TrapezoidProfile.Constraints(
                    ElevatorConstants.MAX_VELOCITY,
                    ElevatorConstants.MAX_ACCELERATION));

    private TrapezoidProfile.State setpoint = new TrapezoidProfile.State();
    private Distance goalPosition = Meters.zero();

    public Elevator(ElevatorIO io) {
        this.io = io;

        Scheduler.getDefault().addPeriodic(() -> {
            io.updateInputs(inputs);
        });
        outsideHeightRange().onTrue(stop()); // stop when outside of range
    }

    public Trigger outsideHeightRange() {
        return new Trigger(() -> {
            Distance position = inputs.leaderPositionMeters;
            return position.lt(HEIGHT_1)
                    || position.gt(HEIGHT_2);
        });
    }

    private NeedsNameBuilderStage runVoltageCommand(Supplier<Voltage> volts) {
        return runRepeatedly(() -> io.setVoltage(volts.get()));
    }

    private NeedsNameBuilderStage runPositionCommand(Supplier<Distance> position) {
        return runRepeatedly(() -> {
            goalPosition = position.get();
            setpoint = profile.calculate(0.02, setpoint, new TrapezoidProfile.State(goalPosition.in(Meters), 0.0));
            io.setPosition(Meters.of(setpoint.position));
        });
    }

    public Command goToHeight1() {
        return runPositionCommand(() -> ElevatorConstants.HEIGHT_1)
                .named("Elevator/Height1");
    }

    public Command goToHeight2() {
        return runPositionCommand(() -> ElevatorConstants.HEIGHT_2)
                .named("Elevator/Height2");
    }

    public Command moveTo(Distance position) {
        return runProfiledCommand(() -> position)
                .named("Elevator/MoveTo");
    }

    public Command holdPosition() {
        return runPositionCommand(() -> goalPosition)
            .named("Elevator/HoldPosition");
    }

    public void stop() {
        io.stop();
    }
}
