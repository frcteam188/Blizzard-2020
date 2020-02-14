/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.Base;
import frc.robot.subsystems.Hang;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
/**
 * The teleop command for the robot
 * 
 * @author Shiv Patel
 */
public class TeleopCommand extends CommandBase {

  // makes the objects (subsystems)
  private final Base base;
  private final Intake intake;
  private final Shooter shooter;
  private final Hang hang;
  private final Joystick stick1;
  private final JoystickButton rbBtn1, rtBtn1;
  /**
   * Creates a new TeleopCommand.
   */
  public TeleopCommand(Base base, Intake intake, Shooter shooter, Hang hang, Joystick stick1, JoystickButton rbBtn1, JoystickButton rtBtn1) {
    this.base = base;
    this.intake = intake;
    this.shooter = shooter;
    this.hang = hang;
    this.stick1 = stick1;
    this.rbBtn1 = rbBtn1;
    this.rtBtn1 = rtBtn1;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(base);
    addRequirements(intake);
    addRequirements(shooter);
    addRequirements(hang);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // using the drive method in the base subsystem to run the base based on the input from the controller's axis
    base.drive(-stick1.getRawAxis(1), stick1.getRawAxis(2));

    // running the intake if rb is pressed
    if (rbBtn1.get()){
      intake.succ(0.65);
    }
    // if nothing is being pressed, do not run the intake
    else{
      intake.succ(0);
    }

    // run the feeder if the right trigger is pressed
    if(rtBtn1.get()){
      intake.feed(0.30);
    }

    // keep the feeder and 0 if nothing is pressed
    else{
      intake.feed(0);
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // stops the base by setting the inputs to 0
    base.drive(0, 0);
    intake.succ(0);
    intake.feed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
