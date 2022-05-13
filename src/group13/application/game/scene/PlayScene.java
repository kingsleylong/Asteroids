package group13.application.game.scene;

import group13.application.characters.Bullet;
import group13.application.characters.Character;
import group13.application.characters.asteroid.Asteroid;
import group13.application.characters.asteroid.LargeAsteroid;
import group13.application.characters.asteroid.MediumAsteroid;
import group13.application.characters.ship.EnemyShip;
import group13.application.characters.ship.PlayerShip;
import group13.application.game.GameEngine;
import group13.application.game.events.handlers.CollisionEventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static group13.application.common.Constants.*;

/**
 * This scene manages the life cycle of a game.
 */
public class PlayScene extends BaseScene {

    private int numberOfLives;
    private int gameLevel;
    private final static Random random = new Random();
    private long lastSecondsAlienShipAdded = 0;
    private PlaySceneParams playSceneParams;
    private boolean isGameContinue = true;
    private int score=0;
    private Label scoreLabel;
    private Label lifeLabel;
    private Label levelLabel;
    private ArrayList<Integer> scores;
    private HashMap<Integer, String> score_time;
    private int TopScores = 10;
    public static PlayerShip playerShip;
    public static EnemyShip alienShip;
    public static List<Bullet> bullets = new ArrayList<>();
    public static List<EnemyShip> enemyShips = new ArrayList<>();


    public PlayScene(GameEngine gameEngine) {
        super(gameEngine);
        this.scores = new ArrayList<>();
        this.score_time = new HashMap<>();
    }

    @Override
    public void createScene() {
        numberOfLives = DEFAULT_NUMBER_OF_LIVES;
        gameLevel = GAME_LEVEL_START;
        this.playSceneParams = PlaySceneParams.getConfig(this.gameLevel);
        createSceneByGameLevel();
        this.addEventFilter(COLLISION, new CollisionEventHandler(this));
    }

    // Display the score on screen
    private void displayScore(){
        scoreLabel = new Label();
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setText("SCORES : "+ score);
        scoreLabel.setAlignment(Pos.TOP_LEFT);
        scoreLabel.setPadding(new Insets(10));
        scoreLabel.setFont(Font.font(20));
        this.getPane().getChildren().addAll(scoreLabel);
    }
    // Display the level on screen
    private void displayLevel(){
        levelLabel = new Label();
        levelLabel.setTextFill(Color.WHITE);
        levelLabel.setText("GAME LEVEL : "+ gameLevel);
        levelLabel.setAlignment(Pos.TOP_LEFT);
        levelLabel.setLayoutX(10);
        levelLabel.setLayoutY(60);
        levelLabel.setFont(Font.font(20));
        this.getPane().getChildren().addAll(levelLabel);

    }
    // getters  for score and lives
    public int getScore() {
        return score;
    }
    public int getLives(){return numberOfLives;}

    // Display lives
    private void displayLife() {
        lifeLabel = new Label();
        lifeLabel.setTextFill(Color.WHITE);
        lifeLabel.setText("LIVES : "+ numberOfLives);
        //System.out.println("the total number of lives"+numberOfLives);
        lifeLabel.setAlignment(Pos.TOP_LEFT);
        lifeLabel.setLayoutX(10);
        lifeLabel.setLayoutY(35);
        lifeLabel.setFont(Font.font(20));
        this.getPane().getChildren().addAll(lifeLabel);
    }

    // New scenes for new levels
    private void createSceneByGameLevel() {
        System.out.println("number of ads should be created:"+this.playSceneParams.getNumberOfAsteroids());
        for (int i = 0; i < this.playSceneParams.getNumberOfAsteroids(); i++) {
            LargeAsteroid largeAsteroid = new LargeAsteroid();
            System.out.println("creating an asteroid now");
            getPane().getChildren().add(largeAsteroid);
        }

        createNewPlayerShip(250, 200);
        displayScore();
        displayLife();
        displayLevel();
        this.getPane().setStyle("-fx-background-color: black");

    }

    // Return new player ship
    public void createNewPlayerShip(int x, int y) {
        PlayScene.playerShip = new PlayerShip(SCENE_WIDTH / 2, SCENE_HEIGHT / 2);
        // set a safe location for the player ship
        findASafePoint(playerShip);
        this.getPane().getChildren().addAll(playerShip);
    }

    // Add new alien ship to game
    private void createNewAlienShip() {
        alienShip = new EnemyShip(0, 0);
        this.getPane().getChildren().addAll(alienShip);
        PlayScene.enemyShips.add(alienShip);
    }

    public void upgrade() {
        // some events may arrive later after game is over
        if (this.isGameContinue) {
            if (this.gameLevel == GAME_LEVEL_MAX) {
                pass();
                return;
            }
            resetScene();
            this.gameLevel++;
            setLevelLabel();
            this.playSceneParams = PlaySceneParams.getConfig(this.gameLevel);
            System.err.println("upgraded"+gameLevel);
            // create a new scene of the next level
            createSceneByGameLevel();
        }
    }

    // will be called when there are no lives
    public void end() {
        System.out.println("GAME OVER!");
        addRecord();
        getGameEngin().gameOver(getScore());
        this.isGameContinue = false;
    }

