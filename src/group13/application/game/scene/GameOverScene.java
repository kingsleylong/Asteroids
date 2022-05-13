package group13.application.game.scene;

import group13.application.game.GameEngine;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameOverScene extends BaseScene{
    int score_;

    public GameOverScene(GameEngine gameEngine, int score_) {
        super(gameEngine);
        this.score_ = score_;
        disPlayScore();
    }

    @Override
    public void createScene() {
        setGameOver();
        setReturnButton();
        this.getPane().setStyle("-fx-background-color: black");
    }

    private void setGameOver() {
        Text t = new Text(130, 280, "Game Over !");
        t.setFill(Color.RED);
        Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 80);
        t.setFont(font);
        this.getPane().getChildren().addAll(t);
    }
    private void setReturnButton() {
        this.getPane().getChildren().add(ReturnButton(new Point2D(310, 430), "Main Menu"));
    }

    private void disPlayScore() {
        Text t = new Text(220, 350, "Your Score Is: "+ Integer.toString(score_));
        t.setFill(Color.WHITE);
        Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 40);
        t.setFont(font);
        this.getPane().getChildren().addAll(t);
    }

}
