package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrainSubsystem extends SubsystemBase {
    private final WPI_TalonSRX frontLeft = new WPI_TalonSRX(Constants.FRONT_LEFT_DRIVE_ID);
    private final WPI_TalonSRX frontRight = new WPI_TalonSRX(Constants.FRONT_RIGHT_DRIVE_ID);
    private final WPI_TalonSRX backLeft = new WPI_TalonSRX(Constants.BACK_LEFT_DRIVE_ID);
    private final WPI_TalonSRX backRight = new WPI_TalonSRX(Constants.BACK_RIGHT_DRIVE_ID);
    private final DifferentialDrive diffDrive;
    private final MotorControllerGroup leftGroup;
    private final MotorControllerGroup rightGroup;
    private final AHRS navX = new AHRS();
    private PIDController turnController = new PIDController(1 / 180, 1 / 200, -1 / 200);
    private static DriveTrainSubsystem instance;

    public DriveTrainSubsystem() {
        if (instance == null) {
            instance = this;
        }
        leftGroup = new MotorControllerGroup(backLeft, frontLeft);
        leftGroup.setInverted(true);
        rightGroup = new MotorControllerGroup(backRight, frontRight);
        frontLeft.setNeutralMode(NeutralMode.Brake);
        frontRight.setNeutralMode(NeutralMode.Brake);
        backRight.setNeutralMode(NeutralMode.Brake);
        backLeft.setNeutralMode(NeutralMode.Brake);
        diffDrive = new DifferentialDrive(leftGroup, rightGroup);
        turnController.enableContinuousInput(-180, 180);
        turnController.setTolerance(5, 5);
        ShuffleboardTab tab = Shuffleboard.getTab(Constants.DRIVER_READOUT_TAB_NAME);
        tab.addNumber("Angle", this::getCurrentAngle);
    }

    public void drive(double left, double right) {
        diffDrive.tankDrive(left, right);
    }

    public static DriveTrainSubsystem getInstance() {
        if (instance == null) {
            instance = new DriveTrainSubsystem();
        }
        return instance;
    }

    public void printSensor() {
        System.out.println(frontLeft.getSelectedSensorPosition());
    }

    public double getCurrentAngle() {
        return navX.getAngle();
    }

    public void updateTurnTarget(double turnAmmount) {
        turnController.setSetpoint((getCurrentAngle() + turnAmmount + 180) % 360 - 180);
    }

    public void Turn() {
        if (!atSetPoint()) {
            double amount = turnController.calculate((getCurrentAngle() + 180) % 360 - 180);
            drive(amount, -amount);
        }
    }

    public boolean atSetPoint() {
        return turnController.atSetpoint();
    }

}
