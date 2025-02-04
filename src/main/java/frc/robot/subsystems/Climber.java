package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.commands.MoveClimber;

public class Climber extends SubsystemBase {

  private final TalonFX mClimberMotor = new TalonFX(Constants.CLIMBER_MOTOR);
  private final DigitalInput mLimitIn = new DigitalInput(Constants.LIMIT_SWITCH_IN);
  private final DigitalInput mLimitOut = new DigitalInput(Constants.LIMIT_SWITCH_OUT);

  private final TalonFXConfiguration mConfig = new TalonFXConfiguration();
  private final DutyCycleOut mDutyCycle = new DutyCycleOut(0.0);

  public Climber() {
    mClimberMotor.setInverted(false);
    mClimberMotor.setNeutralMode(NeutralModeValue.Brake);
  }

  public void setSpeed(double speed) {
    mClimberMotor.set(speed);
  }

  public void stopMotor() {
    mClimberMotor.stopMotor();
  }

  public void setOutputWithLimitSensors(double speed) {
    mClimberMotor.setControl(mDutyCycle.withOutput(speed).withLimitForwardMotion(mLimitIn.get()).withLimitReverseMotion(mLimitOut.get()));
  }

  public boolean getPositionIn() {
    return mLimitIn.get();
  }

  public boolean getPositionOut() {
    return mLimitOut.get();
  }

  public void setPositionIn(double position) {
   mClimberMotor.setPosition(position);
  }

  public void setPositionOut(double position) {
    mClimberMotor.setPosition(position);
  }

  @Override
    public void periodic() {}
}