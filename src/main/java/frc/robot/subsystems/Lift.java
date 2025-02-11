package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class Lift extends SubsystemBase {

  private final TalonFX mLiftMotor = new TalonFX(Constants.LIFT_MOTOR);
  private final DigitalInput mBottomLimit = new DigitalInput(Constants.LIFT_LIMIT);
  private final DutyCycleOut mDutyCyle = new DutyCycleOut(0.0);

  
  public Lift() {
    mLiftMotor.setInverted(true);
    mLiftMotor.setNeutralMode(NeutralModeValue.Brake);
  }

  public void setSpeed(double speed) {
    mLiftMotor.set(speed);
  }

  public void setOutputWithLimitSensors(double speed) {
    mLiftMotor.setControl(mDutyCyle.withOutput(speed).withLimitForwardMotion(getTopLimit()).withLimitReverseMotion(mBottomLimit.get()));
  }

  public double getEncoderValue() {
    return mLiftMotor.get();
  }

  public void setEncoderValue(double encoderValue) {
    mLiftMotor.setPosition(encoderValue);
  }

  public boolean getBottomLimit() {
    return mBottomLimit.get();
  }

  public boolean getTopLimit() {
    if(getEncoderValue() > 100.0) {
      return true;
    }
    return false;
  }

  public void stopMotor() {
    mLiftMotor.stopMotor();
  }

  @Override
  public void periodic() {}
}