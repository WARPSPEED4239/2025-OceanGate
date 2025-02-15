package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
  private final TalonFX mArmMotor = new TalonFX(Constants.EXTENTION_MOTOR); 
  private final DigitalInput mLimitLeft = new DigitalInput(Constants.LIMIT_SWITCH_LEFT);
  private final DigitalInput mLimitMiddle = new DigitalInput(Constants.LIMIT_SWITCH_MIDDLE);
  private final DigitalInput mLimitRight = new DigitalInput(Constants.LIMIT_SWITCH_RIGHT);
  private final DutyCycleOut mDutyCycle = new DutyCycleOut(0.0);
  
  public Arm() {
    var talonFXConfigs = new TalonFXConfiguration();
    var slot0Configs = talonFXConfigs.Slot0;
    var motionMagicConfigs = talonFXConfigs.MotionMagic;
    slot0Configs.kG = 0.0;
    slot0Configs.kS = 0.25;
    slot0Configs.kV = 0.12;
    slot0Configs.kA = 0.01;
    slot0Configs.kP = 2.0;
    slot0Configs.kI = 0.0;
    slot0Configs.kD = 0.05;
    slot0Configs.StaticFeedforwardSign = StaticFeedforwardSignValue.UseClosedLoopSign;
    motionMagicConfigs.MotionMagicCruiseVelocity = 80;
    motionMagicConfigs.MotionMagicAcceleration = 160;
    motionMagicConfigs.MotionMagicJerk = 1600; //0

    mArmMotor.getConfigurator().apply(talonFXConfigs);
    mArmMotor.setInverted(true);
    mArmMotor.setNeutralMode(NeutralModeValue.Brake);
  }

  public void setSpeed(double speed) {
    mArmMotor.set(speed);
  }

  public void setOutputWithLimitSensors(double speed) {
    mArmMotor.setControl(mDutyCycle.withOutput(speed).withLimitForwardMotion(getLeftLimit()).withLimitReverseMotion(getRightLimit()));
  }
  
  public void setPosition(double pos) {
    final MotionMagicVoltage request = new MotionMagicVoltage(0);
    mArmMotor.setControl(request.withPosition(pos));
  }

  public double getEncoderValue() {
    return mArmMotor.getPosition().getValueAsDouble();
  }

  public void setEncoderValue(double encoderValue) {
    mArmMotor.setPosition(encoderValue);
  }

  public boolean getLeftLimit() {
    return !mLimitLeft.get();
  }

  public boolean getMiddleLimit() {
    return !mLimitMiddle.get();
  }

  public boolean getRightLimit() {
    return !mLimitRight.get();
  }

  public void stopMotor() {
    mArmMotor.stopMotor();
  }

  @Override
  public void periodic() {
    System.out.println(getEncoderValue());
    SmartDashboard.putBoolean("Limit Left", getLeftLimit());
    SmartDashboard.putBoolean("Limit Middle", getMiddleLimit());
    SmartDashboard.putBoolean("Limit Right", getRightLimit());
  }
}