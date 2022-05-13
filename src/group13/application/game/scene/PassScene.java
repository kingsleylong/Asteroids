package group13.application.game.scene;

import group13.application.game.GameEngine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PassScene extends BaseScene{
    Button returnMainHall;
    int score_;

    public PassScene(GameEngine gameEngine, int score_){
        super(gameEngine);
        this.score_ = score_;
        disPlayScore();
    }
    @Override
    public void createScene() {
        setGamePass();
        setReturnButton();
        this.getPane().setStyle("-fx-background-color: black");
    }

    private void setGamePass() {
        Text t = new Text(20, 200, "Congratulations!\n    You Won");
        t.setFill(Color.GREEN);
        Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 80);
        t.setFont(font);
        this.getPane().getChildren().addAll(t);
    }

    private void setReturnButton () {
        this.getPane().getChildren().add(ReturnButton(new Point2D(310, 430), "Main Menu"));
    }

    private void disPlayScore() {
        Text t = new Text(210, 370, "Your Score Is: "+ score_);
        t.setFill(Color.WHITE);
        Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 40);
        t.setFont(font);
        this.getPane().getChildren().addAll(t);
    }
}
