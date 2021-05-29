package stonegames.javafx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.tinylog.Logger;
import stonegames.model.GameModel;
import stonegames.model.Player;

import stonegames.results.WriteToGson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameController {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label playerNameMsg;

    @FXML
    private Label movedTimes;

    private Player player;
    @FXML
    private GridPane board;
    private static String playerName;
    private Button buttons[] = new Button[6];
    private StackPane rectangles [] = new StackPane[16];
    private GameModel model =  new GameModel();
    private static boolean drugClick = false;
    private static ArrayList<Integer> clickedButtonsLocations = new ArrayList<Integer>();
    private static ArrayList<Rectangle> clickedButtons = new ArrayList<Rectangle>();

    private static int countMove = 0;
    static boolean isSuccess = true;
    int oldLocation1;
    int oldLocation2;
    WriteToGson writeToGson = new WriteToGson();

    public static void setPlayerName(String p) {
        playerName = p;
    }

    @FXML
    public void initialize() {
        playerNameMsg.setText(playerName);
        player = new Player();
        player.setPlayerName(playerName);
        player.setStartTime(new Date());
        movedTimes.setText(String.valueOf(countMove));
        for (int i = 0; i < board.getColumnCount(); i++) {
            if (i == 0 || i== 2 || i == 4)
                board.add(createRectangle(Color.RED),i,0);
            else if (i == 1 || i == 3 || i == 5)
                board.add(createRectangle(Color.BLACK),i,0);
            else
                board.add(createRectangle(Color.TRANSPARENT), i,0);
        }
    }
        public StackPane createRectangle(Color color){
            StackPane rectangle = new StackPane();
            rectangle.getStyleClass().add("rectangle");
            var ball = new Rectangle(40,150);
            ball.setFill(color);
            rectangle.getChildren().add(ball);
            rectangle.setOnMouseClicked(this::handleMouseClicked);
            return rectangle;
        }

        @FXML
        private void handleMouseClicked(MouseEvent event) {
            var rectangle = (StackPane) event.getSource();
            var row = GridPane.getRowIndex(rectangle);
            var col = GridPane.getColumnIndex(rectangle);
            System.out.printf("Click on rectangle (%d,%d)\n", row, col);
            clickedButtonsLocations.add(col);
            clickedButtons.add((Rectangle) rectangle.getChildren().get(0));
            Logger.debug(clickedButtonsLocations.size());
            if (clickedButtonsLocations.size() == 2 ) {
                Logger.debug("Two rectangles have been selected");
                oldLocation1 = clickedButtonsLocations.get(0);
                oldLocation2 = clickedButtonsLocations.get(1);
                if (model.locationsDontContainBalls(oldLocation1, oldLocation2) || !model.isBallsNeighbour(oldLocation1, oldLocation2) || model.oneLocationDoesNotContainBall(oldLocation1,oldLocation2)){
                    clickedButtonsLocations = new ArrayList<>();
                    clickedButtons = new ArrayList<>();
                    Logger.debug("Your selected locations don't contain balls or selected locations are not neighbor");
                }
            }
            if (clickedButtonsLocations.size() == 4) {
                if(clickedButtonsLocations.get(0)<clickedButtonsLocations.get(1)&&clickedButtonsLocations.get(2)<clickedButtonsLocations.get(3)||
                        clickedButtonsLocations.get(0)>clickedButtonsLocations.get(1)&&clickedButtonsLocations.get(2)>clickedButtonsLocations.get(3)){
                    var desiredLocation1 = clickedButtonsLocations.get(2);
                    var desiredLocation2 = clickedButtonsLocations.get(3);
                    if (model.locationsDontContainBalls(desiredLocation1, desiredLocation2) && model.isBallsNeighbour(desiredLocation1, desiredLocation2)) {
                        model.setBallsLocation(oldLocation1, oldLocation2, desiredLocation1, desiredLocation2);
                        Logger.debug("New locations for the balls have been set");

                        setBallsColor(clickedButtons.get(0), clickedButtons.get(1), clickedButtons.get(2), clickedButtons.get(3));
                        Logger.debug(model);
                        isSuccess = model.isGameComplete();
                        countMove ++ ;
                        Logger.debug("Moved times"+countMove);
                        movedTimes.setText(String.valueOf(countMove));
                        Logger.debug("Game is complete = " + isSuccess);
                        if(isSuccess) successProcess();
                        clickedButtonsLocations = new ArrayList<>();
                        clickedButtons = new ArrayList<>();
                    } else {
                        clickedButtonsLocations = new ArrayList<>();
                        clickedButtons = new ArrayList<>();
                        Logger.debug("The locations are invalid");
                    }
                }else {
                    clickedButtonsLocations = new ArrayList<>();
                    clickedButtons = new ArrayList<>();
                    Logger.debug("Can not swap locations");
                }
            }
        }

    private void setBallsColor(Rectangle oldBall1, Rectangle oldBall2, Rectangle newBall1, Rectangle newBall2){
        newBall1.setFill(oldBall1.getFill());
        newBall2.setFill(oldBall2.getFill());
        oldBall1.setFill(Color.TRANSPARENT);
        oldBall2.setFill(Color.TRANSPARENT);
    }

    public void successProcess(){
        SavePlayerDataToJson(player,countMove,playerName);
        List<Player> top10players = null;
        setScoreBoard(top10players);

        Stage stage = new Stage();
        try {
            stage.setScene(new Scene((Parent) FXMLLoader.load(getClass().getResource("/fxml/result.fxml"))));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        stage.setTitle("Red and Black stone game");
        stage.show();
    }

    public void SavePlayerDataToJson(Player player, int countMove, String playerName) {
        player.setEndTime(new Date());
        player.setCount(countMove);
        player.setScore();
        try {
            writeToGson.saveData(player);
        } catch (IOException exc) {
            // TODO Auto-generated catch block
            exc.printStackTrace();
        }

        Logger.info("player" + player);
        ResultController.setMyScore(playerName + ": Congratulation!! You moved :" + countMove + " times" + " score:" + player.getScore());

    }

    public void setScoreBoard(List<Player> top10players){
        try {
            top10players = writeToGson.findTop10Player();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (top10players != null) {
            for (int k = 0; k < 10; k++) {
                if (k < top10players.size()) {
                    stringBuilder.append(" Player name :");
                    stringBuilder.append(top10players.get(k).getPlayerName());
                    stringBuilder.append(" Time : ");
                    stringBuilder.append(top10players.get(k).getSeconds());
                    stringBuilder.append(" Moved times :");
                    stringBuilder.append(top10players.get(k).getCount());
                    stringBuilder.append(" Score :");
                    stringBuilder.append(top10players.get(k).getScore());
                    stringBuilder.append("\n");
                }
            }
        }
        Logger.info("top 10:" + stringBuilder);
        ResultController.setTop10(stringBuilder.toString());
    }
}
