/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autoCommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.commands.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShootingSequence extends ParallelRaceGroup {
  /**
   * Creates a new ShootingSequence.
   */
  public Shooter shooter;
  public Intake intake;
  public ShootingSequence(Shooter s, Intake i) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new MidHoodPID(s),
        new MidShooterPID(s),
        new TurretPID(s, -2),
        new SequentialCommandGroup(
            new ShooterGetToSpeed(s),
            new ParallelRaceGroup(
                new ShootFeed(i, -0.9),
                new WaitCommand(1)))
      
    );
  }
  public ShootingSequence(Shooter s, Intake i, double timeSecs) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new MidHoodPID(s),
        new MidShooterPID(s),
        new TurretPID(s, -2),
        new SequentialCommandGroup(
            new ShooterGetToSpeed(s),
            new ParallelRaceGroup(
                new ShootFeed(i, -0.9, 0.7),
                new WaitCommand(timeSecs)))
      
    );
  }
}
