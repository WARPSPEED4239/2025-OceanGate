package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class LiftKeepPosition extends Command {

  private final Lift mLift;
  private double mSpeed = 0.05;
  private double mGoalPosition;
  private double mCurrentPosition;

  public LiftKeepPosition(Lift lift) {
    mLift = lift;
    addRequirements(mLift);
  }

  @Override
  public void initialize() {
    mGoalPosition = mLift.getEncoderValue();
  }

  @Override
  public void execute() {
    mCurrentPosition = mLift.getEncoderValue();

    if(mGoalPosition - 2 > mCurrentPosition) {
      mLift.setOutputWithLimitSensors(mSpeed);
      System.out.println("0");
    } else if(mGoalPosition + 2 < mCurrentPosition) {
      mLift.setOutputWithLimitSensors(-mSpeed);
      System.out.println("1");
    } else {
      mLift.setOutputWithLimitSensors(0.0);
      System.out.println("2");
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
