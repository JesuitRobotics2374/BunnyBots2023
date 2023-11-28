package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrainSubsystem;

public class DriveForwardCommand extends CommandBase {
    DriveTrainSubsystem m_DriveTrainSubsystem;

    double m_rightCurrent;
    double m_leftCurrent;
    double m_rightTarget;

    public DriveForwardCommand(DriveTrainSubsystem subsystem) {
        m_DriveTrainSubsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        m_leftCurrent = m_DriveTrainSubsystem.frontLeft.getSelectedSensorPosition();
        m_rightCurrent = m_DriveTrainSubsystem.frontRight.getSelectedSensorPosition();
        // Double leftTarget = leftCurrent += p;
        // Double rightTarget= rightCurrent += p;
        // m_DriveTrainSubsystem.updateTurnTarget(90);
        // int p = revNum * 4096;

    }
    // public void driv_distance(int revNum) {

    // int p = revNum * 4096;h
    // m_drive.drive(.5, .5);

    // m_leftTarget = leftCurrent += p;
    // m_rightTarget= rightCurrent += p;

    // }

    // @Override
    // public void execute() {
    // System.out.println("forward");
    // m_DriveTrainSubsystem.driv_distance(2);
    // }

    // @Override
    // public boolean isFinished() {
    // if(rightCurrent > rightTarget){
    // return;
    // }
    // return m_DriveTrainSubsystem.atSetPoint();
    // }

}
