package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Joint;
import frc.robot.subsystems.Lift;

public class SetJointPosition extends Command {

  private final Joint mJoint;
  public boolean mEnd;
  public double mGoalPosition;

  public SetJointPosition(Joint joint, double goalPosition) {
    mJoint = joint;
    mGoalPosition = goalPosition;
    addRequirements(mJoint);
  }

  @Override
  public void initialize() {
    mEnd = false;
  }

  @Override
  public void execute() {

    // if (mGoalPosition < mJoint.convertAbsoluteToRotar(mJoint.getEncoderValue()) + 0.5 && mGoalPosition > mJoint.convertAbsoluteToRotar(mJoint.getEncoderValue()) - 0.5) {
    //   mEnd = true;
    // }

    mJoint.setPosition(mGoalPosition);
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
