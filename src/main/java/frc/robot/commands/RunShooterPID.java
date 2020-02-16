/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class RunShooterPID extends CommandBase {
  /**
   * Creates a new runShooterPID.
   */

  private final Shooter shooter;
  private int setpoint;
  private CANPIDController PIDControllerLeft;
  private CANPIDController PIDControllerRight;


  

  public RunShooterPID(Shooter shooter,int sP) {
    // Use addRequirements() here to declare subsystem dependenacies.

    this.shooter = shooter;
    this.setpoint = sP;
    this.PIDControllerLeft = shooter.getShooterLeftPIDController();
    this.PIDControllerRight = shooter.getShooterRightPIDController();

    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    PIDControllerLeft.setReference(setpoint, ControlType.kVelocity);
    PIDControllerRight.setReference(-setpoint, ControlType.kVelocity);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    PIDControllerLeft.setReference(0,ControlType.kDutyCycle);
    PIDControllerRight.setReference(0,ControlType.kDutyCycle);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
