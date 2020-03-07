/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Base;
import edu.wpi.first.wpilibj.controller.PIDController;

public class StandStill extends CommandBase {
  private Base base;
  private double angleSetpoint;
  private double distanceSetpoint;
  private double gyroOutput;
  private double driveOutput;
  private double drivePow;

  public PIDController stillGyroPID = new PIDController(
    Constants.kStillGyroP,
    Constants.kStillGyroI,
    Constants.kStillGyroD
  );

  public PIDController drivePID = new PIDController(
    Constants.kStillBaseP,
    Constants.kStillBaseI,
    Constants.kStillBaseD
  );

  /**
   * Creates a new DriveStraight. *UNFINISHED*
   */
  public StandStill(Base b, double dPow) {
    this.base = b;
    this.drivePow = dPow;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    angleSetpoint = base.getBaseAngle();

    distanceSetpoint = base.getFrontLeftEnc().getPosition();
    
    // Reporting
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    gyroOutput = stillGyroPID.calculate(base.getBaseAngle(), angleSetpoint);
    driveOutput = drivePID.calculate(base.getFrontLeftEnc().getPosition(), distanceSetpoint);


    if(driveOutput > 0) driveOutput = Math.min(driveOutput, drivePow);
    else driveOutput = Math.max(driveOutput, -drivePow);

    if(gyroOutput > 0) gyroOutput = Math.min(gyroOutput, 0.35);
    else gyroOutput = Math.max(gyroOutput, -0.35);
    System.out.println("Drive out: " + driveOutput);

    // output = gyroOutput + driveOutput;
    base.drive(driveOutput, gyroOutput);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    base.drive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}