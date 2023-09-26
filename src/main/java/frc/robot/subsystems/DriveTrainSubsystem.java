package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrainSubsystem extends SubsystemBase {
    private final WPI_TalonSRX frontLeft = new WPI_TalonSRX(Constants.FRONT_LEFT_DRIVE_ID);
    private final WPI_TalonSRX frontRight = new WPI_TalonSRX(Constants.FRONT_RIGHT_DRIVE_ID);
    private final WPI_TalonSRX backLeft = new WPI_TalonSRX(Constants.BACK_LEFT_DRIVE_ID);
    private final WPI_TalonSRX backRight = new WPI_TalonSRX(Constants.BACK_RIGHT_DRIVE_ID);
    private static DriveTrainSubsystem instance;

    public DriveTrainSubsystem() {
        if (instance == null) {
            instance = this;
        }
    }

    public static DriveTrainSubsystem getInstance() {
        if (instance == null) {
            instance = new DriveTrainSubsystem();
        }
        return instance;
    }

}
