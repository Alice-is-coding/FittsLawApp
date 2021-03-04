package sample;

import javafx.scene.Group;

import java.util.Date;
import java.util.Map;


public class FittsLawController {
    private final FittsLaw fittsLaw;
    private int maxCount;
    private int counter;

    public FittsLawController() {
        this.fittsLaw = new FittsLaw();
    }

    public void initExperiment(int n) {
        this.maxCount = n;
        this.counter = 0;
    }

    public double experiment(Group root){
        if(this.counter < this.maxCount) {
            this.counter++;
            fittsLaw.experiment(root);
        }else {
            root.getChildren().clear();
        }
        return new Date().getTime();
    }

    public Map<String, Number> getResult(double initTime) {
        return this.fittsLaw.getResult(this.counter, initTime);
    }
}
