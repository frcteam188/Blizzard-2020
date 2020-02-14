/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Intake extends SubsystemBase {
  CANSparkMax intake = new CANSparkMax(RobotMap.intake, MotorType.kBrushless);
  
  DoubleSolenoid intakeS = new DoubleSolenoid(1, 2); // THESE ARE TEMPORARY VALUES, WILL BE CHANGED WHEN WE GET ACTUAL VALUES
  /**
   * Creates a new Intake.
   */
  public Intake() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
