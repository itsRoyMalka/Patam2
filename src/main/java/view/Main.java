package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 742, 800);
        Controller c = fxmlLoader.getController();
        //Facade f = new Facade();
        //ViewModel vm = new ViewModel(f);
        c.init();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}