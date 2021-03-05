package tests;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.stage.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.FittsLaw;

import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FittsLawTest.
 *
 * @author Alice B.
 * @version 05/03/2021
 */
class FittsLawTest {
    private FittsLaw fittsLaw;
    private double distance;
    private int diameter;
    private Group root;
    private double time;

    @BeforeEach
    void setUp() {
        fittsLaw = new FittsLaw() {
            @Override
            public Screen getPrimary() {
                return null;
            }
            @Override
            public Rectangle2D getVisualBounds(Screen screen) {
                return new Rectangle2D(0, 0, 1000, 800);
            }
            @Override
            public double calculate_ID() {
                return Math.log(1 + distance / diameter) / Math.log(2);
            }
        };
        root = new Group();
    }

    @Test
    void experiment() {
        fittsLaw.experiment(root);
        distance = fittsLaw.getDistance();
        diameter = fittsLaw.getDiameter();
        assertTrue(fittsLaw.getDistance() != 0.0);
        assertTrue(fittsLaw.getDiameter() != 0);
    }

    @Test
    void calculate_ID() {
        distance = 639.11;
        diameter = 67;
        double result = fittsLaw.calculate_ID();
        assertEquals(3.3976599478172944, result);

        distance = 424.8529;
        diameter = 172;
        result = fittsLaw.calculate_ID();
        assertEquals(1.7949668446360298, result);

        distance = 929.877;
        diameter = 12;
        result = fittsLaw.calculate_ID();
        assertEquals(6.294432359214083, result);
    }

    @Test
    void calculateDistance() {
        int cursor_xPos = 600;
        int cursor_yPos = 150;
        int circle_centerXPos = 230;
        int circle_centerYPos = 45;
        double result = fittsLaw.calculateDistance(cursor_xPos, cursor_yPos, circle_centerXPos, circle_centerYPos);
        assertEquals(384.61019227264376, result);

        cursor_xPos = 1524;
        cursor_yPos = 600;
        circle_centerXPos = 954;
        circle_centerYPos = 605;
        result = fittsLaw.calculateDistance(cursor_xPos, cursor_yPos, circle_centerXPos, circle_centerYPos);
        assertEquals(570.0219294027204, result);

        cursor_xPos = 1;
        cursor_yPos = 2;
        circle_centerXPos = 4;
        circle_centerYPos = 6;
        result = fittsLaw.calculateDistance(cursor_xPos,cursor_yPos,circle_centerXPos,circle_centerYPos);
        assertEquals(5.0, result);
    }

    @Test
    void getDistance() {
        fittsLaw.experiment(root);
        assertTrue(fittsLaw.getDistance() != 0.0);
        fittsLaw.experiment(root);
        assertTrue(fittsLaw.getDistance() != 0.0);
        fittsLaw.experiment(root);
        assertTrue(fittsLaw.getDistance() != 0.0);
    }

    @Test
    void getDiameter() {
        fittsLaw.experiment(root);
        assertTrue(fittsLaw.getDiameter() != 0.0);
        fittsLaw.experiment(root);
        assertTrue(fittsLaw.getDiameter() != 0.0);
        fittsLaw.experiment(root);
        assertTrue(fittsLaw.getDiameter() != 0.0);
    }

    @Test
    void getResult() {
        time = new Date().getTime();
        fittsLaw.experiment(root);
        Map<String, Number> results = fittsLaw.getResult(1, time);
        assertFalse(results.isEmpty());
        assertEquals(5, results.size());
        time = new Date().getTime();
        fittsLaw.experiment(root);
        results = fittsLaw.getResult(1, time);
        assertFalse(results.isEmpty());
        assertEquals(5, results.size());
    }
}