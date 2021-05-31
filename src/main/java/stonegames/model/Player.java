package stonegames.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Duration;
import java.util.Date;
import java.time.LocalTime;

/**
 * A player entity class,
 * Includes the player's name, game start time, game end time, number of moves and final score.
 */
public class Player {
    /**
     * player's name.
     */
    private String playerName;
    /**
     * number of moves.
     */
    private int count;
    /**
     * game start time.
     */
    private LocalTime startTime;
    /**
     * game end time.
     */
    private LocalTime endTime;
    /**
     * final score.
     */
    private int score;

    /**
     * final score,
     * @return score.
     */
    public int getScore() {
        return score;
    }

    /**
     * count score.
     */
    public void setScore() {
        this.score =  1000000/(100+getSeconds()*getCount()); //gettime() returns long
    }

    /**
     * Get the total game time
     * @return the total game time
     */
    public int getSeconds() {
        return (int) Duration.between(getStartTime(),getEndTime()).getSeconds();
    }

    /**
     * Get player's name,
     * @return playerName.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Set the name of player,
     * @param playerName the name of player.
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Get the number of moves,
     * @return number of moves.
     */
    public int getCount() {
        return count;
    }

    /**
     * Set number of moves,
     * @param count number of moves.
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Get the start time of the game,
     * @return startTime.
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Set the start time of the game,
     * @param startTime startTime.
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Get the end time of the game,
     * @return end Time.
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Set the end time of the game,
     * @param endTime end time.
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Converts the class to JSON data ,
     * @return JSON Data of this object.
     */
    @Override
    public String toString() {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public static void main(String[] args) {

    }
}
