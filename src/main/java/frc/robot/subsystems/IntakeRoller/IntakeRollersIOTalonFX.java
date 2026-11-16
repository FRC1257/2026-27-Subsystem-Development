package frc.robot.subsystems.IntakeRoller;

import static edu.wpi.first.units.Units.Amps;
import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.NeutralOut;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.ParentDevice;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

public class IntakeRollersIOTalonFX implements IntakeRollersIO {
    private static final double GEAR_RATIO = 0; // motor rotations per roller rotation
    private static final double UPDATE_HZ = 0;

    private final TalonFX leader;
    private final TalonFX follower;

    private final VoltageOut voltageRequest = new VoltageOut(0).withEnableFOC(false);
    private final NeutralOut neutralRequest = new NeutralOut();

    private final StatusSignal<Angle> leaderPosition;
    private final StatusSignal<AngularVelocity> leaderVelocity;
    private final StatusSignal<Voltage> leaderVolts;
    private final StatusSignal<Current> leaderCurrent;
    private final StatusSignal<Temperature> leaderTemp;

    private final StatusSignal<Angle> followerPosition;
    private final StatusSignal<AngularVelocity> followerVelocity;
    private final StatusSignal<Voltage> followerVolts;
    private final StatusSignal<Current> followerCurrent;
    private final StatusSignal<Temperature> followerTemp;

    public IntakeRollersIOTalonFX(int leaderId, int followerId, CANBus bus) {
        leader = new TalonFX(leaderId, bus);
        follower = new TalonFX(followerId, bus);

        leaderPosition = leader.getPosition();
        leaderVelocity = leader.getVelocity();
        leaderVolts = leader.getMotorVoltage();
        leaderCurrent = leader.getStatorCurrent();
        leaderTemp = leader.getDeviceTemp();

        followerPosition = follower.getPosition();
        followerVelocity = follower.getVelocity();
        followerVolts = follower.getMotorVoltage();
        followerCurrent = follower.getStatorCurrent();
        followerTemp = follower.getDeviceTemp();

    }
}

