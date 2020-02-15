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
  private final Joystick opStick;
  private final Joystick drStick;

  /**
   * Creates a new TeleopCommand.
   */
  public TeleopCommand(Base base, Intake intake, Shooter shooter, Hang hang, Joystick opStick, Joystick drStick) {
    this.base = base;
    this.intake = intake;
    this.shooter = shooter;
    this.hang = hang;
    this.opStick = opStick;
    this.drStick = drStick;
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
    // using the drive method in the base subsystem to run the base based on the
    // input from the controller's axis
    base.drive(-drStick.getRawAxis(1), drStick.getRawAxis(2));
    // shift the base on if the right button is pressed
    if (drStick.getRawButton(5)){
      base.gearShiftOff();
    }
    // shift the base off if the right button is pressed
    else{
      base.gearShiftOn();
    }

    // // running the intake if right button is pressed
    // if (opStick.getRawButton(7)) {
    //   intake.succ(0.65);
    // }
    // // if nothing is being pressed, do not run the intake
    // else {
    //   intake.succ(0);
    // }

    // // run the feeder if the right trigger is pressed
    // if (opStick.getRawButton(8)) {
    //   intake.feed(-1);
    // }

    // keep the feeder and 0 if nothing is pressed
    // else {
    //   intake.feed(0);
    // }

    // if(opStick.getRawButton(2)){
    //   hang.moveStageOne(Hang.STATE_IN);
    // }
    // else if(opStick.getRawButton(3)){
    //   hang.moveStageOne(Hang.STATE_OUT);
    // }

    // if(opStick.getRawButton(1)){
    //   hang.moveStageTwo(Hang.STATE_IN);
    // }
    // else if(opStick.getRawButton(4)){
    //   hang.moveStageTwo(Hang.STATE_OUT);
    // }

    if(opStick.getRawButton(7)){
      intake.feed(-1);
    }
    else{
      intake.feed(0);
    }

    if(opStick.getRawButton(5)){
      shooter.runShooterFeeder(-1);
    }
    else{
      shooter.runShooterFeeder(0);
    }

    if(drStick.getRawButton(7)){
      intake.deployIntake();
    }
    else if(drStick.getRawButton(8)){
      intake.resetIntake();
    }

    if(drStick.getRawButton(6)){
      intake.succ(0.5);
    }
    else{
      intake.succ(0);
    }


    if(opStick.getPOV() == 90){
      shooter.moveTurret(1);
    }
    else if(opStick.getPOV() == 270){
      shooter.moveTurret(-1);
    }
    else{
      shooter.moveTurret(0);
    }

    if(opStick.getPOV() == 0){
      shooter.moveHood(0.50);
    }
    else if(opStick.getPOV() == 180){
      shooter.moveHood(-0.50);
    }
    else{
      shooter.moveHood(0);
    }

    if(opStick.getRawButton(1)){
      shooter.shoot(0.7);
    }

    if(opStick.getRawButton(2)){
      shooter.shoot(0.8);
    }

    if(opStick.getRawButton(3)){
      shooter.shoot(0.9);
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
