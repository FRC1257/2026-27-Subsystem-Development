//lael
public class Pivot extends Mechanism {
private final PivotIO io;
private PivotIOInputs inputs = new PivotIOInputs();
private TrapezoidProfile profile;
private TrapezoidProfile.State goal= new TrapezoidProfile.State();
private TrapezoidProfile.State setpoint = null;
private Angle goalAngle = Radians.zero();
public Pivot (PivotIO io) {
this.io = io;
this.profile = new TrapezoidProfile(
new TrapezoidProfile.Constraints(
PivotConstants.MAX_ANGULAR_VELOCITY.magnitude(), PivotConstants.MAX_ANGULAR_ACCELERATION.magnitude()
}
Scheduler.getDefault().addPeriodic(()->{
});
io.updateInputs(inputs);
public Trigger atGoalAngle() {
return new Trigger(()->inputs.leaderAngleRads.isNear (goalAngle, PivotConstants.ANGLE_TOLERANCE));
}
private NeedsNameBuilderStage runVoltageCommand(Supplier<Voltage> volts) {
}
return runRepeatedly(()-> io.setVoltage(volts.get()));
private NeedsNameBuilderStage runAngleCommand(Supplier<Angle> angle) {
return run(co -> {
});
setpoint = new TrapezoidProfile.State(inputs. LeaderAngleRads.magnitude(), inputs.leaderVelocityRadsPerSec.magnitude()); while (true) {
goalAngle = UnitUtil.clamp (angle.get(), PivotConstants.MIN_ANGLE,  PivotConstants.MAX_ANGLE);
goal = new TrapezoidProfile.State(goalAngle.magnitude(), 0.0);
setpoint = profile.calculate(0.02, setpoint, goal);
io.setAngle(Radians.of(setpoint.position), RadiansPerSecond.of(setpoint.velocity));
co.yield();
