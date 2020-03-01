/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autoCommands;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.autoCommands.*;
import frc.robot.commands.*;
import frc.robot.subsystems.Base;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoTestCommand extends SequentialCommandGroup {
  /**
   * Creates a new AutoTestCommand.
   */

  public AutoTestCommand(Base b, Intake i, Shooter s) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new ParallelCommandGroup(
            new BaseHighGearShift(b),
            new DeployIntake(i, false),
            new TurnTurret(s, -70)),
      
          new ShootingSequence(s, i),
          new ParallelRaceGroup(
            new DriveStraight(b, -6, 0, 0.65), 
            new ResetTurret(s)), 
          new TimeKillCommand(
            new TurnBasePID(b, -90), 1.2),
          new ParallelRaceGroup(new AutoIntake(i, s, b), 
            new SequentialCommandGroup(new DriveStraight(b, 14, -90, 0.45),
                                      new DriveStraight(b, -4.5, -90, 0.65))),
          new TimeKillCommand(
            new TurnBasePID(b, 0), 1.2),
          new ParallelRaceGroup(
            new AutoIntake(i, s, b),
            new DriveStraight(b, 5, 0, 0.65)),
          // new DriveStraight(b, -1.5, -25, 0.65),
          new TimeKillCommand(
            new TurnBasePID(b, -90), 1.2),
          new DriveStraight(b, -8, -90, 0.65),
          new ShootingSequence(s, i),
          new ResetTurret(s)
         );
  }
}
