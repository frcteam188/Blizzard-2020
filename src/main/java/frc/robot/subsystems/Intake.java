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
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

/**
 * Class for the intake subsystem
 * 
 * @author Shiv Patel
 */
public class Intake extends SubsystemBase {
  CANSparkMax intake = new CANSparkMax(RobotMap.intake, MotorType.kBrushless);
  CANSparkMax feeder = new CANSparkMax(RobotMap.feeder, MotorType.kBrushless);

  DoubleSolenoid intakeS = new DoubleSolenoid(RobotMap.forwardChannelIntake, RobotMap.reverseChannelIntake);

  CANSparkMax shooterFeeder = new CANSparkMax(RobotMap.shooterFeeder, MotorType.kBrushless);

  AnalogInput sensor = new AnalogInput(0);

  /**
   * Creates a new Intake.
   */
  public Intake() {

    shooterFeeder.enableVoltageCompensation(12);
    feeder.enableVoltageCompensation(12);

  }

  /**
   * Method to run the intake
   * 
   * @param pow - the power at which the intake will be run at
   */
  public void intake(double pow) {
    intake.set(pow);
  }

  /**
   * Method to run the feeder
   * 
   * @param pow - the power at which the feeder will be run at
   */
  public void feed(double pow) {
    feeder.set(pow);
  }

  /**
   * Method to deploy the intake
   * 
   */
  public void deployIntake() {
    intakeS.set(kReverse);
  }

  /**
   * Method reset the intake
   * 
   */
  public void resetIntake() {
    intakeS.set(kForward);
  }

  /**
   * Returns the value that the sensor is outputting
   * 
   * @return the value that the sensor is outputting
   */
  public double getValueOfSensor() {
    return sensor.getVoltage();
  }

  /**
   * Runs the feeder under the shooter at a certain power
   * 
   * @param pow - will run the shooterFeeder at this power
   */
  public void runShooterFeeder(double pow) {
    shooterFeeder.set(pow);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
