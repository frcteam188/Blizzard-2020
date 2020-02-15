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

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */

  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");

  double x = tx.getDouble(0.0);
  double y = ty.getDouble(0.0);
  double area = ta.getDouble(0.0);

  // Flywheel (motors, encoder)
  CANSparkMax shooterLeft = new CANSparkMax(RobotMap.shooterLeft, MotorType.kBrushless);
  CANSparkMax shooterRight = new CANSparkMax(RobotMap.shooterRight, MotorType.kBrushless);
  CANEncoder shooterEnc = new CANEncoder(shooterLeft);


  // Turret motor, encoder
  CANSparkMax turret = new CANSparkMax(RobotMap.turret, MotorType.kBrushless);
  CANEncoder turretEnc = new CANEncoder(turret);

  // Hood Motor, encoder
  CANSparkMax hood = new CANSparkMax(RobotMap.hood, MotorType.kBrushless);
  CANEncoder hoodEnc = new CANEncoder(hood);

  CANSparkMax shooterFeeder = new CANSparkMax(RobotMap.shooterFeeder, MotorType.kBrushless);

  
  public Shooter() {

  }

  /**
   * Method to run the shooter flywheel
   * 
   * @param pow - the power at which the shooter will be run at
   * @author Zayeed Ghori
   */
  public void shoot(double pow){
    shooterLeft.set(pow);
    shooterRight.set(-pow);
  }

  public void runShooterFeeder(double pow){
    shooterFeeder.set(pow);
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

  /**
   * Method to get the velocity of the flywheel, through the speed of motors
   * 
   * @author Zayeed Ghori
   */

  public double getVelShooter(){
    return shooterEnc.getVelocity();
  }

  /**
   * Method to get the hood's position
   * 
   * @author Zayeed Ghori
   */

  public double getHoodPos(){
    return hoodEnc.getPosition();
  }


  /**
   * Method to get the turret's position
   * 
   * @author Zayeed Ghori
   */

  public double getTurretPos(){
    return turretEnc.getPosition();
  }

  /**
   * Testing method to reset the hood encoder to 0
   * 
   * @author Zayeed Ghori
   */

  public void resetHoodPos(){
    hoodEnc.setPosition(0);
  }

  public double getLimelightX(){
    x = tx.getDouble(0.0);
    return x;
  }
  public double getLimelightY(){
    y = ty.getDouble(0.0);
    return y;
  }


  /**
   * Testing method to reset the turret encoder to 0
   * 
   * @author Zayeed Ghori
   */

  public void resetTurretPos(){
    turretEnc.setPosition(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
