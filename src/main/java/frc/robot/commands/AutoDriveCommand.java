package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;

public class AutoDriveCommand extends CommandBase {
    private DriveTrainSubsystem m_DriveTrainSubsystem;

    public AutoDriveCommand(DriveTrainSubsystem driveTrainSubsystem) {
        m_DriveTrainSubsystem = driveTrainSubsystem;
        addRequirements(driveTrainSubsystem);
    }

    @Override
    public void execute() {
        m_DriveTrainSubsystem.drive(-.6, -0.6);
    }
}
