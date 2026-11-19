package frc.robot.subsystems.elevator;

import org.wpilib.command3.Mechanism;
import org.wpilib.command3.Scheduler;
import org.wpilib.command3.NeedsNameBuilderStage;
import org.wpilib.units.measure.Distance;


import frc.robot.subsystems.elevator.ElevatorIO.ElevatorIOInputs;

public class Elevator extends Mechanism { //Warren
    private final ElevatorIO io;
    private final ElevatorIOInputs inputs=new ElevatorIOInputs();
    public Elevator(ElevatorIO io) {
        this.io=io;

        Scheduler.getDefault().addPeriodic(()->{
            io.updateInputs(inputs);
        });
    }

    public NeedsNameBuilderStage runHeight(Distance height) {
        return runRepeatedly(()->{
            io.runHeight(height);
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