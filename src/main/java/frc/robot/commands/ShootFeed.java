/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

/**
 * This command will run the feeder, the intake, and the shooter feeder in unison
 * This command is only to be run when the shooter is at optimal speeds
 * 
 * @author Zayeed Ghori, Edward Su, Shiv Patel
 */
public class ShootFeed extends CommandBase {
  public double feederSpeed = -0.6;
  public double shooterLimit; 
  public double intakeSpeed = 0.3;
  /**
   * Creates a new Shoot.
   */
  public double speed;
  private final Intake intake;
  public ShootFeed(Intake i, double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.intake = i;
    this.speed = speed;
    addRequirements(i);    
  }

  public ShootFeed(Intake i, double speed, double intakeSpeed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.intake = i;
    this.speed = speed;
    this.intakeSpeed = intakeSpeed;
    addRequirements(i);    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.runShooterFeeder(-0.9);
    intake.intake(intakeSpeed);
    intake.feed(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.runShooterFeeder(0);
    intake.intake(0);
    intake.feed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
