package stonegames.javafx.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;
import stonegames.javafx.Controller.GameController;

import javax.inject.Inject;
import java.io.IOException;

public class OpeningController {
    @Inject
    private FXMLLoader fxmlLoader;


    @FXML
    private TextField playerNameTextField;

    @FXML
    private Label errorLabel;

    public void startAction(ActionEvent actionEvent) throws IOException {
        if (playerNameTextField.getText().isEmpty()) {
            errorLabel.setText("Please enter your name!");
        } else {
            GameController.setPlayerName(playerNameTextField.getText());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene((Parent) FXMLLoader.load(getClass().getResource("/fxml/game.fxml"))));
            stage.show();
            Logger.info("The user's name is set to {}, loading game scene", playerNameTextField.getText()); // TODO
        }
    }

}