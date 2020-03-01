/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.Constants;

public class ShooterPID extends CommandBase {
  /**
   * Creates a new ShooterPID.
   */
  public double pidOutput;
  public double setpoint;
  public PIDController pidController;

  private Shooter shooter;
  public ShooterPID(Shooter s, double sP) {
    this.shooter = s;
    this.setpoint = sP;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.

  public double getF(){
    return pidController.getSetpoint() * Constants.kShooterF;
  }
  @Override
  public void initialize() {
    // pidController = new PIDController(SmartDashboard.getNumber("Shooter P: ", Constants.kShooterP),
    //                 SmartDashboard.getNumber("Shooter I: ", Constants.kShooterI), 
    //                 SmartDashboard.getNumber("Shooter D: ", Constants.kShooterD));
    pidController = new PIDController(Constants.kShooterP,Constants.kShooterI, Constants.kShooterD);
    pidController.setIntegratorRange(-1, 1);
    // shooter.setLimelightLED(Shooter.LED_ON);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    pidOutput = pidController.calculate(shooter.getVelShooter(), setpoint);

    shooter.shoot(pidOutput + getF());

    // shooter.setLimelightLED(Shooter.LED_ON);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.shoot(0);
    // shooter.setLimelightLED(Shooter.LED_OFF);

    // Colours always white, blinking yellow, solid red
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
