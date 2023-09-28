package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
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

}
