package group13.application.characters.asteroid;

import javafx.scene.transform.Scale;

public class LargeAsteroid extends Asteroid {
    public LargeAsteroid(){
        super(0.5);
    }

    @Override
    public Scale resize(){
        Scale s = new Scale();
        s.setX(4);
        s.setY(4);
        return s;
    }

}
