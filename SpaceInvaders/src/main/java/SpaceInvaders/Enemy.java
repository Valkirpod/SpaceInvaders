package SpaceInvaders;

import lombok.Getter;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

@Getter

public class Enemy {
    private int x;
    private int y;
    private static int enemySpeed = 2;
    Image image;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            image = ImageIO.read(getClass().getResource("/Enemy.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void move(int velocity) {
        if (velocity != 0) {
            this.x += velocity*enemySpeed;
        }else {
            this.y += 32;
        }
    }
}
