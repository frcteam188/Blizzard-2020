/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.hal.PowerJNI;
import edu.wpi.first.wpilibj.util.Units;

/**
 * Does math for different things
 * 
 * @author Zayeed Ghori, Shiv Patel
 */
public class RobotMath {

  private static double a1 = 26; //28 original value
  private static double a2;
  private static double h1 = 24.5;
  private static double h2 = 90;
  private static double d;

  private static double hoodPosFromDistance;

  private static double area;

  /**
   * Calculates distance from Limelight target
   * @return Distance from Limelight target
   */
  public static double getDistanceFromTarget(Shooter shooter){
    a2 = shooter.getLimelightY();
    d = (h2 - h1)/Math.tan(Units.degreesToRadians(a1 + a2));

    return d;
  }

  /**
   * Calculates hood position using distance and a relationship between them
   * @return Hood position based on distance
   */
  public static double getHoodPosFromDistance(Shooter shooter){
    d = getDistanceFromTarget(shooter);
    hoodPosFromDistance = 0.3096*d + 40.3;

    return hoodPosFromDistance;
  }

  public static double getVelFromDistance(Shooter shooter){
    d = getDistanceFromTarget(shooter);
    double velFromDistance = 0.2431*Math.pow(d, 2)-42.176*d + 4759.2;
    return velFromDistance;
  }

}