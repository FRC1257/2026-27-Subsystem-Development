\\This was coded by Alex Chan\\

package frc.robot.subsystems.PIDRoller;

import static edu.wpi.first.units.Units.RadiansPerSecond;

import java.util.function.Supplier;

import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.framework.Scheduler;
import frc.robot.framework.NeedsNameBuilderStage;
import frc.robot.subsystems.Mechanism;
import frc.robot.subsystems.PIDRoller.PIDRollerIO.PIDRollerIOInputs;

public class PIDRoller extends Mechanism {

  private final PIDRollerIO io;
  private final PIDRollerIOInputs inputs = new PIDRollerIOInputs();
  private AngularVelocity goalVelocity = RadiansPerSecond.of(0.0);

  public PIDRoller(PIDRollerIO io) {
    this.io = io;

    Scheduler.getDefault().addPeriodic(() -> {
      io.updateInputs(inputs);
    });
  }

  private NeedsNameBuilderStage runVoltageCommand(Supplier<Voltage> volts) {
    return runRepeatedly(() -> io.runVoltage(volts.get()));
  }

  private NeedsNameBuilderStage runVelocityCommand(Supplier<AngularVelocity> velocity) {
    return runRepeatedly(() -> {
      this.goalVelocity = velocity.get();
      io.setVelocity(velocity.get());
    });
  }

  public Trigger atGoalVelocity() {
    return new Trigger(() -> inputs.firstMotorAngularVelocity.isNear(goalVelocity, PIDRollerConstants.VELOCITY_TOLERANCE));
  }

  public Trigger isJammed() {
    return new Trigger(
        () -> inputs.firstMotorAngularVelocity.lte(PIDRollerConstants.PIDROLLER_JAMMED_VELOCITY)
            && inputs.firstMotorCurrent.gte(PIDRollerConstants.PIDROLLER_JAMMED_CURRENT)
    ).debounce(PIDRollerConstants.PIDROLLER_JAMMED_TIME, DebounceType.kRising);
  }

  public Command runIntakeVoltage() {
    return runVoltageCommand(() -> PIDRollerConstants.INTAKE_VOLTAGE)
        .named("PIDRoller/IntakeVoltage");
  }

  public Command runEjectVoltage() {
    return runVoltageCommand(() -> PIDRollerConstants.EJECT_VOLTAGE)
        .named("PIDRoller/EjectVoltage");
  }

  public Command runIdleVoltage() {
    return runVoltageCommand(() -> PIDRollerConstants.IDLE_VOLTAGE)
        .named("PIDRoller/IdleVoltage");
  }

  public Command runIntake() {
    return runVelocityCommand(() -> PIDRollerConstants.INTAKE_VELOCITY)
        .named("PIDRoller/Intake");
  }

  public Command runEject() {
    return runVelocityCommand(() -> PIDRollerConstants.EJECT_VELOCITY)
        .named("PIDRoller/Eject");
  }

  public Command runIdle() {
    return runVelocityCommand(() -> PIDRollerConstants.IDLE_VELOCITY)
        .named("PIDRoller/Idle");
  }

  public Command runUnjam() {
    return runVelocityCommand(() -> PIDRollerConstants.UNJAM_VELOCITY)
        .until(isJammed().negate())
        .withPriority(Command.HIGHEST_PRIORITY)
        .named("PIDRoller/Unjam");
  }
}