package stonegames.model;

public class Box {
    private BallColor color;

    public Box(BallColor color){
        this.color = color;
    }

    public void setColor(BallColor color) {
        this.color = color;
    }

    public BallColor getColor() {
        return color;
    }
}
