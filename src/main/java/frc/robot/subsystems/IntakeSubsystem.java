package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
    /*
     * 
     */
    // public final WPI_TalonSRX indexer = new WPI_TalonSRX(Constants.INTAKE_MOTOR_ONE);
    public final CANSparkMax indexMotor = new CANSparkMax(Constants.INTAKE_MOTOR_ONE, MotorType.kBrushless);
    public static IntakeSubsystem instance;

    public IntakeSubsystem() {
        instance = this;
    }

    public void spin() {
        indexMotor.set(.5);

    }

    public void stop() {
        indexMotor.stopMotor();
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
