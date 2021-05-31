package stonegames.javafx.Controller;


import javafx.fxml.FXML;
import org.tinylog.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Slf4JSqlLogger;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import stonegames.results.GameResultDao;
import stonegames.results.GameResult;

import javax.inject.Inject;
import java.util.List;
public class ResultController {
    @FXML
    private TableColumn<GameResult , String> winnerName;
    @FXML
    private TableColumn<GameResult , Integer> TotalMoves;
    @FXML
    private TableColumn<GameResult , Integer> Duration;
    @FXML
    private TableColumn<GameResult , Integer> Score;

    @Inject
    private FXMLLoader fxmlLoader;

    @Inject
    private GameResultDao gameResultDao;

    @FXML
    private TableView<GameResult> highScoreTable;


    @FXML
    private void initialize() {

        Logger.debug("Loading high scores...");
        Jdbi jdbi = Jdbi.create("jdbc:oracle:thin:@oracle.inf.unideb.hu:1521:ora19c", "U_DDBUA9", "kalvinter");
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.setSqlLogger(new Slf4JSqlLogger());
        List<GameResult> winnerResults = jdbi.withExtension(GameResultDao.class, GameResultDao::listTop10Results);
        winnerName.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        TotalMoves.setCellValueFactory(new PropertyValueFactory<>("stepsByPlayer"));
        Duration.setCellValueFactory(new PropertyValueFactory<>("gameTime"));
        Score.setCellValueFactory(new PropertyValueFactory<>("playerScore"));

        ObservableList<GameResult> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(winnerResults);
        highScoreTable.setItems(observableResult);

    }

    public void handleExitButton(){
        Logger.info("Game Over!");
        System.exit(0);
    }
}