package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;


public class MoveArm extends Command {
  private double mSpeed;
  private final Arm mArm;
  private boolean mEnd;
  public double mGoalPosition;
  public double mEncoderValue;

  public MoveArm(Arm arm, double speed) {
    mArm = arm;
    mSpeed = speed;
    addRequirements(mArm); 
  }

  @Override
  public void initialize() {
    mEnd = false;
  }

  @Override
  public void execute() {
    mEncoderValue = mArm.getEncoderValue();

    if(mArm.getPositionIn()) {
      mArm.setEncoderValue(0.0);
    } else if(mArm.getPositionMiddle()) {
      mArm.setEncoderValue(50.0);
    } else if(mArm.getPositionOut()) {
      mArm.setEncoderValue(100.0);
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
  public void end(boolean interrupted) {
    mArm.setOutputWithLimitSensors(0.0);
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

