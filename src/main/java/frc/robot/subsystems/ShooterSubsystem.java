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
//todo
        }

    public double calculateInitialVelocity(double distance,double correction) {
        return ((distance*correction)/Math.cos(Constants.SHOOTER_RELEASE_ANGLE))*Math.sqrt((9.80665/2)*())+Constants.DRAG_COEFFICIENT;
    }


    // public double getShooterVelocity() {
    //     // gives 2048 units / 100ms
    //     return shooterLeftMotor.getSelectedSensorVelocity();
    // }

}
