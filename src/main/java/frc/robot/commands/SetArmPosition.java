package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

public class SetArmPosition extends Command {
  private final Arm mArm;
  private boolean mEnd;
  public double mGoalPosition;
  public double mEncoderValue;
  double mSpeed;
  double mStartingPosition;

  public SetArmPosition(Arm arm, double speed, double GoalPosition) {
    mArm = arm;
    mSpeed = speed;
    mGoalPosition = GoalPosition;
    addRequirements(mArm); 
  }

  @Override
  public void initialize() {
    mStartingPosition = mArm.getEncoderValue();
    mEnd = false;
    mArm.setPosition(mGoalPosition);
  }

  @Override
  public void execute() {

    if (mArm.getLeftLimit() && mStartingPosition > mArm.getEncoderValue()) {
      mArm.setEncoderValue(-15.95);
      mEnd = true;
    } else if(mArm.getMiddleLimit()) {
      mArm.setEncoderValue(0.0);
    } else if(mArm.getRightLimit() && mStartingPosition < mArm.getEncoderValue()) {
      mArm.setEncoderValue(59.95);
      mEnd = true;
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