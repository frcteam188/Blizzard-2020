/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Spark;

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

  CANSparkMax rightFront = new CANSparkMax(RobotMap.rightFront, MotorType.kBrushless);

  CANSparkMax rightBack = new CANSparkMax(RobotMap.rightBack, MotorType.kBrushless);

  // initializing a double solenoid for the gearbox's shifters
  DoubleSolenoid baseS = new DoubleSolenoid(RobotMap.forwardChannelBase, RobotMap.reverseChannelBase);

  // ini
  AHRS navx = new AHRS(Port.kMXP);

  // LED Strip
  Spark ledStrip = new Spark(RobotMap.ledStrip);

  /**
   * Creates a new Base.
   */
  public Base() {
    // leftFront.setOpenLoopRampRate(0.2);
    // leftBack.setOpenLoopRampRate(0.2);
    // rightFront.setOpenLoopRampRate(0.2);
    // rightBack.setOpenLoopRampRate(0.2);
    frontLeftEnc.setPositionConversionFactor(0.1626704);

  }
  /**
   * Method used to actually drive the robot itself
   * 
   * @param y - the y axis for the joystick
   * @param x - the x axis for the joystick
   */
  public void drive(double y, double x){
    leftFront.set(y + x);
    // leftBack.set(y + x);
    // rightFront.set(-y + x);
    // rightBack.set(-y + x);
  }

  /**
   * Method shift the gear in
   */
  public void gearShiftOn(){
    baseS.set(kReverse);
  }
  
  /**
   * Method to shift the gear back out
   * 
   */
  public void gearShiftOff(){
    baseS.set(kForward);
  }

  /**
   * Returns the angle of the navx
   * @return the angle of the base from the navx
   */
  public double getBaseAngle(){
    return navx.getAngle();
  }

  /**
   * Returns the navx
   * @return the navx on the base
   */
  public AHRS getNavx(){
    return navx;
  }

  // Test, change to zero navx angle if necessary
  /**
   * This will reset the navx angle
   */
  public void resetNavxAngle(){
    navx.zeroYaw();
  }

  // Test
  /**
   * Returns the Spark at the Left front motor
   * 
   * @return the Left Front Spark
   */
  public CANSparkMax getLeftFront(){
    return leftFront;
  }
  /**
   * Returns the Spark at the Left Back motor
   * 
   * @return the Right Front Spark
   */
  public CANSparkMax getLeftBack(){
    return leftBack;
  }
  /**
   * Returns the Spark at the Right Front motor
   * 
   * @return the Right Front Spark
   */
  public CANSparkMax getRightFront(){
    return rightFront;
  }
  /**
   * Returns the Spark at the Right Back motor
   * 
   * @return the Right Back Spark
   */
  public CANSparkMax getRightBack(){
    return rightBack;
  }

  /**
   * Sets the position of the base to zero when called
   */
  public void resetBaseEnc(){
    frontLeftEnc.setPosition(0);
  }

  /**
   * @return the frontLeftEnc
   */
  public CANEncoder getFrontLeftEnc() {
    return frontLeftEnc;
  }

  /**
   * Sets the mode of the LED strip based on the power to LED mode table
   * @link Table: http://www.revrobotics.com/content/docs/REV-11-1105-UM.pdf
   * @param mode - The mode of the LED strip
   */
  public void setLEDStripMode(double mode){
    ledStrip.set(mode);
    System.out.println(mode);
  }

  /**
   * Returns the LEDStrip
   * @return - The LEDStrip on the base
   */
  public Spark getLEDStrip(){
    return ledStrip;
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setToCoast(){
    leftFront.setIdleMode(IdleMode.kCoast);
    leftBack.setIdleMode(IdleMode.kCoast);
    rightFront.setIdleMode(IdleMode.kCoast);
    rightBack.setIdleMode(IdleMode.kCoast);
  }

  public void setToBrake(){
    leftFront.setIdleMode(IdleMode.kBrake);
    leftBack.setIdleMode(IdleMode.kBrake);
    rightFront.setIdleMode(IdleMode.kBrake);
    rightBack.setIdleMode(IdleMode.kBrake);
  }
}
