/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMath;
import frc.robot.subsystems.Shooter;

public class ShooterGetToSpeed extends CommandBase {
  /**
   * Creates a new ShooterGetToSpeed.
   */
  private Shooter shooter;
  private double shooterLimit;
  public ShooterGetToSpeed(Shooter s) {
    this.shooter = s;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooterLimit = RobotMath.getVelFromDistance(shooter);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooterLimit = RobotMath.getVelFromDistance(shooter);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(shooter.getVelShooter() - shooterLimit) < 80;
  }
}
