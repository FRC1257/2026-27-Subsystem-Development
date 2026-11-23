
import frc.robot.mechanisms.Basic.IntakeRollers.BasicRollersIO.BasicRollersIOIinputs;

public class BasicRollers extends Mechanism {

    private final BasicRollersIO io;
    private BasicRollersIOIinputs inputs = new BasicRollersIOIinputs();

    public BasicRollers(BasicRollersIO io) {
        this.io = io;

        Scheduler.getDefault().addPeriodic(() -> {
            io.updateInputs(inputs);
        });
    }

    private NeedsNameBuilderStage runVoltageCommand(Supplier<Voltage> volts) {
        return runRepeatedly(() -> io.setVoltage(volts.get()));
    }

    public Command runIntake() {
        return RunVoltageCommand(() -> BasicRollersConstants.INTAKE_VOLTAGE)
            .named("IntakeRollers/Intake");
    }

    public Command runEject() {
        return RunVoltageCommand(() -> BasicRollersConstants.EJECT_VOLTAGE)
            .named("IntakeRollers/Eject");