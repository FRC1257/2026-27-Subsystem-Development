//caitlin & lael
package frc.robot.Mechanisms.Elevator;

import static org.wpilib.units.Units.Radians;
import static org.wpilib.units.Units.RadiansPerSecond;

import java.util.function.Supplier;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.NeedsNameBuilderStage;
import org.wpilib.command3.Scheduler;
import org.wpilib.command3.Trigger;
import org.wpilib.math.trajectory.TrapezoidProfile;
import org.wpilib.units.measure.Voltage;

import frc.robot.Mechanisms.ElevatorIO.ElevatorIOInputs;
import frc.robot.util.Units.UnitUtil;

public class Elevator extends Mechanism {

    private final ElevatorIO io;
    private ElevatorIOInputs inputs = new ElevatorIOInputs();

    private TrapezoidProfile profile;
    private TrapezoidProfile.State goal = new TrapezoidProfile.State();
    private TrapezoidProfile.State setpoint = null;

    public Elevator(ElevatorIO io) {
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

    private NeedsNameBuilderstage runHeight1Command() {
        return runVoltageCommand(() -> 25); //height 1 voltage
    }
    private NeedsNameBuilderStage runHeight2Command() {
        return runVoltageCommand(() -> 50); //height 2 voltage
    }
    // private NeedsNameBuilderStage runAngleCommand(Supplier<Angle> angle) {
    //     return run(co -> {
    //         setpoint = new TrapezoidProfile.State(inputs.leaderAngleRads.magnitude(), inputs.leaderVelocityRadsPerSec.magnitude());
    //         while (true) {
    //             goalAngle = UnitUtil.clamp(angle.get(), 2.0, 4.0); //(angle.get(), min angle, max angle)
    //             goal = new TrapezoidProfile.State(goalAngle.magnitude(), 0.0);
    //             setpoint = profile.calculate(0.02, setpoint, goal);
    //             io.setAngle(Radians.of(setpoint.position), RadiansPerSecond.of(setpoint.velocity));
    //             co.yield();
    //         }
    //     });
    // }

    // public Command runAngle() {
    //     return runAngleCommand(() -> ElevatorConstants._ANGLE)
    //             .named("Elevator/Angle");
    // }

    // public Command runStowAngle() {
    //     return runAngleCommand(() -> ElevatorConstants.STOW_ANGLE)
    //             .named("Elevator/StowAngle");
    // }
    
}
/*
    Trapezoidal profile
    Commands
    Triggers for it is not b/w height 1 and 2
    Commands to go to height 1 and height 2 + command using trigger to stop all motion if not within height 1 or 2
    Meters
    code
    code
    code
    */