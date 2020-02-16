/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

/**
 * 
 */
public class HoodPID extends CommandBase {
  /**
   * Creates a new HoodPID2.
   */

  private int setpoint;
  private Shooter shooter;
  private CANPIDController hoodPIDController;

  public HoodPID(Shooter s, int sP) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.shooter = s;
    this.setpoint = sP;
    hoodPIDController = shooter.getHoodPIDController();

    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    hoodPIDController.setReference(setpoint,ControlType.kPosition);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    hoodPIDController.setReference(0,ControlType.kDutyCycle);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
