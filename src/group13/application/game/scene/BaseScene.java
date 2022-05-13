package group13.application.game.scene;

import group13.application.game.GameEngine;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import static group13.application.common.Constants.SCENE_HEIGHT;
import static group13.application.common.Constants.SCENE_WIDTH;

/**
 * The BaseScene creates the scene with default parameters, and creates the root node of the scene.
 * In the constructor, an abstract method createScene() is called.
 */
public abstract class BaseScene extends Scene {

    private Pane pane;
    private GameEngine gameEngine;

    /**
     * Convenient constructor for subclasses.
     */
    public BaseScene(GameEngine gameEngine) {
        // create the default scene
        super(new Pane(), SCENE_WIDTH, SCENE_HEIGHT, Color.BLACK);
        this.gameEngine = gameEngine;
        this.pane = (Pane) this.getRoot();
        this.createScene();
    }

    public GameEngine getGameEngin(){
        return gameEngine;
    }
    /**
     * Create the characters and add them to the root node.
     * The concrete implementation depends on the subclass.
     */
    public abstract void createScene();

    public Pane getPane() {
        return pane;
    }

    /**
     * Disable the current pane and create a new pane for the scene
     */
    public void newPane() {
        this.pane.setDisable(true);
        this.pane = new Pane();
        this.setRoot(this.pane);
    }

    public Button getButton(Point2D position, String text) {
        String hoverStyle = "-fx-background_color:white; -fx-text-fill: black";
        String exitStyle = "-fx-background-color: black; -fx-border-color: white; -fx-text-fill: white";
        Button button = new Button(text);
        button.setLayoutX(position.getX());
        button.setLayoutY(position.getY());
        button.setWrapText(false);
        button.setShape(new Polygon(100.0, 250.0, 700.0, 250.0, 600.0, 350.0, 0.0, 350.0));
        button.setStyle(exitStyle);
        button.setPadding(new Insets(5, 30, 5, 30));
        button.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
        button.setOnMouseEntered(e-> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(exitStyle));
        return button;
    }

    // arguments: the position of the button, the text that will be written on the label
    // return: a button that will load to the main hall(welcome scene) when being clicking
    public Button ReturnButton(Point2D position, String text) {
        Button returnMainHall = getButton(position,text);
        returnMainHall.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameEngine.welcome();
            }
        });
        return returnMainHall;
    }
}
