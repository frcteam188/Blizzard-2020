/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class TurnTurret extends CommandBase {
  private Shooter shooter;
  private double angle;
  private double turretDeadZone = 5;
  /**
   * Creates a new TurnTurret.
   */
  public TurnTurret(Shooter s, double angle) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.shooter = s;
    this.angle = angle;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Move Turret to middle
    if (shooter.getTurretAngle() > angle + turretDeadZone) {
      shooter.moveTurret(-0.5);
    } else if (shooter.getTurretAngle() < angle - turretDeadZone) {
      shooter.moveTurret(0.5);
    } else {
      shooter.moveTurret(0);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.moveTurret(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return shooter.getTurretAngle() < angle + turretDeadZone && 
    shooter.getTurretAngle() > angle - turretDeadZone;
  }
}
