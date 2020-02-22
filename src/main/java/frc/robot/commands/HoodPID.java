/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotMath;
import frc.robot.subsystems.Shooter;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command will run the PID on the hood
 * 
 * @author Edward Su
 */
public class HoodPID extends CommandBase {
  /**
   * Creates a new HoodPID2.
   */

  private double setpoint;
  private Shooter shooter;
  private CANPIDController hoodPIDController;

  public HoodPID(Shooter s, double sp) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.shooter = s;
    SmartDashboard.putNumber("Hood Setpoint: ", 0);
    this.setpoint = sp;
    hoodPIDController = shooter.getHoodPIDController();
    
  }

  public HoodPID(Shooter s){
    this.shooter = s; 
    this.setpoint = RobotMath.getDistanceFromTarget(shooter);
    hoodPIDController = shooter.getHoodPIDController();
  }

  public void setSetpoint(double setpoint) {
    this.setpoint = setpoint;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putNumber("Hood Setpoint: ", setpoint);
    hoodPIDController.setReference(setpoint,ControlType.kPosition);
    hoodPIDController.setP(Constants.kHoodP);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // setpoint = SmartDashboard.getNumber("Hood Setpoint: ", setpoint);
    hoodPIDController.setReference(setpoint, ControlType.kPosition);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    hoodPIDController.setReference(0,ControlType.kPosition);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
