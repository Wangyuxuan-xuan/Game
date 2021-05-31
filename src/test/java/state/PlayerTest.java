package state;

import org.junit.jupiter.api.Test;
import stonegames.model.Player;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {

    @Test
    public void testPlayer() {
        Player player=new Player();
        LocalTime startTime = LocalTime.of(14,40);
        player.setStartTime(startTime);
        player.setPlayerName("playertest");
        player.setCount(100);
        LocalTime endTime = LocalTime.of(14,41);
        player.setEndTime(endTime);
        player.setScore();


        assertNotNull(player);
        assertTrue("playertest".equals(player.getPlayerName()));
        assertTrue(100==player.getCount());
        assertTrue(startTime.equals(player.getStartTime()));
        assertTrue(endTime.equals(player.getEndTime()));
        assertTrue(1000000/((int)((Duration.between(startTime,endTime).getSeconds())*100+100))==player.getScore());
    }


}
