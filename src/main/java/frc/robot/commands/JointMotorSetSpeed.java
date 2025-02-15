package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Joint;

public class JointMotorSetSpeed extends Command {

  private final Joint mJoint;
  private double mSpeed;

  public JointMotorSetSpeed(Joint joint, double speed) {
    mJoint = joint;
    mSpeed = speed;
    addRequirements(mJoint);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    mJoint.setSpeed(mSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    mJoint.stopMotor();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}