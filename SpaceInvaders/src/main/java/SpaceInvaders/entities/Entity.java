package SpaceInvaders.entities;

import SpaceInvaders.Game;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

@Getter
@Setter

public abstract class Entity {
    protected int x, y;
    protected int width;
    protected int height;

    private Image image;
    private Color color;

    public Entity(int x, int y, int width, int height, String imageName) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        try {
            image = ImageIO.read(getClass().getResource("/"+imageName+".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Entity(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public abstract void move();

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

    public void render(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, null);
        } else {
            g.setColor(color);
            g.fillRect(x, y, width, height);
        }
    }
}
