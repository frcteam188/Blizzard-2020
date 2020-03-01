/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Holds all of the constants for things like ids for motors and selenoids
 * 
 * @author Zayeed Ghori, Edward Su, Shiv Patel
 */
public class RobotMap {
    // ids for the base motors
    public static int leftFront = 15; //1
    public static int leftBack = 2;
    public static int rightFront = 6;
    public static int rightBack = 7;

    public static int hood = 10;
    public static int turret = 11;
    public static int shooterLeft = 12;
    public static int shooterFeeder = 13;
    public static int shooterRight = 14;

    // PCM ids for solenoids
    // id for forward and reverse channel for base
    public static int forwardChannelBase = 0;
    public static int reverseChannelBase = 7;

    // id for forward and reverse channel for intake
    public static int forwardChannelIntake = 1;
    public static int reverseChannelIntake = 6; 

    // id for forward and reverse channel for stage 1 of the hang 
    public static int forwardChannelHangStage1 = 2; 
    public static int reverseChannelHangStage1 = 5;

    // id for forward and reverse channel for stage 2 of the hang
    public static int forwardChannelHangStage2 = 3;
    public static int reverseChannelHangStage2 = 4;

    // id for hang motor
    public static int hang = 4;
    
    // id for intake motor
    public static int intake = 3;

    // id for feeder motor
    public static int feeder = 5;

    // PWM port for ledStrip
    public static int ledStrip = 0;



}
