package frc.robot.subsystems;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.hardware.TalonFXS;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class Lift extends SubsystemBase {

  private final TalonFX mLiftMotor = new TalonFX(Constants.LIFT_MOTOR);
  private final DigitalInput mBottomLimit = new DigitalInput(Constants.LIFT_LIMIT);
  private final DutyCycleOut mDutyCyle = new DutyCycleOut(0.0);
  
  public Lift() {
    var slot0Configs = new Slot0Configs();
    slot0Configs.kP = Constants.kP;
    slot0Configs.kI = Constants.kI;
    slot0Configs.kD = Constants.kD;
    slot0Configs.kG = Constants.kG;
    slot0Configs.GravityType = GravityTypeValue.Elevator_Static;

    mLiftMotor.getConfigurator().apply(slot0Configs);
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
    return mLiftMotor.getPosition().getValueAsDouble();
  }

  public void setEncoderValue(double encoderValue) {
    mLiftMotor.setPosition(encoderValue);
  }

  public void setPosition(double pos) {
    final PositionVoltage request = new PositionVoltage(0).withSlot(0);
    mLiftMotor.setControl(request.withPosition(pos).withLimitForwardMotion(getTopLimit()).withLimitReverseMotion(mBottomLimit.get()));
  }

  public boolean getBottomLimit() {
    return mBottomLimit.get();
  }

  public boolean getTopLimit() {
    if(getEncoderValue() > 230.0) {
      return true;
    }
    return false;
  }

  public void stopMotor() {
    mLiftMotor.stopMotor();
  }

  @Override
  public void periodic() {
    System.out.println(getEncoderValue());
  }
}