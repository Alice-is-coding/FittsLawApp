package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.Map;


public class Main extends Application {
    private static double time;
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setTitle("Fitts Law Experimentation");
        Scene firstScene = new Scene(root, 300, 275);
        firstScene.setFill(Color.WHITE);
        primaryStage.setScene(firstScene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        FittsLawController controller = new FittsLawController();

        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Map<String, Number> results = controller.getResult(time);
                for(String key : results.keySet()) {
                    System.out.println(key + " = " + results.get(key));
                }
                time = controller.experiment(root);
                // TODO : Create the Log class to log the results obtained.
            }
        });
        // Launch of the experiment. The rest is managed inside the click event above.
        controller.initExperiment(5);
        time = controller.experiment(root);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
