package main_package;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Enemy implements Runnable {

    int shooterCount;
    Node entity;
    Magazine magazine;
    private Pane gameRoot;
    private Player player;
    private ArrayList<Bullet> bulletsEnemy;
    private ArrayList<Node> platforms;

    public Enemy(double x, double y, ArrayList<Bullet> bulletsEnemy, ArrayList<Node> platforms, Pane gameRoot, Player player, Magazine magazine) {
        this.bulletsEnemy = bulletsEnemy;
        this.platforms = platforms;
        this.gameRoot = gameRoot;
        this.player = player;
        this.magazine = magazine;
        entity = new Rectangle(40, 40, Color.CORAL);
        entity.setTranslateX(x + 10);
        entity.setTranslateY(y + 20);
        shooterCount = 0;
        gameRoot.getChildren().add(entity);
    }

    Bullet makeBullet(double sceneX, double sceneY) {
        Bullet bullet = null;
        try {
            bullet = takeBullet();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bullet.setBullet(entity.getTranslateX(), entity.getTranslateY(), sceneX - entity.getTranslateX(), sceneY - entity.getTranslateY(), Color.GOLD);
        return bullet;
    }


    public void shoot() {

        if (shooterCount > 30) shooterCount = 0;
        shooterCount++;
        if (shooterCount != 1) return;
        Line line = new Line(entity.getTranslateX(), entity.getTranslateY(), player.getEntity().getTranslateX() + 5, player.getEntity().getTranslateY() + 5);
        for (Node platform : platforms) {
            int minX = (int) platform.getBoundsInParent().getMinX();
            int maxX = (int) platform.getBoundsInParent().getMaxX();
            int minY = (int) platform.getBoundsInParent().getMinY();
            int maxY = (int) platform.getBoundsInParent().getMaxY();
            for (int j = minY; j < maxY; j++) {
                if (line.contains(new Point2D(minX, j))) {
                    return;
                }
            }
            for (int j = minY; j < maxY; j++) {
                if (line.contains(new Point2D(maxX, j))) {
                    return;
                }
            }
            for (int i = minX; i < maxX; i++) {
                if (line.contains(new Point2D(i, minY))) {
                    return;
                }
                if (line.contains(new Point2D(i, maxY))) {
                    return;
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

    private Bullet takeBullet() throws InterruptedException {
        Bullet bullet;
        synchronized (magazine) {
            while (magazine.getMagazine().size() == 0) {
                magazine.wait();
            }
            bullet = magazine.getMagazine().get(magazine.getMagazine().size() - 1);
            magazine.getMagazine().remove(bullet);
            if (magazine.getMagazine().size() == magazine.getMaxSize() - 1) {
                magazine.notifyAll();
            }
        }
        System.out.println(magazine.getMagazine().size());
        return bullet;
    }

    @Override
    public void run() {

    }
}