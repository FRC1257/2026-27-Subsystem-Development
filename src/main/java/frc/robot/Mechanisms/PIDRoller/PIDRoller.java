package frc.robot.subsystems.PIDRoller;


import java.util.function.Supplier;


import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.NeedsNameBuilderStage;
import org.wpilib.command3.Scheduler;
import org.wpilib.units.measure.Voltage;


import frc.robot.mechanisms.Intake.PIDRoller.PIDRollerIO.PIDRollerIOInputs;


public class PIDRoller extends Mechanism {
    /*
    Commands for reaching goal velocity
    Voltage
    Velocity
    Triggers for if the roller is jammed
    Used in command to help unjam
    */


    private final PIDRollerIO io;
    private PIDRollerIOInputs inputs = new PIDRollerIOInputs();


    private Angle goalAngle = Radians.zero();




    public PIDRoller(PIDRollerIO io) {
        this.io = io;


        Scheduler.getDefault().addPeriodic(() -> {
            io.updateInputs(inputs);
        });
    }


    private NeedsNameBuilderStage runVoltageCommand(Supplier<Voltage> volts) {
        return runRepeatedly(() -> io.setVoltage(volts.get()));
    }


    private NeedsNameBuilderStage runVelocityCommand(Supplier<AngularVelocity> velocity) {
        return runRepeatedly(() -> {
            goalVelocity = velocity.get();
            io.setVelocity(velocity.get());
        });
    }


    public Command runRoller() {
        return runVoltageCommand(() -> PIDRollerConstants.INTAKE_VOLTAGE)
                .named("PIDRoller/Intake");
    }


    public Command runEject() {
        return runVoltageCommand(() -> PIDRollerConstants.EJECT_VOLTAGE)
                .named("PIDRoller/Eject");
    }


    public Command runIdle() {
        return runVoltageCommand(() -> PIDRollerConstants.IDLE_VOLTAGE)
                .named("PIDRoller/Idle");
    }


    public Trigger isJammed() {
        return new Trigger(
            () -> inputs.motorVelocityRadsPerSec.lte(PIDRollerConstants.PIDROLLER_JAMMED_VELOCITY)
            && inputs.motorCurrentAmps.gte(PIDRollerConstants.PIDROLLER_JAMMED_CURRENT)
        ).debounce(PIDRollerConstants.PIDROLLER_JAMMED_TIME, DebounceType.kRising);
    }
}

