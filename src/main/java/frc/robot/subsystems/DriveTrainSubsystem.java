package frc.robot.subsystems;

import java.lang.Math;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;



public class DriveTrainSubsystem extends SubsystemBase {
    public final WPI_TalonSRX frontLeft = new WPI_TalonSRX(Constants.FRONT_LEFT_DRIVE_ID);
    public final WPI_TalonSRX frontRight = new WPI_TalonSRX(Constants.FRONT_RIGHT_DRIVE_ID);
    private final WPI_TalonSRX backLeft = new WPI_TalonSRX(Constants.BACK_LEFT_DRIVE_ID);
    private final WPI_TalonSRX backRight = new WPI_TalonSRX(Constants.BACK_RIGHT_DRIVE_ID);
    private final DifferentialDrive diffDrive;
    private final MotorControllerGroup leftGroup;
    private final MotorControllerGroup rightGroup;
    private final AHRS navX = new AHRS();
    private PIDController turnController = new PIDController(1 / 180, 1 / 200, -1 / 200);
    private static DriveTrainSubsystem instance;
    private double target = 0;
    private double getCurrentAngle;
    private final DigitalInput d0;
   
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
        d0 = new DigitalInput(0);
        tab.addBoolean("input 1", this::getd0state);
    }
    public boolean getd0state(){
        return !d0.get();
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

    /*public void driv_distance(int revNum) {
        int p = revNum * 4096;

        //double leftCurrent = frontLeft.getSelectedSensorPosition();
        double rightCurrent = frontRight.getSelectedSensorPosition();
        //Double leftTarget = leftCurrent += p;
        Double rightTarget= rightCurrent += p;
        System.out.println("Inside drive distance: ");
        System.out.println("Current: " + rightCurrent + " Target: " + rightTarget);

        while(true){
        System.out.println(rightCurrent);//should be left current
       // System.out.println(leftTarget);
        //drive(.5, .5);
        //leftCurrent = frontLeft.getSelectedSensorPosition();
        rightCurrent = frontRight.getSelectedSensorPosition();
        /*if(leftCurrent > leftTarget){
        return;
        }
        if(rightCurrent > rightTarget){
        return;
        }*/
    }

    }

    /*
     * for(int i = 0; i <= revNum; i++){
     * while(p != revNum) {
     * final double c = 17.5;
     * revNum = revNum/4096;
     * revNum *= c;
     * p = revNum;
     * 
     */

    public void printSensor() {
        System.out.println(frontLeft.getSelectedSensorPosition());
    }

    public double getCurrentAngle() {
        return navX.getAngle();
    }

    public void updateTurnTarget(double turnAmmount) {
        double currentAngle = getCurrentAngle();
        if(currentAngle < 0){
            System.out.println(currentAngle);
        }
        double targetAngle = currentAngle + turnAmmount;
        System.out.println(currentAngle);
        System.out.println(targetAngle);
        if(targetAngle >=360){
            targetAngle = targetAngle -360;
        }
        target = targetAngle;
        Turn();


        //turnController.setSetpoint(targetAngle);


    }

    public void Turn() {
        double CA = getCurrentAngle(); 

        if (target < CA) {
           drive(.3, -.3);
        } else {
           drive(-.3, .3);
        }

        double delta = target - CA;
        //delta = Math.abs(delta);  
        System.out.println(delta);
        if ( delta < 0.3)  {
            target = CA;
        }
    }

    public boolean atSetPoint() {
        return turnController.atSetpoint();
    }
}
