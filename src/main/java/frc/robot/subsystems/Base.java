/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

/**
 * Class for the base of the robot
 * 
 * @author Shiv Patel
 */
public class Base extends SubsystemBase {

  //initalize all the motors and encoders and make names for them
  CANSparkMax leftFront = new CANSparkMax(RobotMap.leftFront, MotorType.kBrushless);
  CANEncoder frontLeftEnc = new CANEncoder(leftFront);
  
  CANSparkMax leftBack = new CANSparkMax(RobotMap.leftBack, MotorType.kBrushless);
  CANEncoder frontBackEnc = new CANEncoder(leftBack);

  CANSparkMax rightFront = new CANSparkMax(RobotMap.rightFront, MotorType.kBrushless);
  CANEncoder rightLeftEnc = new CANEncoder(rightFront);

  CANSparkMax rightBack = new CANSparkMax(RobotMap.rightBack, MotorType.kBrushless);
  CANEncoder rightBackEnc = new CANEncoder(rightBack);

  // initializing a double solenoid for the gearbox's shifters
  DoubleSolenoid baseS = new DoubleSolenoid(RobotMap.forwardChannelBase, RobotMap.reverseChannelBase);
  /**
   * Creates a new Base.
   */
  public Base() {
    leftFront.setOpenLoopRampRate(0.2);
    leftBack.setOpenLoopRampRate(0.2);
    rightFront.setOpenLoopRampRate(0.2);
    rightBack.setOpenLoopRampRate(0.2);
  }
  /**
   * Method used to actually drive the robot itself
   * 
   * @author Shiv Patel
   * @param y - the y axis for the joystick
   * @param x - the x axis for the joystick
   */
  public void drive(double y, double x){
    leftFront.set(y + x);
    leftBack.set(y + x);
    rightFront.set(-y + x);
    rightBack.set(-y + x);
  }

  /**
   * Method shift the gear in
   * 
   * @author Shiv Patel
   */
  public void gearShiftOn(){
    baseS.set(kForward);
  }
  
  /**
   * Method to shift the gear back out
   * 
   * @author Shiv Patel
   */
  public void gearShiftOff(){
    baseS.set(kReverse);
  }





  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
