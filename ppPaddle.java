package ppPackage;

// The following code contains lines from the assignment handouts written by Professor Ferrie and from Katrina Poulin's tutorial session.

import acm.graphics.GPoint;
import acm.graphics.GRect;

import static ppPackage.ppSimParams.*;

import java.awt.Color;

import acm.program.GraphicsProgram;

/**
 * The following class creates an instance of a paddle on the right hand side
 * of the simulation.
 *
 * @author jzhao
 */

public class ppPaddle extends Thread {

    double X;
    double Y;
    double Vx;
    double Vy;
    GRect RPaddle;
    GraphicsProgram GProgram;
    ppTable myTable;
    Color myColor;

    public ppPaddle(double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram) {
        // code to create GRec-the paddle-and adding it to the display
        this.X = X;
        this.Y = Y;
        this.myTable = myTable;
        this.GProgram = GProgram;
        this.myColor = myColor;

        //world coordinates

        double upperLeftX = X - ppPaddleW / 2;
        double upperLeftY = Y + ppPaddleH / 2;

        // p is in  screen coordinates

        GPoint p = myTable.W2S(new GPoint(upperLeftX, upperLeftY));

        double ScrX = p.getX();
        double ScrY = p.getY();

        this.RPaddle = new GRect(ScrX, ScrY, ppPaddleW * Xs, ppPaddleH * Ys);
        RPaddle.setFilled(true);
        RPaddle.setColor(myColor);
        GProgram.add(RPaddle);
    }


    public void run() {

        double lastX = X;
        double lastY = Y;

        while (true) {
            Vx = (X - lastX) / TICK;
            Vy = (Y - lastY) / TICK;
            lastX = X;
            lastY = Y;
            GProgram.pause(TICK * TSCALE);
        }
    }

    /**
     * getP() method that returns the X and Y coordinates of the paddle
     *
     * @return
     */

    public GPoint getP() {

        return new GPoint(X, Y);
    }

    /**
     * Method that sets and moves the paddle to a location (X,Y)
     *
     * @param P
     */
    public void setP(GPoint P) {

        //update instance variables

        this.X = P.getX();        // world coordinates
        this.Y = P.getY();


        //world coordinates

        double upperLeftX = X - ppPaddleW / 2;
        double upperLeftY = Y + ppPaddleH / 2;

        // p is in  screen coordinates

        GPoint p = myTable.W2S(new GPoint(upperLeftX, upperLeftY));

        //screen
        double ScrX = p.getX();
        double ScrY = p.getY();

        // move the GRect instance

        this.RPaddle.setLocation(ScrX, ScrY);


    }

    /**
     * @return returns the X, Y velocity of the paddle
     */
    public GPoint getV() {

        return new GPoint(Vx, Vy);

    }

    /**
     * Method that evaluates to true if the ball comes into contact with the paddle and false otherwise.
     *
     * @param Sx current X position of the ball in world coordinates
     * @param Sy current Y position of the ball in world coordinates
     * @return
     */
    public boolean contact(double Sx, double Sy) {

        return ((Sy >= Y - ppPaddleH / 2 && Sy <= Y + ppPaddleH / 2));            // true if ballY is in the paddleY range

        // called when X + Xo >= myPaddle.getP().getX()

    }

    /**
     * Gets the direction of the velocity along Y of the paddle.
     *
     * @return
     */
    public double getSgnVy() {
        if (Vy < 0)
            return -1;    // return -1 when Vy < 0
        else
            return 1;    // return 1 when Vy >=0
    }

}

