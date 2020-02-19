/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hang;


/**
 * This command will shoot the hang pistons when called
 * 
 * @author Shiv Patel
 */
public class DeployHang extends CommandBase {
  /**
   * Creates a new DeployHang.
   * 
   */
  public Hang hang;

  public DeployHang(Hang h) {
    this.hang = h;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(h);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    hang.moveStageOne(Hang.STATE_OUT);
    // wait(1000, 0);
    hang.moveStageTwo(Hang.STATE_OUT);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
