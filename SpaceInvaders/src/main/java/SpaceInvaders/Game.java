package SpaceInvaders;

import SpaceInvaders.enemyMovementState.DownMovement;
import SpaceInvaders.enemyMovementState.RegularMovement;
import SpaceInvaders.entities.Enemy;
import SpaceInvaders.entities.Entity;
import SpaceInvaders.entities.Player;
import SpaceInvaders.entities.Projectile;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game extends JFrame {
    public static final int DEFAULT_SIZE = 32;
    public static final int WIDTH = 16*DEFAULT_SIZE;
    public static final int HEIGHT = 12*DEFAULT_SIZE;
    public static final int FRAME_RATE = 24;
    public static final int MILLISECONDS_IN_A_SECOND = 1000;

    public static final int ENEMY_SPACING_X = (int)(DEFAULT_SIZE * 1.5);
    public static final int ENEMY_SPACING_Y = DEFAULT_SIZE;
    public static final int ENEMY_AMOUNT_X = (int)(WIDTH/ENEMY_SPACING_X/1.5);
    public static final int ENEMY_AMOUNT_Y = HEIGHT/ENEMY_SPACING_Y/2;

    private final Player player;
    private List<Enemy> enemies;
    private List<Projectile> projectiles;

    private final Random random = new Random();

    private int score;
    private boolean playerDead;
    Timer timer;

    public Game() {
        setTitle("Space Invaders");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        score = 0;
        playerDead = false;

        player = Player.getPlayer(this);
        addKeyListener(player);
        enemies = new ArrayList<>();
        spawnEnemies();
        projectiles = new ArrayList<>();

        Panel panel = new Panel();
        add(panel);
        pack();

        setVisible(true);
    }

    //

    private class Panel extends JPanel implements ActionListener {
        Panel() {
            setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
            setBackground(Color.BLACK);
            timer = new Timer(MILLISECONDS_IN_A_SECOND/FRAME_RATE, this);
            timer.start();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            player.render(g);
            for (Enemy enemy : enemies) {
                enemy.render(g);
            }
            for (Projectile projectile : projectiles) {
                projectile.render(g);
            }

            g.setColor(Color.green);
            g.setFont(new Font("Arial", Font.BOLD, DEFAULT_SIZE/2));
            g.drawString("Score: " + score, DEFAULT_SIZE, DEFAULT_SIZE/2);

            if (playerDead) {
                g.setColor(Color.red);
                Font font = new Font("Arial", Font.BOLD, DEFAULT_SIZE);
                FontMetrics metrics = g.getFontMetrics(font);
                g.setFont(font);
                g.drawString("GAME OVER", (Game.WIDTH - metrics.stringWidth("GAME OVER")) / 2, DEFAULT_SIZE * 4);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) { //game loop
            checkCollisions();

            player.move();
            for(Projectile projectile : projectiles) {
                projectile.move();
            }
            enemyLogic();

            if(enemies.isEmpty())
            {
                projectiles.clear();
                spawnEnemies();
            }

            repaint();
            if (playerDead)
                timer.stop();
        }

        private void enemyLogic() {
            if(isAtEdge(enemies) && Enemy.getState().getClass() != DownMovement.class) {
                Enemy.changeState(new DownMovement());
                Enemy.velocity = -Enemy.velocity;
            } else {
                Enemy.changeState(new RegularMovement());
            }

            for (Enemy enemy : enemies) {
                if (enemy.getY() >= player.getY()) {
                    playerDead = true;
                    break;
                }
                if (random.nextInt(1000) < 1) {
                    fireProjectile(enemy);
                }
                enemy.move();
            }
        }

        private void checkCollisions() {
            List<Projectile> deadProjectiles = new ArrayList<>();
            List<Enemy> deadEnemies = new ArrayList<>();
            for (Projectile projectile : projectiles) {
                if (projectile.getY() < 0 || projectile.getY() > Game.HEIGHT) {
                    deadProjectiles.add(projectile);
                    break;
                }
                Rectangle projectileRect = projectile.getRect();
                if (projectile.isFromPlayer()) {
                    for (Enemy enemy : enemies) {
                        if (projectileRect.intersects(enemy.getRect())) {
                            deadProjectiles.add(projectile);
                            deadEnemies.add(enemy);
                            score++;
                            break;
                        }
                    }
                } else {
                    if (projectileRect.intersects(player.getRect())) {
                        playerDead = true;
                        break;
                    }
                }
            }
            projectiles.removeAll(deadProjectiles);
            enemies.removeAll(deadEnemies);
        }
    }

    //

    public static boolean isAtEdge(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy.getX() <= 0 && Enemy.velocity == -1 || enemy.getX() >= Game.WIDTH - DEFAULT_SIZE && Enemy.velocity == 1) {
                return true;
            }
        }
        return false;
    }

    public void spawnEnemies() {
        Enemy.velocity = 1;

        for (int j = 1; j < ENEMY_AMOUNT_Y; j++) {
            for (int i = 1; i < ENEMY_AMOUNT_X; i++) {
                Enemy enemy = new Enemy(i*ENEMY_SPACING_X, j*ENEMY_SPACING_Y, DEFAULT_SIZE, DEFAULT_SIZE);
                enemies.add(enemy);
            }
        }
    }
    public void fireProjectile(Entity entity) {
        if (entity instanceof Player) {
            projectiles.add(new Projectile(entity.getX() + entity.getWidth()/2, entity.getY(), -1, true));
        } else if (entity instanceof Enemy) {
            projectiles.add(new Projectile(entity.getX() + entity.getWidth()/2, entity.getY() + entity.getHeight(), 1, false));
        }
    }

    public boolean isOver() {
        return playerDead;
    }
    public void restart() {
        score = 0;
        playerDead = false;
        enemies.clear();
        projectiles.clear();
        spawnEnemies();
        timer.start();
    }
}