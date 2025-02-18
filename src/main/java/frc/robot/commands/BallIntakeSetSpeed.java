package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.BallIntake;

public class BallIntakeSetSpeed extends Command {
  
  private final BallIntake mBallIntake;
  private double mSpeed;

  public BallIntakeSetSpeed(BallIntake ballIntake, double speed) {
     mBallIntake = ballIntake;
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
