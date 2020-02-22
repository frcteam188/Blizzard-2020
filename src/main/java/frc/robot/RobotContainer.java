/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.*;
import frc.robot.commands.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Base base = new Base();
  private final Intake intake = new Intake();
  private final Hang hang = new Hang();
  private final Shooter shooter = new Shooter();

  private int setpoint = 2868;

  // creating a new stick (controller) which will be the OPERATOR controller (port 0)
  private Joystick opStick = new Joystick(0);
  private JoystickButton opABtn = new JoystickButton(opStick, 2);

  //creating a new stick (controller) which will be the DRIVER controller (port 1)
  private Joystick drStick =  new Joystick(1);
  
  // Buttons Operator
  JoystickButton xBtnOp = new JoystickButton(opStick, 1);
  JoystickButton aBtnOp = new JoystickButton(opStick, 2);
  JoystickButton bBtnOp = new JoystickButton(opStick, 3);
  JoystickButton yBtnOp = new JoystickButton(opStick, 4);
  JoystickButton lbBtnOp = new JoystickButton(opStick, 5);
  JoystickButton rbBtnOp = new JoystickButton(opStick, 6);
  JoystickButton ltBtnOp = new JoystickButton(opStick, 7);
  JoystickButton rtBtnOp = new JoystickButton(opStick, 8);
  POVButton upBtnOp = new POVButton(opStick, 0);
  

  // Buttons Driver
  JoystickButton xBtnDr = new JoystickButton(drStick, 1);
  JoystickButton aBtnDr = new JoystickButton(drStick, 2);
  JoystickButton bBtnDr = new JoystickButton(drStick, 3);
  JoystickButton yBtnDr = new JoystickButton(drStick, 4);
  JoystickButton lbBtnDr = new JoystickButton(drStick, 5);
  JoystickButton rbBtnDr = new JoystickButton(drStick, 6);
  JoystickButton ltBtnDr = new JoystickButton(drStick, 7);
  JoystickButton rtBtnDr = new JoystickButton(drStick, 8);

  // Variables
  private double feederPow = 0;
  private double intakePow = 0;
  private double hoodSp = 0;
  private double shooterSp = 0;
  private double turretSp = -2.5;
  private double closeRPM = 2350;
  

  // constructor for teleopCommand
  private final TeleopCommand teleopCommand = new TeleopCommand(base, intake, shooter, hang, opStick, drStick);
  private final DisabledCommand disabledCommand = new DisabledCommand(drStick, base, shooter);
  private final SmartDashboardPrints smartDashboardPrints = new SmartDashboardPrints(shooter, intake, hang, base);
  private final AutoIntake autoIntake = new AutoIntake(intake, shooter);
  private final MoveIntake moveIntake = new MoveIntake(intake, -0.4);
  private final HoodPID hoodPID = new HoodPID(shooter, 0);
  private final HoodPID closeHood = new HoodPID(shooter, 38.832951);
  private final TurretPID turretPID = new TurretPID(shooter, turretSp);
  private final ShootBall shootBall = new ShootBall(shooter, 0.65);
  private final ResetTurret resetTurret = new ResetTurret(shooter);
  private final ManualHood manualHood = new ManualHood(shooter, opStick);
  private final DeployIntake deployIntake = new DeployIntake(intake, true);
  private final DeployIntake resetIntake = new DeployIntake(intake, false);
  private final ShootFeed shootFeed = new ShootFeed(intake, -0.9);
  private final ShootFeed closeFeed = new ShootFeed(intake, -0.6);
  private final ConditionalCommand variableFeed = new ConditionalCommand(closeFeed, shootFeed, aBtnOp::get);
  private final MidHoodPID midHoodPID = new MidHoodPID(shooter);
  private final MidShooterPID midShooterPID = new MidShooterPID(shooter);

  private final DeployHang deployHang = new DeployHang(hang);
  private final RetractHang retractHang = new RetractHang(hang);
  private final Winch winch = new Winch(hang);
  private final BaseLowGearShift baseLowGearShift = new BaseLowGearShift(base);
  private final BaseHighGearShift baseHighGearShift = new BaseHighGearShift(base);

  // constructor for auto commmand

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Operator Controls

    // Regular intake with autofeed
    rbBtnOp.whileActiveOnce(autoIntake);

    // Outtake
    rtBtnOp.whileActiveOnce(moveIntake);

    // CLOSE 
    aBtnOp.whileActiveOnce(closeHood);
    // aBtnOp.whileActiveOnce(shootBall);
    aBtnOp.whenReleased(resetTurret);
    aBtnOp.whenReleased(manualHood);
    // aBtnOp.cancelWhenPressed(manualHood);
    aBtnOp.cancelWhenPressed(resetTurret);
    aBtnOp.whileActiveOnce(new ShooterPID(shooter, closeRPM));



    // can call like Btn.whenHeld().whenHeld();
    bBtnOp.whileActiveOnce(midHoodPID);
    bBtnOp.whileActiveOnce(turretPID);
    bBtnOp.whileActiveOnce(midShooterPID);
    bBtnOp.whenReleased(resetTurret);
    // bBtnOp.whenReleased(manualHood);
    // bBtnOp.cancelWhenPressed(manualHood);
    bBtnOp.cancelWhenPressed(resetTurret);


    yBtnOp.whileActiveOnce(midHoodPID);
    yBtnOp.whileActiveOnce(turretPID);
    yBtnOp.whileActiveOnce(midShooterPID);
    yBtnOp.whenReleased(resetTurret);
    // yBtnOp.whenReleased(manualHood);
    // yBtnOp.cancelWhenPressed(manualHood);
    yBtnOp.cancelWhenPressed(resetTurret);



    lbBtnOp.whenPressed(deployIntake);
    ltBtnOp.whenPressed(resetIntake);

    upBtnOp.whileActiveOnce(variableFeed);
    upBtnOp.cancelWhenPressed(turretPID);

    // Driver Controls
    aBtnDr.whenPressed(deployHang);
    bBtnDr.whenPressed(retractHang);
    yBtnDr.whileActiveOnce(winch);

    ltBtnDr.whenPressed(baseHighGearShift);
    rtBtnDr.whenPressed(baseLowGearShift);

  }

  /**
   * Returns the teleop command
   * 
   * @return the command to run in teleop
   */
  public Command getTeleopCommand(){
    return teleopCommand;
  }

  public Command getAutoCommand(){
    return null;
  }

  public Command getDisabledCommand(){
    return disabledCommand;
  }

  public Command getSmartDashboardPrints(){
    return smartDashboardPrints;
  }



}
