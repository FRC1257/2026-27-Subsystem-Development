//RyanNg
package frc.robot.subsystems.Climb;

import frc.robot.subsystems.Climb.ClimbIO.ClimbIOInputs;
import frc.robot.subsystems.PIDRoller.PIDRoller;
import frc.robot.subsystems.PIDRoller.PIDRollerConstants;
import frc.robot.subsystems.PIDRoller.PIDRollerIO;
import frc.robot.subsystems.PIDRoller.PIDRollerIO.PIDRollerIOInputs;
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
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.mechanisms.PIDRoller.PIDRollerIO.PIDRollerIOInputs;
import frc.robot.subsystems.Kicker.KickerConstants;
import static org.wpilib.units.Units.RadiansPerSecond;
import org.wpilib.math.trajectory.TrapezoidProfile;

public class Climb extends Mechanism {
    private final ClimbIO io;
    private ClimbIOInputs inputs = new ClimbIOInputs();
    private TrapezoidProfile profile;

    private TrapezoidProfile.State goal = new TrapezoidProfile.State();
    private TrapezoidProfile.State setpoint = null;

    public Climb(ClimbIO io) {
        this.io = io;
        this.profile = new TrapezoidProfile(
            new TrapezoidProfile.Constraints(ClimbConstants.MAX_VELOCITY, ClimbConstants.MAX_ACCELERATION)
        );
        Scheduler.getDefault().addPeriodic(() -> {
            io.updateInputs(inputs);
        });
    }
    private NeedsNameBuilderStage runVoltageCommand(Supplier<Voltage> volts) {
        return runRepeatedly(() -> io.setVoltage(volts.get()));
    }
    public Trigger atPosition0() {
        return new Trigger(
            () -> inputs.positionMeters.lte(ClimbConstants.) 
            == inputs.setpointMeters.gte(ClimbConstants.)
        ).debounce(PIDRollerConstants.PIDROLLER_JAMMED_TIME, DebounceType.kRising);
    }

    public NeedsNameBuilderStage moveTo(double goalPosition, double Name) {
        return run(coroutine -> {
            if atGoal(goalPosition) { // atGoal will be method to check position using inputs position vs goal maybe add a tolerance -Albert
                return;
            }
        })
    }

    // should create trapezoidal profile here to move it if not already at goal position -Albert 

    public Command goPosition0() {
        return moveTo(ClimbConstants.POSITION_0_METERS, "Position 0");
    }

    public Command goPosition1() {
        return moveTo(ClimbConstants.POSITION_1_METERS, "Position 1");
    }
}
