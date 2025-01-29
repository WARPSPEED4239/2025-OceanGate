
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralIntake;

public class CIntake extends Command {
  
  private final CoralIntake mCoralIntake;
  private double  mSpeed;
  
  public CIntake(CoralIntake coralintake, double speed) {
    mCoralIntake = coralintake;
    mSpeed = speed;
    addRequirements(mCoralIntake);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    mCoralIntake.setSpeed(mSpeed);
  }

  @Override
  public void end(boolean interrupted) {
    mCoralIntake.stopMotor();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
