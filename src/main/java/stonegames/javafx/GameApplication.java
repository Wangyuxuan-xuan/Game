package stonegames.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

public class GameApplication extends Application {

    public void start(Stage stage) throws Exception {
        Logger.info("Starting application");

        stage.setTitle("Red and Black stone game");
        stage.setResizable(false);
        stage.setScene(new Scene((Parent) FXMLLoader.load(getClass().getResource("/fxml/opening.fxml"))));
        stage.show();
    }
}