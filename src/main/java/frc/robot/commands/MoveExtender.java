package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Extender;


public class MoveExtender extends Command {
  private double mSpeed;
  private final Extender mExtenderMotor;

  public MoveExtender(Extender extender, double speed) {
    mExtenderMotor = extender;
    mSpeed = speed;
    addRequirements(mExtenderMotor); 
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if(mExtenderMotor.getPositionIn() == true && mSpeed > 0.0) {
      mExtenderMotor.stopMotor();
    } else if (mExtenderMotor.getPositionOut() == true && mSpeed < 0.0) {
      mExtenderMotor.stopMotor();
    } else {
      mExtenderMotor.setSpeed(mSpeed);
    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return false;
  }
}
