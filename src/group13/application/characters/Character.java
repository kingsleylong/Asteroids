package group13.application.characters;

import group13.application.common.Constants;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Character extends Polygon {

    protected Polygon Character;
    private Point2D velocity;
    public int counter;
    private boolean isTimeOut = false;
    private double timeToLive;

    // All characters including ships asteroids and bullets, are different shapes of polygon
    // Constructor for character without a time to live limit e.g. player-ship
    public Character(double x, double y, double... points) {
        super(points);
        setTranslateX(x);
        setTranslateY(y);
        setFill(Color.WHITE);
        this.velocity = new Point2D(0, 0);
    }

    // Constructor for character with a time to live limit e.g. bullets
    public Character(int x, int y, boolean isTimeOut, double timeToLive, double... points) {
        this(x, y, points);
        setFill(Color.WHITE);
        this.isTimeOut = isTimeOut;
        this.timeToLive = timeToLive;
    }

    // Constrictor for asteroid objects, asteroid are customized by the asteroid class
    public Character(){
        super();
        this.velocity = new Point2D(0, 0);
    }

    // Get method for character
    public Polygon getCharacter() {
        return Character;
    }

    // Set velocity method for character
    public void setVelocity(Point2D v){
        this.velocity = v;
    }

    // Get velocity method for character
    public Point2D getVelocity() {
        return velocity;
    }

    // Method called each frame of the animation-timer for each character to adjust its position on the screen
    // Does this by changing the x and y positions based on velocity of character
    public void move() {
        this.setTranslateX(this.getTranslateX() + this.velocity.getX());
        this.setTranslateY(this.getTranslateY() + this.velocity.getY());

        // Additionally, handles character reaching th screen edge and allows them to reappear from the other side
        if (this.getTranslateX() + this.getLayoutBounds().getWidth() < 0)
            this.setTranslateX(Constants.SCENE_WIDTH);

        if (this.getTranslateX() > Constants.SCENE_WIDTH)
            this.setTranslateX(-this.getLayoutBounds().getWidth());

        if (this.getTranslateY() + this.getLayoutBounds().getHeight() < 0)
            this.setTranslateY(Constants.SCENE_HEIGHT);

        if (this.getTranslateY() > Constants.SCENE_HEIGHT)
            this.setTranslateY(-this.getLayoutBounds().getHeight());
    }

    // Allows forward movement of object, changes the characters velocity based on cos and tan angles and applies
    // modifier parameter to allow different behaviour for each character
    public void accelerate(double acceleration) {
        double changeX = Math.cos(Math.toRadians(this.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.getRotate()));

        changeX *= acceleration;
        changeY *= acceleration;

        this.velocity = this.velocity.add(changeX, changeY);
    }

    // Check if a character can time out, ie has time to live limit
    public boolean getIsTimeOut() {
        return isTimeOut;
    }

    // Check if character time to live has timed out
    public boolean checkTimeOut() {
        return this.counter > this.timeToLive;
    }

    // Getter for character time to live counter
    public double getCounter() {
        return counter;
    }

    // getter for current position of character
    public Point2D getCurrentPosition(){
        return new Point2D(this.getTranslateX(), this.getTranslateY());
    }

    // Check the future position of a character for safe positioning
    public Point2D getFuturePosition(float seconds){
        int numberOfFrames = (int) (70 * seconds);
        double futureX = this.getTranslateX() + this.velocity.getX() * numberOfFrames;
        double futureY = this.getTranslateY() + this.velocity.getY() * numberOfFrames;

        if (futureX + this.getLayoutBounds().getWidth() < 0) {
            futureX = Constants.SCENE_WIDTH;
        }

        if (futureX > Constants.SCENE_WIDTH)
            futureX = -this.getLayoutBounds().getWidth();

        if (futureY + this.getLayoutBounds().getHeight() < 0)
            futureY = Constants.SCENE_HEIGHT;

        if (futureY > Constants.SCENE_HEIGHT)
            futureY = -this.getLayoutBounds().getHeight();
        return new Point2D(futureX, futureY);
    }

}
