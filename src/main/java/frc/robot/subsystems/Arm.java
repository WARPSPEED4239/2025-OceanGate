package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
  private final TalonFX mArm = new TalonFX(Constants.EXTENTION_MOTOR); 
  private final DigitalInput mLimitIn = new DigitalInput(Constants.LIMIT_SWITCH_IN);
  private final DigitalInput mLimitMiddle = new DigitalInput(Constants.LIMIT_SWITCH_MIDDLE);
  private final DigitalInput mLimitOut = new DigitalInput(Constants.LIMIT_SWITCH_OUT);
  private final DutyCycleOut mDutyCycle = new DutyCycleOut(0.0);
  
  public Arm() {
    mArm.setInverted(false);
    mArm.setNeutralMode(NeutralModeValue.Brake);
  }

  public void setSpeed(double speed) {
    mArm.set(speed);
  }

  public void setOutputWithLimitSensors(double speed) {
    mArm.setControl(mDutyCycle.withOutput(speed).withLimitForwardMotion(mLimitIn.get()).withLimitReverseMotion(mLimitOut.get()));
  }

  public boolean getPositionIn() {
    return mLimitIn.get();
  }

  public boolean getPositionMiddle() {
    return mLimitMiddle.get();
  }

  public boolean getPositionOut() {
    return mLimitOut.get();
  }

  public double getMotorPosition() {
    return mArm.get();
  }

  public void setPositionIn(double position) {
    mArm.setPosition(position);
  }

  public void setPossitionMiddle(double position) {
    mArm.setPosition(position);
  }
 
  public void setPositionOut(double position) {
    mArm.setPosition(position);
  }

  public void stopMotor() {
    mArm.stopMotor();
  }

  @Override
  public void periodic() {}
}
