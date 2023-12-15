package frc.robot;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.subsystems.DriveTrainSubsystem;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class RobotContainer {

    // private final AutonomousChooser autonomousChooser = new AutonomousChooser(
    // new AutonomousTrajectories(DrivetrainSubsystem.TRAJECTORY_CONSTRAINTS));
    DriveTrainSubsystem m_DriveTrainSubsystem = new DriveTrainSubsystem();
    IndexerSubsystem m_IndexerSubsystem = new IndexerSubsystem();
    ShooterSubsystem m_ShooterSubsystem = new ShooterSubsystem();

    private final XboxController m_driveController = new XboxController(Constants.CONTROLLER_USB_PORT_DRIVER);
    private final XboxController m_operatorController = new XboxController(Constants.CONTROLLER_USB_PORT_OPERATOR);

    private SlewRateLimiter leftLimiter = new SlewRateLimiter(5);
    private SlewRateLimiter rightLimiter = new SlewRateLimiter(5);

    private int driveMode = 0;

    // 0 = Normal, -1 = Slow, 1 = Fast

    /**
     * The robot container. Need I say more?
     */
    public RobotContainer() {
        resetDrive();
        configureButtonBindings();
        configureShuffleBoard();
        System.out.println("container created");
    }

    /**
     * Reset the default drive command
     */
    public void resetDrive() {
        m_DriveTrainSubsystem.setDefaultCommand(
                new DefaultDriveCommand(this::getLeftY, this::getRightY, m_DriveTrainSubsystem));
    }

    public void clearDrive() {
        m_DriveTrainSubsystem.removeDefaultCommand();
    }

    /**
     * Get the main controller
     * 
     * @return The main controller
     */
    public XboxController getMainController() {
        return m_driveController;
    }

    /**
     * Get the second controller
     * 
     * @return The second controller
     */
    // public XboxController getSecondController() {
    // return m_operatorController;
    // }

    /**
     * Set up the Shuffleboard
     */
    public void configureShuffleBoard() {
        ShuffleboardTab tab = Shuffleboard.getTab(Constants.DRIVER_READOUT_TAB_NAME);
        tab.addBoolean("Slow Mode", this::getSlowMode);
        tab.addBoolean("Turbo Mode", this::getTurboMode);
    }

    /**
     * Setup all of the button controls for the robot
     */
    public void configureButtonBindings() {
        new Trigger(m_driveController::getLeftBumper).onTrue(new InstantCommand(this::toggleSlowMode));
        new Trigger(m_driveController::getRightBumper).onTrue(new InstantCommand(this::toggleTurboMode));
        

        new Trigger(m_operatorController::getYButton)
                .onTrue(new InstantCommand(() -> m_IndexerSubsystem.shoot()));
        new Trigger(m_operatorController::getXButton)
                .onTrue(new InstantCommand(() -> m_ShooterSubsystem.stopMotor()));
        
        new Trigger(m_operatorController::getAButton)
                .onTrue(new InstantCommand(() -> m_IndexerSubsystem.cycleIndexers()));
        new Trigger(m_operatorController::getAButton)
                .onFalse(new InstantCommand(() -> m_IndexerSubsystem.stopCycleIndexers()));
        
                new Trigger(m_operatorController::getBButton)
                .onTrue(new InstantCommand(() -> m_IndexerSubsystem.spinIntake()));
        new Trigger(m_operatorController::getBButton)
                .onFalse(new InstantCommand(() -> m_IndexerSubsystem.stopIntake()));
        new Trigger(m_operatorController::getRightBumper)
            .onTrue(new InstantCommand(() -> m_ShooterSubsystem.fireFromConstants(ShooterSubsystem.getInstance().fireDistance, 1.06)));
        new Trigger(m_operatorController::getLeftBumper)
                .onFalse(new InstantCommand(() -> m_IndexerSubsystem.flushIndexer()));
        new Trigger(m_operatorController::getBackButton).onTrue(new InstantCommand(() -> ShooterSubsystem.getInstance().decreaseDistance()));
        new Trigger(m_operatorController::getStartButton).onTrue(new InstantCommand(() -> ShooterSubsystem.getInstance().increaseDistance()));
        // // new Trigger(m_operatorController::getRightBumper)
        // //         .onTrue(new InstantCommand(() -> m_ShooterSubsystem.fireFromConstants(7, 1.06)));
        // // new Trigger(m_operatorController::getRightBumper)
        // //         .onFalse(new InstantCommand(() -> m_IndexerSubsystem.fireFromConstants()));
    }

    /**
     * Adjusts the input to remove the tolerance w hile retaining a smooth line with
     * tolerance as 0 and 100 as 100
     * 
     * @param value     The value to adjust
     * @param tolerance The amount of inner area to remove
     * @return The adjusted value
     */
    public static double deadband(double value, double tolerance) {
        if (Math.abs(value) < tolerance)
            return 0.0;

        return Math.copySign(value, (value - tolerance) / (1.0 - tolerance));
    }

    /**
     * Copy sign square
     * 
     * @param value Value to square
     * @return The copy sign square
     */
    public static double square(double value) {
        return Math.copySign(value, value);
    }

    /**
     * Toggle the slow mode
     */
    public void toggleSlowMode() {
        if (driveMode == -1) {
            driveMode = 0;
        } else {
            driveMode = -1;
        }
    }

    public void toggleTurboMode() {
        if (driveMode == 1) {
            driveMode = 0;
        } else {
            driveMode = 1;
        }
    }

    /**
     * Get the adjusted Left Y axis of the main controller
     * 
     * @return The adjusted Left Y axis of the main controller
     */
    public double getLeftY() {
        if (driveMode == -1) {
            return square(leftLimiter.calculate(deadband(m_driveController.getLeftY(), Constants.DEADBAND))) * .5;
        } else if (driveMode == 1) {
            return square(leftLimiter.calculate(deadband(m_driveController.getLeftY(), Constants.DEADBAND)));
        } else {
            return Constants.SPEED_MULTIPLIER
                    * square(leftLimiter.calculate(deadband(m_driveController.getLeftY(), Constants.DEADBAND)));
        }
    }

    /**
     * Get the adjusted Right Y axis of the main controller
     * 
     * @return The adjusted Right Y axis of the main controller
     */

     public double getRightY() {
        if (driveMode == -1) {
            return square(rightLimiter.calculate(deadband(m_driveController.getRightY(), Constants.DEADBAND))) * .5;
        } else if (driveMode == 1) {
            return square(rightLimiter.calculate(deadband(m_driveController.getRightY(), Constants.DEADBAND)));
        } else {
            return Constants.SPEED_MULTIPLIER
                    * square(rightLimiter.calculate(deadband(m_driveController.getRightY(), Constants.DEADBAND)));
        }
    }
/* 
    public double getLeftX() {
        return rightLimiter.calculate(deadband(m_driveController.getLeftX(), Constants.DEADBAND)) * .5;
    }

    */
    /**
     * Accessor to the Autonomous Chooser
     * 
     * @return The Autonomous Chooser
     */
    // public AutonomousChooser getAutonomousChooser() {
    // return autonomousChooser;
    // }

    /**
     * Accessor to the DriveTrain Subsystem
     * 
     * @return The DriveTrain Subsystem
     */
    public DriveTrainSubsystem getDrivetrain() {
        return m_DriveTrainSubsystem;
    }

    /**
     * Get the slow boolean
     * 
     * @return slow
     */
    public boolean getSlowMode() {
        return (driveMode == -1);
    }

    /**
     * Get the turbo boolean
     * 
     * @return turbo
     */
    public boolean getTurboMode() {
        return (driveMode == 1);
    }
}
