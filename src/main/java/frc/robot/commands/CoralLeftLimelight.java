package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CoralLimelight;

public class CoralLeftLimelight extends Command {

  private final CoralLimelight mCoralLimelight;
  private final CommandSwerveDrivetrain mSwerveDrivetrain;

  public CoralLeftLimelight(CoralLimelight coralLimelight, CommandSwerveDrivetrain swerveDrivetrain) {
    mCoralLimelight = coralLimelight;
    mSwerveDrivetrain = swerveDrivetrain;
    addRequirements(coralLimelight, swerveDrivetrain);
  }

  @Override
  public void initialize() {}

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
