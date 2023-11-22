package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.SensorVelocityMeasPeriod;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {

    private WPI_TalonFX shooterLeftMotor = new WPI_TalonFX(Constants.SHOOTER_LEFT_MOTOR_CAN_ID);
    // private WPI_TalonFX shooterRightMotor = new
    // WPI_TalonFX(Constants.SHOOTER_RIGHT_MOTOR_CAN_ID);

    public ShooterSubsystem() {
        // shooterRightMotor.setInverted(true);
        shooterLeftMotor.configFactoryDefault();
        shooterLeftMotor.configVelocityMeasurementPeriod(SensorVelocityMeasPeriod.Period_1Ms);
        shooterLeftMotor.configVelocityMeasurementWindow(32);
        shooterLeftMotor.config_kF(0, 0.02);
        shooterLeftMotor.config_kP(0, Constants.SHOOTER_KP);
        shooterLeftMotor.config_kI(0, Constants.SHOOTER_KI);
        shooterLeftMotor.config_kD(0, Constants.SHOOTER_KD);
        shooterLeftMotor.config_IntegralZone(0, 0);
        shooterLeftMotor.configNominalOutputForward(0);
        shooterLeftMotor.configNominalOutputReverse(0);
        shooterLeftMotor.configPeakOutputForward(1);
        shooterLeftMotor.configPeakOutputReverse(-1);
        shooterLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 30);
        shooterLeftMotor.setInverted(true);

        // shooterRightMotor.follow(shooterLeftMotor);
        ShuffleboardTab tab = Shuffleboard.getTab(Constants.DRIVER_READOUT_TAB_NAME);
        tab.addDouble("Speed", () -> getShooterVelocity());

    }

    @Override
    public void periodic() {

    }

    public double calculateFinalMotorVelocity(double distance, double correction) {
        // NEVER EVER TOUCH THIS
        return (((Math.pow(distance, correction)) / Math.cos(Constants.SHOOTER_RELEASE_ANGLE))
                * Math.sqrt((9.80665 / 2)
                        * (1 / (distance * Math.tan(Constants.SHOOTER_RELEASE_ANGLE) - Constants.DELTA_HEIGHT)))
                + Constants.DRAG_COEFFICIENT) * Constants.MOTOR_VELOCITY_CONVERSION * 2;
    }

    public double getShooterVelocity() {
        return shooterLeftMotor.getSelectedSensorVelocity();
    }

    public void spinAtVelocity(double speed) {
        // (speed / 360) * 2048 * 10
        // flywheelPID.setSetpoint((speed / 9) * 512);
        shooterLeftMotor.set(ControlMode.Velocity, speed); // Speed in units / 100ms for value
    }

    public void fireFromConstants(double distance, double correction) {
        System.out.println("1");
        double motorVelocity = calculateFinalMotorVelocity(distance, correction);
        System.out.println(motorVelocity);
        spinAtVelocity(motorVelocity);
        System.out.println("2");
    }

    public void stopMotor() {
        System.out.println(getShooterVelocity());
        System.out.println(shooterLeftMotor.getClosedLoopTarget());
        shooterLeftMotor.stopMotor();
    }

}
