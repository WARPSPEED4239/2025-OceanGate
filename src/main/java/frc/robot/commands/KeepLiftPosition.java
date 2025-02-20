package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class KeepLiftPosition extends Command {

  private final Lift mLift;
  private boolean mEnd;
  double mStartingPosition;
  
  public KeepLiftPosition(Lift lift) {
    mLift = lift;
    addRequirements(mLift);
  }

  @Override
  public void initialize() {
    mStartingPosition = mLift.getEncoderValue();
    if (mStartingPosition < 0.0) {
      mStartingPosition = 0.0;
    }
    mEnd = false;
  }

  @Override
  public void execute() {
    mLift.setPosition(mStartingPosition);

    if (mLift.getBottomLimit() && mStartingPosition > mLift.getEncoderValue()) {
      mLift.setEncoderValue(0.0);
    }
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