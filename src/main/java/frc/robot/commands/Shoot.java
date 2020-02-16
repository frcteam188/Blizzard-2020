/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class Shoot extends CommandBase {
  /**
   * Creates a new Shoot.
   */

  private final Intake intake;
  public Shoot(Intake i) {
    // Use addRequirements() here to declare subsystem dependencies.
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
    intake.runShooterFeeder(-1);
    intake.succ(0.30);
    intake.feed(-1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.runShooterFeeder(0);
    intake.succ(0);
    intake.feed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
