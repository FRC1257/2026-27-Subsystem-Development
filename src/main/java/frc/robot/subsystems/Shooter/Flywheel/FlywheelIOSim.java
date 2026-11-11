package frc.robot.subsystems.Shooter.Flywheel;

import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Amps;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;



public class FlywheelIOSim implements FlywheelIO {

    private final DCMotor m_flywheelGearbox = DCMotor.getFalcon500(2);
    
    // Example FlywheelSim using WPILib's LinearSystemId helper
    private final FlywheelSim sim = new FlywheelSim(
        LinearSystemId.createFlywheelSystem(
            m_flywheelGearbox, 
            0.002, // Moment of inertia (kg*m^2)
            1.0    // Gearing reduction
        ),
        m_flywheelGearbox
    );

    private Voltage appliedVolts = Volts.of(0.0);
    private double cumulativeAngleDeg = 0.0;

    // --- Mechanism2d Setup ---
    // 1. Create canvas 
    private final Mechanism2d m_mech = new Mechanism2d(1.0, 1.0);
    
    // 2. Add root point at the center of the canvas
    private final MechanismRoot2d m_root = m_mech.getRoot("FlywheelRoot", 0.5, 0.5);
    
    // 3. Create a rotating wheel ligament indicator (name, length, initial angle, line weight, color)
    private final MechanismLigament2d m_wheelLigament = m_root.append(
        new MechanismLigament2d(
            "FlywheelWheel", 
            0.3, // Radius/length of the visual bar
            0.0, 
            8, 
            new Color8Bit(Color.kBlue)
        )
    );

    public FlywheelIOSim() {
        // Publish Mechanism2d structure to SmartDashboard / Glass
        SmartDashboard.putData("Flywheel Sim", m_mech);
    }

    @Override
    public void updateInputs(FlywheelIOInputs inputs) {
        // Update simulation state with 20ms timestep
        sim.setInput(appliedVolts.in(Volts));
        sim.update(0.02);

        // Accumulate rotation angle based on velocity (rad/s * seconds)
        cumulativeAngleDeg += Math.toDegrees(sim.getAngularVelocityRadPerSec() * 0.02);
        cumulativeAngleDeg %= 360.0; // Keep it clean between 0-360 degrees

        inputs.flywheelLeaderVoltage = appliedVolts;
        inputs.flywheelLeaderAngularVelocity = RadiansPerSecond.of(sim.getAngularVelocityRadPerSec());

        // Update visual spin angle on Mechanism2d
        m_wheelLigament.setAngle(cumulativeAngleDeg);
    }

    @Override
    public void setVoltage(Voltage volts) {
        appliedVolts = volts;
        sim.setInput(appliedVolts.in(Volts));
    }
}