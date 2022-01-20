package ppPackage;

//The following code contains lines from the assignment handouts written by Professor Ferrie and from Katrina Poulin's tutorial session.

import static ppPackage.ppSimParams.*;

import java.awt.Color;

import javax.swing.JToggleButton;

import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

/**
 * @author jzhao
 * <p>
 * ppBall class extends the Thread class allowing multiple thread objects to run simultaneously. It simulates an instance of
 * a red ping-pong ball launching from the left wall, with potential collisions against the ground and a moving paddle on the right.
 */

public class ppBall extends Thread {

    //Instance variables
    double Xinit;                // Initial position of ball - X
    double Yinit;                // Initial position of ball - Y
    double Vo;                    // Initial velocity (magnitude)
    double theta;                // Initial direction
    double loss;                // Energy loss upon collision
    Color color;                // Color ball
    ppTable myTable;            // Instance of ppTable
    GraphicsProgram GProgram;    // Instance of ppSim class (this)
    GOval myBall;                        // Graphics object representing ball
    double Vx, Vox;
    double Vy;
    double X, Xo, Y, Yo;
    ppPaddle RPaddle;            // Instance of ppPaddle
    ppPaddle LPaddle;
    boolean running;

    // absolute X position is X + Xo, same for absolute Y


    /**
     * The constructor for the ppBall class copies parameters to the above instance variables, creates an instance of a GOval to represent
     * the ping-pong ball, and adds it to the display.
     * The myBall within the ppBall constructor is set to a new GOval object with precise parameters, creating the balls we need to run our simulation
     * in the ppSim class upon creating a new myBall object.
     *
     * @param Xinit    // Initial position of ball - X
     * @param Yinit    // Initial position of ball - Y
     * @param Vo       // Initial velocity (magnitude)
     * @param theta    // Initial direction
     * @param loss     // Energy loss upon collision
     * @param color    // Color ball
     * @param myTable  // Instance of ppTable class
     * @param GProgram // Instance of ppSim class (this)
     */

    public ppBall(double Xinit, double Yinit, double Vo, double theta, double loss, Color color, ppTable myTable, GraphicsProgram GProgram) {

        this.Xinit = Xinit;                                    // Copy constructor parameters to instance variables
        this.Yinit = Yinit;
        this.Vo = Vo;
        this.theta = theta;
        this.loss = loss;
        this.color = color;
        this.myTable = myTable;
        this.GProgram = GProgram;


    }

    /**
     * In a thread, the run method is NOT started automatically.
     * Instead, a start message must be sent to each instance of the ppBall class, e.g.,
     * ppBall myBall = new ppBall(--parameters);
     * myBall.start();
     * The body of the run method is essentially the simulator code in A1.
     */

