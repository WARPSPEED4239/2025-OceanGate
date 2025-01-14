package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Joint extends SubsystemBase {

  private final TalonFX mPivotMotor = new TalonFX(Constants.PIVOT_MOTOR);
  private final DigitalInput mThroughBoreEncoder = new DigitalInput(Constants.REV_THROUGH_BORE_ENCODER);

  public Joint() {}

  @Override
  public void periodic() {}

  public void spinPivotMotor(double speed) {
    mPivotMotor.set(speed);
  }
}