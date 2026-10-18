package frc.robot.subsystems.Hopper.HopperPivot;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;

public class HopperPivotIOSim implements HopperPivotIO {

    private final DCMotor m_armGearbox = DCMotor.getNEO(2);
    private final ArmFeedforward feedforward = new ArmFeedforward(0, 0.0, 4);
    private final PIDController controller = new PIDController(10, 0, 0);

    private Voltage appliedVolts = Volts.of(0.0);

    private SingleJointedArmSim sim = 
        new SingleJointedArmSim(
            m_armGearbox,
            HopperPivotConstants.HopperPivotSimConstants.kArmReduction,
            SingleJointedArmSim.estimateMOI(
                HopperPivotConstants.HopperPivotSimConstants.kArmLength, 
                HopperPivotConstants.HopperPivotSimConstants.kArmMass),
            HopperPivotConstants.HopperPivotSimConstants.kArmLength,
            HopperPivotConstants.HopperPivotSimConstants.kArmAngleMin,
            HopperPivotConstants.HopperPivotSimConstants.kArmAngleMax,
            false,
            0
        );

    // --- Mechanism2d Setup ---
    // 1. Create canvas (width x height in meters or arbitrary canvas units)
    private final Mechanism2d m_mech = new Mechanism2d(1.0, 1.0);
    
    // 2. Add root pivot point at center bottom of canvas
    private final MechanismRoot2d m_root = m_mech.getRoot("HopperPivotRoot", 0.5, 0.2);
    
    // 3. Create the arm ligament (name, length, initial angle in degrees, line weight, color)
    private final MechanismLigament2d m_armLigament = m_root.append(
        new MechanismLigament2d(
            "HopperArm", 
            HopperPivotConstants.HopperPivotSimConstants.kArmLength, 
            Math.toDegrees(sim.getAngleRads()),
            6, 
            new Color8Bit(Color.kRed)
        )
    );

    public HopperPivotIOSim() {
        // Publish Mechanism2d structure to SmartDashboard
        SmartDashboard.putData("Hopper Pivot Sim", m_mech);
    }

    @Override
    public void updateInputs(HopperPivotIOInputs inputs) { 
        sim.update(0.02);
        
        inputs.leftpivotVoltage = appliedVolts;
        inputs.leftpivotAngle = Radians.of(sim.getAngleRads());
        inputs.leftpivotVelocity = RadiansPerSecond.of(sim.getVelocityRadPerSec());

        // Update visual angle on Mechanism2d (expects degrees)
        m_armLigament.setAngle(Math.toDegrees(sim.getAngleRads()));
    }

    @Override
    public void runVoltage(Voltage volts) {
        sim.setInputVoltage(volts.in(Volts));
        appliedVolts = volts;
    }

    @Override
    public void runAngle(double angle, double velocity) {
        runVoltage(Volts.of(controller.calculate(sim.getAngleRads(), angle) + feedforward.calculate(angle, velocity)));
    }

    @Override
    public void stop() {
        sim.setInputVoltage(0);
        appliedVolts = Volts.of(0.0);
    }
}