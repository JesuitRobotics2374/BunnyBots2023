package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;

public class DodgeLeftCommand extends CommandBase {
    DriveTrainSubsystem m_DriveTrainSubsystem;

    public DodgeLeftCommand(DriveTrainSubsystem subsystem) {
        m_DriveTrainSubsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        m_DriveTrainSubsystem.updateTurnTarget(90);
    }

    @Override
    public void execute() {
        m_DriveTrainSubsystem.Turn();

    }

    @Override
    public boolean isFinished() {
        return m_DriveTrainSubsystem.atSetPoint();
    }
}
