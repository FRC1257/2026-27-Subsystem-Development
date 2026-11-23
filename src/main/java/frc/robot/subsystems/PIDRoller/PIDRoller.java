package frc.robot.subsystems.PIDroller;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import java.util.function.Supplier;

import org.littletonrobotics.junction.Logger;
import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.NeedsNameBuilderStage;
import org.wpilib.command3.Scheduler;
import org.wpilib.command3.Trigger;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;

public class PIDroller extends Mechanism {
    private final PIDrollerIO io;
    private final PIDrollerIOInputsAutoLogged inputs = new PIDrollerIOInputsAutoLogged();

    private AngularVelocity goalVelocity = RadiansPerSecond.zero();

    private static final Current JAM_CURRENT_THRESHOLD = Amps.of(0.0);
    private static final AngularVelocity JAM_VELOCITY_THRESHOLD = RadiansPerSecond.of(0.0);
    private static final AngularVelocity VELOCITY_TOLERANCE = RadiansPerSecond.of(0.0);

    public PIDroller(PIDrollerIO io) {
        this.io = io;


        Scheduler.getDefault().addPeriodic(() -> {
            io.updateInputs(inputs);
            Logger.processInputs("PIDroller", inputs);
        });
    }

    public Trigger atGoalVelocity() {
        return new Trigger(() ->
            inputs.PIDrollerLeaderAngularVelocity.isNear(goalVelocity, VELOCITY_TOLERANCE)
        );
    }

    public Trigger isJammed() {
        return new Trigger(() -> {
            boolean leaderJammed = inputs.PIDrollerLeaderCurrent.gt(JAM_CURRENT_THRESHOLD) &&
                inputs.PIDrollerLeaderAngularVelocity.abs(RadiansPerSecond) < JAM_VELOCITY_THRESHOLD.in(RadiansPerSecond);


            boolean followerJammed = inputs.PIDrollerFollowerCurrent.gt(JAM_CURRENT_THRESHOLD) &&
                inputs.PIDrollerFollowerAngularVelocity.abs(RadiansPerSecond) < JAM_VELOCITY_THRESHOLD.in(RadiansPerSecond);


            return leaderJammed || followerJammed;
        });
    }

    private NeedsNameBuilderStage runVoltageCommand(Supplier<Voltage> volts) {
        return runRepeatedly(() -> {
            goalVelocity = RadiansPerSecond.zero();
            io.setVoltage(volts.get());
        });
    }

    private NeedsNameBuilderStage runVelocityCommand(Supplier<AngularVelocity> velocity) {
        return runRepeatedly(() -> {
            goalVelocity = velocity.get();
            io.setVelocity(goalVelocity);
        });
    }

    public Command runIdle() {
        return runRepeatedly(() -> {
            goalVelocity = RadiansPerSecond.zero();
            io.stop();
        }).named("PIDroller/Idle");
    }

    public Command runTargetVelocity(Supplier<AngularVelocity> targetVelocity) {
        return runVelocityCommand(targetVelocity)
            .named("PIDroller/TargetVelocity");
    }

    public Command runTargetVoltage(Supplier<Voltage> targetVoltage) {
        return runVoltageCommand(targetVoltage)
            .named("PIDroller/TargetVoltage");
    }

    public Command runUnjam() {
        return runVoltageCommand(() -> Volts.of(0.0))
            .named("PIDroller/Unjam");
    }
}