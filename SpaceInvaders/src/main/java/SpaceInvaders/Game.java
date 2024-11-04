package SpaceInvaders;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game extends JFrame {
    public static int width = 16*32;
    public static int height = 12*32;

    private final Player player;
    private List<Enemy> enemies;
    private List<Projectile> projectiles;

    private int enemyVelocity = 1;
    private final Random random = new Random();

    private int score;
    private boolean playerDead;
    Timer timer;

    public Game() {
        setTitle("Space Invaders");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        score = 0;
        playerDead = false;

        player = new Player(width / 2 -32, height -64, this);
        addKeyListener(player);
        enemies = new ArrayList<>();
        spawnEnemies();
        projectiles = new ArrayList<>();

        Panel panel = new Panel();
        add(panel);
        pack();

        setVisible(true);
    }

    public void spawnEnemies() {
        enemyVelocity = 1;
        for (int j = 1; j < height/32/2; j++) {
            for (int i = 1; i < width/32/1.5; i+=2) {
                Enemy enemy = new Enemy(i*32, j*32);
                enemies.add(enemy);
            }
        }
    }

    //public <T> void fireProjectile(T entity) {
    public void firePlayerProjectile(Player _player) {
        projectiles.add(new Projectile(player.getX()+16, player.getY(), -1, true));
    }
    public void fireEnemyProjectile(Enemy enemy) {
        projectiles.add(new Projectile(enemy.getX()+16, enemy.getY()+32, 1, false));
    }

    private class Panel extends JPanel implements ActionListener {
        Panel() {
            setPreferredSize(new Dimension(width, height));
            setBackground(Color.BLACK);
            timer = new Timer(1000/24, this);
            timer.start();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(player.getImage(), player.getX(), player.getY(), null);
            for (Enemy enemy : enemies) {
                g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), null);
            }
            g.setColor(Color.yellow);
            for (Projectile projectile : projectiles) {
                g.drawRect(projectile.getX(), projectile.getY(), projectile.getWidth(), projectile.getHeight());
            }

            g.setColor(Color.green);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Score: " + score, 32, 16);

            if (playerDead) {
                g.setColor(Color.red);
                Font font = new Font("Arial", Font.BOLD, 32);
                FontMetrics metrics = g.getFontMetrics(font);
                g.setFont(font);
                g.drawString("GAME OVER", (width - metrics.stringWidth("GAME OVER")) / 2, 64 * 2);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<Projectile> deadProjectiles = new ArrayList<>();
            List<Enemy> deadEnemies = new ArrayList<>();
            for (Projectile projectile : projectiles) {
                if (projectile.getY() < 0 || projectile.getY() > height) {
                    deadProjectiles.add(projectile);
                    break;
                }
                Rectangle projectileRect = new Rectangle(projectile.getX(), projectile.getY(), projectile.getWidth(), projectile.getHeight());
                if (projectile.isFromPlayer()) {
                    for (Enemy enemy : enemies) {
                        Rectangle enemyRect = new Rectangle(enemy.getX(), enemy.getY(), 32, 32);
                        if (projectileRect.intersects(enemyRect)) {
                            deadProjectiles.add(projectile);
                            deadEnemies.add(enemy);
                            score++;
                            break;
                        }
                    }
                } else {
                    Rectangle plrRect = new Rectangle(player.getX(), player.getY(), 32, 32);
                    if (projectileRect.intersects(plrRect)) {
                        playerDead = true;
                        break;
                    }
                }
            }
            projectiles.removeAll(deadProjectiles);
            enemies.removeAll(deadEnemies);

            player.move();

            for(Projectile projectile : projectiles) {
                projectile.move();
            }

            boolean atEdge = false;
            for (Enemy enemy : enemies) {
                if (enemy.getX() <= 0 && enemyVelocity == -1 || enemy.getX() >= width - 32 && enemyVelocity == 1) {
                    atEdge = true;
                    enemyVelocity *= -1;
                    break;
                }
                if (enemy.getY() == player.getY())
                    playerDead = true;
            }
            for (Enemy enemy : enemies) {
                if (atEdge) {
                    enemy.move(0);
                }else
                    enemy.move(enemyVelocity);
                if (random.nextInt(1000) < 1) {
                    fireEnemyProjectile(enemy);
                }
            }

            if(enemies.isEmpty())
            {
                projectiles.clear();
                spawnEnemies();
            }

            repaint();
            if (playerDead)
                timer.stop();
        }
    }

    public boolean isOver() {
        if (playerDead)
            return true;
        return false;
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