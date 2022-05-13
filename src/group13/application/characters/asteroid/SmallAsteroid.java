package group13.application.characters.asteroid;

import javafx.geometry.Point2D;

public class SmallAsteroid extends Asteroid {

    public SmallAsteroid(Point2D position, double speed){
        super(speed, position);
    }

    public SmallAsteroid() {
        super(1.5);
    }

}
