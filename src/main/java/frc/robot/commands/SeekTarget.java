/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

/**
 * Command that oscillates turret until a target is found
 * @author Zayeed Ghori
 */
public class SeekTarget extends CommandBase {
  private Shooter shooter;
  private boolean isFound;
  private int range = 25;

  /**
   * Creates a new SeekTarget.
   * @param s - Shooter subsystem
   */
  public SeekTarget(Shooter s) {
    this.shooter = s;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(shooter.getTargetIsVisible()){
      isFound = true;
      return;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // oscillates turret until target is found
    if(shooter.getTargetIsVisible()){
      isFound = true;
      return;
    }
    else if(shooter.getTurretAngle() < range)
      shooter.moveTurret(0.5);
    else if(shooter.getTurretAngle() > -range)
      shooter.moveTurret(-0.5);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.moveTurret(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isFound;
  }
}
