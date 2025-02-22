package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

  private final double liftHomePosition = 50.0;
  private final double armHomePosition = 0.0;
  private final double jointHomePosition = -2.0;

  private boolean mEnd;
  private boolean mIsInHome;
  
  public SetAllPosition(Lift lift, Arm arm, Joint joint, double liftGoal, double armGoal, double jointGoal) {
    mLift = lift;
    mArm = arm;
    mJoint = joint;

    liftGoalPosition = liftGoal;
    armGoalPosition= armGoal;
    jointGoalPosition = jointGoal;

    addRequirements(mLift, mArm, mJoint);
  }

  @Override
  public void initialize() {
    mEnd = false;
  }

  @Override
  public void execute() {
    liftEncoderPosition = mLift.getEncoderValue();
    armEncoderPosition = mArm.getEncoderValue();
    jointEncoderPosition = ((130.216 * mJoint.getRawEncoderValue()) - 65.108);

    // if (liftEncoderPosition < 47.5) {
    //   if (armEncoderPosition < armHomePosition - 0.5 || 
    //       armEncoderPosition > armHomePosition + 0.5 ||
    //       jointEncoderPosition < jointHomePosition - 0.5 ||
    //       jointEncoderPosition > jointHomePosition + 0.5) {
    //         mLift.setPosition(liftHomePosition);
    //         mArm.setPosition(armHomePosition);
    //         mJoint.setPosition(jointHomePosition);
    //       }
    // } else {
    //   mLift.setPosition(liftGoalPosition);
    //   mArm.setPosition(armGoalPosition);
    //   mJoint.setPosition(jointGoalPosition);
    // }

    if (liftEncoderPosition < 47.5 &&
        (Math.abs(armEncoderPosition - armHomePosition) > 0.5) &&
        (Math.abs(jointEncoderPosition - jointHomePosition) > 0.5)) {
      SmartDashboard.putBoolean("Condition1", (liftEncoderPosition < 47.5));
      SmartDashboard.putBoolean("Condition2", (armEncoderPosition < armHomePosition - 0.5));
      SmartDashboard.putBoolean("Condition3", (armEncoderPosition > armHomePosition + 0.5));
      SmartDashboard.putBoolean("Condition4", (jointEncoderPosition < jointHomePosition - 0.5));
      SmartDashboard.putBoolean("Condition5", (jointEncoderPosition > jointHomePosition + 0.5));

      mLift.setPosition(liftHomePosition); // 50.0
      mArm.setPosition(armHomePosition); // 0.0
      mJoint.setPosition(jointHomePosition); // -2.0
      mIsInHome = true;
    } else {
      mLift.setPosition(liftGoalPosition);
      mArm.setPosition(armGoalPosition);
      mJoint.setPosition(jointGoalPosition);
      mIsInHome = false;
    }

    SmartDashboard.putBoolean("IS IN HOME", mIsInHome);
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
