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

/**
 * Use this class to tune the shooterPID
 * 
 * @author Edward Su, Zayeed Ghori
 */

public class TuneShooterPID extends CommandBase {
  /**
   * Creates a new tuneShooterPID.
   */

  private final Shooter shooter;
  private double setpoint = 2860;
  private CANPIDController pidControllerLeft;
  private CANPIDController pidControllerRight;

  double kP;
  double kI;
  double kD;
  double kF;
  double kMaxOutput;
  double kMinOutput;
  double kIz;

  public TuneShooterPID(Shooter shooter, int sP) {
    // Use addRequirements() here to declare subsystem dependenacies.

    this.shooter = shooter;
    this.setpoint = sP;
    this.pidControllerLeft = shooter.getShooterLeftPIDController();
    this.pidControllerRight = shooter.getShooterRightPIDController();


    kP = Constants.kShooterP;
    kI = Constants.kShooterI;
    kD = Constants.kShooterD;
    kMaxOutput = Constants.kShooterMaxOutput;
    kMinOutput = Constants.kShooterMinOutput;
    kF = Constants.kShooterF;
    kIz = Constants.kShooterIZone; 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // SmartDashboard.putNumber("Shooter P: ", kP);
    // SmartDashboard.putNumber("Shooter I: ", kI);
    // SmartDashboard.putNumber("Shooter D: ", kD);
    // SmartDashboard.putNumber("Shooter F: ", kF);
    // SmartDashboard.putNumber("Max Output: ", kMaxOutput);
    // SmartDashboard.putNumber("Min Output: ", kMinOutput);
    SmartDashboard.putNumber("Shooter Setpoint: ", setpoint);

    // shooter.setLimelightLED(Shooter.LED_ON);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    pidControllerLeft.setReference(-setpoint, ControlType.kVelocity);
    pidControllerRight.setReference(setpoint, ControlType.kVelocity);

    double p = SmartDashboard.getNumber("Shooter P: ", kP);
    double i = SmartDashboard.getNumber("Shooter I: ", kI);
    double d = SmartDashboard.getNumber("Shooter D: ", kD);
    double iz = SmartDashboard.getNumber("Shooter I Zone: ", kIz);
    double f = SmartDashboard.getNumber("Shooter F: ", kF);
    double max = SmartDashboard.getNumber("Max Output: ", kMaxOutput);
    double min = SmartDashboard.getNumber("Min Output: ", kMinOutput);
    double sP = SmartDashboard.getNumber("Shooter Setpoint: ", setpoint);

    // if PID coefficients on SmartDashboard have changed, write new values to controller
    if((p != kP)) { 
      pidControllerLeft.setP(p); 
      pidControllerRight.setP(p);
      kP = p; 
    }
    if((i != kI)) { 
      pidControllerLeft.setI(i);
      pidControllerRight.setI(i);
      kI = i; 
    }
    if((d != kD)) { 
      pidControllerLeft.setD(d); 
      pidControllerRight.setD(d);
      kD = d; 
    }
    if((iz != kIz)) { 
      pidControllerLeft.setIZone(iz); 
      pidControllerRight.setIZone(iz);
      kIz = iz; 
    }
    if((f != kF)) { 
      pidControllerLeft.setFF(f); 
      pidControllerRight.setFF(f);
      kF = f; 
    }
    if((sP != setpoint)){
      pidControllerLeft.setReference(-sP, ControlType.kVelocity);
      pidControllerRight.setReference(sP, ControlType.kVelocity);
      setpoint = sP;
    }
    if((max != kMaxOutput) || (min != kMinOutput)) { 
      pidControllerLeft.setOutputRange(min, max);
      pidControllerRight.setOutputRange(min, max);
      kMinOutput = min; kMaxOutput = max;
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Ended shooter PID");
    // shooter.setLimelightLED(Shooter.LED_OFF);
    shooter.shoot(0);
  }


  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
