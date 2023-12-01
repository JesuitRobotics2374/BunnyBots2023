package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

// import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IndexerSubsystem extends SubsystemBase {

    private WPI_TalonFX indexerOneMotor = new WPI_TalonFX(Constants.INDEXER_MOTOR_ONE);
    private WPI_TalonFX indexerTwoMotor = new WPI_TalonFX(Constants.INDEXER_MOTOR_TWO);
    // private DigitalInput sensor1 = new DigitalInput(0);
    // private DigitalInput sensor2 = new DigitalInput(1);

    private boolean[] position;
    // private boolean shoot;

    public IndexerSubsystem() {
        indexerOneMotor.getSelectedSensorVelocity();
        indexerTwoMotor.getSelectedSensorVelocity();
        indexerOneMotor.setInverted(true);
        indexerTwoMotor.setInverted(true);

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
        // updateIndexer();
    }

    public boolean readyToShoot() {
        return position[2];
    }

    public void forwardToShooter() {
        if (!position[1])
            position[2] = false;
        position[1] = false;
        // tryCyclingIndexerTwo(false);
    }

    public void acceptedFromIntake() {
        position[0] = true;
    }

    // public void updateIndexer() {
    //     if (!position[1] && position[0]) {
    //         tryCyclingIndexerOne();
    //     }
    //     if (!position[2] && position[1]) {
    //         tryCyclingIndexerTwo();
    //     }
    // }

    // public void tryCyclingIndexerOne() {
    //     if (!sensor1.get()) {
    //         indexerOneMotor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
    //     } else {
    //         position[1] = true;
    //         position[0] = false;
    //         indexerOneMotor.stopMotor();
    //     }
    // }

    // public void tryCyclingIndexerTwo() {
    //     if (shoot) {
    //         if (sensor2.get()) {
    //             indexerTwoMotor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
    //         } else {
    //             position[2] = (position[1]);
    //             position[1] = false;
    //             shoot = false;
    //             indexerTwoMotor.stopMotor();
    //         }
    //     } else {
    //         if (!sensor2.get()) {
    //             indexerTwoMotor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
    //         } else {
    //             position[2] = (position[1]);
    //             position[1] = false;
    //             indexerTwoMotor.stopMotor();
    //         }
    //     }
    // }

    public void cycleIndexers() {
        indexerTwoMotor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
        indexerOneMotor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
    }

    public void stopCycleIndexers() {
        indexerTwoMotor.stopMotor();
        indexerOneMotor.stopMotor();
    }

    // public void shoot() {
    //     shoot = true;
    // }

}
