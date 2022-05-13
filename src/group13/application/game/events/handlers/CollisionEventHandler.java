package group13.application.game.events.handlers;

import group13.application.characters.Bullet;
import group13.application.characters.EnemyBullet;
import group13.application.characters.asteroid.Asteroid;
import group13.application.characters.ship.EnemyShip;
import group13.application.characters.ship.PlayerShip;
import group13.application.game.events.CollisionEvent;
import group13.application.game.scene.PlayScene;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * As denoted by the class name, this class handles the collision event.
 *
 * @author longyu
 * @date 2022/3/28 23:08
 */
public class CollisionEventHandler implements EventHandler<CollisionEvent> {
    private PlayScene playScene;

    public CollisionEventHandler(PlayScene playScene) {
        this.playScene = playScene;
    }

    @Override
    public void handle(CollisionEvent event) {
        event.consume();

        //System.out.println("collisionEvent caught, source: " + event.getSource());
        //System.out.println("collisionEvent caught, target: " + event.getTarget());
        //System.out.format("Node 1: %s \nNode 2: %s\n", event.getNode1(), event.getNode2());
        // remove the nodes that collide
        Pane pane = (Pane)event.getTarget();
        PlayScene.bullets.remove(event.getNode1());
        PlayScene.bullets.remove(event.getNode2());
//        PlayScene.bullets.remove(event.getNode1());
//        PlayScene.bullets.remove(event.getNode2());
        pane.getChildren().remove(event.getNode1());
        pane.getChildren().remove(event.getNode2());

        // if either node is a PlayerShip, then decrement lives
        if (event.getNode1() instanceof PlayerShip || event.getNode2() instanceof PlayerShip) {
            this.playScene.decrementLives();
        }

        // if either of the node is an asteroid - split, if the other node is a bullet - add score
        if (event.getNode1() instanceof Asteroid || event.getNode2() instanceof Asteroid){
            if(event.getNode1() instanceof Asteroid){
                Asteroid[] asteroids = ((Asteroid) event.getNode1()).split();
                if (asteroids != null) {
                    pane.getChildren().addAll(((Asteroid) event.getNode1()).split()[0], ((Asteroid) event.getNode1()).split()[1]);
                }
                if (event.getNode2() instanceof Bullet){
                    this.playScene.AddScore((Asteroid)event.getNode1());
                }
            }
            else if(event.getNode2() instanceof Asteroid){
                Asteroid[] asteroids = ((Asteroid) event.getNode2()).split();
                if (asteroids != null) {
                    pane.getChildren().addAll(((Asteroid) event.getNode2()).split()[0], ((Asteroid) event.getNode2()).split()[1]);
                }
                if (event.getNode1() instanceof Bullet){
                    this.playScene.AddScore((Asteroid)event.getNode2());
                }
            }
        }

        // if the bullet has hit the enemyShip
        if (event.getNode1() instanceof Bullet && event.getNode2() instanceof EnemyShip || event.getNode2() instanceof Bullet && event.getNode1() instanceof EnemyShip){
            this.playScene.AddScoreShip(30);
        }

        // if number of Asteroids is equal to 0, then upgrade game
        // else if number of Asteroids is greater than 1, then game continue
        for (Node node : pane.getChildren()) {
            if (node instanceof Asteroid) {
                return;
            }
        }
        this.playScene.upgrade();
    }
}
