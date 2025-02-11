package frc.robot.subsystems;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Extender extends SubsystemBase {
  private final TalonFX mExtenderMotor = new TalonFX(Constants.EXTENTION_MOTOR); 
  private final DigitalInput mLimitIn = new DigitalInput(Constants.LIMIT_SWITCH_IN);
  private final DigitalInput mLimitOut = new DigitalInput(Constants.LIMIT_SWITCH_OUT);
  private final DutyCycleOut mDutyCycle = new DutyCycleOut(0.0);
  
  public Extender() {
    mExtenderMotor.setInverted(false);
    mExtenderMotor.setNeutralMode(NeutralModeValue.Brake);
  }

  public void setSpeed(double speed) {
    mExtenderMotor.set(speed);
  }

  public void setOutputWithLimitSensors(double speed) {
    mExtenderMotor.setControl(mDutyCycle.withOutput(speed).withLimitForwardMotion(mLimitIn.get()).withLimitReverseMotion(mLimitOut.get()));
  }

  public boolean getPositionIn() {
    return mLimitIn.get();
  }

  public boolean getPositionOut() {
    return mLimitOut.get();
  }

  public double getMotorPosition() {
    return mExtenderMotor.get();
  }

  public void setPositionIn(double position) {
    mExtenderMotor.setPosition(position);
  }
 
  public void setPositionOut(double position) {
    mExtenderMotor.setPosition(position);
  }

  public void stopMotor() {
    mExtenderMotor.stopMotor();
  }

  @Override
  public void periodic() {}
}
