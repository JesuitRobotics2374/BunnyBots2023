package frc.robot.subsystems;

import java.util.LinkedList;

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
    public LinkedList<Double> xPos = new LinkedList<>(); // PRELOAD WITH NaN
    public LinkedList<Double> yPos = new LinkedList<>(); // PRELOAD WITH NaN
    public boolean willHit = false;
    public static ShooterSubsystem instance;
    // public Limelight limelight = new Limelight();

    public ShooterSubsystem() {
        // shooterRightMotor.setInverted(true);
        for (int i = 0; i < 5; i++) {
            xPos.add(Double.NaN);
            yPos.add(Double.NaN);
        }
        instance = this;
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
        // tab.addDouble("X", () -> getX());
        // tab.addDouble("Y", () -> getY());
    }

    @Override
    public void periodic() {
        // updateFireTruths();
    }

    public void shootFromLimeLight() {
        // Input from limelight
        double phi = 0;

        fireFromConstants((Constants.DELTA_HEIGHT * Math.sin(90 - phi) / Math.sin(phi)), 1.06);
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


    /// AUTOMATIC TARGETING ///

    // public double getX() {
    //     return limelight.getX();
    // }

    // public double getY() {
    //     return limelight.getY();
    // }

    // public void updateFireTruths() {
    //     double currentX = limelight.getX(); //TODO get from limelight
    //     double currentY = limelight.getY(); //TODO get from limelight
    //     xPos.addFirst(currentX);
    //     yPos.addFirst(currentY);
    //     xPos.removeLast();
    //     yPos.removeLast();
    //     double[] xVelArray = new double[4];
    //     double[] yVelArray = new double[4];
    //     int xTot = 0;
    //     int yTot = 0;
    //     for (int i = 0; i < 4; i++) {
    //         if (!Double.isNaN(xPos.get(i)) && !Double.isNaN(xPos.get(i+1))) {
    //             xVelArray[i] = xPos.get(i) - xPos.get(i+1);
    //             xTot++;
    //         } else {
    //             xVelArray[i] = Double.NaN;
    //         }
    //         if (!Double.isNaN(yPos.get(i)) && !Double.isNaN(yPos.get(i+1))) {
    //             yVelArray[i] = yPos.get(i) - yPos.get(i+1);
    //             yTot++;
    //         } else {
    //             yVelArray[i] = Double.NaN;
    //         }
    //     }
    //     double xVel = 0;
    //     double yVel = 0;
    //     if (xTot != 0) {
    //         for (int i = 0; i < 4; i++) {
    //             if (!Double.isNaN(xVelArray[i])) {
    //                 xVel+=xVelArray[i];
    //             }
    //         }
    //         xVel /= xTot;
    //     } else {
    //         willHit = false;
    //         return;
    //     }
    //     if (yTot != 0) {
    //         for (int i = 0; i < 4; i++) {
    //             if (!Double.isNaN(yVelArray[i])) {
    //                 yVel+=yVelArray[i];
    //             }
    //         }
    //         yVel /= yTot;
    //     } else {
    //         willHit = false;
    //         return;
    //     }
    //     Double time = -currentY/yVel - Constants.INDEXER_TO_SHOOTER_TRAVEL_TIME_TARS;
    //     Double initialV = Math.pow(currentX + time*xVel,1.06)/(time*Math.cos(Constants.SHOOTER_RELEASE_ANGLE));
    //     willHit = Math.abs((initialV * time * Math.sin(Constants.SHOOTER_RELEASE_ANGLE) - 9.81/2 * time*time) - Constants.DELTA_HEIGHT) < .3;
    //     if (willHit) {
    //         IndexerSubsystem.getInstance().shootNoFire();
    //         shooterLeftMotor.set(ControlMode.Velocity, initialV * Constants.MOTOR_VELOCITY_CONVERSION);
    //     }
    // }

    public static ShooterSubsystem getInstance() {
        if (instance == null) {
            instance = new ShooterSubsystem();
        }
        return instance;
    }
}
