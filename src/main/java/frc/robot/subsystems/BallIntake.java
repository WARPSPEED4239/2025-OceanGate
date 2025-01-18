package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class BallIntake extends SubsystemBase {

  private final TalonFX mBallIntake = new TalonFX(Constants.BALL_INTAKE_MOTOR);
  
  public BallIntake() {}

  @Override
  public void periodic() {
  }
}