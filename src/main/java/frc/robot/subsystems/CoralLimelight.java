package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;

public class CoralLimelight extends SubsystemBase {
  
  private final LimelightHelpers mCoralLimelight = new LimelightHelpers();
  private final double KpSIDE = 0.075; //Tune; too high will cause oscillations

  public CoralLimelight() {}

  public double GetLeftRightSpeed(double maxDriveSpeed, double TXWithOffset) {

    return -TXWithOffset * KpSIDE * maxDriveSpeed;
  }

  public double getTX() {
    return mCoralLimelight.getTX("limelight-coral");
  }

  public double getTA() {
    return mCoralLimelight.getTA("limelight-coral");
  }

  public double TXOffsetRight(double TA){
    return (TA -1.56) / -0.0615;
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Limelight Distance", getTA());
  }
  
}
