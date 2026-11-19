package frc.robot.subsystems.Climb;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import java.util.function.Supplier;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.NeedsNameBuilderStage;
import org.wpilib.command3.Scheduler;
import org.wpilib.command3.Trigger;
import org.wpilib.math.trajectory.TrapezoidProfile;
import org.wpilib.units.measure.Angle;
import org.wpilib.units.measure.Voltage;

import frc.robot.subsystems.Climb.ClimbIO.ClimbIOInputs;
import frc.robot.util.Units.UnitUtil;
public class Climb extends ClimbIO{
    private final ClimbIO io;
    private ClimbIOInputs inputs = new ClimbIOInputs();

    
    private TrapezoidProfile profile;
    private TrapezoidProfile.State goal = new TrapezoidProfile.State();
    private TrapezoidProfile.State setpoint = null;

    private Angle goalAngle = Radians.zero();

    public Climb(ClimbIO io) {
        this.io = io;
        this.profile = new TrapezoidProfile("PUT STUFF HERE EVENTUALLY");

    }
    Scheduler.getDefault().addPeriodic(() -> {
        io.updateInputs(inputs);
    });
    public Trigger atGoalAngle(){
        return new Trigger(() -> inputs.climbAngle.equals(goalAngle, 0.0));

    }
    private NeedsNameBuilderStage runVoltageCommand(Supplier<Voltage> volts){
        return run Repeatedly(() -> io.runVoltage(volts.get()));
    }
    private NeedsNameBuilderStage runAngleCommand(Supplier<Angle> angle){
        return run(co->
            setpoint = new TrapezoidProfie.State(inputs.climbAngle.magnitude(), inputs.climbVelocity.magnitude());
        while (true){
            goalAngle = UnitUtil.clamp(angle.get(), Radians.of(0.0), Radians.of(Math.PI));
            goal = new TrapezoidProfile.State(goalAngle.magnitude(), 0.0);
            io.setAngle(Radians.of(setpoint.position), RadiansPerSecond.of(setpoint.velocity));
            co.yield();
        }
        );
    }
    public Command runIdleVelocity(){
        return runVelocityCommand(() -> RadiansPerSecond.of(0.0))
        .named("Climb/IdleVelocity");
    }
    public Command RunTargeted(Supplier<Distance> targetDistance){
        return runAngleCommand(() -> Radians.of(targetDistance.get().magnitude()))
        .named("Climb/Targeted");
    }
    public Command runPresetVelocity(){
        return runVelocityCommand(() -> RadiansPerSecond.of(0.5))
        .named("Climb/PresetVelocity");
    }
    public Command runTargetVelocity(Supplier<AngularVelocity> targetVelocity){
        return runVelocityCommand(() -> targetVelocity.get())
        .named("Climb/TargetVelocity");
    }
}