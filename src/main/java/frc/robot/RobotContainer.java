// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.MoveClimber;
import frc.robot.commands.MoveExtender;
import frc.robot.commands.SpinPivotMotor;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Extender;
import frc.robot.subsystems.Joint;
import frc.robot.commands.SpinPivotMotor;

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

    private final CommandXboxController mController = new CommandXboxController(0);
    private final CommandJoystick mJoystick = new CommandJoystick(0);

    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public final Joint mJoint = new Joint();
    private final Climber mClimber = new Climber();
    private final Extender mExtender = new Extender();

    public RobotContainer() {
        mClimber.setDefaultCommand(new MoveClimber(mClimber, 0.0));
        mExtender.setDefaultCommand(new MoveExtender(mExtender, 0.0));

        UsbCamera mainCamera = CameraServer.startAutomaticCapture();
        mainCamera.setResolution(320, 240);
        mainCamera.setFPS(10);

        configureBindings();
    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-mController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-mController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-mController.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        mController.a().whileTrue(drivetrain.applyRequest(() -> brake));
        mController.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-mController.getLeftY(), -mController.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        mController.back().and(mController.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        mController.back().and(mController.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        mController.start().and(mController.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        mController.start().and(mController.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // reset the field-centric heading on left bumper press
        mController.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        drivetrain.registerTelemetry(logger::telemeterize);
        mJoystick.button(5).whileTrue(new SpinPivotMotor(mJoint, 0.10));
        mJoystick.button(3).whileTrue(new SpinPivotMotor(mJoint, -0.10));
        mJoystick.button(6).whileTrue(new MoveClimber(mClimber, 0.10));
        mJoystick.button(4).whileTrue(new MoveClimber(mClimber, -0.10));
        mJoystick.button(1).whileTrue(new MoveExtender(mExtender, 0.10));
        mJoystick.button(2).whileTrue(new MoveExtender(mExtender, -0.10));
    }

    public Command getAutonomousCommand() {
        return null;
    }
}