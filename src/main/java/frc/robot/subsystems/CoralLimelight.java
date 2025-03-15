package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;

public class CoralLimelight extends SubsystemBase {
  
  private final LimelightHelpers mCoralLimelight = new LimelightHelpers();
  private final double KpAngle = 0.35; //Tune; too high will cause oscillations
  private final double KpDistance = 0.10; //Tune

  public CoralLimelight() {}

  public double getTargetingAngularVelocity(double maxAngularSpeed) {
    double targetingAngularVelocity = mCoralLimelight.getTX("limelight-coral") * KpAngle * -1.0;
    return targetingAngularVelocity * maxAngularSpeed;
  }

  public double getTargetingDistanceSpeed(double maxDriveSpeed) {
    double targetingDistanceSpeed = mCoralLimelight.getTA("limelight-coral") * -1.0;
    return targetingDistanceSpeed * maxDriveSpeed;
  }

  public double getTX() {
    return mCoralLimelight.getTX("limelight-coral");
  }

  public double getTA() {
    return mCoralLimelight.getTA("limelight-coral");
  }

  @Override
  public void periodic() {
    System.out.println(getTX());
  }
  
}
