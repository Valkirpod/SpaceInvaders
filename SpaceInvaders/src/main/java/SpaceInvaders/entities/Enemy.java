package SpaceInvaders.entities;

import SpaceInvaders.enemyMovementState.MovementState;
import SpaceInvaders.enemyMovementState.RegularMovement;
import lombok.Getter;
import lombok.Setter;

@Getter

public class Enemy extends Entity {
    public static int SPEED = 3;
    private final static String IMAGE_NAME = "Enemy";

    public static int velocity;
    private static MovementState movementState = new RegularMovement();

    public Enemy(int x, int y, int width, int height) {
        super(x, y, width, height, IMAGE_NAME);
    }

    public void move() {
        movementState.move(this);
    }
    public static void changeState(MovementState state) {
        movementState = state;
    }
    public static MovementState getState() {
        return movementState;
    }
}
