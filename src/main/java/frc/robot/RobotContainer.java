/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.TeleopCommand;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // Creating a base object
  private final Base base = new Base();
  private final Intake intake = new Intake();
  private final Hang hang = new Hang();
  private final Shooter shooter = new Shooter();

  // creating a new stick (controller) which will be the DRIVER controller (port 0)
  private Joystick stick1 = new Joystick(0);
  private JoystickButton rbBtn1 = new JoystickButton(stick1, 6);
  private JoystickButton rtBtn1 = new JoystickButton(stick1, 8);

  // constructor for teleopCommand
  private final TeleopCommand teleopCommand = new TeleopCommand(base, intake, hang, shooter, stick1, rbBtn1, rtBtn1);






  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  }


  /**
   * Returns the teleop command
   * 
   * @author Shiv Patel
   * @return the command to run in teleop
   */
  public Command getTeleopCommand(){
    return teleopCommand;
  }
}
