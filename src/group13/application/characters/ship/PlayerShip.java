package group13.application.characters.ship;

import group13.application.characters.asteroid.Asteroid;
import group13.application.game.scene.PlayScene;
import javafx.geometry.Point2D;

import static group13.application.common.Constants.playerTurnRate;

/**
 * @author longyu
 * @date 2022/3/26 15:21
 */

// Create a triangular player ship polygon based on the ship class
public class PlayerShip extends Ship {
    public PlayerShip(int x, int y) {
        super(x, y, -15, -15, 30, 0, -15, 15);
    }

    // method to change the player-ship angle by -5 degrees
    public void rotateLeft() {
        setRotate(getRotate() - playerTurnRate);
    }

    // method to change the player-ship angle by +5 degrees
    public void rotateRight() {
        setRotate(getRotate() + playerTurnRate);
    }

    // Method to implement the hyperspaceJump, player ship can disappear and reappear at a new point on the screen.
    public void hyperspaceJump(PlayScene playScene) {
        double rotate = playScene.playerShip.getRotate();
        playScene.getPane().getChildren().remove(playScene.playerShip);
        Point2D newPosition = Asteroid.start();
        playScene.createNewPlayerShip((int) newPosition.getX(), (int) newPosition.getY());
        playScene.playerShip.setRotate(rotate);
    }
}
