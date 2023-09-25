package frc.robot.util;

// import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.RobotContainer;

public class AutonomousChooser {
    private final AutonomousTrajectories trajectories;

    private final SendableChooser<AutonomousMode> autonomousModeChooser = new SendableChooser<>();

    public AutonomousChooser(AutonomousTrajectories trajectories) {
        this.trajectories = trajectories;

        autonomousModeChooser.setDefaultOption("Test thing", AutonomousMode.ONE_METER_F);
    }

    public SendableChooser<AutonomousMode> getModeChooser() {
        return autonomousModeChooser;
    }

    public Command getOneMeterFAuto(RobotContainer container) {
        SequentialCommandGroup command = new SequentialCommandGroup();

        command.addCommands(
        // resetRobotPose(container, trajectories.getOneMeterF()),
        // follow(container, trajectories.getOneMeterF())
        /*
         * IDEA:
         * score, go back, wait till > 10, wait till < 2, wait .2, go forward,
         * wait till > 10, wait .1, slow down, wait till < 8, balence
         */
        // BALENCING
        // new InstantCommand(() -> container.getDrivetrain().drive(new
        // ChassisSpeeds(-1.6, 0, 0)),
        // container.getDrivetrain()),
        // new WaitUntilCommand(new BooleanSupplier() {
        // public boolean getAsBoolean() {
        // return container.getDrivetrain().getPitch() < -10;
        // };
        // }),
        // new WaitCommand(.1),
        // new InstantCommand(() -> container.getDrivetrain().drive(new
        // ChassisSpeeds(-0.6, 0, 0)),
        // container.getDrivetrain()),
        // new WaitUntilCommand(new BooleanSupplier() {
        // public boolean getAsBoolean() {
        // return container.getDrivetrain().getPitch() > -8;
        // };
        // }),
        // new RunCommand(() -> container.getDrivetrain().autoBalenceTick(),
        // container.getDrivetrain())
        // mountAndBalence(container)

        // ALIGNING
        // new RunCommand(() -> {
        // if ((container.getDrivetrain().getYaw() - 90) % 360 > 0) {
        // container.getDrivetrain().drive(
        // new ChassisSpeeds(0, 0,
        // 2 * ((container.getDrivetrain().getYaw() - 90) % 360 / 360) + 0.2));
        // } else {
        // container.getDrivetrain().drive(
        // new ChassisSpeeds(0, 0,
        // -2 * ((container.getDrivetrain().getYaw() - 90) % 360 / 360) - 0.2));
        // }
        // }).until(new BooleanSupplier() {
        // public boolean getAsBoolean() {
        // if (Math.abs((container.getDrivetrain().getYaw() - 90) % 360) < 5) {
        // System.out.println(true);
        // }
        // return Math.abs((container.getDrivetrain().getYaw() - 90) % 360) < 5;
        // };
        // })

        // followLine(container, 1, 0, 90)
        );

        return command;
    }

    // Handler to determine what command was requested for the autonmous routine to
    // execute
    public Command getCommand(RobotContainer container) {
        switch (autonomousModeChooser.getSelected()) {
            case ONE_METER_F:
                return getOneMeterFAuto(container);
            default:
                break;
        }
        return new InstantCommand();
    }

    private enum AutonomousMode {
        ONE_METER_F
    }
}