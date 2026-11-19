package frc.robot.subsystems.Roller;


import java.util.function.Supplier;


import static org.wpilib.units.Units.Amps;
import static org.wpilib.units.Units.Volts;
import static org.wpilib.units.Units.Celsius;
import org.littletonrobotics.junction.Logger;
import org.wpilib.math.filter.Debouncer;
import org.wpilib.math.filter.Debouncer.DebounceType;
import org.wpilib.units.measure.Voltage;
import org.wpilib.units.measure.Current;
import org.wpilib.units.measure.Temperature;
import org.wpilib.util.Alert;
import org.wpilib.util.Alert.Level;
import org.wpilib.command2.Command;
import org.wpilib.command2.SubsystemBase;
import frc.robot.Robot;
import frc.robot.util.Units.UnitUtil;


public class BasicRollers extends SubsystemBase {


    private final BasicRollerIO io;
    private BasicRollerIOInputs inputs = new BasicRollerIOInputs();


    public BasicRollers(BasicRollerIO io) {
        this.io = io;


        Scheduler.getDefault().addPeriodic(() -> {
            io.updateInputs(inputs);
        });
    }


    private NeedsNameBuilderStage runVoltageCommand(Supplier<Voltage> volts) {
        return runRepeatedly(() -> io.setVoltage(volts.get()));
    }


    public Command runIntake() {
        return runVoltageCommand(() -> BasicRollersConstants.INTAKE_VOLTAGE)
                .named("BasicRollers/Intake");
    }


    public Command runEject() {
        return runVoltageCommand(() -> BasicRollersConstants.EJECT_VOLTAGE)
                .named("BasicRollers/Eject");
    }


    public Command runIdle() {
        return runVoltageCommand(() -> BasicRollersConstants.IDLE_VOLTAGE)
                .named("BasicRollers/Idle");
    }


}
// AVyan  
