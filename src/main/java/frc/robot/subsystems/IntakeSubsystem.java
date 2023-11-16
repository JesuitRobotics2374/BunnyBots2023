package frc.robot.subsystems;

import java.lang.Math;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
    /*
     * 
     */
    public final WPI_TalonSRX indexer = new WPI_TalonSRX(Constants.INTAKE_MOTOR_ONE);
    public static IntakeSubsystem instance;

    public IntakeSubsystem() {
        instance = this;
    }

    public void spin() {
        indexer.set(.5);

    }

    public void stop() {
        indexer.stopMotor();
    }

    public static IntakeSubsystem getInstance() {
        if (instance == null) {
            instance = new IntakeSubsystem();
        }
        return instance;
    }

    // Speed in units / 100ms for value
    // public void buttonDown();

};
