package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Lift;

public class MoveLift extends Command {

  private final Lift mLift;
  private double mMotorPosition;
  private double mSpeed;
  private boolean mEnd;

  public MoveLift(Lift lift, double speed) {
    mLift = lift;
    mSpeed = speed;
    addRequirements(mLift);
  }

  @Override
  public void initialize() {
    mEnd = false;
  }

  @Override
  public void execute() {
    mMotorPosition = mLift.getEncoderValue();

    if(mSpeed > 0.0 && !mLift.getTopLimit()) {
      mLift.setSpeed(mSpeed);
    } else if(mSpeed < 0.0 && mMotorPosition > 0.0 && !mLift.getBottomLimit()) {
      mLift.setSpeed(mSpeed);
    } else {
      mLift.stopMotor();
      mEnd = true;
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