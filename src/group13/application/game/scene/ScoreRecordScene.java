package group13.application.game.scene;

import group13.application.game.GameEngine;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 * @author Yating
 * @date 2022/4/09 14:19
 */
public class ScoreRecordScene extends BaseScene{

    public ScoreRecordScene(GameEngine gameEngine){
        super(gameEngine);
    }

    @Override
    public void createScene() {
        displayScore();
        setbackButton();
        this.getPane().setStyle("-fx-background-color: black");
    }

    private void displayScore() {
        URL url = PlayScene.class.getClassLoader().getResource("Score.txt");
        File file = new File(url.getPath());
        Text title = new Text(280, 60,"10 best scores");
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 30));
        this.getPane().getChildren().addAll(title);
        try {
            Scanner scanner = new Scanner(file);
            int start = 110;
            int rank = 1;
            while(scanner.hasNextLine()){
                Text text = new Text(240, start,rank+". "+scanner.nextLine());
                text.setFill(Color.GREEN);
                Font font = Font.font("Verdana", FontPosture.ITALIC, 18);
                text.setTextAlignment(TextAlignment.LEFT);
                text.setFont(font);
                rank+=1;
                start+=40;
                this.getPane().getChildren().addAll(text);
            }
        }catch(IOException e){
            System.out.println("fail to read score");
        }
    }

    private void setbackButton() {
        getPane().getChildren().add(ReturnButton(new Point2D(330, 540), "Back"));
    }

}
