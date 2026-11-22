// Albert Sadykov
package frc.robot.subsystems.Roller;

import org.wpilib.command3.Command;
import org.wpilib.command3.Scheduler;
import org.wpilib.command3.Mechanism;

import frc.robot.subsystems.Roller.RollerIO.RollerIOInputs;

public class Roller implements Mechanism {
    private final RollerIO io;
    private final RollerIOInputs inputs = new RollerIOInputs();

    public Roller(RollerIO io) {
        this.io = io;
        setDefaultCommand(stop());
        
        Scheduler.getDefault().addPeriodic(() -> {
            io.updateInputs(inputs); 
        });
    }

    private void setVoltage(double voltage) {
        io.runVoltage(voltage); 
    }

    public Command stop() {
        return runRepeatedly(() -> setVoltage(0.0)).named("Roller Stop");
    }

    public Command intake() {
        return run(coroutine -> {
            while (true) {
                setVoltage(RollerConstants.appliedVoltage);
                coroutine.yield();
            }
        }).named("Roller Intake");
    }

    public Command outtake() {
        return run(coroutine -> {
            while (true) {
                setVoltage(-RollerConstants.appliedVoltage);
                coroutine.yield();
            }
        }).named("Roller Outtake");
    }
}
