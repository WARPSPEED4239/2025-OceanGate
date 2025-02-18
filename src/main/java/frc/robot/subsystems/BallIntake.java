package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class BallIntake extends SubsystemBase {

  private final SparkMax mBallIntakeMotor = new SparkMax(Constants.BALL_INTAKE_MOTOR, MotorType.kBrushed);
  
  public BallIntake() {
    mBallIntakeMotor.setInverted(false);
  }

  public void setSpeed(double speed) {
    mBallIntakeMotor.set(speed);
  }

  public void stopMotor() {
    mBallIntakeMotor.stopMotor();
  }

  @Override
  public void periodic() {}

}