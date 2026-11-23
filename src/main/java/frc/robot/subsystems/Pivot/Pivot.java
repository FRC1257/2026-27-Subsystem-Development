package frc.robot.mechanisms.Pivot;
import static org.wpilib.units.Units.Radians;
import static org.wpilib.units.Units.RadiansPerSecond;
// ALL OF PIVOT IS ARMAN
import java.util.function.Supplier;


import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.NeedsNameBuilderStage;
import org.wpilib.command3.Scheduler;
import org.wpilib.command3.button.Trigger;
import org.littletonrobotics.junction.Logger;

import org.wpilib.math.trajectory.TrapezoidProfile;
import org.wpilib.units.measure.Angle;
import org.wpilib.units.measure.Voltage;


//import frc.robot.mechanisms.Intake.Pivot.PivotIO.PivotIOInputs;
import frc.robot.util.Units.UnitUtil;


public class Pivot extends Mechanism {


    private final PivotIO io;
    private final PivotIO io;
    private final PivotIOInputsAutoLogged inputs = new PivotIOInputsAutoLogged();  

    private TrapezoidProfile profile;
    private TrapezoidProfile.State goal = new TrapezoidProfile.State();
    private TrapezoidProfile.State setpoint = null;


    private Angle goalAngle = Radians.zero();


    public Pivot(PivotIO io) {
        this.io = io;


        this.profile = new TrapezoidProfile(
            new TrapezoidProfile.Constraints(
                PivotConstants.MAX_ANGULAR_VELOCITY.in(RadiansPerSecond),
                PivotConstants.MAX_ANGULAR_ACCELERATION.in(RadiansPerSecondPerSecond)
            )
        );


        Scheduler.getDefault().addPeriodic(() -> {
            io.updateInputs(inputs);
             Logger.processInputs("Pivot", inputs); 
        });
    }


    public Trigger atGoalAngle() {
        return new Trigger(() -> inputs.rightpivotAngle.isNear(goalAngle, PivotConstants.ANGLE_TOLERANCE));
    }


    private NeedsNameBuilderStage runVoltageCommand(Supplier<Voltage> volts) {
        return runRepeatedly(() -> io.setVoltage(volts.get()));
    }


    private NeedsNameBuilderStage runAngleCommand(Supplier<Angle> angle) {
        return run(co -> {
            setpoint = new TrapezoidProfile.State(inputs.rightPivotAngle.in(Radians), inputs.rightPivotVelocity.in(RadiansPerSecond));
            while (true) {
                goalAngle = UnitUtil.clamp(angle.get(), PivotConstants.MIN_ANGLE, PivotConstants.MAX_ANGLE);
                goal = new TrapezoidProfile.State(goalAngle.in(Radians), 0.0);
                setpoint = profile.calculate(0.02, setpoint, goal);
                io.setAngle(Radians.of(setpoint.position), RadiansPerSecond.of(setpoint.velocity));
                co.yield();
            }
        });
    }


    public Command runAngle1() {
        return runAngleCommand(() -> PivotConstants.ANGLE1)
                .named("Pivot/Angle1");
    }


    public Command runAngle2() {
        return runAngleCommand(() -> PivotConstants.ANGLE2)
                .named("Pivot/Angle2");
    }


   
}
