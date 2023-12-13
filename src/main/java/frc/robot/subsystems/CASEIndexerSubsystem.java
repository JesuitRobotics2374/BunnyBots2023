package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CASEIndexerSubsystem extends SubsystemBase {

    private WPI_TalonFX motor = new WPI_TalonFX(Constants.CASE_INDEXER_MOTOR); 
    private DigitalInput sensor = new DigitalInput(0);
    public static CASEIndexerSubsystem instance; 
    public boolean shoot = false;
    private static Debouncer debouncer = new Debouncer(.1, DebounceType.kFalling);

    public CASEIndexerSubsystem(){
        instance = this;
    }

    @Override
    public void periodic() {
        if(debouncer.calculate(sensor.get()) || shoot){
            motor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
        } else {
            motor.stopMotor();
        }
        
        if (debouncer.calculate(sensor.get()) && shoot){
            shoot = false;
        }
    }

    public void shoot() {
        shoot = true;
    }
}