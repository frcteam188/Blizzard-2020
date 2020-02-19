/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.util.Units;

public class RobotMath extends CommandBase {
  /**
   * Creates a new RobotMath.
   */

  private Shooter shooter;
  private double a1 = 26; //28 original value
  private double a2;
  private double h1 = 24.5;
  private double h2 = 90;
  private double d;

  private double area;
  
  public RobotMath(Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.shooter = shooter;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // h1 = Units.inchesToMeters(h1);
    // h2 = Units.inchesToMeters(h2);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    a2 = shooter.getLimelightY();
    d = (h2 - h1)/Math.tan(Units.degreesToRadians(a1 + a2)); // 105
    // d = Units.metersToInches(d);

    
    System.out.println("Distance: " + d);
    System.out.println("Y Angle: " + a2);
    // area = shooter.getLimelightArea();
    // System.out.println("Area: " + area);

    // d = area * (1.0/453.0);
    // System.out.println("Distance: " + d);
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
