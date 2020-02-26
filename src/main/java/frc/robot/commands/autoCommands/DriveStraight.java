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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraight extends CommandBase {
  private Base base;
  private double angleSetpoint;
  private double distanceSetpoint;
  private double gyroOutput;
  private double driveOutput;
  private double output;

  public PIDController gyroPID = new PIDController(
    Constants.kTurnBaseP,
    Constants.kTurnBaseI,
    Constants.kTurnBaseD
  );

  public PIDController drivePID = new PIDController(
    Constants.kBaseP,
    Constants.kBaseI,
    Constants.kBaseD
  );

  /**
   * Creates a new DriveStraight. *UNFINISHED*
   */
  public DriveStraight(Base b, double distInches) {
    this.base = b;

    this.distanceSetpoint = distInches;
    base.getFrontLeftEnc().setPositionConversionFactor(1);

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    // angle setpoint is equal to current base angle
    angleSetpoint = base.getBaseAngle();
    
    // Reporting
    SmartDashboard.putNumber("Turn P: ", Constants.kTurnBaseP);
    SmartDashboard.putNumber("Turn I: ", Constants.kTurnBaseI);
    SmartDashboard.putNumber("Turn D: ", Constants.kTurnBaseD);

    SmartDashboard.putNumber("Drive P", Constants.kBaseP);
    SmartDashboard.putNumber("Drive I: ", Constants.kBaseI);
    SmartDashboard.putNumber("Drive D: ", Constants.kBaseD);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Makes the PID tuneable
    gyroPID.setPID(
      SmartDashboard.getNumber("Turn P: ", Constants.kTurnBaseP),
      SmartDashboard.getNumber("Turn I: ", Constants.kTurnBaseI),
      SmartDashboard.getNumber("Turn D: ", Constants.kTurnBaseD)
    );

    drivePID.setPID(
      SmartDashboard.getNumber("Drive P", Constants.kBaseP),
      SmartDashboard.getNumber("Drive I: ", Constants.kBaseI),
      SmartDashboard.getNumber("Drive D: ", Constants.kBaseD)
    );

    gyroOutput = gyroPID.calculate(base.getBaseAngle(), angleSetpoint) * 0.3;
    driveOutput = drivePID.calculate(base.getFrontLeftEnc().getPosition(), distanceSetpoint) * 0.7;

    output = gyroOutput + driveOutput;

    base.drive(output, -output);
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
