package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BallIntake extends SubsystemBase {

  private final TalonFX mPivotMotor = new TalonFX(Constants.BALL);

  public BallIntake() {}

  @Override
  public void periodic() {
    
  }
}