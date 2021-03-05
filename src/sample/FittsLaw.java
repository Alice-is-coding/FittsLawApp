package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;

import java.awt.*;
import java.util.*;

/**
 * FittsLaw class.
 * To make experiments with the Fitts law formula, and returning results of such experiments.
 *
 * @author Alice B.
 * @version 05/03/2021
 */
public class FittsLaw {
    /**
     * Distance between the pointer device and the center of the current circle drawn.
     */
    private double distance;
    /**
     * Diameter of the current circle drawn.
     */
    private int diameter;

    /**
     * To create a new test :
     * - Clear the circle previously drawn.
     * - Generate a new random circle (randomX, randomY, randomDiameter) to be drawn on the object passed in param.
     *   (Note : The random values are generated according to boundaries : screenWidth, screenHeight).
     * - Calculate the current distance between the pointing device and the circle just drawn.
     *
     * @param root The object on which the circle is appended.
     */
    public void experiment(Group root) {
        // Get the bounds of the screen.
        Screen screen = getPrimary();
        Rectangle2D bounds = getVisualBounds(screen);
        int min_x = (int) bounds.getMinX();
        int max_x = (int) bounds.getMaxX();
        int min_y = (int) bounds.getMinY();
        int max_y = (int) bounds.getMaxY();
        // For generating coordinates.
        Random random = new Random();
        int x, y;
        // Clear screen.
        root.getChildren().clear();

        // Picking random (x, y) values & a diameter for the next circle.
        x = random.ints(min_x, max_x).findAny().getAsInt();
        y = random.ints(min_y, max_y).findAny().getAsInt();
        this.diameter = random.ints(min_x, (max_y / 2) / 2).findAny().getAsInt();

        // Drawing a circle with a center value of (x, y) and a dimension diameter.
        Circle circle = new Circle(x, y, this.diameter);
        root.getChildren().add(circle);

        // Calculate the distance between the current cursor position and the center of the circle just drawn.
        Point p = getCursorPos();
        int cursor_xPos = p.x;
        int cursor_yPos = p.y;
        float circle_centerXPos = (float) circle.getCenterX();
        float circle_centerYPos = (float) circle.getCenterY();
        this.distance = calculateDistance(cursor_xPos, cursor_yPos, circle_centerXPos, circle_centerYPos);
    }

    /**
     * To calculate the Index of Difficulty -> ID = log2(1 + D/W)
     *
     * @return The ID calculated.
     */
    public double calculate_ID() {
        return Math.log(1 + this.distance / this.diameter) / Math.log(2);
    }

    /**
     * To get the current position of the cursor (P<x, y>).
     *
     * @return The point (position) where the pointer device is.
     */
    public Point getCursorPos() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    /**
     * To calculate the distance between the pointer device and the center of the circle,
     * thanks to the Euclidean distance formula -> d(P1, P2) = sqrt((P2x - P1x)² + (P2y - P1y)²)
     *
     * @param cursor_xPos x pos of the cursor. P1<x, ...>
     * @param cursor_yPos y pos of the cursor. P1<..., y>
     * @param circle_centerXPos x center pos of the circle. P2<x, ...>
     * @param circle_centerYPos y center pos of the circle. P2<..., y>
     *
     * @return The distance calculated.
     */
    public double calculateDistance(int cursor_xPos, int cursor_yPos, float circle_centerXPos, float circle_centerYPos) {
        return Math.sqrt(Math.pow(cursor_xPos - circle_centerXPos, 2) + Math.pow(cursor_yPos - circle_centerYPos, 2));
    }

    /**
     * Distance getter.
     *
     * @return The distance between the pointer device and the center of the circle.
     */
    public double getDistance() {
        return this.distance;
    }

    /**
     * Diameter getter.
     *
     * @return The diameter of the circle.
     */
    public int getDiameter() {
        return this.diameter;
    }

    /**
     * Primary screen getter.
     *
     * @return The primary Screen.
     */
    public Screen getPrimary() {
        return Screen.getPrimary();
    }

    /**
     * Visual bounds getter.
     *
     * @param screen The characteristics of a graphics destination such as monitor.
     * @return The visual bounds of this Screen. These bounds account for objects in the native windowing system such as
     *         task bars and menu bars. These bounds are contained by Screen.bounds
     */
    public Rectangle2D getVisualBounds(Screen screen) {
        return screen.getVisualBounds();
    }

    /**
     * To build results of the experiment, readable for a Human Being.
     *
     * @param i The current test number.
     * @param initTime The time where the new test has been requested by the user (at the initialization or when requested a new test) (in ms).
     * @return A map containing the results :
     *          - testNumber (i).
     *          - time used by the user to select a circle drawn (in ms).
     *          - distance between the pointer device and the center of the circle drawn.
     *          - diameter of the circle.
     *          - index of difficulty (ID).
     */
    public Map<String, Number> getResult(int i, double initTime) {
        Map<String, Number> map = new LinkedHashMap<>();
        int T = (int)(new Date().getTime() - initTime);
        double D = getDistance();
        int W = getDiameter();
        double ID = calculate_ID();

        map.put("i", i);
        map.put("T", T);
        map.put("D", D);
        map.put("W", W);
        map.put("ID", ID);

        return map;
    }
}
