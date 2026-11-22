//Made by Owen Steed-Bordner
package frc.robot.subsystems.Elevator;
import static edu.wpi.first.math.trajectory.TrapezoidProfile;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Volts;
import static edu.wpi.first.units.Units.Celsius;
  import org.littletonrobotics.junction.AutoLog;


import edu.wpi.first.units.measure.Distance;
  import edu.wpi.first.units.measure.LinearVelocity;
  import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;


public interface ElevatorIO {
  @AutoLog
  public static class ElevatorIOInputs{
    public boolean Connection = false;
    public Current ElevatorCurrent = Amps.of(0.0);
    public Voltage ElevatorVoltage = Volts.of(0.0);
    public Temperature ElevatorTemperature = Celsius.of(0.0);
    public Distance ElevatorHeight = Meters.of(0.0);
    public LinearVelocity ElevatorSpeed = MetersPerSecond.of(0.0);
  }
  
public default void updateInputs(ElevatorIOInputs inputs) {}
  
  public default void runVoltsElevator(Voltage voltage) {}
  public default void PositionElevator(double distance, double LinearVelocity){}
  public default void VelocityElevator(double distance, double LinearVelocity){}
  public default void ElevatorTemperature(Temperature temperature){}
  public default void stop(){}

  public default void PIDSetup(double kP, double kI, double kD){}
  public default void FFSetup(double kS, double kV, double kA, double kG){}
}
