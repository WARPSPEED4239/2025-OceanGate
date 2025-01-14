package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Joint;

public class SpinPivotMotor extends Command {

  private final Joint mJoint;
  private double mSpeed;

  public SpinPivotMotor(Joint joint, double speed) {
    mJoint = joint;
    mSpeed = speed;
    addRequirements(mJoint);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    mJoint.spinPivotMotor(mSpeed);
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}