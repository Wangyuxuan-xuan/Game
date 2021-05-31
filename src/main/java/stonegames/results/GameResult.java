package stonegames.results;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor

/**
 *gameResult class of the application
 */
public class GameResult {
    private int id;
    private String playerName;
    private int stepsByPlayer;
    private int gameTime;
    private int playerScore;

}