package group13.application.game;

import group13.application.game.scene.*;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;

import static group13.application.common.Constants.GAME_TITLE;

/**
 * GameEngine includes all the functions of this game application, for instance,
 * welcome page, game scenes and rankings, etc.
 *
 * @author longyu
 * @date 2022/3/28 23:53
 */
public class GameEngine {

    private Stage stage;

    public GameEngine(Stage stage) {
        this.stage = stage;
        stage.setTitle(GAME_TITLE);
        stage.show();

        // Start the game Timeline, which is running forever
        // This is used to detect game events such as collision
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.INDEFINITE);
        //add the keyframe to the timeline
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    public void welcome() {
        WelcomeScene welcome = new WelcomeScene(this);
        stage.setScene(welcome);
        AnimationTimer timer = new WelcomeSceneBackgroundController(welcome);
        timer.start();
    }
    public void scoreDisplay() {
        ScoreRecordScene scoreRecordScene = new ScoreRecordScene(this);
        stage.setScene(scoreRecordScene);
    }

    public void instruction() {
        InstructionScene instructionScene = new InstructionScene(this);
        stage.setScene(instructionScene);
    }
    public void newGame() {
        PlayScene scene = new PlayScene(this);
        stage.setScene(scene);

        AnimationTimer timer = new PlaySceneController(scene);
        timer.start();
    }

    public void gameOver(int score_) {
        GameOverScene gameOver = new GameOverScene(this, score_);
        stage.setScene(gameOver);
    }

    public void gamePass(int score_){
        PassScene passScene = new PassScene(this, score_);
        stage.setScene(passScene);
    }

}
