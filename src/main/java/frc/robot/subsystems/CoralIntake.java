package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CoralIntake extends SubsystemBase {

  private final TalonFX mCoralIntake = new TalonFX(Constants.CORAL_INTAKE_MOTOR);
  
  public CoralIntake() {
    mCoralIntake.setInverted(false);
    mCoralIntake.setNeutralMode(NeutralModeValue.Brake);
  }

  public void setSpeed(double speed) {
    mCoralIntake.set(speed);
  }

  public void stopMotor() {
    mCoralIntake.stopMotor();
  }

  @Override
  public void periodic() {}
}