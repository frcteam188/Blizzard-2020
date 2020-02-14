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


public class Hang extends SubsystemBase {
  /**
   * Creates a new Hang.
   */

  CANSparkMax hang = new CANSparkMax(0, MotorType.kBrushless);

  public Hang() {
  }

  /**
   * Method to run the hang
   * This function makes the hang motor move, pow is from 0.0 to 1.0
   * 
   * @param pow - the power at which the hang will be run at
   * @author Zayeed Ghori
   */
  public void hang(double pow){
    hang.set(pow);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
