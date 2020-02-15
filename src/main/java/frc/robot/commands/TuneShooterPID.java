/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class TuneShooterPID extends CommandBase {
  /**
   * Creates a new runShooterPID.
   */

  private final Shooter shooter;
  CANPIDController pidControllerLeft;
  CANPIDController pidControllerRight;

  double kP;
  double kI;
  double kD;
  double kF;
  double kMaxOutput;
  double kMinOutput;
  double kIz;

  public TuneShooterPID(Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependenacies.

    this.shooter = shooter;
    pidControllerLeft = shooter.getShooterLeft().getPIDController();


    kP = Constants.kShooterP;
    kI = Constants.kShooterI;
    kD = Constants.kShooterD;
    kMaxOutput = Constants.kShooterMaxOutput;
    kMinOutput = Constants.kShooterMinOutput;
    kF = Constants.kShooterF;
    kIz = Constants.kShooterIZone; 

    pidControllerLeft.setP(kP);
    pidControllerLeft.setI(kI);
    pidControllerLeft.setD(kD);
    pidControllerLeft.setFF(kF);
    pidControllerLeft.setOutputRange(kMinOutput, kMaxOutput);
    pidControllerLeft.setIZone(kIz);
    
    pidControllerRight = pidControllerLeft;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putNumber("Shooter P: ", kP);
    SmartDashboard.putNumber("Shooter I: ", kI);
    SmartDashboard.putNumber("Shooter D: ", kD);
    SmartDashboard.putNumber("Shooter F: ", kF);
    SmartDashboard.putNumber("Max Output: ", kMaxOutput);
    SmartDashboard.putNumber("Min Output: ", kMinOutput);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double setPoint = Constants.shooterSetpoint;
    pidControllerLeft.setReference(setPoint, ControlType.kVelocity);

    pidControllerRight = pidControllerLeft;
    pidControllerRight.setReference(-setPoint, ControlType.kVelocity);

    double p = SmartDashboard.getNumber("P Gain", kP);
    double i = SmartDashboard.getNumber("I Gain", kI);
    double d = SmartDashboard.getNumber("D Gain", kD);
    double iz = SmartDashboard.getNumber("I Zone", kIz);
    double f = SmartDashboard.getNumber("Feed Forward", kF);
    double max = SmartDashboard.getNumber("Max Output", kMaxOutput);
    double min = SmartDashboard.getNumber("Min Output", kMinOutput);

    // if PID coefficients on SmartDashboard have changed, write new values to controller
    if((p != kP)) { pidControllerLeft.setP(p); kP = p; }
    if((i != kI)) { pidControllerLeft.setI(i); kI = i; }
    if((d != kD)) { pidControllerLeft.setD(d); kD = d; }
    if((iz != kIz)) { pidControllerLeft.setIZone(iz); kIz = iz; }
    if((f != kF)) { pidControllerLeft.setFF(f); kF = f; }
    if((max != kMaxOutput) || (min != kMinOutput)) { 
      pidControllerLeft.setOutputRange(min, max);
      kMinOutput = min; kMaxOutput = max;
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
