package ppPackage;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

//The following code contains lines from the assignment handouts written by Professor Ferrie and from Katrina Poulin's tutorial session.

/**
 * @author jzhao
 * Global constants used by all classes.
 * Contains the parameters that define the screen dimensions of the ping-pong table and the simulation parameters with the ball and its
 * trace point diameter and the tick increment at each iteration. Additionally, paddle parameters and parameters used by the ppSim class are listed.
 */

public class ppSimParams {

    // Screen Dimensions (pixels, acm coordinates)

    public static final int WIDTH = 1280;            //defines the width of the screen in pixels
    public static final int HEIGHT = 600;            //distance from top of screen to ground plane
    public static final int OFFSET = 200;            //distance from bottom of screen to ground plane

    // Ping pong table parameters

    public static final double ppTableXlen = 2.74;    // Length
    public static final double ppTableHgt = 1.52;    // Ceiling
    public static final double XwallL = 0.05;        // Position of left wall
    public static final double XwallR = 2.69;        // Position of right wall

    // Parameters defined in simulation coordinates

    public static final double g = 9.8;                // gravitational constant
    public static final double k = 0.1316;            // air friction
    public static final double Pi = 3.1416;            // Pi to 4 places
    public static final double bSize = 0.02;        // Radius of the ball (m)
    public static final double bMass = 0.0027;        // Mass of the ball (kg)
    public static final double ETHR = 0.001;        ///Minimum ball energy
    public static final double Xmin = 0.0;            // Minimum value of X
    public static final double Xmax = ppTableXlen;    // Maximum value of X
    public static final double Ymin = 0.0;            // Minimum value of Y
    public static final double Ymax = ppTableHgt;    // Maximum value of Y
    public static final int xmin = 0;                // Minimum value of x
    public static final int xmax = WIDTH;            // Maximum value of x
    public static final int ymin = 0;                // Minimum value of y
    public static final int ymax = HEIGHT;            // Maximum value of y
    public static final double Xs = (xmax - xmin) / (Xmax - Xmin);    // S.F. X
    public static final double Ys = (ymax - ymin) / (Ymax - Ymin);    // S.F. Y
    public static final double Xinit = XwallL;        // Initial X position of ball
    public static final double Yinit = Ymax / 2;        // Initial Y position of ball
    public static final double PD = 1;                // Trace point diameter
    public static final double TICK = 0.01;            // Clock tick duration (sec)
    public static final double TSCALE = 1500;        // Scaling parameter for pause()

    // Player paddle parameters

    static final double ppPaddleH = 8 * 2.54 / 100;        // Paddle height
    static final double ppPaddleW = 0.5 * 2.54 / 100;    // Paddle width
    static final double ppPaddleXinit = XwallR - ppPaddleW / 2; // Initial Paddle X
    static final double ppPaddleYinit = Yinit;        // Initial paddle Y
    static final double ppPaddleXgain = 2.0;        // Vx gain on paddle hit
    static final double ppPaddleYgain = 1.5;        // Vy gain on paddle hit

    // Agent paddle parameters

    static final double LPaddleXinit = XwallL - ppPaddleW / 2;  // Initial X position of Agent paddle
    static final double LPaddleYinit = Yinit;        //  Initial Agent paddle Y
    static final double LPaddleXgain = 2.0;        // Vx gain on Agent paddle hit
    static final double LPaddleYgain = 1.5;        // Vy gain on Agent paddle hit
    // Parameters used by the ppSim class

    static final double YinitMAX = 0.75 * Ymax;        // Max initial height at 75% of range
    static final double YinitMIN = 0.25 * Ymax;        // Min initial height at 25% of range
    static final double EMIN = 0.2;                    // Minimum loss coefficient
    static final double EMAX = 0.2;                    // Maximum loss coefficient
    static final double VoMIN = 5.0;                // Minimum velocity
    static final double VoMAX = 5.0;                // Maximum velocity
    static final double VoxMAX = 6.0;
    static final double ThetaMIN = 0.0;                // Minimum launch angle
    static final double ThetaMAX = 20.0;            // Maximum launch angle
    static final long RSEED = 8976232;                // Random number gen. seed value

    // Global variables for bonus features

    public static JToggleButton traceButton;        // Toggle button for turning on/off the ball's tracing
    public static int agentPoints, playerPoints;    // Agent and Player points
    public static JLabel agentScore, playerScore;    // Labels for the Agent's and the player's score
    public static JSlider timeSlider;                // Slider that changes the playing speed
    public static JSlider lagSlider;                // Slider that changes the Agent paddle's lag


    // miscellaneous

    public static final boolean DEBUG = false;        // Debug msg. and single step if true
    public static final boolean MESG = false;        // Enable status messages on console
    public static final int STARTDELAY = 100;        // Delay between setup and start

}


