package tests;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.stage.Screen;

import sample.FittsLaw;
import sample.FittsLawController;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FittsLawControllerTest.
 *
 * @author Alice B.
 * @version 05/03/2021
 */
class FittsLawControllerTest {
    private FittsLawController controller;
    private Group root;
    private int maxNb;
    private FittsLaw fittsLaw;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        controller = new FittsLawController();
        controller.initExperiment(5);
        root = new Group();
        maxNb = 5;
        fittsLaw = new FittsLaw() {
          @Override
          public Screen getPrimary() {
              return null;
          }
          @Override
            public Rectangle2D getVisualBounds(Screen screen) {
              return new Rectangle2D(0, 0, 1000, 800);
          }
        };
        controller.setFittsLaw(fittsLaw);
    }

    @org.junit.jupiter.api.Test
    void experiment() {
        int counter = 0;
        for(int i = 0; i < maxNb; i++) {
            controller.experiment(root);
            assertFalse(root.getChildren().isEmpty());
            counter++;
        }
        assertEquals(maxNb, counter);
        controller.experiment(root);
        assertTrue(root.getChildren().isEmpty());
        assertEquals(maxNb, counter);
    }

    @org.junit.jupiter.api.Test
    void getResult() {
        double time = 0;
        for(int i = 0; i < maxNb; i++) {
            time = controller.experiment(root);
            assertFalse(root.getChildren().isEmpty());
        }
        Map<String, Number> results = controller.getResult(time);
        assertEquals(5, results.size());
    }
}