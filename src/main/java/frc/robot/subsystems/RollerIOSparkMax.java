package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.subsystems.Roller.RollerIO;

public class RollerIOSparkMax implements RollerIO{
    private final SparkFlex motor; 
    private final SparkFlexConfig config;
    private SimpleMotorFeedforward feedforward;
    
    public RollerIOSparkMax(){
     motor = new SparkFlex(0, null);
     config = new SparkFlexConfig(); 
       
    config 
      .smartCurrentLimit(0) 
      .idleMode(IdleMode.kCoast) 
      .inverted(false); 
    config 
      .encoder
      .positionConversionFactor(0)
      .velocityConversionFactor(0); 
    config 
      .closedLoop 
      .pid(0, 0, 0);

    motor.configure(config, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters); 

    feedforward = new SimpleMotorFeedforward(0, 0, 0);  
    } 
    @Override 
    public void updateInputs(RollerIOInputs inputs){ 
        inputs.rollerconnected= motor.getLastError()==REVLibError.kOk; 
        inputs.rollerVoltage =  Volts.of(motor.getAppliedOutput() *12);; 
        inputs.rollerAngularVelocity = RadiansPerSecond.of(motor.getEncoder().getVelocity()); 
        inputs.rollerCurrent= Amps.of(motor.getOutputCurrent());
    }
    @Override 
    public void setVoltage(Voltage voltage){
      motor.setVoltage(0);
    }
    @Override
    public void setPID(double kp, double ki, double kd) {
      motor.configure(
        new SparkMaxConfig() 
         .apply(new ClosedLoopConfig()
            .p(kp) 
            .i(ki) 
            .d(kd)), 
        ResetMode.kNoResetSafeParameters, 
        PersistMode.kPersistParameters
      );
    } 
    @Override
    public void setFF(double ks, double kv, double ka) {
       feedforward.setKs(ks); 
       feedforward.setKv(kv); 
       feedforward.setKa(ka);
    } 
    @Override
    public void stop(){
      motor.stopMotor();
    }
}