package SpaceInvaders.enemyMovementState;
import SpaceInvaders.entities.Enemy;

public class RegularMovement implements MovementState {
    @Override
    public void move(Enemy enemy) {
        enemy.setX(enemy.getX() + Enemy.velocity * Enemy.SPEED);
    }
}
