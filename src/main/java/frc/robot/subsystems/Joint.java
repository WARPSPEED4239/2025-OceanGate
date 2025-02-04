package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Joint extends SubsystemBase {

  private final TalonFX mPivotMotor = new TalonFX(Constants.PIVOT_MOTOR);
  private final DigitalInput mThroughBoreEncoder = new DigitalInput(Constants.REV_THROUGH_BORE_ENCODER);
  private final DigitalInput mMin = new DigitalInput(Constants.JOINT_MIN);
  private final DigitalInput mMax = new DigitalInput(Constants.JOINT_MAX);

  public Joint() {}

  @Override
  public void periodic() {}

  public void spinPivotMotor(double speed) {
    mPivotMotor.set(speed);
  }

  public boolean getEncoderPosition() {
    return mThroughBoreEncoder.get();
  }
  public boolean getMin() {
    return mMin.get();
  }
  public boolean getMax() {
    return mMax.get();
  }
}