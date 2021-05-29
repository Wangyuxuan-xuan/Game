package stonegames.model;

import org.tinylog.Logger;

public class GameModel {
    public Box boxes[];


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

    public void setBallsLocation(int oldLocation1, int oldLocation2, int newLocation1,int newLocation2){
        if (isBallsNeighbour(oldLocation1, oldLocation2)){
            if(newLocation1 <0 || newLocation1>  15 || newLocation2 <0 || newLocation2 > 15 ){
                throw new IndexOutOfBoundsException();
            }
            else{
                if (locationsDontContainBalls(newLocation1, newLocation2)){
                    BallColor color1, color2;
                    color1 = boxes[oldLocation1].getColor();
                    color2 = boxes[oldLocation2].getColor();
                    boxes[oldLocation1].setColor(BallColor.NONE);
                    boxes[oldLocation2].setColor(BallColor.NONE);
                    boxes[newLocation1].setColor(color1);
                    boxes[newLocation2].setColor(color2);
                    Logger.debug("Successful Move");
                    if(isGameComplete()){

                        Logger.debug("Game Complete");
                    }
                }
                else{
                    Logger.debug("Locations already have balls");
                }

            }

        }


    }

    public boolean isBallsNeighbour(int location1,int location2){
        if (location1 +1 == location2 || location1 - location2 == 1){
            return true;
        }
        return false;
    }

    public boolean locationsDontContainBalls(int location1, int location2){
        if (boxes[location1].getColor() == BallColor.NONE && boxes[location2].getColor() == BallColor.NONE){
            return true;
        }
        return false;
    }

    public boolean oneLocationDoesNotContainBall(int location1, int location2){
        if (boxes[location1].getColor() == BallColor.NONE || boxes[location2].getColor() == BallColor.NONE){
            return true;
        }
        return false;
    }
    public boolean isGameComplete() {
        for (int i = 0; i < 16; i++) {
            if (i > 5 && !(boxes[i].getColor().equals(BallColor.NONE))) {
                return false;
            }
            if (i < 3 && !(boxes[i].getColor().equals(BallColor.RED))) {
                return false;
            }
        }
        return true;
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

    public Box[] getBoxes() {
        return boxes;
    }

    public static void main(String[] args) {
        GameModel model = new GameModel();
        model.setBallsLocation(1,2,6,7);
        model.setBallsLocation(5,6, 1,2);
        model.setBallsLocation(3,4, 5,6);
        model.setBallsLocation(1,2, 3,4);
        model.setBallsLocation(6,7, 1,2);
        System.out.println(model);


    }
}
