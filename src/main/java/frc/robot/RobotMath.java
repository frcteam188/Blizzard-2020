/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class RobotMath {
    double a1 = 61.0; // Vert Mounting angle of limelight
    double a2 = shooter.getLimelightY(); // Vert angle of limelight target

    double h1 = 24.8; // height of limelight to ground (inches)
    double h2 = 98.25; // height of limelight to target (inches)
    
    
    
    double d = (h2 - h1)/Math.tan(a1 + a2); // Calculates the distance between the limelight and the targets

    System.out.println("distance: "+ d);
}
