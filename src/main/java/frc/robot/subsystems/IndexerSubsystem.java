package frc.robot.subsystems;

import java.lang.reflect.Array;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;

public class IndexerSubsystem extends SubsystemBase {

    private WPI_TalonFX indexerOneMotor = new WPI_TalonFX(Constants.INDEXER_MOTOR_ONE);
    private WPI_TalonFX indexerTwoMotor = new WPI_TalonFX(Constants.INDEXER_MOTOR_TWO);
    private DigitalInput sensor1 = new DigitalInput(0);
    private DigitalInput sensor2 = new DigitalInput(1);

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
        cycleIndexerTwo(false);
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
            cycleIndexerTwo(true);
        } 
    }

    public void cycleIndexerOne() {
        while (!sensor1.get()) indexerOneMotor.set(Constants.INDEXER_MOTOR_SPIN_SPEED); 
        indexerOneMotor.stopMotor();
    }

    public void cycleIndexerTwo(boolean shoot) { 
        if (shoot) { //If this function is called to shoot, turn motor until ball has left position 2
            while (sensor2.get()) {
                indexerTwoMotor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
            }
        } else { //If this function is called to a ball to position 2, turn motor until ball has entered position 2
            while (!sensor2.get()) {
                indexerTwoMotor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
            }
        }

        //Stop motor once condition met
        indexerTwoMotor.stopMotor();
    }

}
