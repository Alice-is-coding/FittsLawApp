package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;

import java.awt.*;
import java.util.*;

public class FittsLaw {
    private double distance;
    private int diameter;

    public void experiment(Group root) {
        // Get the bounds of the screen.
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
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

    public double calculate_ID() {
        return Math.log(1 + this.distance / this.diameter) / Math.log(2);
    }

    public Point getCursorPos() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    public double calculateDistance(int cursor_xPos, int cursor_yPos, float circle_centerXPos, float circle_centerYPos) {
        return Math.sqrt(Math.pow(cursor_xPos - circle_centerXPos, 2) + Math.pow(cursor_yPos - circle_centerYPos, 2));
    }

    public double getDistance() {
        return this.distance;
    }

    public double getDiameter() {
        return this.diameter;
    }

    public Map<String, Number> getResult(int i, double initTime) {
        Map<String, Number> map = new LinkedHashMap<>();
        double T = new Date().getTime() - initTime;
        double D = getDistance();
        double W = getDiameter();
        double ID = calculate_ID();

        map.put("i", i);
        map.put("T", T);
        map.put("D", D);
        map.put("W", W);
        map.put("ID", ID);

        return map;
    }
}
