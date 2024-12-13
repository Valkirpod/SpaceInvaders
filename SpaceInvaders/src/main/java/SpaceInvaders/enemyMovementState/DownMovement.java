package SpaceInvaders.enemyMovementState;

import SpaceInvaders.entities.Enemy;

public class DownMovement implements MovementState {
    @Override
    public void move(Enemy enemy) {
        enemy.setY(enemy.getY() + enemy.getHeight());
    }
}
