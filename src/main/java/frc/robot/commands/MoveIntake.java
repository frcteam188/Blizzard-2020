/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;

/**
 * Command to run the intake at the value of pow
 * 
 * @author Zayeed Ghori, Edward Su, Shiv Patel
 */

public class MoveIntake extends CommandBase {
  private final Intake intake;
  private final double pow;
  /**
   * Creates a new MoveIntake.
   */
  public MoveIntake(Intake i, double pow) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.pow = pow;
    this.intake = i;
    addRequirements(i);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.intake(pow);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.intake(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
