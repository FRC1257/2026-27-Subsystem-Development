package frc.robot.subsystems.Pivot;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;

import java.util.function.Supplier;

import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Mechanism;
import edu.wpi.first.wpilibj2.command.NeedsNameBuilderStage;
import edu.wpi.first.wpilibj2.command.Scheduler;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.util.UnitUtil;

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
            new TrapezoidProfile.Constraints(
                PivotConstants.MAX_ANGULAR_VELOCITY.magnitude(),
                PivotConstants.MAX_ANGULAR_ACCELERATION.magnitude()
            )
        );

        Scheduler.getDefault().addPeriodic(() -> {
            io.updateInputs(inputs);
        });
    }

    public Trigger atGoalAngle() {
        return new Trigger(() -> inputs.leftpivotAngle.isNear(goalAngle, PivotConstants.ANGLE_TOLERANCE));
    }

    private NeedsNameBuilderStage runVoltageCommand(Supplier<Voltage> volts) {
        return runRepeatedly(() -> io.runVoltage(volts.get()));
    }

    private NeedsNameBuilderStage runAngleCommand(Supplier<Angle> angle) {
        return run(co -> {
            setpoint = new TrapezoidProfile.State(
                inputs.leftpivotAngle.magnitude(),
                inputs.leftpivotVelocity.magnitude()
            );

            while (true) {
                goalAngle = UnitUtil.clamp(angle.get(), PivotConstants.MIN_ANGLE, PivotConstants.MAX_ANGLE);
                goal = new TrapezoidProfile.State(goalAngle.magnitude(), 0.0);

                setpoint = profile.calculate(0.02, setpoint, goal);

                io.runAngle(
                    setpoint.position,
                    setpoint.velocity
                );

                co.yield();
            }
        });
    }

    public Command goToAngle(Supplier<Angle> angle) {
        return runAngleCommand(angle).named("Pivot/GoToAngle");
    }

    public Command runVoltage(Supplier<Voltage> volts) {
        return runVoltageCommand(volts).named("Pivot/RunVoltage");
    }
}
