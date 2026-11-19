package frc.robot.subsystems.Roller;





public class Roller {
    
public class Roller implements Mechanism {
    private final RollerIO io;

    public Roller() {
        this.io=io;
        setDefaultCommand(stop());
  }

    public Command stop() {
        return run(coroutine -> {
            io.setVoltage(0);
            coroutine.park();
        }).named("Roller.stop");
    }

    public Command intake() {
        return run(coroutine -> {
            
            coroutine.park();    
        }).named("Roller.Intake");
    }
}
}