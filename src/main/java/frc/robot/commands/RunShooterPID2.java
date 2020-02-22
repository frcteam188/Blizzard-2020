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

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class RunShooterPID2 extends PIDCommand {
  private Shooter shooter;
  /**
   * Creates a new RunShooterPID2.
   */
  public RunShooterPID2(Shooter s) {
    super(
        // The controller that the command will use
        new PIDController(Constants.kShooterP, Constants.kShooterI, Constants.kShooterD),
        // This should return the measurement
        () -> s.getVelShooter(),
        // This should return the setpoint (can also be a constant)
        () -> 5420,
        // This uses the output
        output -> {
          // Use the output here
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    this.shooter = s;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
