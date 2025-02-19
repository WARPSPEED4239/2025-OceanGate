package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;

public class MoveArm extends Command {
  private final Arm mArm;
  public double mGoalPosition;
  public double mMotorPosition;
  private double mSpeed;
  private boolean mEnd;

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
    mMotorPosition = mArm.getEncoderValue();
    mArm.setSpeed(mSpeed);

    // if (mArm.getLeftLimit()) {
    //   mArm.setEncoderValue(-16.0);
    // } else if(mArm.getMiddleLimit()) {
    //   mArm.setEncoderValue(0.0);
    // } else if(mArm.getRightLimit()) {
    //   mArm.setEncoderValue(61.0);
    // }

    // if(mSpeed < 0.0 && mMotorPosition >= -16.0) {
    //   mArm.setOutputWithLimitSensors(mSpeed);
    // } else if(mSpeed > 0.0 && mMotorPosition <= 61.0) {
    //   mArm.setOutputWithLimitSensors(mSpeed);
    // } else {
    //   mArm.stopMotor();
    // }


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