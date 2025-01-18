package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class BallIntake extends SubsystemBase {

  private final TalonFX mBallIntake = new TalonFX(Constants.BALL_INTAKE_MOTOR);

  public BallIntake() {
    mBallIntake.setInverted(false);
    mBallIntake.setNeutralMode(NeutralModeValue.Brake);
  }

  public void setSpeed(double speed) {
    mBallIntake.set(speed);
  }

  public void stopMotor() {
    mBallIntake.stopMotor();
  }

  public void setWheelSpeed(double topSpeed, double bottomSpeed) {
    mBallIntake.set(topSpeed);
    mBallIntake.set(bottomSpeed);
  }

  @Override
  public void periodic() {}

}