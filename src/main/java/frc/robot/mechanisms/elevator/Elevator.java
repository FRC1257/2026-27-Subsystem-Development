package frc.robot.mechanisms.elevator;

import org.wpilib.command2.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.Scheduler;
import org.wpilib.command3.NeedsNameBuilderStage;
import org.wpilib.units.measure.Distance;
import org.wpilib.math.trajectory.TrapezoidProfile;

import frc.robot.mechanisms.elevator.ElevatorIO.ElevatorIOInputs;

public class Elevator extends Mechanism { //Warren
    private final ElevatorIO io;
    private final ElevatorIOInputs inputs=new ElevatorIOInputs();

    private final TrapezoidProfile trapProfile=new TrapezoidProfile(new TrapezoidProfile.Constraints(ElevatorConstants.maxVel.baseUnitMagnitude(),ElevatorConstants.maxAccel.baseUnitMagnitude()));
    private final TrapezoidProfile.State goal=new TrapezoidProfile.State();
    private TrapezoidProfile.State setpoint = null;

    public Elevator(ElevatorIO io) {
        this.io=io;

        Scheduler.getDefault().addPeriodic(()->{
            io.updateInputs(inputs);
        });
    }

    public NeedsNameBuilderStage runHeight(Distance height) {
        setpoint=new TrapezoidProfile.State(inputs.leftHeight.baseUnitMagnitude(),inputs.leftVelocity.baseUnitMagnitude());
        return runRepeatedly(()->{
            goal=new TrapezoidProfile.State(height.baseUnitMagnitude());
            setpoint=profile.calculate(0.02,setpoint,goal);

            io.runHeight(setpoint.position());
        });
    }

    public Command runPos1() {
        return runHeight(ElevatorConstants.pos1Height).named("Elevator/Pos1");

    }

    public Command runPos2() {
        return runHeight(ElevatorConstants.pos2Height).named("Elevator/Pos2");
    
    }

    

    public Command stopIfOutofRange() {
        return run(coroutine -> {
            if (!(io.getHeight().baseUnitMagnitude()>pos1Height && io.getHeight().baseUnitMagnitude<pos2Height)){
                io.stop();
            }
        }).named("Elevator/CheckedStop");
    }


}