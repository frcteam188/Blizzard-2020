/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotMap;
/**
 * Shooter subsystem
 * 
 * @author Zayeed Ghori
 */

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */

  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");
  NetworkTableEntry toggleLED = table.getEntry("ledMode");

  double x = tx.getDouble(0.0);
  double y = ty.getDouble(0.0);
  double area = ta.getDouble(0.0);

  // Flywheel (motors, encoder)
  CANSparkMax shooterLeft = new CANSparkMax(RobotMap.shooterLeft, MotorType.kBrushless);
  CANSparkMax shooterRight = new CANSparkMax(RobotMap.shooterRight, MotorType.kBrushless);
  CANEncoder shooterEnc = new CANEncoder(shooterLeft);
  CANPIDController pidControllerLeft = shooterLeft.getPIDController();
  CANPIDController pidControllerRight = shooterRight.getPIDController();


  // Turret motor, encoder
  CANSparkMax turret = new CANSparkMax(RobotMap.turret, MotorType.kBrushless);
  CANEncoder turretEnc = new CANEncoder(turret);

  // Hood Motor, encoder, PID controller
  // Hood Motor, encoder
  CANSparkMax hood = new CANSparkMax(RobotMap.hood, MotorType.kBrushless);
  CANEncoder hoodEnc = new CANEncoder(hood);
  CANPIDController hoodPIDController = hood.getPIDController();

  //limelight onstants
  public static final int LED_DEFAULT = 0;
  public static final int LED_OFF = 1;
  public static final int LED_BLINK = 2;
  public static final int LED_ON = 3;
  

  //creating shooter object

  
  public Shooter() {
    hoodPIDController.setP(Constants.kHoodP);
    hoodPIDController.setI(Constants.kHoodI);
    hoodPIDController.setD(Constants.kHoodD);

    pidControllerLeft.setP(Constants.kShooterP);
    pidControllerLeft.setI(Constants.kShooterI);
    pidControllerLeft.setD(Constants.kShooterD);
    pidControllerLeft.setFF(Constants.kShooterF);
    pidControllerLeft.setOutputRange(Constants.kShooterMinOutput,Constants.kShooterMaxOutput);
    pidControllerLeft.setIZone(0);

    pidControllerRight = pidControllerLeft;

    turretEnc.setPositionConversionFactor(1.54373);

    // Nominal Voltage Compensation
    shooterRight.enableVoltageCompensation(12);
    shooterLeft.enableVoltageCompensation(12);
    hood.enableVoltageCompensation(12);
    turret.enableVoltageCompensation(12);
  }
  /**
   * returns the hoodPIDController object
   * 
   * @return hoodPIDController - the PIDController instance variable
   * 
   */
  public CANPIDController getHoodPIDController(){
    return hoodPIDController;
  }
  /**
   * returns the hoodPIDController object
   * 
   * @return hoodPIDController - the PIDController instance variable
   */
  public CANPIDController getShooterLeftPIDController(){
    return pidControllerLeft;
  }
  public CANPIDController getShooterRightPIDController(){
    return pidControllerRight;
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


  /**
   * Method to run the turret
   * 
   * @param pow - the power at which the turret will be run at
   * @author Zayeed Ghori
   */
  public void moveTurret(double pow){
    if(pow > 0 && getTurretAngle() > 30){
      pow = 0;
    }
    else if(pow < 0 && getTurretAngle() < -210){
      pow = 0;
    }
    turret.set(pow);
    
  }
/**
 * Method that turns the limelight on or off
 * 
 * @param state - state of limelight
 */
  public void setLimelightLED(int state){
    toggleLED.setNumber(state);
  }


  /**
   * Method to run the hood
   * 
   * @param pow - the power at which the hood will be run at
   * @author Zayeed Ghori
   */

  public void moveHood(double pow){
    if(pow > 0 && getHoodPos() > 110){
      pow = 0;
    }
    else if(pow < 0 && getHoodPos() < 0){
      pow = 0;
    }
    
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

  public double getTurretAngle(){
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

  public CANSparkMax getShooterLeft(){
    return this.shooterLeft;
  }

  public CANSparkMax getShooterRight(){
    return this.shooterRight;
  }

  public void setTurretAngle(double newAngle){
    turretEnc.setPosition(newAngle);
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
