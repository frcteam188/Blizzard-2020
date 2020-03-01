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

public class HoodGetToPos extends CommandBase {
  private Shooter shooter;
  private double hoodDeadZone;
  private double hoodSp;
  private int count;
  /**
   * Creates a new HoodGetToPos.
   */
  public HoodGetToPos(Shooter s) {
    this.shooter = s;
    hoodDeadZone = 5;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    hoodSp = RobotMath.getHoodPosFromDistance(shooter);
    count = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    hoodSp = RobotMath.getHoodPosFromDistance(shooter);

    if(Math.abs(shooter.getHoodPos() - hoodSp) < hoodDeadZone){
      count += 1;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return count > 5;
  }
}
