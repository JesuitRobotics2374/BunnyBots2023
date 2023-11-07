package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {

    private WPI_TalonFX shooterLeftMotor = new WPI_TalonFX(Constants.SHOOTER_LEFT_MOTOR_CAN_ID);
    private WPI_TalonFX shooterRightMotor = new WPI_TalonFX(Constants.SHOOTER_RIGHT_MOTOR_CAN_ID);

    public ShooterSubsystem() {
        shooterRightMotor.setInverted(true);
        shooterLeftMotor.getSelectedSensorVelocity();
        shooterLeftMotor.config_kP(0, Constants.SHOOTER_KP);
        shooterLeftMotor.config_kI(0, Constants.SHOOTER_KI);
        shooterLeftMotor.config_kD(0, Constants.SHOOTER_KD);
        shooterRightMotor.follow(shooterLeftMotor);
    }

    @Override
    public void periodic() {

    }

    public double calculateFinalMotorVelocity(double distance, double correction) {
        // NEVER EVER TOUCH THIS
        return (((distance * correction) / Math.cos(Constants.SHOOTER_RELEASE_ANGLE))
                * Math.sqrt((9.80665 / 2)
                        * (1 / (distance * Math.tan(Constants.SHOOTER_RELEASE_ANGLE) - Constants.DELTA_HEIGHT)))
                + Constants.DRAG_COEFFICIENT) * Constants.MOTOR_VELOCITY_CONVERSION;
    }

    // public double getShooterVelocity() {
    // // gives 2048 units / 100ms
    // return shooterLeftMotor.getSelectedSensorVelocity();
    // }

    public void spinAtVelocity(double speed) {
        // (speed / 360) * 2048 * 10
        // flywheelPID.setSetpoint((speed / 9) * 512);
        shooterLeftMotor.set(ControlMode.Velocity, (speed / 9) * 512); // Speed in units / 100ms for value
    }

    public void fireFromConstants(double distance, double correction) {
        double motorVelocity = calculateFinalMotorVelocity(distance, correction);
        spinAtVelocity(motorVelocity);
    }

}
