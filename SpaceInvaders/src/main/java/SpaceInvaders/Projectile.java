package SpaceInvaders;

import lombok.Getter;
import java.awt.*;

@Getter

public class Projectile {
    private final int height = 5;
    private final int width = 3;
    private int x, y;
    private final int velocity;
    private final boolean fromPlayer;
    private static final int speed = 16;

    public Projectile(int x, int y, int velocity, boolean fromPlayer) {
        this.x = x+width/2;
        this.y = y;
        this.velocity = velocity;
        this.fromPlayer = fromPlayer;
    }

    public void move() {
        y += velocity * speed;
    }
}
