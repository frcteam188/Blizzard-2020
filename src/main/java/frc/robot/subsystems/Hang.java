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
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;



public class Hang extends SubsystemBase {
  /**
   * Creates a new Hang.
   */

<<<<<<< HEAD
  CANSparkMax hang = new CANSparkMax(0, MotorType.kBrushless);
  DoubleSolenoid hangTopLeftS = new DoubleSolenoid(0, 0);
  DoubleSolenoid hangTopRightS = new DoubleSolenoid(0, 0);
  DoubleSolenoid hangBotLeftS = new DoubleSolenoid(0, 0);
  DoubleSolenoid hangBotRightS = new DoubleSolenoid(0, 0);



=======
  CANSparkMax hang; //= new CANSparkMax(0, MotorType.kBrushless);
  DoubleSolenoid stageOne = new DoubleSolenoid(RobotMap.forwardChannelHangStage1, RobotMap.reverseChannelHangStage1);
  DoubleSolenoid stageTwo = new DoubleSolenoid(RobotMap.forwardChannelHangStage2, RobotMap.reverseChannelHangStage2);
  public static final int STATE_OUT = 1;
  public static final int STATE_IN = -1;
>>>>>>> 39b4daf7f26334f50061ffa82cc6823be295cb48
  public Hang() {
  }

  /**
<<<<<<< HEAD
   * Method to hang properly
   * This function is incomplete
=======
   * Method to run the hang
   * This method will pull the robot up with the motor
>>>>>>> 39b4daf7f26334f50061ffa82cc6823be295cb48
   * 
   * @param pow - the power at which the hang will be run at
   * @author Zayeed Ghori
   */
<<<<<<< HEAD
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
=======
  public void pullUp(double pow){
    hang.set(pow);
>>>>>>> 39b4daf7f26334f50061ffa82cc6823be295cb48
  }

  /**
   * Method to move stage one of the hang based on the state that is passed in
   * 
   * @param state: can either be 1 (to deploy) or -1 (to retract)
   * @author Shiv Patel
   */
  public void moveStageOne(int state){
    if (state == 1){
      stageOne.set(kForward);
    }
    else if(state == -1){
      stageOne.set(kReverse);
    }
  }

  /**
   * Method to move stage two of the hang based on the state that is passed in
   * 
   * @param state: can either be 1 (to deploy) or -1 (to retract)
   * @author Shiv Patel
   */
  public void moveStageTwo(int state){
    if (state == 1){
      stageTwo.set(kForward);
    }
    else if(state == -1){
      stageTwo.set(kReverse);
    }
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
