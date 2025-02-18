package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CoralIntake extends SubsystemBase {

  private final SparkMax mCoralIntakeMotor = new SparkMax(Constants.CORAL_INTAKE_MOTOR, MotorType.kBrushed);
  
  public CoralIntake() {
    mCoralIntakeMotor.setInverted(false);
  }

  public void setSpeed(double speed) {
    mCoralIntakeMotor.set(speed);
  }

  public void stopMotor() {
    mCoralIntakeMotor.stopMotor();
  }
  
  @Override
  public void periodic() {}
}