/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import frc.robot.RobotMath;
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

  private final ShootFeed shootFeed;
  private final Joystick opStick;
  private final Joystick drStick;
  
  // power for the feeder
  private final double pow = -1;
  private double hoodSetpoint;
  private final int turretDeadZone = 5;
  private final double shooterP = 0.0001;
  private double shooterSetpoint = 5420;
  private int shooterDeadZone = 5;

  private boolean shootPressed = false;

  private boolean canShoot = false;
  /**
   * Creates a new TeleopCommand.
   */
  public TeleopCommand(final Base base, final Intake intake, final Shooter shooter, final Hang hang,
      final Joystick opStick, final Joystick drStick) {
    this.base = base;
    this.intake = intake;
    this.shooter = shooter;
    this.hang = hang;
    this.opStick = opStick;
    this.drStick = drStick;

    shootFeed = new ShootFeed(intake);

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.base);
    // addRequirements(this.shooter);
    // addRequirements(this.hang);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putNumber("Turret P: ", Constants.kTurretP);
    SmartDashboard.putNumber("Turret I: ", Constants.kTurretI);
    SmartDashboard.putNumber("Turret D: ", Constants.kTurretD);
    SmartDashboard.putNumber("Turret Setpoint: ", 0);
    

    // Start in low gear
    base.gearShiftOff();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // Reporting to SmartDashBoard

    // SmartDashboard.putNumber("Limelight Angle:", shooter.getLimelightX());
    // SmartDashboard.putNumber("Turret Angle", shooter.getTurretAngle());

    // SmartDashboard.putNumber("Limelight Angle Graph", shooter.getLimelightX());
    // SmartDashboard.putNumber("Angle of Base: ", base.getBaseAngle());

    // // Shooter Stuff (Actual PID values are printed and modified in
    // // TuneShooterPID.java)
    SmartDashboard.putNumber("Shooter RPM:", shooter.getVelShooter());
    // SmartDashboard.putNumber("Shooter RPM Graph", shooter.getVelShooter());

    // Uncomment after not using tuneShooterPID
    // SmartDashboard.putNumber("Shooter P: ", Constants.kShooterP);
    // SmartDashboard.putNumber("Shooter I: ", Constants.kShooterI);
    // SmartDashboard.putNumber("Shooter D: ", Constants.kShooterD);
    // SmartDashboard.putNumber("Shooter F: ", Constants.kShooterF);
    // SmartDashboard.putNumber("Max Output: ", Constants.kShooterMaxOutput);
    // SmartDashboard.putNumber("Min Output: ", Constants.kShooterMinOutput);

    // Base Motor report
    // SmartDashboard.putNumber("Base leftFront Current: ", base.getLeftFront().getOutputCurrent());
    // SmartDashboard.putNumber("Base leftBack Current: ", base.getLeftBack().getOutputCurrent());
    // SmartDashboard.putNumber("Base rightFront Current: ", base.getRightFront().getOutputCurrent());
    // SmartDashboard.putNumber("Base rightBack Current: ", base.getRightBack().getOutputCurrent());

    // HOOD
    // SmartDashboard.putNumber("Hood Pos", shooter.getHoodPos());

    // CAN SHOOT?
    SmartDashboard.putBoolean("Can Shoot", canShoot);

    // Distance
    SmartDashboard.putNumber("Distance from Target: ", RobotMath.getDistanceFromTarget());

    // CONTROLS

    base.drive(-drStick.getRawAxis(1), drStick.getRawAxis(2)*0.4); // Joysticks (driver) - drive

    if(drStick.getRawAxis(2) < -0.5) {
      drStick.setRumble(RumbleType.kLeftRumble, (-drStick.getRawAxis(2)-0.5)*2);
      drStick.setRumble(RumbleType.kRightRumble, 0);
    }
    else if(drStick.getRawAxis(2) > 0.5) {
      drStick.setRumble(RumbleType.kLeftRumble, 0);
      drStick.setRumble(RumbleType.kRightRumble, (drStick.getRawAxis(2)-0.5)*2);
    }
    else {
      drStick.setRumble(RumbleType.kLeftRumble, 0);
      drStick.setRumble(RumbleType.kRightRumble, 0);
    }


    // Blink LEDs
    if (!opStick.getRawButton(2) && 
    !opStick.getRawButton(3) && 
    !opStick.getRawButton(4) && 
    !opStick.getRawButton(6) &&
    drStick.getRawButton(12)){
      shooter.setLimelightLED(Shooter.LED_BLINK);
    }
    else if (!opStick.getRawButton(2) && 
    !opStick.getRawButton(3) && 
    !opStick.getRawButton(4) && 
    !opStick.getRawButton(6) &&
    !drStick.getRawButton(12)){
      shooter.setLimelightLED(Shooter.LED_ON); // change to off
    }

    //runs all feeding mechanisms if lb is pressed
    if (opStick.getPOV(0) == 0 && !shootPressed){
      shootFeed.schedule();
    }
    else if (opStick.getPOV(0) != 0 ){
      shootFeed.cancel();
    }

    //boolean value for proper rpm
    if (shooter.getVelShooter() >= 4500){
      canShoot = true;
    }
    else{
      canShoot = false;
    }
    
    // HOOD - 1
    // TURRET - 2

    // if(opStick.getRawButton(4) && turretButtonPressed == false && turretPIDOn ==
    // false){
    // // turretPID.schedule();
    // tuneTurretPID.schedule();
    // turretPIDOn = true;
    // }
    // else if(opStick.getRawButton(4) && turretPIDOn == true && turretButtonPressed
    // == false){
    // // turretPID.cancel();
    // tuneTurretPID.cancel();
    // turretPIDOn = false;
    // }
    // if(turretPIDOn == false){
    // shooter.moveTurret(opStick.getRawAxis(2)*0.8);
    // }

    // flags for toggling
    // turretButtonPressed = opStick.getRawButton(4);
    shootPressed = opStick.getPOV(0) == 0;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
    // stops the base by setting the inputs to 0
    base.drive(0, 0);
    intake.intake(0);
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
