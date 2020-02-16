/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import frc.robot.commands.*;
import frc.robot.subsystems.*;


/**
 * The teleop command for the robot
 * 
 * @author Shiv Patel, Edward Su, Zayeed Ghori
 */
public class TeleopCommand extends CommandBase {

  // makes the objects (subsystems)
  private final Base base;
  private final Intake intake;
  private final Shooter shooter;
  private final Hang hang;

  private final TurretPID turretPID;
  private final AutoIntake autoIntake;
  private final MoveFeeder moveFeeder;
  private final Joystick opStick;
  private final Joystick drStick;
  
  // power for the feeder
  private double pow = -1;

  private boolean gearShiftTrue = false;
  private boolean gearShiftOn = false;
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

    turretPID = new TurretPID(shooter);
    autoIntake = new AutoIntake(intake);
    moveFeeder = new MoveFeeder(intake, pow);
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.base);
    addRequirements(this.shooter);
    addRequirements(this.hang);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putNumber("Turret P:", Constants.kTurretP);
    SmartDashboard.putNumber("Turret I:", Constants.kTurretI);
    SmartDashboard.putNumber("Turret D:", Constants.kTurretD);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // Reporting to SmartDashBoard

    // Turret Stuff
    SmartDashboard.getNumber("Turret P:", Constants.kTurretP);
    SmartDashboard.getNumber("Turret I:", Constants.kTurretI);
    SmartDashboard.getNumber("Turret D:", Constants.kTurretD);

    SmartDashboard.putNumber("Limelight Angle:", shooter.getLimelightX());
    SmartDashboard.putNumber("Turret Pos", shooter.getTurretPos());

    SmartDashboard.putNumber("Limelight Angle Graph", shooter.getLimelightX());
    SmartDashboard.putNumber("Angle of Base: ", base.getBaseAngle());


    // Shooter Stuff (Actual PID values are printed and modified in TuneShooterPID.java)
    SmartDashboard.putNumber("Shooter RPM:", shooter.getVelShooter());
    SmartDashboard.putNumber("Shooter RPM Graph", shooter.getVelShooter());

    // Uncomment after not using tuneShooterPID
    // SmartDashboard.putNumber("Shooter P: ", Constants.kShooterP);
    // SmartDashboard.putNumber("Shooter I: ", Constants.kShooterI);
    // SmartDashboard.putNumber("Shooter D: ", Constants.kShooterD);
    // SmartDashboard.putNumber("Shooter F: ", Constants.kShooterF);
    // SmartDashboard.putNumber("Max Output: ", Constants.kShooterMaxOutput);
    // SmartDashboard.putNumber("Min Output: ", Constants.kShooterMinOutput);

    // CONTROLS

    base.drive(-drStick.getRawAxis(1), drStick.getRawAxis(2)); // Joysticks (driver) - drive

    if (drStick.getRawButton(2)){ // LB (driver) - hold to shift the gear, otherwise the gear shift will be on
      base.gearShiftOff();
      gearShiftOn = true;
    }
    //else if(drStick.getRawButton(2) && gearShiftTrue == false && gearShiftOn == true){
      //base.gearShiftOn();
      //gearShiftOn = false;
   // }
    else if(drStick.getRawButton(3)){
      base.gearShiftOn();
    }

    if(opStick.getRawButton(7) && !moveFeeder.isScheduled()){
      moveFeeder.schedule();
   
    }
    else if(!opStick.getRawButton(7) && moveFeeder.isScheduled()){
      moveFeeder.cancel();
    }


    if(opStick.getRawButton(7)){ // LB - feeder under the shooter
      shooter.runShooterFeeder(-1);
    }
    else{
      shooter.runShooterFeeder(0);
    }
    
    // LT (driver) - fire piston to deploy intake and run the intake motor
    if(drStick.getRawButton(7)){
      intake.deployIntake();
      intake.succ(0.30);
    }

    else if(drStick.getRawButton(8)){ // RT (driver) - retract the intake piston
      intake.resetIntake();
    }

    if(drStick.getRawButton(6)){ // RB (driver) - run the intake
      intake.succ(0.65);
    }
    else if (drStick.getRawButton(5)){ // LB (driver) - run the intake in reverse
      intake.succ(-0.65);
    }
    else{
      intake.succ(0);
    }



    if(opStick.getPOV() == 90){ // Right Depad (operator) - move turret to the right
      shooter.moveTurret(0.25);
    }
    else if(opStick.getPOV() == 270){ // Left Depad (operator) - move turret to the left
       shooter.moveTurret(-0.25);
    }
    else{
      shooter.moveTurret(0);
    }


    if(opStick.getPOV() == 0){ // Up Depad (operator) - move hood up
      shooter.moveHood(0.50);
    }
    else if(opStick.getPOV() == 180){ // Down Depad (operator) - move hood up
      shooter.moveHood(-0.50);
    }
    else{
      shooter.moveHood(0);
    }

    // if(opStick.getRawButton(1)){ // X (operator) - shoot at 70%
    //   shooter.shoot(0.7);
    // }

    // else if(opStick.getRawButton(2)){ // A (operator) - shoot at 80%
    //   shooter.shoot(0.8);
    // }

    if(drStick.getRawButton(1)){ // B (operator) - shoot at 90%
      shooter.shoot(0.9);
    }
    else{
      shooter.shoot(0);
    }

    base.getBaseAngle();

    // if(opStick.getRawButton(4)){
    //   turretPID.schedule();
    // }
    // else
    //   turretPID.cancel();



//    if (drStick.getRawButton(2)){
  //    gearShiftTrue = true;
    //}

    if(opStick.getRawButton(1)){ // X (operator) - Fire both hang stages
      hang.moveStageOne(1);
      hang.moveStageTwo(1);
      System.out.println("Hang");

    } 
    else if (opStick.getRawButton(2)){ // A (operator) - Retract both
      hang.moveStageOne(-1);
      hang.moveStageTwo(-1);
    }


    if(opStick.getRawButton(5)){
      hang.pullUp(-1);
    }
    else{
      hang.pullUp(0);
    }

  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // stops the base by setting the inputs to 0
    base.drive(0, 0);
    intake.succ(0);
    intake.feed(0);
    shooter.moveTurret(0);
    shooter.moveHood(0);
    hang.pullUp(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
