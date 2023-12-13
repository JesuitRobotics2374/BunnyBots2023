package frc.robot.util;

import org.opencv.features2d.BRISK;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;


public class Limelight {

    private NetworkTable table;

    private NetworkTableEntry tx;
    private NetworkTableEntry ty;
    private NetworkTableEntry ta;
    private NetworkTableEntry tv;
    private NetworkTableEntry ts;
    private NetworkTableEntry tl;


    private NetworkTableEntry[] tas; 
    private NetworkTableEntry[] thors; 
    private NetworkTableEntry[] tys; 
	
	public enum LightMode {
        DEFAULT(0), OFF(1), BLINK(2), ON(3);

        private final int ledMode;

        private LightMode(int ledMode) {
            this.ledMode = ledMode;
        }

        /**
         * Get the current LED Mode
         *
         * @return the LED mode as int
         */
        public int getLedMode() {
            return ledMode;
        }

        public enum StreamMode {
            // Side by side, Secondary camera is placed in lower right, Primary camera is
            // placed in lower right
            STANDARD(0), MAIN(1), SECONDARY(2);

            private final int mode;

            private StreamMode(int mode) {
                this.mode = mode;
            }

            /**
             * Get the Stream mode
             *
             * @return The Stream Mode as an int
             */
            public int getMode() {
                return mode;
            }
        }

        public enum CamMode {
            // Camera feed uses vision processing, camera feed increases exposure and
            // disables vision processing
            VISION(0), DRIVER(1);

            private int mode;

            private CamMode(int mode) {
                this.mode = mode;
            }

            /**
             * Get the Camera mode
             *
             * @return The Cam Mode as an int
             */
            public int getMode() {
                return mode;
            }
        }
    }

    /**
     * Lime Light Driver Singleton
     */
    public Limelight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");

        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        tv = table.getEntry("tv");
        ts = table.getEntry("ts");
        tl = table.getEntry("tl");
        ShuffleboardTab tab = Shuffleboard.getTab(Constants.DRIVER_READOUT_TAB_NAME);
        tab.addDouble("LR Angle", () -> getXAngle());
        tab.addDouble("UD Angle", () -> getYAngle());

