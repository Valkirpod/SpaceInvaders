package SpaceInvaders;
import SpaceInvaders.enemyMovementState.DownMovement;
import SpaceInvaders.entities.Enemy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EnemyTest {
    @Test
    public void moveTest() {
        Enemy enemy = new Enemy(0, 0, 1, 1);
        Enemy.velocity = 1;
        Enemy.SPEED = 1;
        enemy.setX(5);
        enemy.move();
        assertEquals("Moved right by 1.", 6, enemy.getX());
        Enemy.changeState(new DownMovement());
        enemy.move();
        enemy.move();
        assertEquals("Moved down by 2.",2, enemy.getY());
    }

    @Test
    public void collisionTest() {
        Enemy enemy1 = new Enemy(5, 5, 2, 2);
        Enemy enemy2 = new Enemy(4, 4, 1, 1);

        boolean collided = enemy1.getRect().intersects(enemy2.getRect());
        assertEquals("No collision", false, collided);
    }

    @Test
    public void edgeTest() {
        List<Enemy> enemies = new ArrayList<Enemy>();
        Enemy.velocity = -1;
        Enemy enemy1 = new Enemy(-5, 5, 2, 2);
        Enemy enemy2 = new Enemy(10, 4, 1, 1);
        enemies.add(enemy1);
        enemies.add(enemy2);

        boolean atEdge = Game.isAtEdge(enemies);
        assertEquals("atEdge true", true, atEdge);
    }
}
