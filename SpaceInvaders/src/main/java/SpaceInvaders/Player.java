package SpaceInvaders;

import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

@Getter

public class Player implements KeyListener {
    private int x;
    private int y;
    private Image image;
    private int velocity;
    private static int speed = 6;
    private Game game;

    public Player(int x, int y, Game game) {
        this.x = x;
        this.y = y;
        this.game = game;
        try {
            image = ImageIO.read(getClass().getResource("/Player.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void move() {
        if (x + velocity < Game.width - image.getWidth(null) && x + velocity > 0)
            x += velocity;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(game.isOver())
            game.restart();

        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            velocity = -1*speed;
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            velocity = 1*speed;
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT && velocity == -1*speed || e.getKeyCode() == KeyEvent.VK_RIGHT && velocity == 1*speed)
            velocity = 0;
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            game.firePlayerProjectile(this);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}
