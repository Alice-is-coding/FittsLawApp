package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Map;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Main.
 * Manages the application (start, events, log of info, stop).
 *
 * @author Alice B.
 * @version 05/03/2021
 */
public class Main extends Application {
    /** Current time (in ms). */
    private static double time;
    /** Info to be appended to the log file. */
    private static String resultFormatter;
    /** Logger which appends info. */
    private static Logger logger;
    /** Controller to manage experimentation with the Fitts law. */
    private static FittsLawController controller;

    /**
     * Configure the logger field, with a filehandler in order to log directly in a log file.
     * Note : The format is simplified in order to lighten the amount of info appended.
     * Note : Displaying log info in the console is deactivated.
     *
     * @throws IOException An exception occurred...
     */
    public void configureLoggingSystem() throws IOException {
        // Configuring the logger.
        logger = Logger.getLogger(Main.class.getName());
        FileHandler fileHandler = new FileHandler("app.log", true);
        SimpleFormatter formatter = new SimpleFormatter();    // To lighten the log information.
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);
        logger.setUseParentHandlers(false);   // To avoid displaying information into the console
                                              // (Change to true if you would like to see the logging info in the console too
                                              // (could be useful when debugging)).
    }

    /**
     * Event handling.
     * ADD a MouseClicked event on the object passed in param.
     * Generate results to construct the final logging message to be appended to the log file, then continue the experimentation.
     *
     * @param root The object on which the event is attached. (An observable).
     */
    public void setOnMouseClicked(Group root) {
        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Map<String, Number> results = controller.getResult(time);

                for(String key : results.keySet()) {
                    resultFormatter += key + " = " + results.get(key) + "\n";
                }
                resultFormatter += "\n";
                time = controller.experiment(root);
            }
        });
    }

    /**
     * Event handling.
     * Add a OnCloseRequest event on the stage passed in param.
     * Append the information to the log file before closing the app as requested by the user.
     *
     * @param primaryStage The stage on which the event is attached. (An observable).
     */
    public void setOnCloseRequest(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                resultFormatter += "----------------------------------------------\n";
                logger.info(resultFormatter);
            }
        });
    }

    /**
     * Main entry point and only method that has to be overridden in order to launch an app in javafx.
     * Note : The 1st window of the app (primaryStage) has been already prepared and is passed as a param.
     *
     * @param primaryStage The 1st window of the app.
     * @throws IOException An exception occurred...
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Group root = loader.load();    // Where shades are drawn.
        controller = loader.getController();
        configureLoggingSystem();
        resultFormatter = "\nPointing device : touchpad\n";

        primaryStage.setTitle("Fitts Law Experimentation");
        Scene firstScene = new Scene(root, 300, 275);
        firstScene.setFill(Color.WHITE);
        primaryStage.setScene(firstScene);
        primaryStage.setMaximized(true);
        primaryStage.show();

        // Generate results to construct the final logging message to be appended to the log file, then continue the experimentation.
        setOnMouseClicked(root);

        // Append the information to the log file before closing the app as requested by the user.
        setOnCloseRequest(primaryStage);

        // Launch of the experiment. The rest is managed inside the click event above.
        controller.initExperiment(5);
        time = controller.experiment(root);
    }

    /**
     * The main method.
     *
     * @param args Arguments from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
