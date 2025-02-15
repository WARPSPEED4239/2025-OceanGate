package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Joint;

public class JointMotorSetPosition extends Command {

  private final Joint mJoint;
  public double mGoalPosition;

  public JointMotorSetPosition(Joint joint, double goalPosition) {
    mJoint = joint;
    mGoalPosition = goalPosition;
    addRequirements(mJoint);
  }

  @Override
  public void initialize() {
    mJoint.setPosition(mGoalPosition);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
