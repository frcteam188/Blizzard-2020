/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class AutoIntake extends CommandBase {
  /**
   * Creates a new AutoIntake.
   */
  private final Intake intake;
  private final Shooter shooter;

  public AutoIntake(Intake intake, Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.intake = intake;
    this.shooter = shooter;
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // If sensor is detecting a ball, turn on the feeder
    if(intake.getValueOfSensor() < 2.5){
      intake.feed(-0.3);
      shooter.setLimelightLED(Shooter.LED_BLINK);
    }
    else{
      intake.feed(0);
      shooter.setLimelightLED(Shooter.LED_OFF);
    }
    intake.runShooterFeeder(0.75);
    intake.succ(0.4);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.feed(0);
    intake.runShooterFeeder(0);
    intake.succ(0);
    shooter.setLimelightLED(Shooter.LED_OFF);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
