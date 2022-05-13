package group13.application.characters.ship;

import group13.application.characters.Bullet;
import group13.application.characters.Character;
import group13.application.characters.asteroid.Asteroid;
import group13.application.game.scene.BaseScene;
import group13.application.game.scene.PlayScene;
import javafx.geometry.Point2D;

import static group13.application.game.PlaySceneController.playScene;

public class Ship extends Character {

    // Constructor used by child classes and inheriting from the superclass character
    public Ship(int x, int y, double... points) {
        super(x, y, points);
    }

    public Ship(int x, int y, boolean b, double d, double ... points) {
        super(x, y, b, d,  points);
    }


    // Method to fire a bullet in a straight line, speed is based on the firing ships speed and its direction of travel
    public void fire() {
        Bullet bullet = new Bullet((int)  (playScene.playerShip.getTranslateX()), (int)  (playScene.playerShip.getTranslateY()));
        bullet.setRotate(getRotate());
        PlayScene.bullets.add(bullet);
        double speed = 10 + Math.abs(Math.max(this.getVelocity().getX(), this.getVelocity().getX()));
        bullet.accelerate(speed);
        playScene.getPane().getChildren().add(bullet);
    }
}
