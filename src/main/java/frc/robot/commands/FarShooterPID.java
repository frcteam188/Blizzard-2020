/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotMath;
import frc.robot.subsystems.Shooter;

public class FarShooterPID extends CommandBase {
  /**
   * Creates a new MidShooterPID.
   */
  public double pidOutput;
  public PIDController pidController;
  private Shooter shooter;
  public FarShooterPID(Shooter s) {
    this.shooter = s;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  public double getF(){
    return pidController.getSetpoint() * Constants.kShooterF;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    pidController = new PIDController(Constants.kShooterP, Constants.kShooterI, Constants.kShooterD);
    pidController.setIntegratorRange(-1, 1);
    // shooter.setLimelightLED(Shooter.LED_ON);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    pidOutput = pidController.calculate(shooter.getVelShooter(), RobotMath.getMagicVelocityFromDistance(shooter));
    shooter.shoot(pidOutput + getF());
    // shooter.shoot(0.78);
    shooter.setLimelightLED(Shooter.LED_ON);
    // if(shooter.getVelShooter() - pidController.getSetpoint() < 5){
    //   pidController.reset();
    // }
    if(shooter.getVelShooter() - pidController.getSetpoint() < -600){
      pidController.reset();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.shoot(0);
    // shooter.setLimelightLED(Shooter.LED_OFF);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
