package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Joint extends SubsystemBase {

  private final TalonFX mJointMotor = new TalonFX(Constants.JOINT_MOTOR);
  private final DutyCycleOut mDutyCycle = new DutyCycleOut(0.0);
  private final DutyCycleEncoder mAbsoluteEncoder = new DutyCycleEncoder(4);

  public Joint() {
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
    motionMagicConfigs.MotionMagicJerk = 0; //1600

    mJointMotor.getConfigurator().apply(talonFXConfigs);
    mJointMotor.setInverted(true);
    mJointMotor.setNeutralMode(NeutralModeValue.Brake);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("JointEncoder", ((130.216 * getEncoderValue()) - 65.108));
  }

  public void setSpeed(double speed) {
    mJointMotor.set(speed);
  }

  public void setOutputWithLimitSensors(double speed) {
    mJointMotor.setControl(mDutyCycle.withOutput(speed)); // add limit motions with encoders
  }

  public void setPosition(double pos) {
    final MotionMagicVoltage request = new MotionMagicVoltage(0);
    mJointMotor.setControl(request.withPosition(pos));
  }

  public double getEncoderValue() {
    return mAbsoluteEncoder.get();
  }

  public void resetEncoderPosition() {
    mJointMotor.setPosition(convertAbsoluteToRotar(getEncoderValue()));
  }

  public double convertAbsoluteToRotar(double rotar) {
    return ((130.216 * rotar) - 65.108);
  }

  // add safety feature
  public boolean getEncoderConnection() {
    return mAbsoluteEncoder.isConnected();
  }

  public void stopMotor() {
    mJointMotor.stopMotor();
  }
}