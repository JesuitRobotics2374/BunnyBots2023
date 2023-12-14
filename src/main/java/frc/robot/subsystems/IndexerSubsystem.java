package frc.robot.subsystems;

import javax.sound.midi.MidiChannel;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IndexerSubsystem extends SubsystemBase {

    private WPI_TalonFX indexerOneMotor = new WPI_TalonFX(Constants.INDEXER_MOTOR_ONE);
    private WPI_TalonFX indexerTwoMotor = new WPI_TalonFX(Constants.INDEXER_MOTOR_TWO);
    private DigitalInput sensor0 = new DigitalInput(5);
    private DigitalInput sensor1 = new DigitalInput(1);
    private DigitalInput sensor2 = new DigitalInput(2);
    private Debouncer debouncer0 = new Debouncer(.1, DebounceType.kRising);
    private Debouncer debouncer1 = new Debouncer(.1, DebounceType.kBoth);
    private Debouncer debouncer2 = new Debouncer(.2, DebounceType.kFalling);
    private Debouncer debouncerFire = new Debouncer(.4, DebounceType.kFalling);
    private boolean[] position;
    private boolean shoot;
    private boolean manualIndexer = false;

    public final CANSparkMax intakeMotor = new CANSparkMax(Constants.INTAKE_MOTOR_ONE, MotorType.kBrushless);
    public static IndexerSubsystem instance;

    public IndexerSubsystem() {
        instance = this;

        indexerOneMotor.getSelectedSensorVelocity();
        indexerTwoMotor.getSelectedSensorVelocity();
        indexerOneMotor.setInverted(true);
        indexerTwoMotor.setInverted(true);
        indexerTwoMotor.setNeutralMode(NeutralMode.Brake);
        indexerOneMotor.setNeutralMode(NeutralMode.Brake);

        position = new boolean[3];

        ShuffleboardTab tab = Shuffleboard.getTab(Constants.DRIVER_READOUT_TAB_NAME);
        tab.addBoolean("sensor0", ()-> sensor0.get());
        tab.addBoolean("sensor1", ()-> sensor1.get());
        tab.addBoolean("sensor2", ()-> sensor2.get());
        tab.addBoolean("internalPosition0", ()-> position[0]);
        tab.addBoolean("internalPosition1", ()-> position[1]);
        tab.addBoolean("internalPosition2", ()-> position[2]);
        tab.addBoolean("shoot", () -> getShoot());

        // indexerOneMotor.config_kP(0, Constants.SHOOTER_KP);
        // indexerOneMotor.config_kI(0, Constants.SHOOTER_KI);
        // indexerOneMotor.config_kD(0, Constants.SHOOTER_KD);

        // indexerTwoMotor.config_kP(0, Constants.SHOOTER_KP);
        // indexerTwoMotor.config_kI(0, Constants.SHOOTER_KI);
        // indexerTwoMotor.config_kD(0, Constants.SHOOTER_KD);

    }

    public static IndexerSubsystem getInstance() {
        if (instance == null) {
            instance = new IndexerSubsystem();
        }
        return instance;
    }

    @Override
    public void periodic() {
        updateIndexer();
        recievedIntake();
        if (manualIndexer) {
            updateIndexerNum(1);
            updateIndexerNum(2);
        }
    }

    public void updateIndexerNum(int num) {
        if (num == 1) indexerOneMotor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
        if (num == 2) indexerTwoMotor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
    }

    public void shoot() {
        if (position[2]) {
            shoot = true;
        }
    }

    public void shootNoFire() {
        if (position[2]) {
            shoot = true;
        }
    }

    public boolean readyToShoot() {
        return position[2];
    }

    public void recievedIntake() {
        if (!position[0] && debouncer0.calculate(!sensor0.get())) {
            position[0] = true;
            stopIntake();
        }
    }

    public void updateIndexer() {
        if (!position[1] && position[0]) tryCyclingIndexerOne();
        if (!position[2] && position[1]) tryCyclingIndexerTwo();
        if (position[2] && shoot) tryCyclingIndexerTwo();
        if (position[2] && sensor2.get() && !shoot) {
            indexerTwoMotor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
        } else if (position[2] && !shoot) {
            indexerTwoMotor.stopMotor();
        }
    }

    public void tryCyclingIndexerOne() {
        if (!debouncer1.calculate(!sensor1.get())) {
            indexerOneMotor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
        } else {
            position[1] = true;
            position[0] = false;
            indexerOneMotor.stopMotor();
        }
    }

    public void tryCyclingIndexerTwo() {
        if ((shoot && debouncerFire.calculate(!sensor2.get())) || (!shoot && !debouncer2.calculate(!sensor2.get()))) {
            indexerTwoMotor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
        } else if (shoot) {
            position[2] = false;
            shoot = false;
            ShooterSubsystem.getInstance().stopMotor();
            indexerTwoMotor.stopMotor();
        } else {
            position[2] = position[1];
            position[1] = false;
            shoot = false;
            indexerTwoMotor.stopMotor();
        }
    }

    //INAKE CODE

    public void spinIntake() {
        intakeMotor.set(.5);
        indexerOneMotor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
    }

    public void stopIntake() {
        intakeMotor.stopMotor();
        indexerOneMotor.stopMotor();
    }

    // //INDEXER TESTING CODE

    public void cycleIndexers() {
        indexerTwoMotor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
        indexerOneMotor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
        flushIndexer();
    }

    public void stopCycleIndexers() {
        indexerTwoMotor.stopMotor();
        indexerOneMotor.stopMotor();
    }

    public void flushIndexer() {
        position = new boolean[3];
    }

    // public void cycleOneMotor() {
    //     indexerOneMotor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
    // }

    // public void stopCycleOneMotor() {
    //     indexerOneMotor.stopMotor();
    // }

    public boolean getShoot() {
        return shoot;
    }

    //BS stuff

    public void manualIndexerTrue() {
        manualIndexer = true;
    }

    public void manualIndexerFalse() {
        manualIndexer = false;
    }
}
