package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Lift extends SubsystemBase {

  private final TalonFX mLiftMotor = new TalonFX(Constants.LIFT_MOTOR);
  private final DigitalInput mBottomLimit = new DigitalInput(Constants.LIFT_LIMIT);
  private final DutyCycleOut mDutyCyle = new DutyCycleOut(0.0);
  
  public Lift() {
    var talonFXConfigs = new TalonFXConfiguration();
    var slot0Configs = talonFXConfigs.Slot0;
    var motionMagicConfigs = talonFXConfigs.MotionMagic;
    slot0Configs.kG = 0.02;
    slot0Configs.kS = 0.25;
    slot0Configs.kV = 0.12;
    slot0Configs.kA = 0.01;
    slot0Configs.kP = 2.0; // 2.0
    slot0Configs.kI = 0.0;
    slot0Configs.kD = 0.1;
    motionMagicConfigs.MotionMagicCruiseVelocity = 160; //80
    motionMagicConfigs.MotionMagicAcceleration = 250; // 160
    motionMagicConfigs.MotionMagicJerk = 1600; //0

    mLiftMotor.getConfigurator().apply(talonFXConfigs);
    mLiftMotor.setInverted(true);
    mLiftMotor.setNeutralMode(NeutralModeValue.Brake);
  }

  public void setSpeed(double speed) {
    mLiftMotor.set(speed);
  }

  public void setOutputWithLimitSensors(double speed) {
    mLiftMotor.setControl(mDutyCyle.withOutput(speed).withLimitForwardMotion(getTopLimit()).withLimitReverseMotion(mBottomLimit.get()));
  }

  public void setPosition(double pos) {
    final MotionMagicVoltage request = new MotionMagicVoltage(0);
    mLiftMotor.setControl(request.withPosition(pos));
  }

  public double getEncoderValue() {
    return mLiftMotor.getPosition().getValueAsDouble();
  }

  public void setEncoderValue(double encoderValue) {
    mLiftMotor.setPosition(encoderValue);
  }

  public boolean getBottomLimit() {
    return mBottomLimit.get();
  }

  public boolean getTopLimit() {
    if(getEncoderValue() > Constants.LIFT_ENCODER_TOP_LIMIT) {
      return true;
    }
    return false;
  }

  public void stopMotor() {
    mLiftMotor.stopMotor();
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("LiftLimitDown", getBottomLimit());
  }
}