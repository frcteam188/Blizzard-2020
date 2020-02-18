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
  private final TuneTurretPID tuneTurretPID;
  private final HoodPID hoodPID;
  private final AutoIntake autoIntake;
  private final MoveFeeder moveFeeder;
  private final MoveIntake moveIntake;
  private final Shoot shoot;
  private final Joystick opStick;
  private final Joystick drStick;
  private final TuneShooterPID shooterPID;
  
  // power for the feeder
  private final double pow = -1;
  private double hoodSetpoint;
  private final int turretDeadZone = 5;
  private final double shooterP = 0.0001;
  private double shooterSetpoint = 5420;
  private int shooterDeadZone = 5;

  private boolean gearShiftTrue = false;
  private boolean gearShiftOn = false;
  private boolean autoIntakeOn = false;
  private final boolean turretPIDOn = false;
  private final boolean turretButtonPressed = false;
  private boolean shootOn = false;
  private boolean intakeOn = false;
  private boolean intakeDeployed = false;
  private final boolean hoodPIDTrue = false;
  private boolean shootingButtonX = false;
  private boolean shootingButtonA = false;
  private boolean shootingButtonB = false;
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

    turretPID = new TurretPID(shooter);
    tuneTurretPID = new TuneTurretPID(shooter);
    hoodPID = new HoodPID(shooter, hoodSetpoint);
    autoIntake = new AutoIntake(intake, shooter);
    moveFeeder = new MoveFeeder(intake, pow);
    moveIntake = new MoveIntake(intake, -0.4);
    shoot = new Shoot(intake);
    shooterPID = new TuneShooterPID(shooter, 4360);
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(this.base);
    addRequirements(this.shooter);
    addRequirements(this.hang);
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

    // Base Motor retport
    // SmartDashboard.putNumber("Base leftFront Current: ", base.getLeftFront().getOutputCurrent());
    // SmartDashboard.putNumber("Base leftBack Current: ", base.getLeftBack().getOutputCurrent());
    // SmartDashboard.putNumber("Base rightFront Current: ", base.getRightFront().getOutputCurrent());
    // SmartDashboard.putNumber("Base rightBack Current: ", base.getRightBack().getOutputCurrent());

    // HOOD
    // SmartDashboard.putNumber("Hood Pos", shooter.getHoodPos());

    // CAN SHOOT?
    SmartDashboard.putBoolean("Can Shoot", canShoot);

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

    
      

    if (drStick.getRawButton(8)) { // LB (driver) - hold to shift the gear, otherwise the gear shift will be on
      base.gearShiftOff();
    }
    else if(drStick.getRawButton(7)) {
      base.gearShiftOn();
    }

    if (drStick.getRawButton(2)) { // X (operator) - Fire both hang stages
      hang.moveStageOne(1);
      hang.moveStageTwo(1);

    } else if (drStick.getRawButton(3)) { // A (operator) - Retract both
      hang.moveStageOne(-1);
      hang.moveStageTwo(-1);
    }

    if (drStick.getRawButton(4)) {
      hang.pullUp(-1);
    } else {
      hang.pullUp(0);
    }

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
      shooter.setLimelightLED(Shooter.LED_OFF);
    }

    // LT (driver) - fire piston to deploy intake and run the intake motor
    if (opStick.getRawButton(6)) {
      if (autoIntakeOn == false) {
        moveIntake.cancel();
        autoIntake.schedule();
        autoIntakeOn = true;
      } 
      
    }
    else if(opStick.getRawButton(8)){
      if (intakeOn == false){
        autoIntake.cancel();
        moveIntake.schedule();
        intakeOn = true;
      }
    }
    else{ // RT (driver) - retract the intake piston
      if (autoIntakeOn == true) {
        autoIntake.cancel();
        autoIntakeOn = false;
      }
      else if (intakeOn == true){
        intakeOn = false;
        moveIntake.cancel();  
      }
    }

    if (opStick.getRawButton(5) && intakeDeployed == false){
      intake.deployIntake();
      intakeDeployed = true;
    }
    else if(opStick.getRawButton(7) && intakeDeployed == true){
      intake.resetIntake();
      intakeDeployed = false;
    }

      

    // if (opStick.getRawButton(6) && intakeOn == false) {
    //   moveIntake.schedule();
    //   intakeOn = true;
    // } else if (!opStick.getRawButton(6) && intakeOn == true) {
    //   intakeOn = false;
    //   moveIntake.cancel();
    // }

    if (opStick.getRawButton(2)) { // X (operator) - shoot at 70%
      opStick.setRumble(RumbleType.kLeftRumble, 0.5);
      opStick.setRumble(RumbleType.kRightRumble, 0.5);
      shooter.shoot(0.98);
      if (!shootingButtonX) {
        hoodPID.setSetpoint(60);
        hoodPID.schedule();
        turretPID.schedule();
      }

    }

    else if (opStick.getRawButton(3)) {
      opStick.setRumble(RumbleType.kLeftRumble, 1.0);
      opStick.setRumble(RumbleType.kRightRumble, 1.0);
      shooter.shoot(0.98);
      if (!shootingButtonA) {
        hoodPID.setSetpoint(85);
        hoodPID.schedule();
        turretPID.schedule();
      }

    } else if (opStick.getRawButton(4)) { // Y (operator) - shoot at 90%
      opStick.setRumble(RumbleType.kLeftRumble, 0.2);
      opStick.setRumble(RumbleType.kRightRumble, 0.2);

      // banG BANG
      if(shooter.getVelShooter() < shooterSetpoint){
        shooter.shoot(1);
      }
      else if(shooter.getVelShooter() > shooterSetpoint + shooterDeadZone){
        shooter.shoot(0.6);
      }
      else{
        shooter.shoot(0.95);
      }
      
      shooter.shoot(0.95);
      // shooter.shoot(shooterP * (setpoint - shooter.getVelShooter()) + 0.95);
      if (!shootingButtonB) {
        hoodPID.setSetpoint(105);
        hoodPID.schedule();
        turretPID.schedule();
      }
    } else {
      opStick.setRumble(RumbleType.kLeftRumble, 0);
      opStick.setRumble(RumbleType.kRightRumble, 0);
      shooter.shoot(0);
      hoodPID.setSetpoint(0);
      if (turretPID.isScheduled()) {
        turretPID.cancel();
      }
      
      //if turret pid is off, manual turret mvmt
      if (!turretPID.isScheduled()){
        shooter.moveTurret(opStick.getRawAxis(0));

      }
      // put turret to middle again
      if (shooter.getTurretAngle() > 0 + turretDeadZone) {
        shooter.moveTurret(-0.5);
      } else if (shooter.getTurretAngle() < 0 - turretDeadZone) {
        shooter.moveTurret(0.5);
      } else {
        shooter.moveTurret(0);
      }
    }

    //runs all feeding mechanisms if lb is pressed
    if (opStick.getPOV(0) == 0 && !shootPressed){
      shoot.schedule();
    }
    else if (opStick.getPOV(0) != 0 ){
      shoot.cancel();
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
    shootingButtonX = opStick.getRawButton(1);
    shootingButtonA = opStick.getRawButton(2);
    shootingButtonB = opStick.getRawButton(3);
    shootPressed = opStick.getPOV(0) == 0;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
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
