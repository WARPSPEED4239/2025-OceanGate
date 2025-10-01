package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class BallIntake extends SubsystemBase {

  private final SparkMax mBallIntakeMotor = new SparkMax(Constants.BALL_INTAKE_MOTOR, MotorType.kBrushed);
  private final SparkMaxConfig mBallIntakeMotorConfig = new SparkMaxConfig();
  
  public BallIntake() {
    mBallIntakeMotorConfig.inverted(false);
    try{
      mBallIntakeMotor.configure(mBallIntakeMotorConfig,ResetMode.kNoResetSafeParameters,PersistMode.kPersistParameters);
      System.out.println("Successfully configured ball intake motor");
    } catch (Exception e1){
        e1.printStackTrace();
        DriverStation.reportWarning("Failed to configure ball intake motor",true);
    }
  }

  public void setSpeed(double speed) {
    mBallIntakeMotor.set(speed);
  }

  public void stopMotor() {
    mBallIntakeMotor.stopMotor();
  }

  @Override
  public void periodic() {}

}