package stonegames.model;

/**
 * Class representing a state of Box
 */
public class Box {
    /**
     * The color of type BallColor represent the state of stones
     */
    private BallColor color;

    /**
     * Set the state of boxes when initialize a box
     * @param color A enum of type BallColor represent the state of stones
     */
    public Box(BallColor color){
        this.color = color;
    }

    /**
     * Set the state of boxes and stones
     * @param color A enum of type BallColor represent the state of stones
     */
    public void setColor(BallColor color) {
        this.color = color;
    }

    /**
     * Get the box and stone state
     * @return the enum value represents the state
     */
    public BallColor getColor() {
        return color;
    }
}
