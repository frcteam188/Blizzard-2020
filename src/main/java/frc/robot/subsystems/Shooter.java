/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */
  // Flywheel motors
  CANSparkMax shooterLeft = new CANSparkMax(0, MotorType.kBrushless);
  CANSparkMax shooterRight = new CANSparkMax(0, MotorType.kBrushless);

  // Turret motor
  CANSparkMax turret = new CANSparkMax(0, MotorType.kBrushless);

  // Hood Motor
  CANSparkMax hood = new CANSparkMax(0, MotorType.kBrushless);

  Encoder hoodEnc;
  Encoder turretEnc;
  Encoder shooterEnc;

  
  public Shooter() {

  }

  /**
   * Method to run the shooter flywheel
   * 
   * @param lPow - the power *opposite of right motor* at which the left motor (shooterLeft) will be run at 
   * @param rPow - the power *opposite of left motor* at which the right motor (shooterRight) will be run at
   * @author Zayeed Ghori
   */
  public void shoot(double lPow, double rPow){
    shooterLeft.set(lPow);
    shooterRight.set(rPow);
  }





  /**
   * Method to run the turret
   * 
   * @param pow - the power at which the turret will be run at
   * @author Zayeed Ghori
   */
  public void moveTurret(double pow){
    turret.set(pow);
  }


  


  /**
   * Method to run the hood
   * 
   * @param pow - the power at which the hood will be run at
   * @author Zayeed Ghori
   */
  public void moveHood(double pow){
    hood.set(pow);
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
