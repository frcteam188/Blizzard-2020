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
public class TuneTurnBasePID extends PIDCommand {

  /**
   * Creates a new TurnBasePID. *UNFINISHED*
   */

  private double angle;
  private Base base;
  
  public TuneTurnBasePID(Base b, double a) {
    super(
        // The controller that the command will use
        new PIDController(
          SmartDashboard.getNumber("Turn P", Constants.kTurnBaseP),
          SmartDashboard.getNumber("Turn I", Constants.kTurnBaseI),
          SmartDashboard.getNumber("Turn D", Constants.kTurnBaseD)
        ),
        // This should return the measurement
        b::getBaseAngle,
        // This should return the setpoint (can also be a constant)
        () -> {
          return SmartDashboard.getNumber("Angle Setpoint", a);
        },
        // This uses the output
        output -> {
          if(output > 0) 
            output = Math.min(output, 0.7);
          else 
            output = Math.max(output, -0.7);
          // Use the output here
          SmartDashboard.putNumber("PID pwr", output);
          b.drive(0, output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    this.angle = a;
    this.base = b;
    getController().setIntegratorRange(-1, 1);
  }

  @Override
  public void initialize() {
    // TODO Auto-generated method stub
    super.initialize();
    SmartDashboard.putNumber("Turn P", Constants.kTurnBaseP);
    SmartDashboard.putNumber("Turn I", Constants.kTurnBaseI);
    SmartDashboard.putNumber("Turn D", Constants.kTurnBaseD);
    SmartDashboard.putNumber("Angle Setpoint", this.angle);

  }

  @Override
  public void execute() {
    // TODO Auto-generated method stub
    super.execute();
    double p = SmartDashboard.getNumber("Turn P", Constants.kTurnBaseP);
    double i = SmartDashboard.getNumber("Turn I", Constants.kTurnBaseI);
    double d = SmartDashboard.getNumber("Turn D", Constants.kTurnBaseD);
    // getController().setSetpoint(SmartDashboard.getNumber("Angle Setpoint", this.angle));
    getController().setPID(p, i, d);
    
    if(Math.abs(base.getBaseAngle() - SmartDashboard.getNumber("Angle Setpoint", this.angle)) < 1)
      getController().reset();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
