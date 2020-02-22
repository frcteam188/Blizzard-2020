/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotMath;
import frc.robot.subsystems.Shooter;

public class MidHoodPID extends CommandBase {

  private Shooter shooter;
  private CANPIDController hoodPIDController;
  /**
   * Creates a new MidHoodPID.
   */

  public MidHoodPID(Shooter s){
    this.shooter = s; 
    hoodPIDController = shooter.getHoodPIDController();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    hoodPIDController.setP(Constants.kHoodP);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    hoodPIDController.setReference(RobotMath.getHoodPosFromDistance(shooter), ControlType.kPosition);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    hoodPIDController.setReference(0, ControlType.kPosition);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
