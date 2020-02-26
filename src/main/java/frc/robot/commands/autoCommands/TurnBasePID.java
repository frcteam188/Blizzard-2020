/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autoCommands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Base;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class TurnBasePID extends PIDCommand {

  /**
   * Creates a new TurnBasePID. *UNFINISHED*
   */
  public TurnBasePID(Base b, double angle) {
    super(
        // The controller that the command will use
        new PIDController(
          SmartDashboard.getNumber("Turn P: ", Constants.kTurnBaseP),
          SmartDashboard.getNumber("Turn I: ", Constants.kTurnBaseI),
          SmartDashboard.getNumber("Turn D: ", Constants.kTurnBaseD)
        ),
        // This should return the measurement
        () -> b.getBaseAngle(),
        // This should return the setpoint (can also be a constant)
        () -> angle,
        // This uses the output
        output -> {
          // Use the output here
          b.drive(output, output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  @Override
  public void initialize() {
    // TODO Auto-generated method stub
    super.initialize();
    SmartDashboard.putNumber("Turn P: ", Constants.kTurnBaseP);
    SmartDashboard.putNumber("Turn I: ", Constants.kTurnBaseI);
    SmartDashboard.putNumber("Turn D: ", Constants.kTurnBaseD);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
