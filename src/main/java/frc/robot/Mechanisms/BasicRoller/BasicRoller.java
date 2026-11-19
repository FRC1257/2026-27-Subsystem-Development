package frc.robot.Mechanisms.BasicRoller;

import java.util.function.Supplier;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.NeedsNameBuilderStage;
import org.wpilib.command3.Scheduler;
import org.wpilib.units.measure.Voltage;

import frc.robot.Mechanisms.BasicRoller.BasicRollerIO.BasicRollerIOInputs;

public class BasicRoller extends Mechanism {

    private final BasicRollerIO io;
    private BasicRollerIOInputs inputs = new BasicRollerIOInputs();

    public BasicRoller(BasicRollerIO io) {
        this.io = io;

        Scheduler.getDefault().addPeriodic(() -> {
            io.updateInputs(inputs);
        });
    }

    private NeedsNameBuilderStage runVoltageCommand(Supplier<Voltage> volts) {
        return runRepeatedly(() -> io.setVoltage(volts.get()));
    }

    public Command runIntake() {
        return runVoltageCommand(() -> BasicRollerConstants.INTAKE_VOLTAGE)
                .named("BasicRoller/Intake");
    }

    public Command runEject() {
        return runVoltageCommand(() -> BasicRollerConstants.EJECT_VOLTAGE)
                .named("BasicRoller/Eject");
    }

    public Command runIdle() {
        return runVoltageCommand(() -> BasicRollerConstants.IDLE_VOLTAGE)
                .named("BasicRoller/Idle");
    }

}

//Sophie Wang