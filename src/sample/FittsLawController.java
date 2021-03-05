package sample;

import javafx.scene.Group;

import java.util.Date;
import java.util.Map;


/**
 * FittsLawController.
 * Controls requests from the app concerning the experiment :
 *     - Inits the experiment with a maximum number of tests to be performed.
 *     - Checks the validity of the user request (if he still can perform a test or not (max nb of tests reached)).
 *
 * @author Alice B.
 * @version 05/03/2021
 */
public class FittsLawController {
    /** The instance used to transmit request and perform tests. */
    private final FittsLaw fittsLaw;
    /** Max number of tests to be performed (initialized in the initExperiment method). */
    private int maxCount;
    /** Actual counter to be compared to the maxCount above, in order to perform the test (or not). */
    private int counter;

    /**
     * Constructor.
     */
    public FittsLawController() {
        this.fittsLaw = new FittsLaw();
    }

    /**
     * To initialize the whole experiment with a max number of tests to be performed.
     *
     * @param n The max nb tests to be performed.
     */
    public void initExperiment(int n) {
        this.maxCount = n;
        this.counter = 0;
    }

    /**
     * To initialize a new test for the experiment.
     * Note : If the max number of tests has been reached, the circles will be simply cleared from the object so that the user can't pass tests anymore.
     *
     * @param root The one who visually speaking allows the users to do the tests.
     * @return The current time (in ms).
     */
    public double experiment(Group root){
        if(this.counter < this.maxCount) {
            this.counter++;
            fittsLaw.experiment(root);
        }else {
            root.getChildren().clear();
        }
        return new Date().getTime();
    }

    /**
     * To get the final results of the experiment.
     *
     * @param initTime The time when the experiment() method was called.
     * @return A map containing the results :
     *          - testNumber
     *          - time used by the user to select a circle drawn (in ms).
     *          - distance between the pointer device and the center of the circle drawn.
     *          - diameter of the circle.
     *          - index of difficulty (ID).
     */
    public Map<String, Number> getResult(double initTime) {
        return this.fittsLaw.getResult(this.counter, initTime);
    }
}
