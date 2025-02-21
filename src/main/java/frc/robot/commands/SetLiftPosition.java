package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class SetLiftPosition extends Command {

  private final Lift mLift;
  private boolean mEnd;
  private double mGoalPosition;
  double mStartingPosition;
  
  public SetLiftPosition(Lift lift, double goalPosition) {
    mLift = lift;
    mGoalPosition = goalPosition;
    addRequirements(mLift);
  }

  @Override
  public void initialize() {
    mStartingPosition = mLift.getEncoderValue();
    mEnd = false;
  }

  @Override
  public void execute() {

    // if (mGoalPosition < mLift.getEncoderValue() + 2 && mGoalPosition > mLift.getEncoderValue() - 2) {
    //   mEnd = true;
    // }

    if (mLift.getBottomLimit() && mStartingPosition > mLift.getEncoderValue()) {
      mLift.setEncoderValue(0.0);
    }

    mLift.setPosition(mGoalPosition);
  }

  @Override
  public void end(boolean interrupted) {
    mLift.stopMotor();
  }

  @Override
  public boolean isFinished() {
    if(mEnd) {
      return true;
    }
    return false;
  }
}