		tas = new NetworkTableEntry[]{table.getEntry("ta0"), table.getEntry("ta1"), table.getEntry("ta2"), table.getEntry("ta3"), table.getEntry("ta4")};
    	thors = new NetworkTableEntry[]{table.getEntry("thor0"), table.getEntry("thor1"), table.getEntry("thor2"), table.getEntry("thor3"), table.getEntry("thor4")};
		tys = new NetworkTableEntry[]{table.getEntry("ty0"), table.getEntry("ty1"), table.getEntry("ty2"), table.getEntry("ty3"), table.getEntry("ty4")};
	}

    /**
     * ALWAYS USE HAS TARGET BEFORE CALLING
     * @return Whether the limelight's target is valid team
     */
    public boolean validateTarget(boolean isTeamRed) {
        if (!hasTarget()) return false;

        int bigRectangleIndex = -1;
        double maxArea = Double.MIN_VALUE;
        double acurr;
		int longRectangleIndex = -1;
        double maxHor = Double.MIN_VALUE;
		double hcurr;

        for (int i = 0; i < tas.length; i++) {
            acurr = tas[i].getDouble(Double.MIN_VALUE);
			hcurr = thors[i].getDouble(Double.MIN_VALUE);
			System.out.println(i + " " + acurr + " " + hcurr);
            if (acurr >= maxArea) {
                bigRectangleIndex = i;
                maxArea = acurr;
            }
            if (hcurr >= maxHor) {
                longRectangleIndex = i;
                maxHor = hcurr;
            }
        }

        //Error/False Values Detection
        if (longRectangleIndex != bigRectangleIndex) {
            System.out.println(bigRectangleIndex + " " + maxArea + " Limelight Reading Not Consistent. " + longRectangleIndex + " " + maxHor);
            return false;
        }

        if (isTeamRed && isIndexGreatestY(longRectangleIndex)) return true;
        else if (!isTeamRed && isIndexLeastY(longRectangleIndex)) return true;
        return false;
    }
    /**
     * Helper for validateTarget
     */
    private boolean isIndexGreatestY(int k){
        double value = tys[k].getDouble(0.0);
        for (int i = 0; i < tys.length; i++){
            if (i != k){
                double curr = tys[i].getDouble(Double.MIN_VALUE);
                if (curr == 0.0 || curr == Double.MIN_VALUE || curr > value) return false;
            }
        } return true;
    }

    /**
     * Helper for validateTarget
     */
    private boolean isIndexLeastY(int k){
        double value = tys[k].getDouble(0.0);
        for (int i = 0; i < tys.length; i++){
            if (i != k){
                double curr = tys[i].getDouble(Double.MAX_VALUE);
                if (curr == 0.0 || curr == Double.MAX_VALUE || curr < value) return false;
            }
        } return true;
    }

    /**
     * Are we currently tracking any potential targets
     *
     * @return Whether the limelight has any valid targets (0 or 1)
     */
    public boolean hasTarget() {
        return tv.getDouble(0.0) != 0;
    }

    /**
     * Horizontal offset from crosshair to target
     *
     * @return Horizontal Offset From Crosshair To Target (LL1: -27 degrees to 27
     *         degrees | LL2: -29.8 to 29.8 degrees)
     */
    public double getXAngle() {
        return tx.getDouble(Double.NaN);
    }

    public double getX() {
        if (getXAngle() == Double.NaN || getYAngle() == Double.NaN) 
            return Double.NaN;
        return Constants.DELTA_HEIGHT * Math.cos(Math.toRadians(getXAngle())) / Math.tan(Math.toRadians(getYAngle()));
    }

    public double getY() {
        if (getXAngle() == Double.NaN || getYAngle() == Double.NaN) 
            return Double.NaN;
        return Constants.DELTA_HEIGHT * Math.sin(Math.toRadians(getXAngle())) / Math.tan(Math.toRadians(getYAngle()));
    }

    /**
     * Vertical offset from crosshair to target
     *
     * @return Vertical Offset From Crosshair To Target (LL1: -20.5 degrees to 20.5
     *         degrees | LL2: -24.85 to 24.85 degrees)
     */
    public double getYAngle() {
        return ty.getDouble(Double.NaN);
    }

    /**
     * Get the area of the vision tracking box
     *
     * @return Target Area (0% of image to 100% of image)
     */
    public double getArea() {
        return ta.getDouble(0);
    }

    /**
     * Rotation of the object
     *
     * @return Skew or rotation (-90 degrees to 0 degrees)
     */
    public double getSkew() {
        return ts.getDouble(0);
    }

    /**
     * Latency in ms of the pipeline
     *
     * @return The pipeline’s latency contribution (ms) Add at least 11ms for image
     *         capture latency.
     */
    public double getDeltaTime() {
        return tl.getDouble(0);
    }

    /**
     * Returns the index of the current vision pipeline (0... 9)
     *
     * @return True active pipeline index of the camera (0 .. 9)
     */
    public int getPipeIndex() {
        return (int) table.getEntry("getpipe").getDouble(0);
    }

    /**
     * Sets the limelights current pipeline
     *
     * @param pipeline The pipeline index (0-9)
     */
    public void setPipe(int pipeline) {
        table.getEntry("pipeline").setNumber(pipeline);
    }

    /**
     * Get the distance of the limelight target
     *
     * @param h1 - The height of the limelight with respect to the floor
     * @param h2 - The height of the target
     * @param a1 - The mounting angle for the limelight
     * @param a2 - The angle between the limelight angle and the target
     *
     * @return The distance between the robot and the limelight
     */
    public double getDistance(double h1, double h2, double a1, double a2) {
        return (h2 - h1) / Math.abs(Math.tan(Math.toRadians(a1) + Math.toRadians(a2)));
    }

    //Extra Necessary Methods



    /**
     * Output diagnostics
     */
    public void outputTelemetry() {
        SmartDashboard.putBoolean("HasTarget", hasTarget());
        SmartDashboard.putNumber("Horizontal Offset", getXAngle());
        SmartDashboard.putNumber("Vertical Offset", getYAngle());
        SmartDashboard.putNumber("Area", getArea());
        SmartDashboard.putNumber("Skew", getSkew());
    }

}