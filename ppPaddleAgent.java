package ppPackage;

//The following code contains lines from the assignment handouts written by Professor Ferrie and from Katrina Poulin's tutorial session.

import java.awt.Color;

import static ppPackage.ppSimParams.*;

import acm.graphics.GPoint;
import acm.program.GraphicsProgram;

/**
 * The ppPaddleAgent class contains a ppPaddleAgent constructor with a run method that
 * makes the game somewhat playable by implementing some lag to the Agent paddle.
 *
 * @author jzhao
 */
public class ppPaddleAgent extends ppPaddle {

    ppBall myBall;

    public ppPaddleAgent(double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram) {

        super(X, Y, myColor, myTable, GProgram);

    }

    /**
     * run method that contains a while loop that updates the position of the paddle at every iteration.
     */

    public void run() {

        int AgentLag = 8;
        int ballSkip = 0;


        //position will be updated every AgentLag iteration of the while loop

        while (true) {

            GProgram.pause(getCurrentLagMultiplier() * TICK * TSCALE);


            if (ballSkip++ >= AgentLag) {
                //...code to update paddle position;
                //get ball Y position
                double Y = myBall.getP().getY();
                //set paddle position to Y
                this.setP(new GPoint(this.getP().getX(), Y));
                ballSkip = 0;
            }

        }

    }

    /**
     * Method that attaches the Agent paddle to the ball.
     *
     * @param myBall
     */

    public void attachBall(ppBall myBall) {
        //sets the myBall to the ppBall instance variable in ppPaddleAgent
        this.myBall = myBall;

    }

    /**
     * This method gets the the value of lagSlider.
     *
     * @return Value of lagSlider
     */

    public double getCurrentLagMultiplier() {
        return lagSlider.getValue();

    }

}
