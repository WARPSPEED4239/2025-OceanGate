package frc.robot;

import static edu.wpi.first.units.Units.*;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.ctre.phoenix6.swerve.SwerveRequest;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandGenericHID;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.commands.CoralWheelsSetSpeed;
import frc.robot.commands.ESTOPNOWSTOPTHEMOTORSNOOOAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHH;
import frc.robot.commands.BallIntakeSetSpeed;
import frc.robot.commands.CoralLeftLimelight;
import frc.robot.commands.SetJointPosition;
import frc.robot.commands.MoveJoint;
import frc.robot.commands.ResetEncoderPosition;
import frc.robot.commands.SetAllPosition;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.BallIntake;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CoralIntake;
import frc.robot.subsystems.CoralLimelight;
import frc.robot.subsystems.Joint;
import frc.robot.commands.MoveArm;
import frc.robot.commands.SetArmPosition;
import frc.robot.subsystems.Arm;
import frc.robot.commands.MoveLift;
import frc.robot.commands.SetLiftPosition;
import frc.robot.subsystems.Lift;                                              

public class RobotContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);//TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    private final CoralIntake mCoralIntake = new CoralIntake();

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

    private final SwerveRequest.RobotCentric limelightDrive = new SwerveRequest.RobotCentric()
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
    private final CoralLimelight mCoralLimelight = new CoralLimelight();

    private final BallIntake mBallIntake = new BallIntake();

    private final SendableChooser<Command> autoChooser;
    
    public RobotContainer() {
        NamedCommands.registerCommand("Coral L2", new SetAllPosition(mLift, mArm, mJoint, 105.0, -16.0, 8.0));
        NamedCommands.registerCommand("Coral L4", new SetAllPosition(mLift, mArm, mJoint, 201.0, 5.0, -1.6234896389));
        NamedCommands.registerCommand("Coral Blow", new CoralWheelsSetSpeed(mCoralIntake, 0.5));
        NamedCommands.registerCommand("Coral Suck", new CoralWheelsSetSpeed(mCoralIntake, -0.5));
        NamedCommands.registerCommand("Ball Blow", new BallIntakeSetSpeed(mBallIntake, 0.75));
        NamedCommands.registerCommand("Coral Intake", new SetAllPosition(mLift, mArm, mJoint, 25.5, -5.0, -14.0));

        NamedCommands.registerCommand("Tilt Arm", new SetAllPosition(mLift, mArm, mJoint, 201.0, 5.0, -8.0));

        NamedCommands.registerCommand("Coral Processor", new SequentialCommandGroup(new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 23.0),
                                                                                new SetArmPosition(mArm, 45.0),
                                                                                new SetJointPosition(mJoint, 2.5)),
                                                                        new WaitCommand(1.5)),

                                                                            Commands.parallel(new SetLiftPosition(mLift, 3.0),
                                                                            new SetArmPosition(mArm, 45.0),
                                                                            new SetJointPosition(mJoint, 2.5))));
        NamedCommands.registerCommand("Home", new SequentialCommandGroup(new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 33.0),
                                                                new SetJointPosition(mJoint, -3.0)),
                                                                new WaitCommand(2.3)),
                                                                new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 33.0),
                                                                        new SetArmPosition(mArm, 0.0),
                                                                        new SetJointPosition(mJoint, -3.0)), //-3
                                                                new WaitCommand(1.0)),

                                                                new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 0.0),
                                                                new SetArmPosition(mArm, 0.0),
                                                                new SetJointPosition(mJoint, -3.0)),
                                                                new WaitCommand(3.0)),
                                                                new ParallelRaceGroup(new MoveLift(mLift, -0.1),
                                                                new WaitCommand(0.5))));

        NamedCommands.registerCommand("AlignWithVisionLeft", new CoralLeftLimelight(mCoralLimelight, drivetrain, limelightDrive, 0.5, 4.12));
        NamedCommands.registerCommand("AlignWithVisionRight", new CoralLeftLimelight(mCoralLimelight, drivetrain, limelightDrive, 0.5, -40.0));

        autoChooser = AutoBuilder.buildAutoChooser();

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

        SmartDashboard.putData("Auto Chooser", autoChooser);

        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(xboxController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(xboxController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
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

        //joystick.button(2).whileTrue(new SetLiftPosition(mLift, mLift.getEncoderValue()));
        joystick.button(5).whileTrue(new MoveLift(mLift, -0.1));
        joystick.button(6).whileTrue(new MoveLift(mLift, 0.1));
        joystick.button(4).whileTrue(new MoveArm(mArm, -0.1));
        joystick.button(3).whileTrue(new MoveArm(mArm, 0.1));
        joystick.button(11).onTrue(new ESTOPNOWSTOPTHEMOTORSNOOOAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHH(mLift, mArm, mJoint));
        joystick.povUp().whileTrue(new CoralWheelsSetSpeed(mCoralIntake, 0.5));  //Button 6
        joystick.povDown().whileTrue(new CoralWheelsSetSpeed(mCoralIntake, -0.5));     //Button 7
        joystick.povDown().whileTrue(new BallIntakeSetSpeed(mBallIntake, -0.75)); //Button 4
        joystick.povUp().whileTrue(new BallIntakeSetSpeed(mBallIntake, 1.0));        //Button 3

        xboxController.povLeft().whileTrue(new CoralLeftLimelight(mCoralLimelight, drivetrain, limelightDrive, 0.5, 4.12)); //1.22
        xboxController.povRight().whileTrue(new CoralLeftLimelight(mCoralLimelight, drivetrain, limelightDrive, 0.5, -40.0));
        // buttonBox.button(1).onTrue(Commands.parallel(new SetLiftPosition(mLift, 0.0),
        //                                                     new SetJointPosition(mJoint, -3.0),
        //                                                     new SetArmPosition(mArm, 0.0)));

        // joystick.button(2).onTrue(new SetAllPosition(mLift, mArm, mJoint, 0.0, 0.0, -3.0)); //Bottom Hold Position

        //joystick.trigger().onTrue(new SetAllPosition(mLift, mArm, mJoint, 50.0, 45.0,29.5)); //Ball Up //28

        joystick.trigger().onTrue(new SequentialCommandGroup(new ParallelRaceGroup(new SetAllPosition(mLift, mArm, mJoint, 33.0, 45.0, 0.0),
                                                                                   new WaitCommand(2.0)),
                                                             new SetAllPosition(mLift, mArm, mJoint, 33.0, 45.0, 31.0)));

        // xboxController.y().onTrue(new SequentialCommandGroup(new ParallelRaceGroup(new SetAllPosition(mLift, mArm, mJoint, 22.0, -16.0, 0.0),
        //                                                                            new WaitCommand(2.0)),
        //                                                      new SetAllPosition(mLift, mArm, mJoint, 22.0, -16.0, -32.0)));

        //xboxController.y().onTrue(new SequentialCommandGroup(new ParallelRaceGroup(new SetAllPosition(mLift, mArm, mJoint, 75.39, 0.0, 0.0),
        //                                                                             new WaitCommand(2.0)),
        //                                                                             new SetAllPosition(mLift, mArm, mJoint, 75.39, 48.62, -31.435)));

        // //xboxController.x().whileTrue(new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 20.0),
        //                                                                 new SetArmPosition(mArm, 48.62),
        //                                                                 new SetJointPosition(mJoint, -31.435)), //-3
        //                                                 new WaitCommand(3)));

        // buttonBox.button(1).onTrue(new SetAllPosition(mLift, mArm, mJoint, 19.0, -5.0, -16.0)); //Coral Intake\

        buttonBox.button(1).onTrue((new SequentialCommandGroup(new ParallelRaceGroup(new SetAllPosition(mLift, mArm, mJoint, 23.0, -8.0, -14.0),
                                                                                     new WaitCommand(0.5)),
                                                               Commands.parallel(new SetLiftPosition(mLift, 22.0),
                                                                                 new SetArmPosition(mArm, -8.0),
                                                                                 new SetJointPosition(mJoint, -14.0)))));

        buttonBox.button(2).onTrue(new SequentialCommandGroup(new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 13.0),
                                                                                                             new SetArmPosition(mArm, 0.0),
                                                                                                             new SetJointPosition(mJoint, 1.0)), //-3
                                                                                           new WaitCommand(0.75)), //0.5

                                                                     new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 13.0), //27.5
                                                                                                             new SetArmPosition(mArm, 61.0),
                                                                                                             new SetJointPosition(mJoint, 1.0)), //-3
                                                                                           new WaitCommand(1.75)),   //1.75

                                                                     Commands.parallel(new SetLiftPosition(mLift, 0.0), //27.5
                                                                                       new SetArmPosition(mArm, 61.0),
                              
                              new SetJointPosition(mJoint, -1.0)))); //-6

        buttonBox.button(3).onTrue(new SequentialCommandGroup(new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 13.0),
                                                                                                             new SetArmPosition(mArm, 0.0),
                                                                                                             new SetJointPosition(mJoint, 1.0)), //-3
                                                                                           new WaitCommand(0.75)), //0.5

                                                                     new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 13.0), //27.5
                                                                                                             new SetArmPosition(mArm, 63.0),
                                                                                                             new SetJointPosition(mJoint, 1.0)), //-3
                                                                                           new WaitCommand(1.75)),   //1.75

                                                                     Commands.parallel(new SetLiftPosition(mLift, 4.0), //27.5
                                                                                       new SetArmPosition(mArm, 61.0),
                                                                                       new SetJointPosition(mJoint, -1.0)))); //-6

        buttonBox.button(4).onTrue(new SetAllPosition(mLift, mArm, mJoint, 97.0, -16.0, 8.0)); //Coral Level 2

        buttonBox.button(6).onTrue(new SequentialCommandGroup(new ParallelRaceGroup(new SetLiftPosition(mLift, 70.0), //Aux Coral 1
                                                                                            new WaitCommand(1.5)), 
                                            new SetAllPosition(mLift, mArm, mJoint, 70.0, 50.0, 3.0)));
        
        buttonBox.button(7).onTrue(new SetAllPosition(mLift, mArm, mJoint, 151.0, -16.0, 7.0)); //Coral Level 3

        //buttonBox.button(8).onTrue(new SetAllPosition(mLift, mArm, mJoint, 128.0, 57.80, -0.45)); //Ball Level 2

        buttonBox.button(8).onTrue(new SequentialCommandGroup(new ParallelRaceGroup(new SetLiftPosition(mLift, 101.0), //-3
                                                                                            new WaitCommand(1.5)), 
                                            new SetAllPosition(mLift, mArm, mJoint, 101.0, 57.80, -0.45)));

        buttonBox.button(9).onTrue(new SequentialCommandGroup(new ParallelRaceGroup(new SetLiftPosition(mLift, 142.0), //-3
                                                                                            new WaitCommand(1.5)), 
                                            new SetAllPosition(mLift, mArm, mJoint, 142.0, 30.0, -5.0)));  //Aux Coral 2

        buttonBox.button(10).onTrue((new SetAllPosition(mLift, mArm, mJoint, 201.0, 5.0, -0.5))); //Coral Level 3

        xboxController.x().onTrue((new SetAllPosition(mLift, mArm, mJoint, 201.0, 5.0, -8.0))); //Coral Level 3

        xboxController.x().onFalse((new SetAllPosition(mLift, mArm, mJoint, 201.0, 5.0, -0.5)));

        //buttonBox.button(11).onTrue(new SetAllPosition(mLift, mArm, mJoint, 180.314, 57.79, -0.44)); //Ball Level 3

        buttonBox.button(11).onTrue(new SequentialCommandGroup(new ParallelRaceGroup(new SetLiftPosition(mLift, 153.314), //-3
                                                                                            new WaitCommand(1.5)), 
                                            new SetAllPosition(mLift, mArm, mJoint, 153.314, 57.79, -0.44)));

        buttonBox.button(12).onTrue(new SetAllPosition(mLift, mArm, mJoint, 201.0, 50.0, 25.0));

        /* put back in joystick.button(2).onTrue(new SequentialCommandGroup(new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 33.0),
                                                                                                            new SetJointPosition(mJoint, -3.0)),
                                                                                          new WaitCommand(2.3)),
                                                                    new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 33.0),
                                                                                                             new SetArmPosition(mArm, 0.0),
                                                                                                             new SetJointPosition(mJoint, -3.0)), //-3
                                                                                          new WaitCommand(1.0)),
                                                                                        
                                                            new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 0.0),
                                                                                                    new SetArmPosition(mArm, 0.0),
                                                                                                    new SetJointPosition(mJoint, -3.0)),
                                                                                  new WaitCommand(3.0)),
                                                            new ParallelRaceGroup(new MoveLift(mLift, -0.1),
                                                                new WaitCommand(0.5)))); //-3*/


        buttonBox.button(5).onTrue(new SequentialCommandGroup(new ParallelRaceGroup(Commands.parallel(new SetLiftPosition(mLift, 23.0),
                                                                                                             new SetArmPosition(mArm, 45.0),
                                                                                                             new SetJointPosition(mJoint, 2.5)),
                                                                                           new WaitCommand(1.5)),
                                                                    
                                                                    Commands.parallel(new SetLiftPosition(mLift, 3.0),
                                                                                      new SetArmPosition(mArm, 45.0),
                                                                                      new SetJointPosition(mJoint, 2.5))));




        
        
        joystick.button(9).whileTrue(new MoveJoint(mJoint, -0.1));
        joystick.button(7).whileTrue(new MoveJoint(mJoint, 0.1));
        joystick.button(12).onTrue(new ResetEncoderPosition(mJoint));
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}