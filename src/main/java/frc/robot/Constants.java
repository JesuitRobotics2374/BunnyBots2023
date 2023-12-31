package frc.robot;

public class Constants {
    public static final String CAN_BUS_NAME_CANIVORE = "FastFD";
    public static final String CAN_BUS_NAME_ROBORIO = "rio";
    public static final String CAN_BUS_NAME_DRIVETRAIN = CAN_BUS_NAME_CANIVORE;

    // IO Controller definitions
    public static final int CONTROLLER_USB_PORT_DRIVER = 0; // Drivers Controller
    public static final int CONTROLLER_USB_PORT_OPERATOR = 1; // Ordanence operators controller

    // Things (doesnt compile without these, but I don't think they actually do
    // anything)
    public static final double WHEEL_RADIUS_IN = 2;
    public static final double WHEEL_GEAR_RATIO = 0;
    public static final double SIM_SAMPLE_RATE_SEC = 0.001;
    public static final double Ts = 0.02;

    // Drivetrain Motor IDs
    public static final int FRONT_LEFT_DRIVE_ID = 3;
    public static final int FRONT_RIGHT_DRIVE_ID = 4;
    public static final int BACK_LEFT_DRIVE_ID = 1;
    public static final int BACK_RIGHT_DRIVE_ID = 2;

    // Robotcontainer things
    public static final double DEADBAND = 0.1;
    public static final double SPEED_MULTIPLIER = .8;
    public static final int PIGEON_ID = 0;
    public static final String DRIVER_READOUT_TAB_NAME = "Driver Readout";

    // Shooter Subclass
    public static final int SHOOTER_LEFT_MOTOR_CAN_ID = 7;
    public static final int SHOOTER_RIGHT_MOTOR_CAN_ID = 8;

    public static final double SHOOTER_GROUND_HEIGHT = 0; // TBD
    public static final double SHOOTER_RELEASE_ANGLE = Math.PI / 3; // TBD
    public static final double DRAG_COEFFICIENT = 0.1; // TBD

    public static final double SHOOTER_KP = .25;
    public static final double SHOOTER_KI = .00025;
    public static final double SHOOTER_KD = .0;

    public static final double DELTA_HEIGHT = 0.7;
    public static final double MOTOR_VELOCITY_CONVERSION = 2048 / (Math.PI * 2 * 0.0508 * 10);

    public static final double[] weightValues = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; //TODO

    // Limelight

    public static final double LIMELIGHT_GROUND_HEIGHT = 0; // TBD
    public static final double LIMELIGHT_PITCH = 0;

    // Indexer Subclass

    public static final int INDEXER_MOTOR_TWO = 6;
    public static final int INDEXER_MOTOR_ONE = 5;
    public static final double INDEXER_MOTOR_SPIN_SPEED = 0.35;
    public static final double INDEXER_TO_SHOOTER_TRAVEL_TIME_TARS = 0;

    // Intake subclass
    public static final int INTAKE_MOTOR_ONE = 7;
    public static final int CASE_INDEXER_MOTOR = 0;

}
