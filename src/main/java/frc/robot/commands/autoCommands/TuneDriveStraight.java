/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autoCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Base;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TuneDriveStraight extends CommandBase {
  private Base base;
  private double angleSetpoint;
  private double distanceSetpoint;
  private double gyroOutput;
  private double driveOutput;
  private double output;

  public PIDController gyroPID = new PIDController(
    Constants.kDriveTurnP,
    Constants.kDriveTurnI,
    Constants.kDriveTurnD
  );

  public PIDController drivePID = new PIDController(
    Constants.kBaseP,
    Constants.kBaseI,
    Constants.kBaseD
  );

  /**
   * Creates a new DriveStraight. *UNFINISHED*
   */
  public TuneDriveStraight(Base b, double distInches, double aS) {
    this.base = b;
    this.angleSetpoint = aS;
    this.distanceSetpoint = distInches;

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    // Reporting
    SmartDashboard.putNumber("Drive Turn P", Constants.kDriveTurnP);
    SmartDashboard.putNumber("Drive Turn I", Constants.kDriveTurnI);
    SmartDashboard.putNumber("Drive Turn D", Constants.kDriveTurnD);

    SmartDashboard.putNumber("Drive P", Constants.kBaseP);
    SmartDashboard.putNumber("Drive I", Constants.kBaseI);
    SmartDashboard.putNumber("Drive D", Constants.kBaseD);

    SmartDashboard.putNumber("Drive Setpoint", this.distanceSetpoint);
    SmartDashboard.putNumber("Angle Setpoint", this.angleSetpoint);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Makes the PID tuneable

    gyroPID.setPID(
      SmartDashboard.getNumber("Turn P", Constants.kDriveTurnP),
      SmartDashboard.getNumber("Turn I", Constants.kDriveTurnI),
      SmartDashboard.getNumber("Turn D", Constants.kDriveTurnD)
    );
    drivePID.setPID(
      SmartDashboard.getNumber("Drive P", Constants.kBaseP),
      SmartDashboard.getNumber("Drive I", Constants.kBaseI),
      SmartDashboard.getNumber("Drive D", Constants.kBaseD)
    );

    this.angleSetpoint = SmartDashboard.getNumber("Angle Setpoint", angleSetpoint);

    this.distanceSetpoint = SmartDashboard.getNumber("Drive Setpoint", distanceSetpoint);
  
    gyroOutput = gyroPID.calculate(base.getBaseAngle(), angleSetpoint);
    driveOutput = drivePID.calculate(base.getFrontLeftEnc().getPosition(), distanceSetpoint);

    if(driveOutput > 0) driveOutput = Math.min(driveOutput, 0.65);
    else driveOutput = Math.max(driveOutput, -0.65);

    if(gyroOutput > 0) gyroOutput = Math.min(gyroOutput, 0.35);
    else gyroOutput = Math.max(gyroOutput, -0.35);

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
