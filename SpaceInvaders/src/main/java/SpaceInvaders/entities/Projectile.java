package SpaceInvaders.entities;

import lombok.Getter;
import java.awt.*;

@Getter

public class Projectile extends Entity {
    private static final int HEIGHT = 5;
    private static final int WIDTH = 3;
    private static final int SPEED = 16;
    private static final Color COLOR = Color.YELLOW;

    private final int velocity;
    private final boolean fromPlayer;

    public Projectile(int x, int y, int velocity, boolean fromPlayer) {
        super(x+WIDTH/2, y, WIDTH, HEIGHT, COLOR);
        this.velocity = velocity;
        this.fromPlayer = fromPlayer;
    }

    public void move() {
        y += velocity * SPEED;
    }
}
