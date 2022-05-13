package group13.application.characters.asteroid;
import group13.application.characters.Character;
import group13.application.characters.Splittable;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

import java.util.Random;

import static group13.application.common.Constants.SCENE_HEIGHT;
import static group13.application.common.Constants.SCENE_WIDTH;

public class Asteroid extends Character implements Splittable  {
    public Asteroid(double speed){
        super();
        this.getPoints().addAll(generateShape());
        this.getTransforms().add(resize());

        //set the initial position of the asteroid
        this.setTranslateX(start().getX());
        this.setTranslateY(start().getY());

        this.setFill(Color.BLACK);
        this.setStroke(Color.WHITE);
        setRotate(new Random().nextInt(360));
        accelerate(speed);
        setWidth();
    }

    public Asteroid(double speed, Point2D position){
        super();
        this.getPoints().addAll(generateShape());
        this.getTransforms().add(resize());

        //set the initial position of the asteroid
        this.setTranslateX(position.getX());
        this.setTranslateY(position.getY());

        this.setFill(Color.BLACK);
        this.setStroke(Color.WHITE);
        setRotate(new Random().nextInt(360));
        accelerate(speed);
        setWidth();
    }

    // randomly generate the start position of an asteroid
    public static Point2D start(){
        Random r = new Random();
        return new Point2D(r.nextInt(SCENE_WIDTH-100), r.nextInt(SCENE_HEIGHT-100));
    }

    public void setWidth(){
        if(this instanceof LargeAsteroid){
            this.setStrokeWidth(0.15);
        }
        else if(this instanceof MediumAsteroid){
            this.setStrokeWidth(0.3);
        }
        else{
            this.setStrokeWidth(0.6);
        }
    }

    // randomly generate the coordinates of a polygon, return a Double[] as a coordinate
    public Double[] generateShape(){
        Random r = new Random();
        int shape = r.nextInt(4);
        return switch (shape) {
            case 0 -> new Double[]{8.0, 0.0, 15.0, 3.0, 13.0, 13.0, 9.0, 15.0, 0.0, 10.0};
            case 1 -> new Double[]{7.0, 0.0, 9.0, 3.0, 12.0, 0.0, 15.0, 10.0, 13.0, 14.0, 0.0, 12.0, 5.0, 8.0, 0.0, 3.0};
            case 2 -> new Double[]{0.0, 8.0, 10.0, 0.0, 15.0, 4.0, 12.0, 7.0, 15.0, 13.0, 9.0, 15.0};
            default -> new Double[]{3.0, 0.0, 10.0, 3.0, 10.0, 8.0, 15.0, 9.0, 13.0, 15.0, 2.0, 13.0, 0.0, 8.0};
        };
    }

    // resize an asteroid based on the type of it
    public Scale resize(){
        Scale s = new Scale();
        s.setX(2);
        s.setY(2);
        return s;
    }

    // set the initial position of the asteroid
    public void setInitialPosition(Point2D position){
        this.setTranslateX(position.getX());
        this.setTranslateY(position.getY());
    }


    public Point2D getCurrentPosition(){
        return new Point2D(this.getTranslateX(), this.getTranslateY());
    }

    @Override
    public Asteroid[] split() {
        if(this instanceof LargeAsteroid){
            // speed is between 0.5-0.7
            return new MediumAsteroid[]{new MediumAsteroid(getCurrentPosition(), 2 * Math.random()+0.5), new MediumAsteroid(getCurrentPosition(), 2*Math.random()+ 0.5)};
        }
        else if(this instanceof MediumAsteroid){
            // speed is between 0.8-1.0
            return new SmallAsteroid[]{new SmallAsteroid(getCurrentPosition(), 2 * Math.random()+0.8), new SmallAsteroid(getCurrentPosition(), 2*Math.random()+0.8)};
        }
        else{
            return null;
        }
    }
}
