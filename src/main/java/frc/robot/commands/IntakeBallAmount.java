/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class IntakeBallAmount extends CommandBase {
  /**
   * Creates a new IntakeBallAmount.
   */
  private int limit;
  private Intake intake;
  private int numBalls = 0;
  private double sensorValue = 0;
  public IntakeBallAmount(int l, Intake i) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.limit = l;
    this.intake = i;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(intake.getValueOfSensor() < 2.5){
      intake.feed(-0.3);
    }
    else{
      intake.feed(0);
    }

    if (intake.getValueOfSensor() < 2.5 && sensorValue > 2.5){
      numBalls += 1;
    }
    
    intake.runShooterFeeder(0.75);
    intake.intake(0.7);

    sensorValue = intake.getValueOfSensor();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return numBalls >= limit;
  }
}
