/*
THIS FILE IS MADE BY WILLIAM
EMPTY SPACE
EMPTY SPACE
MORE EMPTY SPACE

THAT WAS EMPTY SPACE
*/

package frc.robot.mechanisms.climb;

import static org.wpilib.units.Units.RadiansPerSecond;

import java.util.function.Supplier;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.Coroutine;
import org.wpilib.command3.NeedsNameBuilderStage;
import org.wpilib.math.trajectory.TrapezoidProfile;
import org.wpilib.command3.Scheduler;
import org.wpilib.command3.Trigger;
import org.wpilib.math.filter.Debouncer;
import org.wpilib.math.filter.Debouncer.DebounceType;
import org.wpilib.units.measure.AngularVelocity;
import org.wpilib.units.measure.Voltage;

import frc.robot.util.Units.UnitUtil;
import frc.robot.mechanisms.climb.ClimbIO.ClimbIOInputs;

public class Climb extends Mechanism {
    private final ClimbIO io;
    private final ClimbIOInputs inputs = new ClimbIOInputs();
    
    private final TrapezoidProfile profile;
    private final TrapezoidProfile.State goal = new TrapezoidProfile.State();
    private final TrapezoidProfile.State setpoint = null;

    public Climb(ClimbIO io) {
        this.io = io;

        Scheduler.getDefault.addPeriodic(() -> {
            io.updateInputs(inputs);
        });
    }

    public Trigger atGoalPosition() {
        return new Trigger(() -> inputs.climbDistance.isnear(goalDistance, ClimbConstants.DISTANCE_TOLERANCE));
    }

    private NeedsNameBuilderStage runVoltageCommand(Supplier<Voltage> volts) {
        return runRepeatedly(() -> io.setVoltage(volts.get()));
    }

    private NeedsNameBuilderStage runPositionCommand(Supplier<Distance> meters) {
        return run(co -> {
            setpoint = new TrapezoidProfile.State(inputs.climbDistance.magnitude(), inputs.climbVelocity.magnitude);
            while (true) {
                goalDistance = UnitsUtil.clamp(meters.get, ClimbConstants.MIN_DISTANCE, ClimbConstants.MAX_DISTANCE);
                goal = new TrapezoidProfile.State(goalDistance.magnitude(), 0.0);
                setpoint = profile.calculate(0.02, setpoint, goal);
                io.setPosition(Meters.of(setpoint.Position), RadiansPerSecond.of(setpoint.velocity));
                co.yield();
            }
        })
    }

    public Command runPositionZero() {
        return runPositionCommand(() -> ClimbConstants.POSITION_ZERO)
            .named("Climb/Position Zero");
    }

    public Command runPositionOne() {
        return runPositionCommand(() -> ClimbConstants.POSITION_ONE)
            .named("Climb/Position One");
    }


}
