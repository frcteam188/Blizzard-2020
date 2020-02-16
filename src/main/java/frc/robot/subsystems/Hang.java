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

  CANSparkMax hang = new CANSparkMax(RobotMap.hang, MotorType.kBrushless); //= new CANSparkMax(0, MotorType.kBrushless);
  DoubleSolenoid stageOne = new DoubleSolenoid(RobotMap.forwardChannelHangStage1, RobotMap.reverseChannelHangStage1);
  DoubleSolenoid stageTwo = new DoubleSolenoid(RobotMap.forwardChannelHangStage2, RobotMap.reverseChannelHangStage2);
  public static final int STATE_OUT = 1;
  public static final int STATE_IN = -1;
  public Hang() {
  }

  /**
   * Method to run the hang
   * This method will pull the robot up with the motor
   * 
   * @param pow - the power at which the hang will be run at
   * @author Zayeed Ghori
   */
  public void pullUp(double pow){
    hang.set(pow);
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
    // System.out.println("State: " + state);
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
    // System.out.println("State: " + state);
  }




  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
