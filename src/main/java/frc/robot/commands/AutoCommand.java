package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;

public class AutoCommand extends CommandBase {
    DriveTrainSubsystem m_DriveTrainSubsystem;

    public AutoCommand(DriveTrainSubsystem subsystem) {
        m_DriveTrainSubsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        m_DriveTrainSubsystem.drive(-.6, -.6);
    }
}
