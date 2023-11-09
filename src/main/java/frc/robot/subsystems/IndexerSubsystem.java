package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IndexerSubsystem extends SubsystemBase {

    private WPI_TalonFX indexerOneMotor = new WPI_TalonFX(Constants.INDEXER_MOTOR_ONE);
    private WPI_TalonFX indexerTwoMotor = new WPI_TalonFX(Constants.INDEXER_MOTOR_TWO);

    private boolean m_position1;
    private boolean m_position2;
    private boolean m_position3;

    public IndexerSubsystem() {
        indexerOneMotor.getSelectedSensorVelocity();
        indexerTwoMotor.getSelectedSensorVelocity();

        m_position1 = false;
        m_position2 = false;
        m_position3 = false;
        
        //indexerOneMotor.config_kP(0, Constants.SHOOTER_KP);
        //indexerOneMotor.config_kI(0, Constants.SHOOTER_KI);
        //indexerOneMotor.config_kD(0, Constants.SHOOTER_KD);

        //indexerTwoMotor.config_kP(0, Constants.SHOOTER_KP);
        //indexerTwoMotor.config_kI(0, Constants.SHOOTER_KI);
        //indexerTwoMotor.config_kD(0, Constants.SHOOTER_KD);
        
    }

    @Override
    public void periodic() {
        updateIndexer();
    }

    public void forwardToShooter() {
        
    }

    public void acceptFromIntake() {
        
    }

    public void updateIndexer() {
        
    }

    public void cycleIndexerOne() {
        
    }

    public void cycleIndexerTwo() {
        
    }






    

}
