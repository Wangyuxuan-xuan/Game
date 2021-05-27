package stonegames.model;

import com.google.gson.Gson;

import java.util.Date;


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
    private Date startTime;
    /**
     * game end time.
     */
    private Date endTime;
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
     * Gain game time,
     * @return endTime.getSeconds() - startTime.getSeconds().
     */
    public int getSeconds() {
        return (int) (endTime.getTime()- startTime.getTime())/1000;
    }

    /**
     * player's name,
     * @return playerName.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * set the name of player,
     * @param playerName the name of player.
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * number of moves,
     * @return number of moves.
     */
    public int getCount() {
        return count;
    }

    /**
     * set number of moves,
     * @param count number of moves.
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * the start time of the game,
     * @return startTime.
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * set the start time of the game,
     * @param startTime startTime.
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * the end time of the game,
     * @return end Time.
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * the end time of the game,
     * @param endTime end time.
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Converts the class to GSON data and returns it,
     * @return GSON Data of this object.
     */
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
