package group13.application.characters.ship;
import group13.application.characters.EnemyBullet;
import javafx.geometry.Point2D;
import java.util.Random;

import static group13.application.common.Constants.*;
import static group13.application.game.PlaySceneController.playScene;

public class EnemyShip extends Ship {

    public EnemyShip(int x, int y) {
        super(x, y, true, enemyTimeToLive, -10, -10, -20, 20, 10, 10, 20, -20);

        double heightMin = 0 + this.getLayoutBounds().getHeight();
        double heightMax = SCENE_HEIGHT - this.getLayoutBounds().getHeight();

        // Create a random bool
        Random randomBool = new Random();

        // Based on random bool spawn enemy ship from one side of th screen at a random height and moving across the screen
        if ( randomBool.nextBoolean()) {
            Point2D start = new Point2D(SCENE_WIDTH, (int) (Math.random() * (heightMax - heightMin)) + heightMin);
            this.setTranslateX(start.getX());
            this.setTranslateY(start.getY());
            setRotate(enemyMoveLeft);
        } else {
            Point2D start = new Point2D(0, (int) (Math.random() * (heightMax - heightMin)) + heightMin);
            this.setTranslateX(start.getX());
            this.setTranslateY(start.getY());
            setRotate(enemyMoveRight);
        }
        accelerate(randomALienSpeed());
    }

    // Return a random speed for the alien ship
    private double randomALienSpeed() {
        Random randomSpeed = new Random(0);
        return (enemySpeedMin + (enemySpeedMax - enemySpeedMin) * randomSpeed.nextDouble());
    }

    // Turn the alien ship a random amount in a set range
    public void randomTurn() {
        setVelocity(new Point2D(0, 0));
        setRotate(getRotate() + ((Math.random() * (enemyTurnRate + enemyTurnRate)) - enemyTurnRate));
        accelerate(randomALienSpeed());
    }

    // Override of move class for the alien ship, does not return once passed out of the window
    @Override
    public void move() {
        this.setTranslateX(this.getTranslateX() + this.getVelocity().getX());
        this.setTranslateY(this.getTranslateY() + this.getVelocity().getY());
    }

    // Override of the fire method for the enemyship, aim towards the position of the playership and ser speed based on firing ships position
    // Source for getting playership position - https://stackoverflow.com/questions/9970281/java-calculating-the-angle-between-two-points-in-degrees
    @Override
    public void fire() {
        float angle = (float) Math.toDegrees(Math.atan2(playScene.playerShip.getTranslateY() - getTranslateY(),
                playScene.playerShip.getTranslateX() - getTranslateX()));
        EnemyBullet enemyBullet = new EnemyBullet((int) (playScene.alienShip.getTranslateX()), (int) (playScene.alienShip.getTranslateY()));
        enemyBullet.setRotate(angle);
        double speed = enemyBulletBaseSpeed + Math.abs(Math.max(this.getVelocity().getX(), this.getVelocity().getX()));
        enemyBullet.accelerate(speed);
        playScene.getPane().getChildren().add(enemyBullet);
    }
}




