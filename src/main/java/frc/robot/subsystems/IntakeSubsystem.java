package frc.robot.subsystems;


import java.lang.Math;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;



public class IntakeSubsystem extends SubsystemBase {
    /*
     * 
     */
    public final WPI_TalonSRX indexer = new WPI_TalonSRX(Constants.INTAKE_MOTOR_ONE);
    //public void buttonDown();
};
