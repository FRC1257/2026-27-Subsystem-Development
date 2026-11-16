package frc.robot.subsystems.flywheelTalon;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.*;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

import java.util.function.Supplier;

public class FlywheelIOTalon implements FlywheelIO{
    private TalonFX leftMotor;
    private TalonFX rightMotor;

    private TalonFXConfiguration config;

    private TalonInputs leftInputs;
    private TalonInputs rightInputs;

    private VoltageOut voltageController=new VoltageOut(0).withUpdateFreqHz(0);
    private VelocityVoltage velocityController=new VelocityVoltage(0).withUpdateFreqHz(0).withSlot(0);
    private NeutralOut neutral=new NeutralOut().withUpdateFreqHz(0);

    private SimpleMotorFeedforward ff;

    public FlywheelIOTalon() { //many temp values, i dont have the  actual numbers
        this.leftMotor=new TalonFX(0);
        this.rightMotor=new TalonFX(1);

        config = new TalonFXConfiguration();

        config.MotorOutput.Inverted= InvertedValue.Clockwise_Positive;
        config.MotorOutput.NeutralMode= NeutralModeValue.Brake;

        config.CurrentLimits.SupplyCurrentLimit=10;
        config.CurrentLimits.SupplyCurrentLimitEnable=true;

        config.Slot0.kP=0;
        config.Slot0.kI=0;
        config.Slot0.kD=0;

        tryUntilOk(()->leftMotor.getConfigurator().apply(config));
        tryUntilOk(()->rightMotor.getConfigurator().apply(config));

        rightMotor.setControl(new Follower(leftMotor.getDeviceID(), MotorAlignmentValue.Opposed)); //idk i looked at some code and maybe this is how following works

        this.leftInputs=new TalonInputs(leftMotor.getVelocity(),leftMotor.getMotorVoltage(),leftMotor.getSupplyCurrent(),leftMotor.getDeviceTemp());
        this.rightInputs=new TalonInputs(rightMotor.getVelocity(),rightMotor.getMotorVoltage(),rightMotor.getSupplyCurrent(),rightMotor.getDeviceTemp());



        tryUntilOk(()->BaseStatusSignal.setUpdateFrequencyForAll(50,leftInputs.getFields()));
        tryUntilOk(()->BaseStatusSignal.setUpdateFrequencyForAll(50,rightInputs.getFields()));

        tryUntilOk(()->leftMotor.optimizeBusUtilization(0));
        tryUntilOk(()->rightMotor.optimizeBusUtilization(0));

        this.ff=new SimpleMotorFeedforward(0,0,0);
    }

    @Override
    public void updateInputs(FlywheelIOInputs inputs) {
        inputs.leftVelocity=leftInputs.velocity.getValue();
        inputs.leftVoltage=leftInputs.voltage.getValue();
        inputs.leftCurrent=leftInputs.current.getValue();
        inputs.leftTemp=leftInputs.temp().getValue();
        inputs.leftConnected=leftMotor.isConnected();

        inputs.rightVelocity=rightInputs.velocity.getValue();
        inputs.rightVoltage=rightInputs.voltage.getValue();
        inputs.rightCurrent=rightInputs.current.getValue();
        inputs.rightTemp=rightInputs.temp().getValue();
        inputs.rightConnected=rightMotor.isConnected();
    }

    @Override
    public void runVoltage(Voltage voltage) {
        leftMotor.setControl(voltageController.withOutput(voltage));
    }

    @Override
    public void runVelocity(AngularVelocity velocity) {
        leftMotor.setControl(velocityController.withVelocity(velocity).withFeedForward(ff.calculate(velocity.magnitude())));
    }

    @Override
    public void stop() {
        leftMotor.setControl(neutral);
    }

    @Override
    public void setCurrentLimit(Current current) {
        new Thread( ()-> {
            config.CurrentLimits.SupplyCurrentLimit=current.baseUnitMagnitude();
            tryUntilOk(()->leftMotor.getConfigurator().apply(config));
            tryUntilOk(()->rightMotor.getConfigurator().apply(config));
        });
    }

    @Override
    public void setBreakMode(BreakMode breakMode) {
        new Thread( ()-> {
            config.MotorOutput.NeutralMode=(breakMode==BreakMode.BREAK?NeutralModeValue.Brake:NeutralModeValue.Coast);
            tryUntilOk(()->leftMotor.getConfigurator().apply(config));
            tryUntilOk(()->rightMotor.getConfigurator().apply(config));
        });

    }

    @Override
    public void setPID(double kp, double ki, double kd) {
        new Thread(()-> {
            config.Slot0.kP=kp;
            config.Slot0.kI=ki;
            config.Slot0.kD=kd;
            tryUntilOk(()->leftMotor.getConfigurator().apply(config));
            tryUntilOk(()->rightMotor.getConfigurator().apply(config));
        });

    }

    @Override
    public void setFF(double ks, double kv, double ka) {
        ff.setKs(ks);
        ff.setKv(kv);
        ff.setKa(ka);
    }



    public static boolean tryUntilOk(Supplier<StatusCode> method) {
        for (int i=0;i<5;i++) {
            StatusCode statusCode=method.get();
            if (statusCode.isOK()) return true;
        }
        return false;
    }

    private record TalonInputs(StatusSignal<AngularVelocity> velocity, StatusSignal<Voltage> voltage, StatusSignal<Current> current, StatusSignal<Temperature> temp) {
        public StatusSignal[] getFields() {
            return new StatusSignal[] {velocity,voltage,current,temp};
        }
    }
}
