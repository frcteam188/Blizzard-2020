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
import edu.wpi.first.wpilibj.DoubleSolenoid;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;


/**
 * Class for the intake subsystem
 * 
 * @author Shiv Patel
 */
public class Intake extends SubsystemBase {
  CANSparkMax intake = new CANSparkMax(RobotMap.intake, MotorType.kBrushless);
  
  DoubleSolenoid intakeS = new DoubleSolenoid(RobotMap.forwardChannelIntake, RobotMap.reverseChannelIntake);
  /**
   * Creates a new Intake.
   */
  public Intake() {

  }

  /**
   * Method to run the intake
   * 
   * @param pow - the power at which the intake will be run at
   * @author Shiv Patel
   */
  public void succ(double pow){
    intake.set(pow);
  }

  public void outtake(double pow){
    intake.set(-pow);
  }

  public void deployIntake(){
    intakeS.set(kForward);
  }

  public void resetIntake(){
    intakeS.set(kReverse);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
