package main.java.yelp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {

    private static Stage primaryStageObj;

    /**
     * The entry point of the application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Gets the primary stage.
     *
     * @return the primary stage obj
     */
    public static Stage getPrimaryStageObj() {
        return primaryStageObj;
    }

    /**
     * Initializes and launches the main screen.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStageObj = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/templates/main.fxml"));
        primaryStage.setTitle("Revidi - Yelp Clone");
        Scene scene = new Scene(root, 1024, 576);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> Platform.exit());
    }



//    @Override
//    public void stop() {
//        Controller.getInstance().shutdown();
//    }



}
