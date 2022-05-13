package group13.application.game;

import group13.application.characters.Character;
import group13.application.game.scene.WelcomeScene;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;

public class WelcomeSceneBackgroundController extends AnimationTimer {
    private WelcomeScene welcome;

    public WelcomeSceneBackgroundController(WelcomeScene welcome) {
        this.welcome = welcome;
    }

    @Override
    public void handle(long timeInNanoseconds) {

        // move the Characters in the background
        for (Node node : welcome.getPane().getChildren()) {
            if (node instanceof Character) {
                ((Character) node).move();
            }
        }
    }
}
