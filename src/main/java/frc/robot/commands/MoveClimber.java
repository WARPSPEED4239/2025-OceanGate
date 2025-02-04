package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Climber;

public class MoveClimber extends Command {

  private final Climber mClimber;
  private double mSpeed;

  public MoveClimber(Climber climber, double speed) {
    mClimber = climber;
    mSpeed = speed;
    addRequirements(mClimber);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if(mClimber.getPositionIn() == true && mSpeed > 0.0) {
      mClimber.stopMotor();
    } else if (mClimber.getPositionOut() == true && mSpeed < 0.0) {
      mClimber.stopMotor();
    } else {
      mClimber.setSpeed(mSpeed);
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
