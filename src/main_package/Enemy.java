package main_package;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Enemy {

    int shooterCount;
    Node entity;
    private Pane gameRoot;
    private Player player;
    private ArrayList<Bullet> bulletsEnemy;
    private ArrayList<Node> platforms;

    public Enemy(double x, double y, ArrayList<Bullet> bulletsEnemy, ArrayList<Node> platforms, Pane gameRoot, Player player) {
        this.bulletsEnemy = bulletsEnemy;
        this.platforms = platforms;
        this.gameRoot = gameRoot;
        this.player = player;
        entity = new Rectangle(40, 40, Color.CORAL);
        entity.setTranslateX(x + 10);
        entity.setTranslateY(y + 20);
        shooterCount = 0;
    }

    Bullet makeBullet(double sceneX, double sceneY) {
        Bullet bullet = new Bullet(entity.getTranslateX(), entity.getTranslateY(), sceneX - entity.getTranslateX(), sceneY - entity.getTranslateY(), Color.GOLD);
        return bullet;
    }

    public void shoot() {

        if (shooterCount > 20) shooterCount = 0;
        shooterCount++;
        if (shooterCount != 1) return;
        Line line = new Line(entity.getTranslateX(), entity.getTranslateY(), player.getEntity().getTranslateX() + 5, player.getEntity().getTranslateY() + 5);
        for (Node platform : platforms) {
            int minX = (int) platform.getBoundsInParent().getMinX();
            int maxX = (int) platform.getBoundsInParent().getMaxX();
            int minY = (int) platform.getBoundsInParent().getMinY();
            int maxY = (int) platform.getBoundsInParent().getMaxY();
            for (int j = minY; j < maxY; j++) {
                for (int i = minX; i < maxX; i++) {
                    if (line.contains(new Point2D(i, j))) {
                        return;
                    }
                }
            }
        }
        Bullet bullet = makeBullet(player.getEntity().getTranslateX(), player.getEntity().getTranslateY());
        bulletsEnemy.add(bullet);
        gameRoot.getChildren().add(bullet.getEntity());
    }

    public Node getEntity() {
        return entity;
    }
}
