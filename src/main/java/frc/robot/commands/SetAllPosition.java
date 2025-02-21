package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Joint;
import frc.robot.subsystems.Lift;

public class SetAllPosition extends Command {

  private final Lift mLift;
  private final Arm mArm;
  private final Joint mJoint;
  
  public SetAllPosition(Lift lift, Arm arm, Joint joint) {
    mLift = lift;
    mArm = arm;
    mJoint = joint;
    addRequirements(getRequirements());
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
