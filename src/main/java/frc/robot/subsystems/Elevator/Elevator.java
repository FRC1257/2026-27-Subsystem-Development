//Made by Owen Steed-Bordner
package frc.robot.subsystems.Elevator;
import static edu.wpi.units.Units.Meters;
import static edu.wpi.units.Units.MetersPerSecond;
import java.util.function.Supplier;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.NeedNameBuilderStage;
import org.wpilib.command3.Scheduler;
import org.wpilib.math.trajectory.TrapezoidProfile;
import edu.wpi.units.measure.Distance;
import edu.wpi.units.measure.Voltage;

import frc.robot.subsystems.Elevator.ElevatorIO.ElevatorIOInputs;
import frc.robot.util.Units.UnitUtil;
public class Elevator implements Mechanism{
  private final ElevatorIO io;
  private final ElevatorIOInputs inputs = new ElevatorIOInputs();
  private TrapezoidProfile profile;
  private TrapezoidProfile.State goal = new TrapezoidProfile.State();
  private TrapezoidProfile.State setpoint = null;
  private Distance goalDistance = Meters.zero();
  public Elevator(ElevatorIO io){
    this.io = io;
    this.profile = new TrapezoidProfile(
	new TrapezoidProfile.Constraints(1.0,2.0)
);


Scheduler.getDefault().addPeriodic(()->{
io.updateInputs(inputs);});
}
private NeedNameBuilderStage runVoltsElevator(Supplier<Voltage> volts){
return runRepeatedly(()->io.runVoltage(volts.get()));
}

private NeedNameBuilderStage PositionElevator(Supplier< Distance> distance){
    return run(co->
	setpoint = new TrapezoidProfile.State(inputs.ElevatorHeight.magnitude(), inputs.ElevatorSpeed.magnitude());
    while (true){
      goalDistance = UnitUtil.clamp(Distance.get(), Meters.of(0.0), Meters.of(1.2));
      goal = new TrapezoidProfile.State(goalDistance.magnitude(), 0.0);
      io.setHeight(Meters.of(setpoint.position), MetersPerSecond.of(setpoint.velocity));
      co.yield();
    }
 ); 
}
public Command RunTargetted(Supplier<Distance> targetDistance){
  return PositionElevator(()->Meters.of(targetDistance.get().magnitude()))
    .named("Elevator/Target");
}
  public Command runPresetVelocity(){
  return VelocityElevator(()->MetersPerSecond.of(1.25))
    .named("Elevator/PresetVelocity");
}
  public Command runTargetVelocity(Supplier<LinearVelocity> targetVelocity){
  return VelocityElevator(()->targetVelocity.get())
    .named("Elevator/TargetVelocity");
}
}
