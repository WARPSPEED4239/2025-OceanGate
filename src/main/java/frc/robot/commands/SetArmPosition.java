package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Lift;

public class SetArmPosition extends Command {
  private final Arm mArm;
  private final Lift mLift;
  private boolean mEnd;
  public double mGoalPosition;
  public double mEncoderValue;
  double mStartingPosition;

  public SetArmPosition(Arm arm, Lift lift, double GoalPosition) {
    mArm = arm;
    mLift = lift;
    mGoalPosition = GoalPosition;
    addRequirements(mArm, mLift); 
  }

  @Override
  public void initialize() {
    mStartingPosition = mArm.getEncoderValue();
    mEnd = false;
  }

  @Override
  public void execute() {

    if (mGoalPosition < mArm.getEncoderValue() + 0.5 && mGoalPosition > mArm.getEncoderValue() - 0.5) {
      mEnd = true;
    }

    if (mArm.getLeftLimit() && mStartingPosition > mArm.getEncoderValue()) {
      mArm.setEncoderValue(-15.95);
      mEnd = true;
    } else if(mArm.getMiddleLimit()) {
      mArm.setEncoderValue(0.0);
    } else if(mArm.getRightLimit() && mStartingPosition < mArm.getEncoderValue()) {
      mArm.setEncoderValue(59.95);
      mEnd = true;
    } 

    if (mLift.getEncoderValue() > 50.0) {
      mArm.setPosition(mGoalPosition);
    } else {
      mArm.stopMotor();
    }
  }

  @Override
  public void end(boolean interrupted) {
    mArm.stopMotor();
  }

  @Override
  public boolean isFinished() {
    if(mEnd) {
      return true;
    }
    return false;
  }
}