package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;
import java.lang.System;

public class DriveForwardCommand extends CommandBase {
    DriveTrainSubsystem m_DriveTrainSubsystem;

    public DriveForwardCommand(DriveTrainSubsystem subsystem) {
        m_DriveTrainSubsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        m_DriveTrainSubsystem.updateTurnTarget(90);
    }

    @Override
    public void execute() {
        System.out.println("forward");
        m_DriveTrainSubsystem.driv_distance(2);
    }

    @Override
    public boolean isFinished() {
        return m_DriveTrainSubsystem.atSetPoint();
    }

}
