package state;

import org.junit.jupiter.api.Test;
import stonegames.model.BallColor;
import stonegames.model.GameModel;

import static org.junit.jupiter.api.Assertions.*;

public class GameModelTest {

    @Test
    public void testSetBallsToNone(){
        GameModel gameModel = new GameModel();
        gameModel.setBallsToNone(0,1);
        assertTrue(gameModel.boxes[0].getColor() == BallColor.NONE);
        assertTrue(gameModel.boxes[1].getColor() == BallColor.NONE);
        gameModel.setBallsToNone(2,3);
        assertTrue(gameModel.boxes[0].getColor() == BallColor.NONE);
        assertTrue(gameModel.boxes[0].getColor() == BallColor.NONE);
        gameModel.setBallsToNone(4,5);
        assertTrue(gameModel.boxes[0].getColor() == BallColor.NONE);
        assertTrue(gameModel.boxes[0].getColor() == BallColor.NONE);
    }

    @Test
    public void testResetBallsColor(){
        GameModel gameModel = new GameModel();
        gameModel.setBallsToNone(0,1);
        gameModel.resetBallsColor(0,1);
        assertTrue(gameModel.boxes[0].getColor() == BallColor.RED);
        assertTrue(gameModel.boxes[1].getColor() == BallColor.BLACK);
        gameModel.setBallsToNone(2,3);
        gameModel.resetBallsColor(2,3);
        assertTrue(gameModel.boxes[2].getColor() == BallColor.RED);
        assertTrue(gameModel.boxes[3].getColor() == BallColor.BLACK);
        gameModel.setBallsToNone(4,5);
        gameModel.resetBallsColor(4,5);
        assertTrue(gameModel.boxes[4].getColor() == BallColor.RED);
        assertTrue(gameModel.boxes[5].getColor() == BallColor.BLACK);
    }
    @Test
    public void testSetBallsLocation(){
        GameModel gameModel = new GameModel();

        assertThrows(IndexOutOfBoundsException.class, () -> gameModel.setBallsLocation(0, 1,15,16));
        assertThrows(IndexOutOfBoundsException.class, () -> gameModel.setBallsLocation(1, 0,-5,-6));
        assertThrows(IndexOutOfBoundsException.class, () -> gameModel.setBallsLocation(1, 0,-5,-6));
        assertThrows(IndexOutOfBoundsException.class, () -> gameModel.setBallsLocation(15, 14,0,-1));
        assertThrows(IndexOutOfBoundsException.class, () -> gameModel.setBallsLocation(8, 9,15,16));

    }

    @Test
    public void testCanBallsMoved(){
        GameModel gameModel = new GameModel();

        assertTrue(gameModel.canBallsMoved(1,2,3,4) == false);
        assertTrue(gameModel.canBallsMoved(1,2,14,15) == true);
        assertTrue(gameModel.canBallsMoved(4,5,3,4) == false);
        assertTrue(gameModel.canBallsMoved(2,3,5,6) == false);
        assertTrue(gameModel.canBallsMoved(2,3,8,9) == true);

    }
    @Test
    public void testIsBallsNeighbour(){
        GameModel gameModel = new GameModel();
        assertTrue(gameModel.isBallsNeighbour(0,1) == true);
        assertTrue(gameModel.isBallsNeighbour(4,3) == true);
        assertTrue(gameModel.isBallsNeighbour(7,8) == true);
        assertTrue(gameModel.isBallsNeighbour(14,15) == true);
        assertTrue(gameModel.isBallsNeighbour(15,14) == true);
        assertTrue(gameModel.isBallsNeighbour(0,6) == false);
        assertTrue(gameModel.isBallsNeighbour(3,6) == false);
        assertTrue(gameModel.isBallsNeighbour(6,3) == false);
        assertTrue(gameModel.isBallsNeighbour(7,3) == false);
        assertTrue(gameModel.isBallsNeighbour(5,10) == false);
    }

    @Test
    public void testLocationsDontContainBalls(){
        GameModel gameModel = new GameModel();
        assertTrue(gameModel.locationsDontContainBalls(1,2) == false);
        assertTrue(gameModel.locationsDontContainBalls(5,6) == false);
        assertTrue(gameModel.locationsDontContainBalls(3,5) == false);
        assertTrue(gameModel.locationsDontContainBalls(2,6) == false);
        assertTrue(gameModel.locationsDontContainBalls(8,9) == true);
        assertTrue(gameModel.locationsDontContainBalls(10,12) == true);
        assertTrue(gameModel.locationsDontContainBalls(10,15) == true);
        assertTrue(gameModel.locationsDontContainBalls(14,15) == true);
    }

    @Test
    public void testOneLocationDoesNotContainBall(){
        GameModel gameModel = new GameModel();
        assertTrue(gameModel.oneLocationDoesNotContainBall(0,1) == false);
        assertTrue(gameModel.oneLocationDoesNotContainBall(2,1) == false);
        assertTrue(gameModel.oneLocationDoesNotContainBall(3,5) == false);
        assertTrue(gameModel.oneLocationDoesNotContainBall(5,4) == false);
        assertTrue(gameModel.oneLocationDoesNotContainBall(5,6) == true);
        assertTrue(gameModel.oneLocationDoesNotContainBall(8,6) == true);
        assertTrue(gameModel.oneLocationDoesNotContainBall(6,8) == true);
        assertTrue(gameModel.oneLocationDoesNotContainBall(14,15) == true);
    }

    @Test
    public void testIsGameComplete(){
        GameModel gameModel = new GameModel();
        assertTrue(gameModel.isGameComplete() == false);
        gameModel.setBallsToNone(1,2);
        gameModel.setBallsLocation(1,2,6,7);
        assertTrue(gameModel.isGameComplete() == false);
        gameModel.setBallsToNone(5,6);
        gameModel.setBallsLocation(5,6, 1,2);
        assertTrue(gameModel.isGameComplete() == false);
        gameModel.setBallsToNone(3,4);
        gameModel.setBallsLocation(3,4, 5,6);
        assertTrue(gameModel.isGameComplete() == false);
        gameModel.setBallsToNone(1,2);
        gameModel.setBallsLocation(1,2, 3,4);
        assertTrue(gameModel.isGameComplete() == false);
        gameModel.setBallsToNone(6,7);
        gameModel.setBallsLocation(6,7, 1,2);
        assertTrue(gameModel.isGameComplete() == true);
    }
}
