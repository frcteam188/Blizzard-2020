/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

public class Winch extends CommandBase {
  /**
   * Creates a new Winch.
   */
  private Hang hang;
  public Winch(Hang h) {
    this.hang = h;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(h);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    hang.pullUp(1);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    hang.moveStageOne(Hang.STATE_IN);
    hang.moveStageTwo(Hang.STATE_IN);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    hang.pullUp(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
