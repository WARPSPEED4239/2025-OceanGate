package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Joint;
import frc.robot.subsystems.Lift;

public class SetAllPosition extends Command {

  private final Lift mLift;
  private final Arm mArm;
  private final Joint mJoint;

  private double liftGoalPosition;
  private double armGoalPosition;
  private double jointGoalPosition;

  private double liftEncoderPosition;
  private double armEncoderPosition;
  private double jointEncoderPosition;

  private boolean mEnd;
  
  public SetAllPosition(Lift lift, Arm arm, Joint joint, double liftGoal, double armGoal, double jointGoal) {
    mLift = lift;
    mArm = arm;
    mJoint = joint;

    liftGoalPosition = liftGoal;
    armGoalPosition= armGoal;
    jointGoalPosition = jointGoal;

    addRequirements(getRequirements());
  }

  @Override
  public void initialize() {
    mEnd = false;
  }

  @Override
  public void execute() {
    liftEncoderPosition = mLift.getEncoderValue();
    armEncoderPosition = mArm.getEncoderValue();
    jointEncoderPosition = mJoint.getEncoderValue();

    if (liftEncoderPosition < 48.0) {
      if ((armEncoderPosition < armGoalPosition - 0.5 || 
           armEncoderPosition > armGoalPosition + 0.5) ||
          (jointEncoderPosition < jointGoalPosition - 0.5 ||
           jointEncoderPosition > jointGoalPosition + 0.5)) {
            mLift.setPosition(45);
            mArm.setPosition(armGoalPosition);
            mJoint.setPosition(jointGoalPosition);
          }
    } else {
      mLift.setPosition(liftGoalPosition);
      mArm.setPosition(armGoalPosition);
      mJoint.setPosition(jointGoalPosition);
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    if (mEnd) {
      return true;
    }
    return false;
  }
}
