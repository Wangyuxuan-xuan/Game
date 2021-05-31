package stonegames.results;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(GameResult.class)

public interface GameResultDao {
    @SqlUpdate("""
        create table gameresult (
            id int primary key not null,
            playerName varchar2(50) not null,
            stepsByPlayer int not null,
            gameTime int not null,
            playerScore int not null
        )
        """

    )
/**
 *   create table of game result
 */
    void createTable();
    /**
     *   insert game result into database
     */
    @SqlUpdate("INSERT INTO gameresult VALUES (:id,:playerName, :stepsByPlayer, :gameTime, :playerScore)")
    void insertGameResult(@Bind("id") int id, @Bind("playerName") String playerName, @Bind("stepsByPlayer") int stepsByPlayer, @Bind("gameTime") int gameTime, @Bind("playerScore") int playerScore);

    /**
     *   insert game result into database
     */
    @SqlUpdate("INSERT INTO gameresult VALUES (:id,:playerName, :stepsByPlayer, :gameTime, :playerScore)")
    void insertGameResult(@BindBean GameResult gameResult);




    /**
     * Return all the game results from the database
     * @return list of all game results
     */
    @SqlQuery("SELECT * FROM gameresult ORDER BY id")
    List<GameResult> listGameResults();

    /**
     * Return top 5 winner's names and number of wins
     * @return top 5 winner's names and number of wins
     */
    @SqlQuery("select * from (select playername , stepsbyplayer , gametime , playerscore from gameresult order by playerscore DESC) where rownum <= 10")
    List<GameResult> listTop10Results();

    /**
     * Return the last game's id
     * @return last game's id
     */
    @SqlQuery("SELECT id FROM gameresult WHERE ROWNUM <=1 ORDER BY id DESC")
    int getLastGameID();
}