package frc.robot.subsystems;

import java.lang.reflect.Array;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IndexerSubsystem extends SubsystemBase {

    private WPI_TalonFX indexerOneMotor = new WPI_TalonFX(Constants.INDEXER_MOTOR_ONE);
    private WPI_TalonFX indexerTwoMotor = new WPI_TalonFX(Constants.INDEXER_MOTOR_TWO);

    private boolean[] position;

    public IndexerSubsystem() {
        indexerOneMotor.getSelectedSensorVelocity();
        indexerTwoMotor.getSelectedSensorVelocity();

        position = new boolean[3];

        // indexerOneMotor.config_kP(0, Constants.SHOOTER_KP);
        // indexerOneMotor.config_kI(0, Constants.SHOOTER_KI);
        // indexerOneMotor.config_kD(0, Constants.SHOOTER_KD);

        // indexerTwoMotor.config_kP(0, Constants.SHOOTER_KP);
        // indexerTwoMotor.config_kI(0, Constants.SHOOTER_KI);
        // indexerTwoMotor.config_kD(0, Constants.SHOOTER_KD);

    }

    @Override
    public void periodic() {
        updateIndexer();
    }

    public boolean readyToShoot() {
        return position[2];
    }

    public void forwardToShooter() {
        if (!position[1]) position[2] = false;
        position[1] = false;
        cycleIndexerTwo(2);
    }

    public void acceptedFromIntake() {
        position[0] = true;
    }

    public void updateIndexer() {
        if (!position[1] && position[0]) {
            position[1] = true;
            position[0] = false;
            cycleIndexerOne();
        }
        if (!position[2] && position[1]) {
            position[2] = true;
            position[1] = false;
            cycleIndexerTwo(1);
        } 
    }

    public void cycleIndexerOne() {

    }

    public void cycleIndexerTwo(int pos) {
        /*
         * pos = 2,3
         * 
         * rotate until sensor pos detects ball
         */
    }

}