package frc.robot.subsystems;

import javax.sound.midi.ShortMessage;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.Constants;

public class ShooterSubsystem {

    private final WPI_TalonFX leftMotor = new WPI_TalonFX(Constants.LEFT_SHOOTER_MOTOR);
    private final WPI_TalonFX rightMotor = new WPI_TalonFX(Constants.RIGHT_SHOOTER_MOTOR);
    private final MotorControllerGroup shooterMotorGroup;

    public ShooterSubsystem() {
        rightMotor.setInverted(true);
        shooterMotorGroup = new MotorControllerGroup(leftMotor, rightMotor);
    }

    
}
