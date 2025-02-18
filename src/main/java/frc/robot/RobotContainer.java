package frc.robot;

import static edu.wpi.first.units.Units.*;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.JointMotorSetPosition;
import frc.robot.commands.JointMotorSetSpeed;
import frc.robot.commands.ResetEncoderPosition;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Joint;
import frc.robot.commands.MoveArm;
import frc.robot.commands.SetArmPosition;
import frc.robot.subsystems.Arm;
import frc.robot.commands.MoveLift;
import frc.robot.commands.SetLiftPosition;
import frc.robot.subsystems.Lift;                                              

public class RobotContainer {
    private double MaxSpeed = 0.5;//TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);
    private final CommandXboxController xboxController = new CommandXboxController(0);
    private final CommandJoystick joystick = new CommandJoystick(1);
    private final CommandGenericHID buttonBox = new CommandGenericHID(2);
    private final Arm mArm = new Arm();
  
    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public final Joint mJoint = new Joint();
    private final Lift mLift = new Lift();

    public RobotContainer() {

        UsbCamera mainCamera = CameraServer.startAutomaticCapture();
        mainCamera.setResolution(320, 240);
        mainCamera.setFPS(10);

        mArm.setDefaultCommand(new MoveArm(mArm, 0.0));
        mLift.setDefaultCommand(new MoveLift(mLift, 0.0));

        configureBindings();
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-xboxController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-xboxController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-xboxController.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        xboxController.a().whileTrue(drivetrain.applyRequest(() -> brake));
        xboxController.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-xboxController.getLeftY(), -xboxController.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.

        mJoint.setDefaultCommand(new ResetEncoderPosition(mJoint));

        xboxController.back().and(xboxController.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        xboxController.back().and(xboxController.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        xboxController.start().and(xboxController.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        xboxController.start().and(xboxController.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // reset the field-centric heading on left bumper press
        xboxController.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));
      
        drivetrain.registerTelemetry(logger::telemeterize);
    
        joystick.button(5).whileTrue(new MoveLift(mLift, -0.1));
        joystick.button(6).whileTrue(new MoveLift(mLift, 0.1));
        joystick.button(3).whileTrue(new MoveArm(mArm, -0.1));
        joystick.button(4).whileTrue(new MoveArm(mArm, 0.1));
        buttonBox.button(1).onTrue(new SetArmPosition(mArm, 0.1, -16.0));
        buttonBox.button(2).onTrue(new SetArmPosition(mArm, 0.1, 0.0));
        buttonBox.button(3).onTrue(new SetArmPosition(mArm, 0.1, 61.0));
        buttonBox.button(4).onTrue(new SetLiftPosition(mLift, 0.1, -0.1));
        buttonBox.button(5).onTrue(new SetLiftPosition(mLift, 0.1, 30.0));
        buttonBox.button(6).onTrue(new SetLiftPosition(mLift, 0.1, 60.0));
        buttonBox.button(7).onTrue(new SetLiftPosition(mLift, 0.1, 90.0));
        buttonBox.button(8).whileTrue(new JointMotorSetSpeed(mJoint, -0.1));
        buttonBox.button(9).whileTrue(new JointMotorSetSpeed(mJoint, 0.1));
        buttonBox.button(10).onTrue(new JointMotorSetPosition(mJoint, 0.379));
        buttonBox.button(11).onTrue(new JointMotorSetPosition(mJoint, 0.479));
        buttonBox.button(12).onTrue(new JointMotorSetPosition(mJoint, 0.579));
    }

    public Command getAutonomousCommand() {
        return null;
    }
}