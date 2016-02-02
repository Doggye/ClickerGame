package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        primaryStage.setTitle("Gierka");
        primaryStage.setScene(new Scene(root, 350, 330));
        primaryStage.show();
        primaryStage.setResizable(false);

        ////////////////////////////////////////////////////////////////////////////////
        // Test KeyPressed
      //  primaryStage.getScene().setOnKeyPressed(event -> {
      //      System.out.println(event.getCode().toString());
      //  });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
