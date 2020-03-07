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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.*;
import frc.robot.commands.*;
import frc.robot.commands.autoCommands.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
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
  POVButton downBtnOp = new POVButton(opStick, 180);
  POVButton leftBtnOp = new POVButton(opStick, 270);
  JoystickButton startBtn = new JoystickButton(opStick, 10);
  

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
  private double turretSp = -2.0;
  private double closeRPM = 2350;
  

  // constructor for teleopCommand
  private final TeleopCommand teleopCommand = new TeleopCommand(base, intake, shooter, hang, opStick, drStick);
  private final DisabledCommand disabledCommand = new DisabledCommand(drStick, base, shooter);
  private final SmartDashboardPrints smartDashboardPrints = new SmartDashboardPrints(shooter, intake, hang, base);
  private final AutoIntake autoIntake = new AutoIntake(intake, shooter, base);
  private final MoveIntake moveIntake = new MoveIntake(intake, -0.8);
  private final HoodPID hoodPID = new HoodPID(shooter, 0);
  private final HoodPID closeHood = new HoodPID(shooter, 38.832951);
  private final TurretPID turretPID = new TurretPID(shooter, -3.1);
  private final ManualTurret manualTurret = new ManualTurret(shooter, opStick);
  private final ShootBall shootBall = new ShootBall(shooter, 0.65); //0.65 // 0.75 is trench test speed
  private final ResetTurret resetTurret = new ResetTurret(shooter);
  private final ManualHood manualHood = new ManualHood(shooter, opStick);
  private final DeployIntake deployIntake = new DeployIntake(intake, true);
  private final DeployIntake resetIntake = new DeployIntake(intake, false);
  private final ShootFeed shootFeed = new ShootFeed(intake, -0.9);
  private final ShootFeed closeFeed = new ShootFeed(intake, -0.6); // -0.3 is trench test speed
  private final SequentialCommandGroup shootFeedLimited = 
                                                    new SequentialCommandGroup(
                                                      new ShooterGetToSpeed(shooter),
                                                      new ShootFeed(intake, -0.9)
                                                    );
  private final OutFeed outFeed = new OutFeed(intake, 0.6);
  private final ConditionalCommand variableFeed = new ConditionalCommand(closeFeed, shootFeed, aBtnOp::get);
  private final ConditionalCommand variableFeedLimited = new ConditionalCommand(new ShootFeed(intake, -0.6), shootFeedLimited, yBtnOp::get);
  private final MidHoodPID midHoodPID = new MidHoodPID(shooter);
  private final MidShooterPID midShooterPID = new MidShooterPID(shooter);
  private final FarHoodPID farHoodPID = new FarHoodPID(shooter);
  private final FarShooterPID farShooterPID = new FarShooterPID(shooter);
  private final TurnTurret180 turnTurret180 = new TurnTurret180(shooter);
  private final FeedFromTrench feedFromTrench = new FeedFromTrench(intake, shooter);
  // private final SequentialCommandGroup shootWhenAtSpeed = new SequentialCommandGroup(new ShooterGetToSpeed(shooter), closeFeed);

  private final DeployHang deployHang = new DeployHang(hang, intake);
  private final RetractHang retractHang = new RetractHang(hang);
  private final Winch winch = new Winch(hang);
  private final BaseLowGearShift baseLowGearShift = new BaseLowGearShift(base);
  private final BaseHighGearShift baseHighGearShift = new BaseHighGearShift(base);

  // constructor for auto commmand
  private final AutoTestCommand autoTestCommand = new AutoTestCommand(base, intake, shooter);
  private final AutoNineBall autoNineBall = new AutoNineBall(base, intake, shooter);
  private final SendableChooser<Command> chooser = new SendableChooser<Command>();
  private final DriveStraight driveStraight = new DriveStraight(base, 0, 0, 0.65);
  private final TuneDriveStraight tuneDriveStraight = new TuneDriveStraight(base, 0, 0);
  private final TuneTurnBasePID turnTurnBasePID = new TuneTurnBasePID(base, 0);
  
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    chooser();
  }



  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */

  private void chooser(){
    chooser.setDefaultOption("Tune Turn ", turnTurnBasePID);
    chooser.addOption("Tune Drive Forward", tuneDriveStraight);
    chooser.addOption("Test Auto", autoTestCommand);
    chooser.addOption("Nine Ball Auto", autoNineBall);
    SmartDashboard.putData("Auto Options", chooser);

  }
  private void configureButtonBindings() {
    // Operator Controls

    // Regular intake with autofeed
    rbBtnOp.whileActiveOnce(autoIntake);

    // Outtake
    rtBtnOp.whileActiveOnce(moveIntake);

    // CLOSE 
    // aBtnOp.whileActiveOnce(closeHood);
    // aBtnOp.whileActiveOnce(shootBall);
    // aBtnOp.whenReleased(resetTurret);
    // // aBtnOp.whenReleased(manualTurret);
    // // aBtnOp.whenReleased(manualHood);
    // // aBtnOp.cancelWhenPressed(manualHood);
    // // aBtnOp.ca`ncelWhenPressed(manualTurret);
    // aBtnOp.cancelWhenPressed(resetTurret);
    // aBtnOp.cancelWhenPressed(turnTurret180);
    // aBtnOp.whileActiveOnce(new ShooterPID(shooter, closeRPM));



    // // can call like Btn.whenHeld().whenHeld();
    // bBtnOp.whileActiveOnce(midHoodPID);
    // bBtnOp.whileActiveOnce(turretPID);
    // bBtnOp.whileActiveOnce(midShooterPID);
    // bBtnOp.whenReleased(resetTurret);
    // bBtnOp.whenReleased(manualTurret);
    // bBtnOp.whenReleased(manualHood);
    // bBtnOp.cancelWhenPressed(manualHood);
    // bBtnOp.cancelWhenPressed(resetTurret);
    // // bBtnOp.cancelWhenPressed(manualTurret);
    // bBtnOp.cancelWhenPressed(turnTurret180);

    

    // yBtnOp.whileActiveOnce(farHoodPID);
    yBtnOp.whileActiveOnce(turretPID);
    yBtnOp.whileActiveOnce(farShooterPID);
    // yBtnOp.whenReleased(resetTurret);
    // yBtnOp.whenReleased(manualTurret);
    yBtnOp.whenReleased(manualHood);
    // yBtnOp.cancelWhenPressed(manualTurret);
    // yBtnOp.cancelWhenPressed(manualHood);
    yBtnOp.cancelWhenPressed(resetTurret);
    yBtnOp.cancelWhenPressed(turnTurret180);



    lbBtnOp.whileActiveOnce(deployIntake);
    ltBtnOp.whileActiveOnce(resetIntake);

    xBtnOp.whileActiveOnce(turnTurret180);
    xBtnOp.cancelWhenPressed(resetTurret);

    startBtn.whileActiveOnce(resetTurret);
    startBtn.cancelWhenPressed(turnTurret180);


    // leftBtnOp.whileActiveOnce(variableFeed);
    leftBtnOp.whileActiveOnce(feedFromTrench);
    leftBtnOp.cancelWhenPressed(turretPID);
    leftBtnOp.cancelWhenPressed(variableFeedLimited);
    leftBtnOp.cancelWhenPressed(outFeed);
    // upBtnOp.whileActiveOnce(shootWhenAtSpeed);
    upBtnOp.whileActiveOnce(variableFeedLimited);
    upBtnOp.cancelWhenPressed(turretPID);
    upBtnOp.cancelWhenPressed(variableFeed);
    upBtnOp.cancelWhenPressed(outFeed);

    downBtnOp.whileActiveOnce(outFeed);
    downBtnOp.cancelWhenPressed(turretPID);
    downBtnOp.cancelWhenPressed(variableFeed);
    downBtnOp.cancelWhenPressed(variableFeedLimited);
    

    // Driver Controls
    aBtnDr.whenActive(deployHang);
    bBtnDr.whileActiveOnce(retractHang);
    yBtnDr.whileActiveOnce(winch);

    ltBtnDr.whileActiveOnce(baseLowGearShift);
    rtBtnDr.whileActiveOnce(baseHighGearShift);

    rbBtnDr.whileActiveOnce(new TuneStandStill(base, 0.5));
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
    return chooser.getSelected();
  }

  public Command getDisabledCommand(){
    return disabledCommand;
  }

  public Command getSmartDashboardPrints(){
    return smartDashboardPrints;
  }

}
