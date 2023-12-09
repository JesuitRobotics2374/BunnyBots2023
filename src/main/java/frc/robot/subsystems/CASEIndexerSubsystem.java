package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CASEIndexerSubsystem extends SubsystemBase {

    private WPI_TalonFX motor = new WPI_TalonFX(Constants.CASE_INDEXER_MOTOR); 
    private DigitalInput sensor = new DigitalInput(0);
    public static CASEIndexerSubsystem instance; 
    public boolean shoot = false;

    public CASEIndexerSubsystem(){
        instance = this;
    }

    @Override
    public void periodic() { //TODO make truth table to see if optimization possible
        if(sensor.get() || shoot){
            motor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
        } else {
            motor.stopMotor();
        }
        
        if (sensor.get() && shoot){
            shoot = false;
        }
    }

    public void shoot() {
        shoot = true;
    }
}