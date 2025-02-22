package frc.robot;

import static edu.wpi.first.units.Units.*;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.CoralWheelsSetSpeed;
import frc.robot.commands.KeepLiftPosition;
import frc.robot.commands.BallIntakeSetSpeed;
import frc.robot.commands.SetJointPosition;
import frc.robot.commands.MoveJoint;
import frc.robot.commands.ResetEncoderPosition;
import frc.robot.commands.SetAllPosition;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.BallIntake;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CoralIntake;
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

    private final CoralIntake mCoralIntake = new CoralIntake();

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

    private final BallIntake mBallIntake = new BallIntake();

    public RobotContainer() {

        
        UsbCamera mainCamera = CameraServer.startAutomaticCapture();
        mainCamera.setResolution(320, 240);
        mainCamera.setFPS(10);
        
        mBallIntake.setDefaultCommand(new BallIntakeSetSpeed(mBallIntake, 0.0));
        mCoralIntake.setDefaultCommand(new CoralWheelsSetSpeed(mCoralIntake, 0.0));
        mArm.setDefaultCommand(new MoveArm(mArm, 0.0));
        mLift.setDefaultCommand(new MoveLift(mLift, 0.0));
        mJoint.setDefaultCommand(new MoveJoint(mJoint, 0.0));

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
        //joystick.button(4).whileTrue(new MoveArm(mArm, -0.1));
        joystick.button(3).whileTrue(new MoveArm(mArm, 0.1));
        joystick.povUp().whileTrue(new CoralWheelsSetSpeed(mCoralIntake, 0.5));
        joystick.povDown().whileTrue(new CoralWheelsSetSpeed(mCoralIntake, -0.5));
        joystick.povDown().whileTrue(new BallIntakeSetSpeed(mBallIntake, 0.75));
        joystick.povUp().whileTrue(new BallIntakeSetSpeed(mBallIntake, -0.75));

        // buttonBox.button(1).onTrue(Commands.parallel(new SetLiftPosition(mLift, 0.0),
        //                                                     new SetJointPosition(mJoint, -3.0),
        //                                                     new SetArmPosition(mArm, 0.0)));

        joystick.button(2).onTrue(new SetAllPosition(mLift, mArm, mJoint, 0.0, 0.0, -3.0)); //Bottom Hold Position

        buttonBox.button(4).onTrue(new SetAllPosition(mLift, mArm, mJoint, 132.0, 8.0, -33.0)); //Coral Level 2

        // joystick.button(2).onTrue(new SequentialCommandGroup(new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 45.0),
        //                                                                                                      new SetArmPosition(mArm, 0.0),
        //                                                                                                      new SetJointPosition(mJoint, -3.0)),
        //                                                                                    new WaitCommand(2)),
                                                                                        
        //                                                             Commands.parallel(new SetLiftPosition(mLift, 0.0),
        //                                                                               new SetArmPosition(mArm, 0.0),
        //                                                                               new SetJointPosition(mJoint, -3.0))));

        buttonBox.button(1).onTrue(new SequentialCommandGroup(new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 45.0),
                                                                                                             new SetArmPosition(mArm, 0.0),
                                                                                                             new SetJointPosition(mJoint, -3.0)),
                                                                                           new WaitCommand(2)),
                                                                                        
                                                                    Commands.parallel(new SetLiftPosition(mLift, 65.0),
                                                                                      new SetArmPosition(mArm, -5.0),
                                                                                      new SetJointPosition(mJoint, -14.0))));

        buttonBox.button(2).onTrue(new SequentialCommandGroup(new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 27.5),
                                                                                                             new SetArmPosition(mArm, 0.0),
                                                                                                             new SetJointPosition(mJoint, -3.0)),
                                                                                           new WaitCommand(2)),

                                                                     new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 27.5),
                                                                                                             new SetArmPosition(mArm, 30.0),
                                                                                                             new SetJointPosition(mJoint, -3.0)),
                                                                                           new WaitCommand(2)),   

                                                                     Commands.parallel(new SetLiftPosition(mLift, 27.5),
                                                                                       new SetArmPosition(mArm, 61.0),
                                                                                       new SetJointPosition(mJoint, -6.0))));

        buttonBox.button(3).onTrue(new SequentialCommandGroup(new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 50.0),
                                                                                                             new SetArmPosition(mArm, 45.0),
                                                                                                             new SetJointPosition(mJoint, -3.0)),
                                                                                           new WaitCommand(2)),

                                                                     Commands.parallel(new SetLiftPosition(mLift, 50.0),
                                                                                       new SetArmPosition(mArm, 45.0),
                                                                                       new SetJointPosition(mJoint, 30.0))));
    
        // buttonBox.button(4).onTrue(Commands.parallel(new SetLiftPosition(mLift, 132.0), //Level 2 Coral
        //                                                     new SetJointPosition(mJoint, 8.0), //ALL AT ONCE
        //                                                     new SetArmPosition(mArm, -33.0)));

        buttonBox.button(5).onTrue(new SequentialCommandGroup(new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 50.0),
                                                                                                             new SetArmPosition(mArm, 45.0),
                                                                                                             new SetJointPosition(mJoint, -3.0)),
                                                                                           new WaitCommand(1)),
                                                                    
                                                                    Commands.parallel(new SetLiftPosition(mLift, 30.0),
                                                                                      new SetArmPosition(mArm, 45.0),
                                                                                      new SetJointPosition(mJoint, -3.0))));
                            
        buttonBox.button(7).onTrue(Commands.parallel(new SetLiftPosition(mLift, 195.0), //Level 3 Coral
                                                            new SetJointPosition(mJoint, 9.0), //ALL AT ONCE
                                                            new SetArmPosition(mArm, -16.0)));

        
        
        joystick.button(9).whileTrue(new MoveJoint(mJoint, -0.1));
        joystick.button(7).whileTrue(new MoveJoint(mJoint, 0.1));
        joystick.button(12).onTrue(new ResetEncoderPosition(mJoint));

        //buttonBox.button(1).onTrue(new SetArmPosition(mArm, 0.1, -16.0));
        //buttonBox.button(2).onTrue(new SetArmPosition(mArm, 0.1, 0.0));
        //buttonBox.button(3).onTrue(new SetArmPosition(mArm, 0.1, 61.0));
        //buttonBox.button(5).onTrue(new SetLiftPosition(mLift, 0.1, 30.0));
        //buttonBox.button(6).onTrue(new SetLiftPosition(mLift, 0.1, 60.0));
        //buttonBox.button(10).onTrue(new JointMotorSetPosition(mJoint, 0.379));
        //buttonBox.button(11).onTrue(new JointMotorSetPosition(mJoint, 0.479));
        //buttonBox.button(12).onTrue(new JointMotorSetPosition(mJoint, 0.579));
    }

    public Command getAutonomousCommand() {
        return null;
    }
}