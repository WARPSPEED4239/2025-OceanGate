package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BallIntake;

public class BIntake extends Command {
  
  private final BallIntake mBallIntake;
  private double mSpeed;

  public BIntake(BallIntake bintake, double speed) {
     mBallIntake = bintake;
     mSpeed = speed;
     addRequirements(mBallIntake);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    mBallIntake.setSpeed(mSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    mBallIntake.stopMotor();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
