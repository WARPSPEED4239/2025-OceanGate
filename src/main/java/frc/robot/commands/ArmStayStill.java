package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;


public class ArmStayStill extends Command {
  private final Arm mArm;
  public double mGoalPosition;
  public double mEncoderValue;
  private double mSpeed;
  private boolean mEnd;

  public ArmStayStill(Arm arm, double speed) {
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

    if(mArm.getPositionLeft()) {
      mArm.setEncoderValue(-100.0);
    } else if(mArm.getPositionMiddle()) {
      mArm.setEncoderValue(0.0);
    } else if(mArm.getPositionRight()) {
      mArm.setEncoderValue(100.0);
    }

    if(mGoalPosition - 2 > mEncoderValue) {
      mArm.setOutputWithLimitSensors(mSpeed);
    } else if(mGoalPosition + 2 < mEncoderValue) {
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

