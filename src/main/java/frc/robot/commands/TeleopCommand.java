/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Base;
/**
 * The teleop command for the robot
 * 
 * @author Shiv Patel
 */
public class TeleopCommand extends CommandBase {

  // makes the objects (subsystems)
  private final Base base;
  private final Joystick stick1;
  /**
   * Creates a new TeleopCommand.
   */
  public TeleopCommand(Base base, Joystick stick1) {
    this.base = base;
    this.stick1 = stick1;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(base);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // using the drive method in the base subsystem to run the base based on the input from the controller's axis
    base.drive(-stick1.getRawAxis(1), stick1.getRawAxis(2));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // stops the base by setting the inputs to 0
    base.drive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
