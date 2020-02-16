/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class TuneTurretPID extends PIDCommand {
  

  /**
   * Creates a new TurretFaceToAngle.
   */
  public TuneTurretPID(Shooter s) {
    super(
        // The controller that the command will use
        new PIDController(Constants.kTurretP, Constants.kTurretI, Constants.kTurretD),
        // This should return the measurement
        // this is the error rate, based off the the x value of the limelight (the # of degrees that you are off the center of the limelight)
        s::getLimelightX, 
        // This should return the setpoint (can also be a constant)
        // This is the turret setpoint, the setpoint for the turret is 0.0 and displays that on the SmartDashboard
        () -> SmartDashboard.getNumber("Turret Setpoint", 0.0),
        // This uses the output
        output -> {
          // moves the turret based off the output of this method
          s.moveTurret(output);
          // outputs the power at which the turret is moving on the SmartDashboard
          SmartDashboard.putNumber("Turret Power", output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
