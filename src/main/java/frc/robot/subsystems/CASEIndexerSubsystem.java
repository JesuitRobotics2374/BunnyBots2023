package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
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
    public boolean cycle = false;
    public boolean override = false;
    private static Debouncer debouncer = new Debouncer(.1, DebounceType.kRising);

    public CASEIndexerSubsystem(){
        instance = this;
        motor.configFactoryDefault();
        motor.setInverted(true);
        motor.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    public void periodic() {
        if(debouncer.calculate(sensor.get()) && cycle) {
            motor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
        } else if (!debouncer.calculate(sensor.get()) && shoot) {
            motor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
            ShooterSubsystem.getInstance().fireFromConstants(ShooterSubsystem.getInstance().fireDistance, 1.06);
        } else if (!override) {
            motor.stopMotor();
        }
        
        if (debouncer.calculate(sensor.get()) && shoot){
            shoot = false;
            ShooterSubsystem.getInstance().stopMotor();
        }
    }

    public void shoot() {
        shoot = true;
    }

    public void cycle() {
        cycle = true;
    }

    public void noCycle() {
        cycle = false;
    }

    public void cycleIndexers() {
        override = true;
        motor.set(Constants.INDEXER_MOTOR_SPIN_SPEED);
    }

    public void stopCycleIndexers() {
        override = false;
        motor.stopMotor();
    }
}