/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.*;
import frc.robot.Constants;
import frc.robot.RobotMath;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Prints data on the SmartDashboard
 * 
 * @author Zayeed Ghori, Shiv Patel
 */
public class SmartDashboardPrints extends CommandBase {
  /**
   * Creates a new SmartDashboardPrints.
   */
  private final Shooter shooter;
  private final Intake intake;
  private final Hang hang; 
  private final Base base;
  public SmartDashboardPrints(Shooter s, Intake i, Hang h, Base b) {
    this.shooter = s;
    this.intake = i;
    this.hang = h;
    this.base = b;
    // Use addRequirements() here to declare subsystem dependencies.

  }

  @Override
  public boolean runsWhenDisabled() {
    // TODO Auto-generated method stub
    return true;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putNumber("Turret P: ", Constants.kTurretP);
    SmartDashboard.putNumber("Turret I: ", Constants.kTurretI);
    SmartDashboard.putNumber("Turret D: ", Constants.kTurretD);
    SmartDashboard.putNumber("Turret Setpoint: ", 0);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Put the limelight angle as a number and the turret angle (in angles, not ticks)
    SmartDashboard.putNumber("Limelight Angle:", shooter.getLimelightX());
    SmartDashboard.putNumber("Turret Angle", shooter.getTurretAngle());

    // Put the limelight angle x as a graph and the angle of the base on SmartDashboard
    SmartDashboard.putNumber("Limelight Angle Graph", shooter.getLimelightX());
    SmartDashboard.putNumber("Angle of Base: ", base.getBaseAngle());

    // Shooter Stuff (Actual PID values are printed and modified in
    // TuneShooterPID.java)
    SmartDashboard.putNumber("Shooter RPM:", shooter.getVelShooter());
    SmartDashboard.putNumber("Shooter RPM Graph", shooter.getVelShooter());

    SmartDashboard.putNumber("Base leftFront Current: ", base.getLeftFront().getOutputCurrent());
    SmartDashboard.putNumber("Base leftBack Current: ", base.getLeftBack().getOutputCurrent());
    SmartDashboard.putNumber("Base rightFront Current: ", base.getRightFront().getOutputCurrent());
    SmartDashboard.putNumber("Base rightBack Current: ", base.getRightBack().getOutputCurrent());

    SmartDashboard.putNumber("Hood Pos", shooter.getHoodPos());

    SmartDashboard.putNumber("Drive Ticks: ", base.getFrontLeftEnc().getPosition());
    SmartDashboard.putNumber("Hood Setpoint: ", RobotMath.getMagicHoodAngleFromDistance(shooter));
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
