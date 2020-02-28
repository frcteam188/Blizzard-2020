/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static double kHoodP = 0.082000;
    public static double kHoodI = 0;
    public static double kHoodD = 0;
    
    public static double kTurretP = 0.050000;
    public static double kTurretI = 0;
    public static double kTurretD = 0.001100;

    public static double kShooterP = 0.000340; // EXPERIMENT WITH THIS
    public static double kShooterI = 0;
    public static double kShooterD = 0.000050;
    public static double kShooterF = 0.00017857142;

    public static double kTurnBaseP = 0.025;
    public static double kTurnBaseI = 0;
    public static double kTurnBaseD = 0.0045;

    public static double kDriveTurnP = 0.025;
    public static double kDriveTurnI = 0;
    public static double kDriveTurnD = 0.003;

    public static double kBaseP = 0.55;
    public static double kBaseI = 0;
    public static double kBaseD = 0.05;
}
