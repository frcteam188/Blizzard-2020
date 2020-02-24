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
import edu.wpi.first.wpilibj.Compressor;
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

  private final Joystick drStick;
  private final Joystick opStick;

  // power for the feeder
  private final double pow = -1;
  private double hoodSetpoint;


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
    this.drStick = drStick;
    this.opStick = opStick;

    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putNumber("Turret P: ", Constants.kTurretP);
    SmartDashboard.putNumber("Turret I: ", Constants.kTurretI);
    SmartDashboard.putNumber("Turret D: ", Constants.kTurretD);
    SmartDashboard.putNumber("Turret Setpoint: ", 0);

    SmartDashboard.putNumber("Shooter P: ", Constants.kShooterP);
    SmartDashboard.putNumber("Shooter I: ", Constants.kShooterI);
    SmartDashboard.putNumber("Shooter D: ", Constants.kShooterD);
    SmartDashboard.putNumber("Shooter F: ", Constants.kShooterF);
    
    

    // Start in low gear
    base.gearShiftOff();


  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    SmartDashboard.putNumber("Left Shooter Pwr", shooter.getShooterLeft().getAppliedOutput());

    SmartDashboard.putNumber("Distance: " , RobotMath.getDistanceFromTarget(shooter));


    
    SmartDashboard.putNumber("Shooter RPM:", shooter.getVelShooter());
    SmartDashboard.putNumber("Shooter RPM Graph", shooter.getVelShooter());

    // Uncomment after not using tuneShooterPID


    // HOOD
    SmartDashboard.putNumber("Hood Pos", shooter.getHoodPos());

    // CAN SHOOT?
    SmartDashboard.putBoolean("Can Shoot", canShoot);


    // CONTROLS

    base.drive(-drStick.getRawAxis(1), drStick.getRawAxis(2)*0.4); 

    shooter.moveTurret(opStick.getRawAxis(0));

    if (shooter.getVelShooter() >= 2750){
      canShoot = true;
    }
    else{
      canShoot = false;
    }
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
