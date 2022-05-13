package group13.application.characters;

import static group13.application.common.Constants.bulletTimeToLive;

public class Bullet extends Character {

    // Create a bullet based on the character class, includes time to live limit
    public Bullet(int x, int y) {
        super(x, y, true, bulletTimeToLive, 2.5, -2.5, 2.5, 2.5, -2.5, 2.5, -2.5, -2.5);
    }
}






