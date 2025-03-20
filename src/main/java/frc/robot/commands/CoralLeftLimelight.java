package frc.robot.commands;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CoralLimelight;

public class CoralLeftLimelight extends Command {

  private final CoralLimelight mCoralLimelight;
  private final CommandSwerveDrivetrain mSwerveDrivetrain;
  private final SwerveRequest.RobotCentric mDrive;

  private double mMaxSpeed;

  private double TX;
  private double TA;
  private double LEFTRIGHTSPEED;
  private double mOffset;
  private double TXWithOffset;

  public CoralLeftLimelight(CoralLimelight coralLimelight, CommandSwerveDrivetrain swerveDrivetrain, SwerveRequest.RobotCentric drive, double maxSpeed, double offset) {
    mCoralLimelight = coralLimelight;
    mSwerveDrivetrain = swerveDrivetrain;
    mMaxSpeed = maxSpeed;

    mDrive = drive;

    mOffset = offset;
    addRequirements(coralLimelight, swerveDrivetrain);
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {

    TX = mCoralLimelight.getTX();
    TA = mCoralLimelight.getTA();
    

    TXWithOffset = TX - mOffset;

    LEFTRIGHTSPEED = mCoralLimelight.GetLeftRightSpeed(mMaxSpeed, TXWithOffset);


    mSwerveDrivetrain.setControl(
      // Drivetrain will execute this command periodically

      mDrive.withVelocityX(0.0) // Drive forward with negative Y (forward)
        .withVelocityY(LEFTRIGHTSPEED) // Drive left with negative X (left)
        .withRotationalRate(0.0) // Drive counterclockwise with negative X (left)
    );
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
