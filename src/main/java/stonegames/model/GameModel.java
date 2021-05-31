package stonegames.model;

import org.tinylog.Logger;

public class GameModel {
    /**
     * The array boxes of type box represents the state of the boxes
     */
    public Box boxes[];

    BallColor oldLocationColor1 , oldLocationColor2;
    /**
     * Creates a {@code GameModel} object that is initialized it with
     * the array boxes.
     */
    public GameModel() {
        boxes = new Box[16];
        for (int i = 0; i < 6; i++) {
            if(i %2==0){
            boxes[i] = new Box(BallColor.RED);
            } else {
                boxes[i] = new Box(BallColor.BLACK);
            }
        }
        for (int i = 6; i < 16; i++) {
            boxes[i] = new Box((BallColor.NONE));
        }
    }

    /**
     * Clear the box when two balls are selected
     *
     * @param oldLocation1 The position of first ball selected
     * @param oldLocation2 The position of second ball selected
     */
    public void setBallsToNone(int oldLocation1, int oldLocation2){
        if(!locationsDontContainBalls(oldLocation1,oldLocation2)){
            oldLocationColor1 = boxes[oldLocation1].getColor();
            oldLocationColor2 = boxes[oldLocation2].getColor();
            boxes[oldLocation1].setColor(BallColor.NONE);
            boxes[oldLocation2].setColor(BallColor.NONE);
        }else Logger.debug("empty boxes are clicked");
    }

    /**
     * Reset the balls to previous boxes
     *
     * @param oldLocation1 The position of first ball selected
     * @param oldLocation2 The position of second ball selected
     */
    public void resetBallsColor(int oldLocation1, int oldLocation2){
        boxes[oldLocation1].setColor(oldLocationColor1);
        boxes[oldLocation2].setColor(oldLocationColor2);
    }
    /**
     *Move the balls from original location to a new location
     *
     * @param oldLocation1 the original column of first stone
     * @param oldLocation2 the original column of second stone
     * @param newLocation1 the column of first stone to be moved
     * @param newLocation2 the column of second stone to be moved
     * @throws IndexOutOfBoundsException if the positions that are
     * trying to move into is invalid
     */
    public void setBallsLocation(int oldLocation1, int oldLocation2, int newLocation1,int newLocation2){
        if (isBallsNeighbour(oldLocation1, oldLocation2)){
            if(newLocation1 <0 || newLocation1>  15 || newLocation2 <0 || newLocation2 > 15 ){
                throw new IndexOutOfBoundsException();
            }
            else{
                if (locationsDontContainBalls(newLocation1, newLocation2)){
                    boxes[newLocation1].setColor(oldLocationColor1);
                    boxes[newLocation2].setColor(oldLocationColor2);
                    Logger.debug("Successful Move");
                    if(isGameComplete()) {
                        Logger.debug("Game Complete");
                    }
                }
                else Logger.debug("Locations already have balls");
            }
        }
        else Logger.debug("Balls are not neighbour");
    }

    public boolean canBallsMoved(int oldLocation1,int oldLocation2,int newLocation1,int newLocation2){
        if (isBallsNeighbour(oldLocation1,oldLocation2) &&
                isBallsNeighbour(newLocation1,newLocation2) &&
                locationsDontContainBalls(newLocation1,newLocation2)){
            return true;
        }
        return false;
    }

    /**
     * Returns whether two selected stones are neighbour or not
     *
     * @param location1 the column of first stone selected
     * @param location2 the column of the second stone selected
     * @return {@code true} if the selected stones are neighbour
     * and can be moved,{@code false} otherwise
     */
    public boolean isBallsNeighbour(int location1,int location2){
        if (location1 +1 == location2 || location1 - location2 == 1){
            return true;
        }
        return false;
    }

    /**
     * Returns whether two selected position that are moving to
     * already have stones or not
     *
     * @param location1 the column of the first box selected
     * @param location2 the column of the second box selected
     * @return {@code true} if the selected boxes are empty
     * and stones can be moved into,{@code false} otherwise
     */
    public boolean locationsDontContainBalls(int location1, int location2){
        if (boxes[location1].getColor() == BallColor.NONE && boxes[location2].getColor() == BallColor.NONE){
            return true;
        }
        return false;
    }

    /**
     * Returns whether one of the two selected position that are moving to
     * is empty or not
     *
     * @param location1 the column of the first box selected
     * @param location2 the column of the second box selected
     * @return {@code true} if one of the selected boxes are empty
     * and stones can be moved into,{@code false} otherwise
     */
    public boolean oneLocationDoesNotContainBall(int location1, int location2){
        if (boxes[location1].getColor() == BallColor.NONE || boxes[location2].getColor() == BallColor.NONE){
            return true;
        }
        return false;
    }

    /**
     * Returns whether the game is success or not
     *
     * @return {@code true} if the game is success,{@code false} otherwise
     */
    public boolean isGameComplete() {
        for (int i = 0; i < 16; i++) {
            if(boxes[i].getColor().equals(BallColor.RED)){
                return boxes[i].getColor().equals(BallColor.RED) &&
                        boxes[i + 1].getColor().equals(BallColor.RED) &&
                        boxes[i + 2].getColor().equals(BallColor.RED) &&
                        boxes[i + 3].getColor().equals(BallColor.BLACK) &&
                        boxes[i + 4].getColor().equals(BallColor.BLACK) &&
                        boxes[i + 5].getColor().equals(BallColor.BLACK);
            }
        }
        return false;
    }

    @Override
    public String toString() {

        for (int i = 0; i < boxes.length; i++) {
            System.out.println((i) +" GameModel{" +
                    "boxes=" + (boxes[i].getColor()) +
                    '}');
        }
        return "";
    }

    public static void main(String[] args) {
        GameModel model = new GameModel();
        model.setBallsToNone(1,2);
        model.setBallsLocation(1,2,6,7);
        model.setBallsToNone(5,6);
        model.setBallsLocation(5,6, 1,2);
        model.setBallsToNone(3,4);
        model.setBallsLocation(3,4, 5,6);
        model.setBallsToNone(1,2);
        model.setBallsLocation(1,2, 3,4);
        model.setBallsToNone(6,7);
        model.setBallsLocation(6,7, 1,2);
        System.out.println(model);

    }
}
