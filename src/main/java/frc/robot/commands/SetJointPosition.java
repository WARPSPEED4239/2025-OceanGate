package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Joint;
import frc.robot.subsystems.Lift;

public class SetJointPosition extends Command {

  private final Joint mJoint;
  private final Lift mLift;
  public boolean mEnd;
  public double mGoalPosition;

  public SetJointPosition(Joint joint, Lift lift, double goalPosition) {
    mJoint = joint;
    mLift = lift;
    mGoalPosition = goalPosition;
    addRequirements(mJoint, mLift);
  }

  @Override
  public void initialize() {
    mEnd = false;
  }

  @Override
  public void execute() {

    if (mGoalPosition < mJoint.convertAbsoluteToRotar(mJoint.getEncoderValue()) + 0.5 && mGoalPosition > mJoint.convertAbsoluteToRotar(mJoint.getEncoderValue()) - 0.5) {
      mEnd = true;
    }

    if (mLift.getEncoderValue() > 50.0) {
      mJoint.setPosition(mGoalPosition);
    } else {
      mJoint.setPosition(mJoint.convertAbsoluteToRotar(0.0));
      System.out.println("StockPosition");
    }
    
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    if(mEnd) {
      return true;
    }
    return false;
  }
}
