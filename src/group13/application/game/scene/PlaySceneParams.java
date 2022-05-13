package group13.application.game.scene;

import static group13.application.common.Constants.*;

/**
 * Represents the parameters required to create a new play scene
 */
public class PlaySceneParams {
    /**
     * Number of Asteroids that will be created
     */
    private int numberOfAsteroids;

    /**
     * Frequency of an Alien ship be created
     */
    private int frequencyOfAlienShip;

    /**
     * Get an instance of PlaySceneParam by game level
     * @param gameLevel
     * @return
     */
    public static PlaySceneParams getConfig(int gameLevel) {
        int numberOfAsteroids = (int) (BASE_NUMBER_OF_ASTEROIDS + gameLevel * MULTIPLIER_OF_ASTEROIDS);
        int frequencyOfAlienShip = BASE_FREQ_OF_ALIEN_SHIP - gameLevel * FREQ_MULTIPLIER_OF_ALIEN_SHIP;
        return new PlaySceneParams(numberOfAsteroids, frequencyOfAlienShip);
    }

    public PlaySceneParams(int numberOfAsteroids, int frequencyOfAlienShip) {
        this.numberOfAsteroids = numberOfAsteroids;
        this.frequencyOfAlienShip = frequencyOfAlienShip;
    }

    public int getNumberOfAsteroids() {
        return numberOfAsteroids;
    }

    public int getFrequencyOfAlienShip() {
        return frequencyOfAlienShip;
    }
}
