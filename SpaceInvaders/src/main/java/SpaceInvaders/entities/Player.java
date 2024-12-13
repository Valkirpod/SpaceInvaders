package SpaceInvaders.entities;

import SpaceInvaders.Game;
import lombok.Getter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Getter

public class Player extends Entity implements KeyListener {
    private final static String IMAGE_NAME = "Player";
    private final static int SPEED = 6;

    private final Game game;
    private int velocity;

    private static Player player;
    private Player(int x, int y, int width, int height, Game game) {
        super(x, y, width, height, IMAGE_NAME);
        this.game = game;
    }
    public static Player getPlayer(Game game) {
        if (player == null) {
            player = new Player(Game.WIDTH/2-Game.DEFAULT_SIZE, Game.HEIGHT - Game.DEFAULT_SIZE * 2, Game.DEFAULT_SIZE, Game.DEFAULT_SIZE, game);
        }
        return player;
    }

    public void move() {
        if (x + velocity*SPEED < Game.WIDTH - width && x + velocity*SPEED > 0)
            x += velocity*SPEED;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(game.isOver())
            game.restart();

        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            velocity = -1;
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            velocity = 1;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT && velocity == -1 || e.getKeyCode() == KeyEvent.VK_RIGHT && velocity == 1)
            velocity = 0;
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            game.fireProjectile(this);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}
