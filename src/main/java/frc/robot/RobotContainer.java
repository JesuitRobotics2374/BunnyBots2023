package frc.robot;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.subsystems.DriveTrainSubsystem;

public class RobotContainer {

    // private final AutonomousChooser autonomousChooser = new AutonomousChooser(
    // new AutonomousTrajectories(DrivetrainSubsystem.TRAJECTORY_CONSTRAINTS));
    DriveTrainSubsystem m_DriveTrainSubsystem = new DriveTrainSubsystem();

    private final XboxController m_driveController = new XboxController(Constants.CONTROLLER_USB_PORT_DRIVER);
    // private final XboxController m_operatorController = new
    // XboxController(Constants.CONTROLLER_USB_PORT_OPERATOR);

    private SlewRateLimiter leftLimiter = new SlewRateLimiter(5);
    private SlewRateLimiter rightLimiter = new SlewRateLimiter(5);

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
        // ShuffleboardTab tab = Shuffleboard.getTab(Constants.DRIVER_READOUT_TAB_NAME);
    }

    /**
     * Setup all of the button controls for the robot
     */
    public void configureButtonBindings() {
    }

    /**
     * Adjusts the input to remove the tolerance while retaining a smooth line with
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
        return Math.copySign(value * value, value);
    }

    /**
     * Get the adjusted Left Y axis of the main controller
     * 
     * @return The adjusted Left Y axis of the main controller
     */
    public double getLeftY() {
        return square(leftLimiter.calculate(deadband(m_driveController.getLeftY(), Constants.DEADBAND)));
    }

    /**
     * Get the adjusted Right Y axis of the main controller
     * 
     * @return The adjusted Right Y axis of the main controller
     */

    public double getRightY() {
        return square(rightLimiter.calculate(deadband(m_driveController.getRightY(), Constants.DEADBAND)));
    }

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
}
