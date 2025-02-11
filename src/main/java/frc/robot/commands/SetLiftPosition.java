package frc.robot.commands;

import javax.print.attribute.standard.MediaName;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class SetLiftPosition extends Command {

  private final Lift mLift;
  private double mSpeed;
  private boolean mEnd;
  private double mEncoderValue;
  private double mGoalPosition;
  
  
  public SetLiftPosition(Lift lift, double speed, double goalPosition) {
    mLift = lift;
    mSpeed = speed;
    mGoalPosition = goalPosition;
    addRequirements(mLift);
  }

  @Override
  public void initialize() {
    mEnd = false;
  }

  @Override
  public void execute() {
    mEncoderValue = mLift.getEncoderValue();

    if (mLift.getBottomLimit()) {
      mLift.setEncoderValue(0.0);
    }

    if(mGoalPosition > mEncoderValue) {
      mLift.setOutputWithLimitSensors(mSpeed);
    } else if(mGoalPosition < mEncoderValue) {
      mLift.setOutputWithLimitSensors(-mSpeed);
    } else {
      mLift.setOutputWithLimitSensors(0.0);
      mLift.stopMotor();
      mEnd = true;
    }
  }

  @Override
  public void end(boolean interrupted) {
    mLift.setOutputWithLimitSensors(0.0);
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