package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.AutoCommand;

public class Robot extends TimedRobot {
    private final RobotContainer m_robotContainer = new RobotContainer();

    private Command m_autonomousCommand = new AutoCommand(m_robotContainer.m_DriveTrainSubsystem).withTimeout(4);

    // @SuppressWarnings("unused")
    // private final CharacterizeDrivetrainCommand characterizeCommand = new
    // CharacterizeDrivetrainCommand(
    // m_robotContainer.getDrivetrain());

    @Override
    public void robotInit() {

    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void disabledExit() {
    }

    @Override
    public void teleopInit() {
        // if (!robotContainer.getClimber().isClimberZeroed()) {
        // new ZeroClimberCommand(robotContainer.getClimber()).schedule();
        // }
        // if (!robotContainer.getShooter().isHoodZeroed()) {
        // new ZeroHoodCommand(robotContainer.getShooter(), true).schedule();
        // }
        m_robotContainer.resetDrive();
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }
    }

    @Override
    public void testInit() {
        // new InstantCommand(robotContainer.getShooter()::disableFlywheel);
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void autonomousInit() {
        // schedule the autonomous command (example)
        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }

    }

    @Override
    public void autonomousPeriodic() {
        if (Timer.getMatchTime() > 12)
            m_robotContainer.getDrivetrain().drive(0.7, 0);
    }

    @Override
    public void autonomousExit() {

    }
}