    private String getCurrentTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    private void AddAndMaintain(){
        // read all the scores and add new score, and rank them
        URL url = PlayScene.class.getClassLoader().getResource("Score.txt");
        File file = new File(url.getPath());
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String nextLIne = scanner.nextLine();
                if(! nextLIne.equals("")){
                    String[] line = nextLIne.split(" ");
                    score_time.put(Integer.parseInt(line[0]), " "+line[1] + " " + line[2]+" "+line[3]);
                }
            }
        }
        catch(IOException e){
            System.out.println("failed to read in");
        }
        score_time.put(getScore(), " Date: "+getCurrentTime());
        scores.addAll(score_time.keySet());
        scores.sort(Collections.reverseOrder());
    }

    // write the content from score_time hashmap to the Score file
    private void writeMapToFile() {
        URL url = PlayScene.class.getClassLoader().getResource("Score.txt");
        File file = new File(url.getPath());
        try {
            // clear the previous records
            FileWriter fwClean = new FileWriter(file, false);
            fwClean.write("");
            fwClean.close();

            // write the map to the file
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < scores.size(); i++) {
                bw.write(scores.get(i) + score_time.get(scores.get(i))+"\n");
            }
            bw.close();
            fw.close();

        } catch(IOException e){
            System.out.println("failed to write");
        }
    }

    private void addRecord() {
        AddAndMaintain();
        if(score_time.size() != scores.size()){
            System.out.println("big error!");
        }
        else {
            if(score_time.size() == TopScores+1){
                int remove = scores.remove(TopScores);
                score_time.remove(remove);
            }
            writeMapToFile();
        }
    }

    public void pass() {
        System.err.println("Congratulation! You have passed all the levels!");
        addRecord();
        getGameEngin().gamePass(getScore());
        this.isGameContinue = false;
    }

    private void setLifeLabel() {
        lifeLabel.setText("LIVES : "+ numberOfLives);
    }

    public void setScoreLabel() {
        scoreLabel.setText("SCORES : "+ score );
    }

    public void setLevelLabel(){levelLabel.setText("GAME LEVEL : "+gameLevel); }


    // add score according to the level of the asteroid that the user has destroyed
    public void AddScore(Asteroid as) {
        if (this.isGameContinue) {
            System.out.println("Current total score: " + score);
            if (as instanceof LargeAsteroid){
                score += 10;
            } else if (as instanceof MediumAsteroid){
                score += 15;
            } else{
                score += 20;
            }
        }
        setScoreLabel();
    }

    public void AddScoreShip(int addScore) {
        if (this.isGameContinue) {
            System.out.println("Current total score: " + score);
            score += addScore;
        }
        setScoreLabel();
    }

    public void decrementLives() {
        if (this.isGameContinue) {
            if (numberOfLives == 1) {
                end();
            }
            else if (numberOfLives > 1) {
                numberOfLives-=1;
                createNewPlayerShip(250, 200);
                setLifeLabel();
            }
        }
    }

    /**
     * Reset the current scene including:
     * 1. remove the KeyEvent listener for the player from the scene to avoid duplicate listeners
     * 2. create a new pane for the scene (and disable the current pane)
     */
    private void resetScene() {
        PlayScene.bullets.clear();
        this.newPane();
        this.lastSecondsAlienShipAdded = 0;
    }

    /**
     * Remove the player key handler from event filter
     */

    /**
     * Check in every frame if an alien ship should be added into the scene
     *
     * @param timeInNanoSeconds
     */
    public void addAlienShips(long timeInNanoSeconds) {
        long currentTimeInSeconds = TimeUnit.SECONDS.convert(timeInNanoSeconds, TimeUnit.NANOSECONDS);
        if (this.lastSecondsAlienShipAdded == 0) {
            this.lastSecondsAlienShipAdded = currentTimeInSeconds;
        } else {
            if (this.isGameContinue
                    && this.lastSecondsAlienShipAdded < currentTimeInSeconds
                    && this.lastSecondsAlienShipAdded + this.playSceneParams.getFrequencyOfAlienShip()
                    == currentTimeInSeconds) {
                System.out.println("Adding new alien ship.");
                createNewAlienShip();
                this.lastSecondsAlienShipAdded = currentTimeInSeconds;
            }
        }
    }

    public void findASafePoint(Character target) {
        boolean found = true;

        int counter = 0;
        while (counter++ < 100) {

            int randX = random.nextInt(SCENE_WIDTH);
            int randY = random.nextInt(SCENE_HEIGHT);

            target.setTranslateX(randX);
            target.setTranslateY(randY);

            for (Node node : getPane().getChildren()) {
                if (node != target && node instanceof Character) {
                    Character character = (Character) node;
                    for (float i = 0.5f; i < 3; i += 0.5f) {
                        Point2D futurePosition = character.getFuturePosition(i);
                        System.out.format("targe position:, %s\n", target.getCurrentPosition());
                        System.out.format("Current position:, %s\n", character.getCurrentPosition());
                        System.out.format("Future position:, %s\n", futurePosition);

                        boolean currentOverlap = target.getBoundsInParent().intersects(character.getBoundsInParent());
                        boolean futureOverlap = target.getBoundsInParent().intersects(
                                futurePosition.getX(),
                                futurePosition.getY(),
                                character.getBoundsInParent().getWidth() * 2,
                                character.getBoundsInParent().getHeight() * 2);

                        if (currentOverlap || futureOverlap) {
                            System.out.println("overlapped");
                            int newRandX = random.nextInt(SCENE_WIDTH);
                            int newRandY = random.nextInt(SCENE_HEIGHT);

                            while (newRandX / 10 == randX / 10 && newRandY / 10 == randY / 10) {
                                newRandX = random.nextInt(SCENE_WIDTH);
                                newRandY = random.nextInt(SCENE_HEIGHT);
                            }

                            target.setTranslateX(newRandX);
                            target.setTranslateY(newRandY);

                            found = false;
                            break;
                        }
                    }
                }
            }
            if (found) {
                System.out.println("found a safe place");
                target.setVelocity(new Point2D(0, 0));
                System.out.println("Number of guesses: " + counter);
                return;
            }
            System.out.println("Error: Didn't find a safe place!!!");
        }
        target.setVelocity(new Point2D(0, 0));
        System.out.println("Number of guesses: " + counter);
    }
}