    public void run() {

        // Create ball instance

        GPoint p = myTable.W2S(new GPoint(Xinit - bSize, Yinit + bSize));
        double ScrX = p.getX();
        double ScrY = p.getY();
        myBall = new GOval(ScrX, ScrY, 2 * bSize * Xs, 2 * bSize * Ys);
        myBall.setColor(color);
        myBall.setFilled(true);
        this.GProgram.add(myBall);

        // Initialize program variables

        Xo = Xinit;                                            // Set initial X position
        Yo = Yinit;                                            // Set initial Y position
        double time = 0;                                    // Time starts at 0 and counts up
        double Vt = bMass * g / (4 * Pi * bSize * bSize * k);            // Terminal velocity
        Vox = Vo * Math.cos(theta * Pi / 180);                    // X component of velocity
        double Voy = Vo * Math.sin(theta * Pi / 180);                // Y component of velocity


        // Simulation loop. Calculate position and velocity, print, increment
        // time. Do this until ball hits the ground.

        running = true;                                    // Initial state = running.

        // X and Y are relative to the initial starting position Xo, Yo.
        // So the absolute position is Xabs= X + Xo and Yabs = Y + Yo.
        // Print out header line for the displayed values.

        //	System.out.printf("\t\t\t Ball Position and Velocity\n");

        while (running) {
            X = Vox * Vt / g * (1 - Math.exp(-g * time / Vt));
            // Update relative position
            Y = Vt / g * (Voy + Vt) * (1 - Math.exp(-g * time / Vt)) - Vt * time;
            Vx = Vox * Math.exp(-g * time / Vt);
            // Update velocity
            Vy = (Voy + Vt) * Math.exp(-g * time / Vt) - Vt;

            //Display current values (1 time/second)
            if (MESG) {
                System.out.printf("t: %.2f\t\t X: %.2f\t Y: %.2f\t Vx: %2f\t Vy: %.2f\n",
                        time, X + Xo, Y + Yo, Vx, Vy);
            }
            GProgram.pause(TICK * 1000);
            //Pause program for SLEEP ms


            //Check for collision

            double KEx = 0.5 * bMass * Vx * Vx;
            double KEy = 0.5 * bMass * Vy * Vy;
            double PE = bMass * g * (Y + Yo);                                                                        // collision with floor case

            if (Vy < 0 && Yo + Y <= bSize) {

                KEx = 0.5 * bMass * Vx * Vx * (1 - loss);
                KEy = 0.5 * bMass * Vy * Vy * (1 - loss);
                PE = 0;

                Vox = Math.sqrt(2 * KEx / bMass);
                Voy = Math.sqrt(2 * KEy / bMass);

                if (Vx < 0) Vox = -Vox;

                time = 0;
                Xo += X;
                Yo = bSize;
                X = 0;
                Y = 0;

            }

            if (Vx > 0 && Xo + X >= (RPaddle.getP().getX() - ppPaddleW / 2 - bSize)) {    // collision with RPaddle
                //possible collision

                if (RPaddle.contact(X + Xo, Y + Yo)) {

                    KEx = 0.5 * bMass * Vx * Vx * (1 - loss);
                    KEy = 0.5 * bMass * Vy * Vy * (1 - loss);
                    PE = bMass * g * Y;

                    Vox = -Math.sqrt(2 * KEx / bMass);
                    Voy = Math.sqrt(2 * KEy / bMass);

                    Vox = Vox * ppPaddleXgain;                                            // Scale X component of velocity
                    Voy = Voy * ppPaddleYgain * RPaddle.getV().getY();                        // Scale Y + same direction as paddle

                    time = 0;
                    Xo = RPaddle.getP().getX() - ppPaddleW / 2 - bSize;
                    Yo += Y;
                    X = 0;
                    Y = 0;

                }
                if (Xo + X > (RPaddle.getP().getX() + ppPaddleW + bSize)) {            // simulation ends when the ball goes past the RPaddle
                    break;
                }
            }

            if (Vx < 0 && Xo + X <= (LPaddle.getP().getX() + ppPaddleW / 2 + bSize)) {                                        // collision with LPaddle

                if (LPaddle.contact(X + Xo, Y + Yo)) {

                    KEx = 0.5 * bMass * Vx * Vx * (1 - loss);
                    KEy = 0.5 * bMass * Vy * Vy * (1 - loss);
                    PE = bMass * g * (Y + Yo);

                    Vox = Math.sqrt(2 * KEx / bMass);
                    Voy = Math.sqrt(2 * KEy / bMass);

                    Vox = Vox * ppPaddleXgain;
                    Voy = Voy * ppPaddleYgain * LPaddle.getV().getY();

                    if (Vy < 0) Voy = -Voy;

                    time = 0;
                    Xo = LPaddle.getP().getX() + ppPaddleW / 2 + bSize;
                    Yo += Y;
                    X = 0;
                    Y = 0;
                }
            }
            if (Xo + X < (LPaddle.getP().getX() - ppPaddleW - bSize)) {            // simulation ends when the ball goes past the LPaddle
                break;
            }

            if ((KEx + KEy + PE) < ETHR || Y + Yo > Ymax) kill();

            if (Vox > VoxMAX) {
                Vox = VoxMAX;
            }


            p = myTable.W2S(new GPoint(Xo + X - bSize, Yo + Y + bSize));        //Get current position in screen coordinates
            ScrX = p.getX();
            ScrY = p.getY();
            myBall.setLocation(ScrX, ScrY);

            time += TICK;

            GProgram.pause(getCurrentTimeMultiplier() * TICK * TSCALE);

            if (traceButton.isSelected()) trace(ScrX, ScrY);

            scoreboardCounter();

        }
    }

    /**
     * A method to plot a dot at the current location in screen coordinates
     *
     * @param ScrX
     * @param ScrY
     */

    public void trace(double ScrX, double ScrY) {

        GOval trace = new GOval(ScrX + bSize * Xs, ScrY + bSize * Ys, PD, PD);
        trace.setColor(Color.BLACK);
        trace.setFilled(true);
        GProgram.add(trace);

    }


    public void setRightPaddle(ppPaddle RPaddle) {
        this.RPaddle = RPaddle;

    }

    public void setLeftPaddle(ppPaddle LPaddle) {
        this.LPaddle = LPaddle;
    }

    public GPoint getP() {

        return new GPoint(X + Xo, Y + Yo);
        // absolute position of the ball
    }

    public GPoint getV() {
        // return velocity of the ball in GPoint object
        return new GPoint(Vx, Vy);
    }

    void kill() {

        running = false;

        // terminate simulation
        // can use the running boolean

    }

    public void scoreboardCounter() {

        if ((Xo + X > (RPaddle.getP().getX() - ppPaddleW / 2 - bSize) && Vx > 0) || (Yo + Y > Ymax && Vx < 0)) {    // Agent gets points whenever the ball goes past the RPaddle or whenever the ball
            // goes out of bounds with a negative Vx.
            agentPoints++;
            agentScore.setText("Agent = " + agentPoints);

        }

        if ((Xo + X < (LPaddle.getP().getX() + ppPaddleW / 2 + bSize) && Vx < 0) || (Yo + Y > Ymax && Vx > 0)) {

            playerPoints++;
            playerScore.setText("Human = " + playerPoints);
        }

    }

    /**
     * This method returns the value of timeSlider.
     *
     * @return Value of timeSlider
     */
    public double getCurrentTimeMultiplier() {
        return timeSlider.getValue();
    }

}



