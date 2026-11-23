package frc.robot.mechanisms.pivot;

import frc.robot.mechanisms.pivot.PivotIO.PivotIOInputs; 
import frc.robot.mechanisms.pivot.PivotConstants; 

import org.wpilib.command2.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.Scheduler;
import org.wpilib.command3.NeedsNameBuilderStage;
import org.wpilib.units.measure.Distance;
import org.wpilib.math.trajectory.TrapezoidProfile;


public class Pivot extends Mechanism {
    private final PivotIO io;   
    private final PivotIOInputs inputs = new PivotIOInputs(); 

    private TrapezoidProfile profile; 
    private TrapezoidProfile.State goal = new TrapezoidProfie.State(); 
    private TrapezoidProfile.State setpoint = null; 

    private Angle goalAngle = Radians.zero();

    public Pivot(PivotIO io) {
        this.io = io;
        
        this.profile = new TrapezoidProfile(
            new TrapezoidProfile.Constraints(
                PivotConstants.MAX_ANGULAR_VELOCITY.magnitude(),
                PivotConstants.MAX_ANGULAR_ACCELERATION.magnitude()
            )
        )
   
        Scheduler.getDefault().addPeriodic(()->{ 
            io.updateInputs(inputs);
        });
    }
    public Trigger atGoalAngle(){
        return new Trigger(() -> inputs.leaderAngleRads.isNear(goalAngle,PivotConstants.ANGLE_TOLERANCE));
    }

    private NeedsNameBuilderStage setVoltageCommand(Supplier<Voltage> volts) { 
        return runRepeatedly(()-> io.setVoltage(volts.get()));
    } 
    private NeedsNameBuilderStage setAngleCommand(Supplier<Angle> angle){ 
        return run(co->{
            setpoint= new TrapezoidProfile.state(inputs.leaderAngleRads.magnitude(), inputs.leaderVelocityRadsPerSec.magnitude()); 
            while(true){
              goalAngle= UnitUtil.clamp(angle.get(),PivotConstants.ANGLE_1, PivotConstants.ANGLE_2);
              goal= new TrapezoidProfile.State(goalAngle.baseUnitMagnitude(), 0.0);
              setpoint= profile.calculate(0.02,setpoint,goal);
              io.setAngle(Radians.of(setpoint.position));
              co.yield();
            } 
        });
    }
    //Tanish Patel was here
}
