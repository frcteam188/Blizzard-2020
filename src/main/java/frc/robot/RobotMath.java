/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.subsystems.Base;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.util.Units;

/**
 * Does math for different things
 * 
 * @author Zayeed Ghori, Shiv Patel, Edward Su
 */
public class RobotMath {

  private static double a1 = 26; //28 original value
  private static double a2;
  private static double h1 = 24.5;
  private static double h2 = 90;
  private static double d;

  private static double robotA;
  private static double horD;
  private static double depth;

  private static double hoodPosFromDistance;

  private static double area;

  /**
   * Calculates distance from Limelight target
   * @param shooter - Shooter subsystem
   * @return Distance from Limelight target
   */
  public static double getDistanceFromTarget(Shooter shooter){
    a2 = shooter.getLimelightY();
    d = (h2 - h1)/Math.tan(Units.degreesToRadians(a1 + a2));

    return d;
  }

  /**
   * 
   * @param shooter - Shooter subsystem
   * @param base - Base subsystem
   * @return The horizontal deviation from the target
   */
  public static double getHorDFromTarget(Shooter shooter, Base base){
    d = getDistanceFromTarget(shooter);
    robotA = shooter.getTurretAngle() + base.getBaseAngle();

    horD = d * Math.cos(Units.degreesToRadians(90 - robotA));

    return horD;
  }

  /**
   * 
   * @param shooter - Shooter subsystem
   * @param base - Base subsystem
   * @return The z-axis deviation from the target
   */
  public static double getDepthFromTarget(Shooter shooter, Base base){
    d = getDistanceFromTarget(shooter);
    robotA = shooter.getTurretAngle() + base.getBaseAngle();

    depth = d * Math.sin(Units.degreesToRadians(90 - robotA));

    return depth;
  }

  /**
   * Calculates hood position using distance and a relationship between them
   * @param shooter - Shooter subsystem
   * @return Hood position based on distance
   */
  public static double getHoodPosFromDistance(Shooter shooter){
    d = getDistanceFromTarget(shooter);
    hoodPosFromDistance = 0.2656*d + 42.772;

    return hoodPosFromDistance;
  }

  /**
   * Calculates shooter velocity using distance and a relationship between them
   * @param shooter - Shooter subsystem
   * @return Velocity of the shooter based on distance
   */
  public static double getVelFromDistance(Shooter shooter){
    d = getDistanceFromTarget(shooter);
    double velFromDistance = 0.2431*Math.pow(d, 2)-42.176*d + 4759.2;
    return velFromDistance;
  }

  /**
   * Calculates turret angle offset using the turret angle and a relationship between them
   * @param shooter - The shooter subsystem
   * @return The angle offset based on the Turret angle
   */
  public static double getAngleOffsetFromRobotAngle(Shooter shooter, Base base){
    robotA = shooter.getTurretAngle() + base.getBaseAngle();

    double angleOffsetFromRobotAngle = 0; // Change

    return angleOffsetFromRobotAngle;
  }
  public static double getTurretHoodPosFromDistance(Shooter shooter){
    d = getDistanceFromTarget(shooter);

    double hoodPos = 78.9;

    return hoodPos;
  }

  public static double getTrenchVelFromDistance(Shooter shooter){
    d = getDistanceFromTarget(shooter);

    double shooterRPM = 4450;

    return shooterRPM;
  }
}