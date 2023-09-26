package frc.robot;

public class Constants {
    public static final String CAN_BUS_NAME_CANIVORE = "FastFD";
    public static final String CAN_BUS_NAME_ROBORIO = "rio";
    public static final String CAN_BUS_NAME_DRIVETRAIN = CAN_BUS_NAME_CANIVORE;

    // IO Controller definitions
    public static final int CONTROLLER_USB_PORT_DRIVER = 0; // Drivers Controller
    public static final int CONTROLLER_USB_PORT_OPERATOR = 1; // Ordanence operators controller

    // Things
    public static final double WHEEL_RADIUS_IN = 2;
    public static final double WHEEL_GEAR_RATIO = 0;
    public static final double SIM_SAMPLE_RATE_SEC = 0.001;
    public static final double Ts = 0.02;

    // Drivetrain Motor IDs
    public static final int FRONT_LEFT_DRIVE_ID = 0;
    public static final int FRONT_RIGHT_DRIVE_ID = 0;
    public static final int BACK_LEFT_DRIVE_ID = 0;
    public static final int BACK_RIGHT_DRIVE_ID = 0;
}