package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class Lift extends SubsystemBase {

  private final TalonFX mLift = new TalonFX(Constants.LIFT_MOTOR);
  
  public Lift() {
    mLift.setInverted(false);
    mLift.setNeutralMode(NeutralModeValue.Brake);
  }

public void setSpeed(double speed) {
  
}

  @Override
  public void periodic() {}
}