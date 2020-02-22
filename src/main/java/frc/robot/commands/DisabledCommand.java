/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Base;
import frc.robot.subsystems.Shooter; 

// THIS COMMAND WILL SET THE ZERO
/**
 * This command will set encoder values (positions) to zero before the robot is enabled
 * 
 * @author Zayeed Ghori, Edward Su
 */

public class DisabledCommand extends CommandBase {
  /**
   * Creates a new DisabledCommand.
   */

  private final Joystick drStick;
  private final Base base;
  private final Shooter shooter;

  public DisabledCommand(Joystick driveStick, Base b, Shooter s) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.drStick = driveStick;
    this.base = b;
    this.shooter = s;
  }

  public boolean runsWhenDisabled() {
    return true;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.setLimelightLED(Shooter.LED_ON); // change to off
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(drStick.getRawButton(3)){
      shooter.resetHoodPos();
      shooter.resetTurretPos();
      base.resetBaseEnc();
      base.resetNavxAngle();
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
