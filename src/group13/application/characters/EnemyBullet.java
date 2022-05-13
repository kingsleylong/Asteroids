package group13.application.characters;

import static group13.application.common.Constants.enemybulletTimeToLive;

public class EnemyBullet extends Character {

    // Create an enemybullet based on the character class, includes time to live limit
    public EnemyBullet(int x, int y) {
        super(x, y, true, enemybulletTimeToLive, 2, -2, 2, 2, -2, 2, -2, -2);
    }
}






