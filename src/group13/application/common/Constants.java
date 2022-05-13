package group13.application.common;

import javafx.event.EventType;

/**
 * @author longyu
 * @date 2022/3/25 23:30
 */
public class Constants {
    public static final String GAME_TITLE = "Asteroids";

    public static final int GAME_LEVEL_START = 1;

    public static final int GAME_LEVEL_MAX = 2;

    public static final int DEFAULT_NUMBER_OF_LIVES = 20;

    public static final int SCENE_WIDTH = 800;

    public static final int SCENE_HEIGHT = 600;

    public static final int BASE_NUMBER_OF_ASTEROIDS = 1;

    public static final int BASE_FREQ_OF_ALIEN_SHIP = 10;

    public static final double MULTIPLIER_OF_ASTEROIDS = 1;

    public static final int FREQ_MULTIPLIER_OF_ALIEN_SHIP = 2;

    public static final EventType COLLISION = new EventType("collision");

    public static final EventType PLAYER_SHIP_DESTROYED = new EventType("player ship destroyed");

    public static final double playerAcceleration = 0.05;

    public static final int playerTurnRate = 4;

    public static final double bulletTimeToLive = 50;

    public static final double enemybulletTimeToLive = 40;

    public static final double counterIncrement = 1;

    public static final int enemyTimeToLive = 900;

    public static final int enemyMoveLeft = 180;

    public static final int enemyMoveRight = 0;

    public static final int enemyTurnRate = 30;

    public static final int enemyBulletBaseSpeed = 5;

    public static final double enemySpeedMin = 0.35;

    public static final double enemySpeedMax = 1.75;

    public static final int countFireIncrement = 100;

    public static final int countRotateIncrement = 200;

}


