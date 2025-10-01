package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CoralIntake extends SubsystemBase {

  private final SparkMax mCoralIntakeMotor = new SparkMax(Constants.CORAL_INTAKE_MOTOR, MotorType.kBrushed);
  private final SparkMaxConfig mCoralMotorConfig = new SparkMaxConfig();
  
  public CoralIntake() {
    mCoralMotorConfig.inverted(false);
    try{
      mCoralIntakeMotor.configure(mCoralMotorConfig,ResetMode.kNoResetSafeParameters,PersistMode.kPersistParameters);
      System.out.println("Successfully configured Coral Intake Motor");
    } catch (Exception e1){
      e1.printStackTrace();
      DriverStation.reportWarning("Failed to configure coral intake motor", true);
    }
  }

  public void setSpeed(double speed) {
    mCoralIntakeMotor.set(speed);
  }

  public void stopMotor() {
    mCoralIntakeMotor.stopMotor();
  }
  
  @Override
  public void periodic() {}
}