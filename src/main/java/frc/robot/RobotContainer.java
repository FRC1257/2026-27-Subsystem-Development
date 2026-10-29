
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import static frc.robot.util.drive.DriveControls.*;

import java.io.Flushable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.util.drive.CommandSnailController;
import frc.robot.util.drive.CommandSnailController.DPad;

import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;




/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems


  public static final CommandSnailController driver = new CommandSnailController(0);
  public static final CommandSnailController operator = new CommandSnailController(1);


  // Dashboard inputs


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    //list of all the various vision IO implementations



    switch (Constants.currentMode) {
      // Real robot, instantiate hardware IO implementations
      case REAL:
        

        //photonVision = new VisionIOPhoton();
    


        break;

      // Sim robot, instantiate physics sim IO implementations
      case SIM:


        break;

      // Replayed robot, disable IO implementations
      default:
 


        break;
    }





    // Set up robot state manager


    // Set up auto routines
    /* NamedCommands.registerCommand(
    "Run Flywheel",
    Commands.startEnd(
            () -> flywheel.runVelocity(flywheelSpeedInput.get()), flywheel::stop, flywheel)
        .withTimeout(5.0)); */
   
            
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  public void configureButtonBindings() {
    configureControls();


  }

 


    


  




  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */


} 

