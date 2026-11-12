package frc.robot.subsystems.Roller;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Volts;

import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;

import edu.wpi.first.units.measure.Voltage;

/*Steps for Hardware Implementation
 * STEP 1 - imports
 * STEP 2 - define objects
 * STEP 3 Constructor
 * STEP 4 - inputs
 * STEP - overriding methods
 */
public class RollerIOSparkMax implements RollerIO {
    private SparkMax rollerMotor;
    private SparkMaxConfig rollerConfig;

    private SimpleMotorFeedforward feedforward;//check

    public RollerIOSparkMax() {
        rollerMotor = new SparkMax(0, MotorType.kBrushless);

        rollerConfig = new SparkMaxConfig();

        rollerConfig
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(60)
            .inverted(false);
        rollerConfig
            .encoder
            .positionConversionFactor(5*Math.PI*1/18)//idk
            .velocityConversionFactor((10* Math.PI*1/18) / 60.0);//idk
        rollerConfig
            .closedLoop
            .pid(1, 2,3);
    rollerMotor.configure(rollerConfig, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters); 
    feedforward = new SimpleMotorFeedforward(4,5,6);
    }
    //new
    @Override
    public void updateInputs(RollerIOInputs inputs) {
        inputs.rollerVoltage = Volts.of(rollerMotor.getAppliedOutput() *12);
        //velocity?
        inputs.rollerCurrent = Amps.of(rollerMotor.getOutputCurrent());
        inputs.rollerConnected = rollerMotor.getLastError() == REVLibError.kOk;
    }

    @Override
    public void runVoltage(Voltage volts) {
        rollerMotor.setVoltage(volts);
    }

    @Override
    public void stop() {
        rollerMotor.stopMotor();
        //simple since REVLib's method for this is literally js stopMotor
    }

    @Override
        public void setPID(double kP, double kI, double kD) {
        rollerMotor.configure(
            new SparkMaxConfig()
            .apply(new ClosedLoopConfig()
                .p(kP)
                .i(kI)
                .d(kD)),
            ResetMode.kNoResetSafeParameters,
            PersistMode.kPersistParameters);
        }
        //available in documentation; telling the motor's config to reset its closed loop with THESE PID values rather than what we defined earlier

    @Override
        public void setFeedforward(double ks, double kv) {
            feedforward.setKs(ks);
            feedforward.setKv(kv);
        }
    }