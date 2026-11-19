// Albert Sadykov
package frc.robot.subsystems.Roller;


import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.Trigger;
import org.wpilib.drivers.motor.PWMSparkFlex;
import org.wpilib.hardware.discrete.DigitalInput;
import org.wpilib.hardware.motor.PWMSparkMax;


import frc.robot.subsystems.Roller.RollerIO.RollerIOInputs;


public class Roller implements Mechanism {
    private final RollerIO io;
    private RollerIOInputs inputs = new RollerIOInputs();


    public Roller(RollerIO io) {
        this.io = io;
        setDefaultCommand(stop());
       
        Scheduler.getDefault().addPeriodic(() -> {
            io.updateInputs(inputs);
        });
    }


    private void setVelocity(double velocity) {
        io.runVelocity(velocity);
    }


    private void setVoltage(double voltage) {
        io.runVoltage(voltage);
    }


    public Command stop() {
        return runRepeatedly(() -> setVoltage(0.0)).named("Roller Stop");
    }


    public Command intake() {
        return run(coroutine -> {
            coroutine.fork(setVoltage(RollerConstants.appliedVoltage));
            coroutine.await(setVelocity(RollerConstants.forwardVelocity));
        }).named("Roller Intake");
    }


    public Command outtake() {
        return run(coroutine -> {
            coroutine.fork(setVoltage(RollerConstants.appliedVoltage));
            coroutine.await(setVelocity(-RollerConstants.forwardVelocity)); // Would negative v work for outtake?
        }).named("Roller Outtake");
    }
}



