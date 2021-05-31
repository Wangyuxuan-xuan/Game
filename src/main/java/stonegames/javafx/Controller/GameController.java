package stonegames.javafx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Slf4JSqlLogger;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;
import stonegames.model.GameModel;
import stonegames.model.Player;

import stonegames.results.GameResult;
import stonegames.results.GameResultDao;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
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
    private GameModel model =  new GameModel();
    private static ArrayList<Integer> clickedButtonsLocations = new ArrayList<Integer>();
    private static ArrayList<Rectangle> clickedButtons = new ArrayList<Rectangle>();


    private static int countMove = 0;
    static boolean isSuccess = true;
    int oldLocation1;
    int oldLocation2;

    public static void setPlayerName(String p) {
        playerName = p;
    }

    @FXML
    public void initialize() {
        playerNameMsg.setText(playerName);
        player = new Player();
        player.setPlayerName(playerName);
        LocalTime startTime = LocalTime.now();
        player.setStartTime(startTime);
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
            StackPane stackPane = new StackPane();
            stackPane.getStyleClass().add("rectangle");
            var ball = new Rectangle(45,150);
            ball.setFill(color);
            stackPane.getChildren().add(ball);
            stackPane.setOnMouseClicked(this::handleMouseClicked);
            return stackPane;
        }

        @FXML
        private void handleMouseClicked(MouseEvent event) {
            var space = (StackPane) event.getSource();
            var row = GridPane.getRowIndex(space);
            var col = GridPane.getColumnIndex(space);
            System.out.printf("Click on space (%d,%d)\n", row, col);
            clickedButtonsLocations.add(col);
            clickedButtons.add((Rectangle) space.getChildren().get(0));
            Logger.debug(clickedButtonsLocations.size());
            if (clickedButtonsLocations.size() == 2 ) {
                Logger.debug("Two rectangles have been selected");
                oldLocation1 = clickedButtonsLocations.get(0);
                oldLocation2 = clickedButtonsLocations.get(1);

                if(oldLocation1 < oldLocation2 ){
                    if (model.locationsDontContainBalls(oldLocation1, oldLocation2) || !model.isBallsNeighbour(oldLocation1, oldLocation2) || model.oneLocationDoesNotContainBall(oldLocation1,oldLocation2)){
                        clickedButtonsLocations = new ArrayList<>();
                        clickedButtons = new ArrayList<>();
                        Logger.debug("Your selected locations don't contain balls or selected locations are not neighbor");
                    }else {
                        model.setBallsToNone(oldLocation1,oldLocation2);
                    }
                }else {
                    clickedButtonsLocations = new ArrayList<>();
                    clickedButtons = new ArrayList<>();
                    Logger.debug("Please select stones from left to right");
                }

            }
            if (clickedButtonsLocations.size() == 3) {
                var desiredLocation1 = clickedButtonsLocations.get(2);
                var desiredLocation2 = clickedButtonsLocations.get(2) + 1;
                if (model.canBallsMoved(oldLocation1,oldLocation2,desiredLocation1,desiredLocation2)) {
                    model.setBallsLocation(oldLocation1, oldLocation2, desiredLocation1, desiredLocation2);
                    Logger.debug("New locations for the balls have been set");

                    var stackPane = (StackPane)getNodeFromGridPane(board,desiredLocation2,0);
                    clickedButtons.add((Rectangle) stackPane.getChildren().get(0));
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
                    model.resetBallsColor(oldLocation1,oldLocation2);
                }
            }
        }

    private void setBallsColor(Rectangle oldBall1, Rectangle oldBall2, Rectangle newBall1, Rectangle newBall2){
        Color color1 = (Color) oldBall1.getFill();
        Color color2 = (Color) oldBall2.getFill();
        oldBall1.setFill(Color.TRANSPARENT);
        oldBall2.setFill(Color.TRANSPARENT);
        newBall1.setFill(color1);
        newBall2.setFill(color2);
    }

    public void successProcess() {
        try {
            createGameResult();
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/result.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void createGameResult() {
        LocalTime endTime = LocalTime.now();
        player.setEndTime(endTime);
        player.setCount(countMove);
        player.setScore();
        int gameTime = player.getSeconds();
        int playerScore = player.getScore();
        Jdbi jdbi = Jdbi.create("jdbc:oracle:thin:@oracle.inf.unideb.hu:1521:ora19c", "U_DDBUA9", "kalvinter");
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.setSqlLogger(new Slf4JSqlLogger());
        List<GameResult> gameResults = jdbi.withExtension(GameResultDao.class, dao -> {
            //dao.createTable();
            int lastGameID = dao.getLastGameID();
            dao.insertGameResult(new GameResult(lastGameID + 1, playerName, countMove,gameTime,playerScore));
            return dao.listGameResults();
        });
        gameResults.forEach(System.out::println);
    }

    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
}
