package frc.robot.Mechanisms.Pivot;

import static org.wpilib.units.Units.Radians;
import static org.wpilib.units.Units.RadiansPerSecond;

import java.util.function.Supplier;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.NeedsNameBuilderStage;
import org.wpilib.command3.Scheduler;
import org.wpilib.command3.Trigger;
import org.wpilib.math.trajectory.TrapezoidProfile;
import org.wpilib.units.measure.Angle;
import org.wpilib.units.measure.Voltage;

import frc.robot.Mechanisms.PivotIO.PivotIOInputs;
import frc.robot.util.Units.UnitUtil;

public class Pivot extends Mechanism {

    private final PivotIO io;
    private PivotIOInputs inputs = new PivotIOInputs();

    private TrapezoidProfile profile;
    private TrapezoidProfile.State goal = new TrapezoidProfile.State();
    private TrapezoidProfile.State setpoint = null;

    private Angle goalAngle = Radians.zero();

    public Pivot(PivotIO io) {
        this.io = io;

        this.profile = new TrapezoidProfile(
            new TrapezoidProfile.Constraints(3,4)//max velocity, max acceleration
        );

        Scheduler.getDefault().addPeriodic(() -> {
            io.updateInputs(inputs);
        });
    }

    public Trigger atGoalAngle() {
        return new Trigger(() -> inputs.leaderAngleRads.isNear(goalAngle, 6.0)); //(goalAngle, angle tolerance)
    }

    private NeedsNameBuilderStage runVoltageCommand(Supplier<Voltage> volts) {
        return runRepeatedly(() -> io.setVoltage(volts.get()));
    }

    private NeedsNameBuilderStage runAngleCommand(Supplier<Angle> angle) {
        return run(co -> {
            setpoint = new TrapezoidProfile.State(inputs.leaderAngleRads.magnitude(), inputs.leaderVelocityRadsPerSec.magnitude());
            while (true) {
                goalAngle = UnitUtil.clamp(angle.get(), 2.0, 4.0); //(angle.get(), min angle, max angle)
                goal = new TrapezoidProfile.State(goalAngle.magnitude(), 0.0);
                setpoint = profile.calculate(0.02, setpoint, goal);
                io.setAngle(Radians.of(setpoint.position), RadiansPerSecond.of(setpoint.velocity));
                co.yield();
            }
        });
    }

    // public Command runAngle() {
    //     return runAngleCommand(() -> PivotConstants._ANGLE)
    //             .named("Pivot/Angle");
    // }

    // public Command runStowAngle() {
    //     return runAngleCommand(() -> PivotConstants.STOW_ANGLE)
    //             .named("Pivot/StowAngle");
    // }
    
}