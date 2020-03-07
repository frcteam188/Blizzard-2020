/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class FeedFromTrench extends CommandBase {
  /**
   * Creates a new FeedFromTrench.
   */
  private Intake intake;
  private Shooter shooter;
  private double shooterRPM = 4650;
  public FeedFromTrench(Intake i, Shooter s) {
    this.intake = i;
    this.shooter = s;

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is schduled.
  @Override
  public void execute() {
    // if(Math.abs(shooter.getVelShooter() - shooterRPM) < 40){
    //   intake.feed(-0.5);
    //   intake.runShooterFeeder(-0.4);
    //   intake.intake(1);
    // }
    // else{
    //   intake.feed(0);
    //   intake.runShooterFeeder(0);
    //   intake.intake(0);
    // }
    intake.feed(-0.5);
    intake.runShooterFeeder(-0.4);
    intake.intake(1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.feed(0);
    intake.runShooterFeeder(0);
    intake.intake(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
