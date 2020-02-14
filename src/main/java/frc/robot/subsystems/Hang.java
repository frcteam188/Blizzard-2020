/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Hang extends SubsystemBase {
  /**
   * Creates a new Hang.
   */

  CANSparkMax hang = new CANSparkMax(0, MotorType.kBrushless);
  DoubleSolenoid hangTopLeftS = new DoubleSolenoid(0, 0);
  DoubleSolenoid hangTopRightS = new DoubleSolenoid(0, 0);
  DoubleSolenoid hangBotLeftS = new DoubleSolenoid(0, 0);
  DoubleSolenoid hangBotRightS = new DoubleSolenoid(0, 0);



  public Hang() {
  }

  /**
   * Method to hang properly
   * This function is incomplete
   * 
   * @param pow - the power at which the hang will be run at
   * @author Zayeed Ghori
   */
  public void hang(double pow){
  }

  /**
   * Method to fire first stage of hang
   * This function is incomplete
   * 
   * @author Zayeed Ghori
   */
  public void fireFirstStage(){
    hangBotLeftS.set(kForward);
    hangBotRightS.set(kForward);
  }


  /**
   * Method to second first stage of hang
   * This function is incomplete
   * 
   * @author Zayeed Ghori
   */
  public void retractFirstStage(){
    hangBotLeftS.set(kReverse);
    hangBotRightS.set(kReverse);
  }

  /**
   * Method to retract second stage of hang
   * This function is incomplete
   * 
   * @author Zayeed Ghori
   */
  public void fireSecondStage(){
    hangTopLeftS.set(kForward);
    hangTopRightS.set(kForward);
  }
  /**
   * Method to fire second stage of hang
   * This function is incomplete
   * 
   * @author Zayeed Ghori
   */
  public void retractSecondStage(){
    hangTopLeftS.set(kReverse);
    hangTopRightS.set(kReverse);
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
