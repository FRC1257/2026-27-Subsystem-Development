
package frc.robot.mechanisms.Intake.IntakePivot;

import static org.wpilib.units.Units.Meters;


import java.util.function.Supplier;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.NeedsNameBuilderStage;
import org.wpilib.command3.Scheduler;
import org.wpilib.command3.Trigger;

import org.wpilib.math.trajectory.TrapezoidProfile;
import org.wpilib.units.measure.Distance;
import org.wpilib.units.measure.Voltage;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.units.measure.Angle;
import frc.robot.mechanisms.Intake.IntakePivot.IntakePivotIO.IntakePivotIOInputs;
import frc.robot.subsystems.Elevator.ElevatorIO.ElevatorIOInputs;
import frc.robot.util.Units.UnitUtil;

public class Elevator extends Mechanism {
    private final ElevatorIO io;
    private ElevatorIOInputs inputs = new ElevatorIOInputs();

    private TrapezoidProfile profile;
    private TrapezoidProfile.State goal = new TrapezoidProfile.State();
    private TrapezoidProfile.State setpoint = null;

    private Distance goalDistance = Meters.zero();

    public Elevator(ElevatorIO io) {
        this.io = io;

        this.profile = new TrapezoidProfile(
            new TrapezoidProfile.Constraints(
                ElevatorConstants.MAX_METERS.magnitude()
                
            )
        );

        Scheduler.getDefault().addPeriodic(() -> {
            io.updateInputs(inputs);
        });
    }
    public Trigger atGoalDistance() {
        return new Trigger(() -> inputs.leaderMeters.isNear(goalMeters, ElevatorConstants.DISTANCE_TOLERANCE));
    }
    private NeedsNameBuilderStage runDistanceCommand(Supplier<Distance> distance) {
        return run(co -> {
            setpoint = new TrapezoidProfile.State(inputs.leaderMeters.magnitude(), inputs.leaderVelocityMeters.magnitude());
            while (true) {
                goalDistance = UnitUtil.clamp(distance.get(), IntakePivotConstants.MIN_DISTANCE, IntakePivotConstants.MAX_DISTANCE);
                goal = new TrapezoidProfile.State(goalDistance.magnitude(), 0.0);
                setpoint = profile.calculate(0.02, setpoint, goal);
                io.setDistance(Meters.of(point.position), Meters.of(setpoint.velocity));
                co.yield();
            }
        });
    }
    public Command runDistanceCommand() {
        return runDistanceCommand(() -> Meters.of(1.6));

}

