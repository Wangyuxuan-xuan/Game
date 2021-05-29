package stonegames.javafx.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.tinylog.Logger;


public class ResultController {

    private static String myScore;
    private  static String top10;

    @FXML
    private TextArea textArea;

    @FXML
    private Label myScoreLabel;

    @FXML
    private Button startButton;

    @FXML
    public void initialize() {

        myScoreLabel.setText(myScore);
        textArea.setText(top10);
    }

    public void closeAction(){
        Logger.info("Game Over!");
        System.exit(0);
    }

    public static void setMyScore(String s) {
        myScore = s;
    }

    public static void setTop10(String t) {
        top10 = t;
    }


}