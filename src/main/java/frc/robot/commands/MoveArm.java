package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;


public class MoveArm extends Command {
  private double mSpeed;
  private final Arm mArm;

  public MoveArm(Arm arm, double speed) {
    mArm = arm;
    mSpeed = speed;
    addRequirements(mArm); 
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if(mArm.getPositionIn() == true && mSpeed > 0.0) {
      mArm.stopMotor();
    } else if (mArm.getPositionOut() == true && mSpeed < 0.0) {
      mArm.stopMotor();
    } else {
      mArm.setSpeed(mSpeed);
    }

    mEncoderValue = mArm.getEncoderValue();

    if (mArm.getBottomLimit()) {
      mArm.setEncoderValue(0.0);
    }

    if(mGoalPosition > mEncoderValue) {
      mArm.setOutputWithLimitSensors(mSpeed);
    } else if(mGoalPosition < mEncoderValue) {
      mArm.setOutputWithLimitSensors(-mSpeed);
    } else {
      mArm.setOutputWithLimitSensors(0.0);
      mArm.stopMotor();
      mEnd = true;
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}

