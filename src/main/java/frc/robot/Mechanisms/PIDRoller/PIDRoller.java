package frc.robot.subsystems.PIDRoller;

import java.util.function.Supplier;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.NeedsNameBuilderStage;
import org.wpilib.command3.Scheduler;
import org.wpilib.units.measure.Voltage;

import frc.robot.mechanisms.Intake.PIDRollers.PIDRollersIO.PIDRollersIOInputs;

public class PIDRoller extends Mechanism {

    private final PIDRollerIO io;
    private PIDRollerIOInputs inputs = new PIDRollerIOInputs();

    public PIDRoller(PIDRollerIO io) {
        this.io = io;

        Scheduler.getDefault().addPeriodic(() -> {
            io.updateInputs(inputs);
        });
    }

    private NeedsNameBuilderStage runVoltageCommand(Supplier<Voltage> volts) {
        return runRepeatedly(() -> io.setVoltage(volts.get()));
    }

    public Command runRollers() {
        return runVoltageCommand(() -> PIDRollersConstants.INTAKE_VOLTAGE)
                .named("PIDRollers/Intake");
    }

    public Command runEject() {
        return runVoltageCommand(() -> PIDRollersConstants.EJECT_VOLTAGE)
                .named("PIDRollers/Eject");
    }

    public Command runIdle() {
        return runVoltageCommand(() -> PIDRollersConstants.IDLE_VOLTAGE)
                .named("PIDRollers/Idle");
    }

}