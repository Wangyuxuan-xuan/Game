package state;

import org.junit.jupiter.api.Test;
import stonegames.model.BallColor;
import stonegames.model.Box;

import static org.junit.jupiter.api.Assertions.*;

public class BoxTest {
    @Test
    public void testBoxConstructor(){
        Box box = new Box(BallColor.RED);
        assertEquals(box.getColor(),BallColor.RED);
        Box box1 = new Box(BallColor.BLACK);
        assertEquals(box1.getColor(),BallColor.BLACK);
        Box box2 = new Box(BallColor.NONE);
        assertEquals(box2.getColor(),BallColor.NONE);
    }
}
