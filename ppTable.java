package ppPackage;

//The following code contains lines from the assignment handouts written by Professor Ferrie and from Katrina Poulin's tutorial session.

import static ppPackage.ppSimParams.*;

import java.awt.Color;

import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

/**
 * The following is the ppTable class containing the ppTable constructor
 *
 * @author jzhao
 */

public class ppTable {

    ppBall myBall;
    GraphicsProgram GProgram;

    /**
     * ppTable constructor calls an instance of the GraphicsProgram class with GProgram and creates the ground plane
     * and the left wall of the simulation.
     *
     * @param GProgram // Instance of the GraphicsProgram class
     */
    public ppTable(GraphicsProgram GProgram) {
        //Create the ground plane and the walls

        this.GProgram = GProgram;

        drawGroundLine();

    }

    /**
     * W2S method that converts world coordinates to screen coordinates
     *
     * @param P
     * @return
     */

    public GPoint W2S(GPoint P) {        // Converts world to screen coordinates

        //			double X = P.getX();
        //			double Y = P.getY();
        //
        //			// Code to calculate x and y, converting world coordinates (X,Y) to simulation coordinates (x, y)
        //
        //			double x = (X - Xmin)*Xs;
        //			double y = ymax - (Y-Ymin)*Ys;
        //
        //			return new GPoint(x,y);

        return new GPoint((P.getX() - Xmin) * Xs, ymax - (P.getY() - Ymin) * Ys);
    }

    /**
     * S2W method that converts screen coordinates to world coordinates
     *
     * @param p
     * @return
     */

    public GPoint S2W(GPoint p) {    // Converts screen to world coordinates

        //			double ScrX = p.getX();
        //			double ScrY = p.getY();
        //			double X = ScrX/Xs + Xmin;
        //			double Y = (ymax - ScrY)/Ys + Ymin;
        //			return new GPoint(X, Y);

        return new GPoint((p.getX() - Xmin) / Xs, (ymax - p.getY()) / Ys + Ymin);

    }


    /**
     * Method that erases all objects on the screen
     */
    public void newScreen() {
        //erases the current display by removing all objects then resetting the display, used when a new serve is requested
        GProgram.removeAll();

        drawGroundLine();
    }

    /**
     * The following method creates an instance of a ground plane.
     */

    public void drawGroundLine() {
        GRect gPlane = new GRect(0, HEIGHT, WIDTH + OFFSET, 3); // A thick line HEIGHT pixels down from the top
        gPlane.setColor(Color.BLACK);
        gPlane.setFilled(true);
        GProgram.add(gPlane);
    }
}
