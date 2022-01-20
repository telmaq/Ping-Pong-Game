package ppPackage;

//The following code contains lines from the assignment handouts written by Professor Ferrie and from Katrina Poulin's tutorial session.

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

import static ppPackage.ppSimParams.*;

/**
 * ppSim implements a simulation of a player paddle and an Agent paddle with a ping-pong ball. The player paddle, located on the right side of the screen,
 * is constrained to moving up and down. Same thing with the Agent paddle, located on the left side of the screen.
 *
 * @author jzhao
 */

public class ppSim extends GraphicsProgram {

    ppTable myTable;
    ppPaddle RPaddle;
    ppPaddleAgent LPaddle;
    ppBall myBall;
    RandomGenerator rgen;
    Color myColor;

    /**
     * The main method tells Java that ppSim is the main class, as it is the entry point of the program containing four classes.
     * It is of type void because it does not return anything.
     */

    public static void main(String[] args) {
        new ppSim().start(args);
    }

    /**
     * Init method that initializes the simulation window and generates the parameters for the ping-pong ball following a random number generator
     * with a set seed RSEED.
     * <p>
     * Within the method are many objects that make up interactive and event-driven features, such as being able to change the playing speed or the agent paddle's lag.
     */


    public void init() {


        this.setSize(ppSimParams.WIDTH, ppSimParams.HEIGHT + OFFSET);

        //create buttons

        JButton newServeButton = new JButton("New Serve");
        JButton quitButton = new JButton("Quit");
        traceButton = new JToggleButton("Trace", false);
        JButton clearButton = new JButton("Clear Score");            // Clear button that clears the score
        timeSlider = new JSlider(1, 3, 1);
        lagSlider = new JSlider(1, 5, 1);
        JButton rTime = new JButton("Reset Time");
        JButton rLag = new JButton("Reset Lag");
        // adding buttons

        add(clearButton, SOUTH);
        add(newServeButton, SOUTH);
        add(quitButton, SOUTH);
        add(traceButton, SOUTH);
        newServeButton.setForeground(Color.RED);

        // adding slider features and button to reset to default value.

        add(new JLabel("Faster"), SOUTH);
        add(timeSlider, SOUTH);
        add(new JLabel("Slower"), SOUTH);

        add(rTime, SOUTH);

        add(new JLabel("-Lag"), SOUTH);
        add(lagSlider, SOUTH);
        add(new JLabel("+Lag"), SOUTH);

        add(rLag, SOUTH);

        // adding scoreboard on top of screen

        agentScore = new JLabel("Agent = " + agentPoints);
        playerScore = new JLabel("Human = " + playerPoints);
        add(agentScore, NORTH);
        add(playerScore, NORTH);

        addMouseListeners();
        addActionListeners();

        rgen = RandomGenerator.getInstance();
        rgen.setSeed(RSEED);

        // add table and start game

        this.myTable = new ppTable(this);
        newGame();

    }

    /**
     * The newBall() method returns an instance of the ppBall() constructor.
     *
     * @return a ppBall constructor
     */
    ppBall newBall() {

        // generate parameters for ppBall

        Color iColor = Color.RED;
        double iYinit = rgen.nextDouble(YinitMIN, YinitMAX);
        double iLoss = rgen.nextDouble(EMIN, EMAX);
        double iVel = rgen.nextDouble(VoMIN, VoMAX);
        double iTheta = rgen.nextDouble(ThetaMIN, ThetaMAX);

        // return ppBall instance

        return new ppBall(Xinit, iYinit, iVel, iTheta, iLoss, iColor, myTable, this);
    }

    /**
     * The newGame() method sets up the display, creates the ball and paddle instances along with starting the respective threads.
     * setRightPaddle and setLeftPaddle are called and the Left paddle is attached to the ball here.
     */

    public void newGame() {
        if (myBall != null) myBall.kill();            // stop current game in play
        myTable.newScreen();
        myBall = newBall();
        RPaddle = new ppPaddle(ppPaddleXinit, ppPaddleYinit, Color.GREEN, myTable, this);
        LPaddle = new ppPaddleAgent(LPaddleXinit, LPaddleYinit, Color.BLUE, myTable, this);
        LPaddle.attachBall(myBall);
        myBall.setRightPaddle(RPaddle);
        myBall.setLeftPaddle(LPaddle);
        pause(STARTDELAY);
        myBall.start();
        LPaddle.start();
        RPaddle.start();
    }

    /**
     * Mouse Handler - a moved event moves the paddle up and down in Y
     */

    public void mouseMoved(MouseEvent e) {

        if (myTable == null || RPaddle == null) return;
        //convert mouse position to a point in screen coordinates

        GPoint Pm = myTable.S2W(new GPoint(e.getX(), e.getY()));
        double PaddleX = RPaddle.getP().getX();
        double PaddleY = Pm.getY();
        RPaddle.setP(new GPoint(PaddleX, PaddleY));
    }

    /**
     * Action listener that defines what happens whenever the user performs a specific operation.
     */

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("New Serve")) {
            newGame();
        } else if (command.equals("Quit")) {
            System.exit(0);
        } else if (command.equals("Trace")) {
            traceButton.isSelected();
        } else if (command.equals("Clear Score")) {
            agentPoints = 0;
            playerPoints = 0;

            agentScore.setText("Agent = " + agentPoints);
            playerScore.setText("Human = " + playerPoints);
        } else if (command.equals("Reset Time")) {

            timeSlider.setValue(1);
        } else if (command.equals("Reset Lag")) {

            lagSlider.setValue(1);
        }
    }
}