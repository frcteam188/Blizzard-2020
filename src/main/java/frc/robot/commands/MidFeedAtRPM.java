/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMath;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class MidFeedAtRPM extends CommandBase {
  /**
   * Creates a new RunFeedAtRPM.
   */
  private Shooter shooter;
  private double shooterSetpoint;
  private Intake intake;
  private double feedingSpeed;
  
  public MidFeedAtRPM(Shooter s, Intake i, double fS) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.shooter = s;
    this.intake = i;
    this.feedingSpeed = fS;
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooterSetpoint = RobotMath.getVelFromDistance(shooter);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Math.abs(shooter.getVelShooter() - shooterSetpoint) < 50){
      intake.runShooterFeeder(feedingSpeed);
      intake.intake(0.30);
      intake.feed(feedingSpeed);
    }
    else{
      intake.runShooterFeeder(0);
      intake.intake(0);
      intake.feed(0);
    }
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
