package main_package;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashMap;

public class Player  {

    private Node entity;
    private int levelWidth;
    private double radius;
    private boolean canJump = true;
    private HashMap<KeyCode, Boolean> keys;
    private ArrayList<Node> platforms = new ArrayList<Node>();
    private Point2D playerVelocity = new Point2D(0, 0);

    public Player(int x, int y, Color color, ArrayList<Node> platforms, HashMap<KeyCode, Boolean> keys, int levelWidth) {
        Circle circle = new Circle(25);
        radius = circle.getRadius();
        circle.setTranslateX(x);
        circle.setTranslateY(y);
        circle.setFill(color);
        entity = circle;
        this.platforms = platforms;
        this.keys = keys;
        this.levelWidth = levelWidth;
    }

    public Node getEntity() {
        return entity;
    }

    public void setVelocity(Point2D playerVelocity) {
        this.playerVelocity = playerVelocity;
    }

    public Bullet shoot(double sceneX, double sceneY) {
        Bullet bullet = new Bullet(entity.getTranslateX(), entity.getTranslateY(),sceneX - entity.getTranslateX(), sceneY - entity.getTranslateY(), Color.RED);
        return bullet;
    }

    public void move() {
        if (isPressed(KeyCode.W) && entity.getTranslateY() >= 5) {
            jump();
        }

        if (isPressed(KeyCode.A) && entity.getTranslateX() >= 5) {
            moveX(-5);
        }

        if (isPressed(KeyCode.D) && entity.getTranslateX() + 40 <= levelWidth - 5) {
            moveX(5);
        }

        if (playerVelocity.getY() < 10) {
            setVelocity(playerVelocity.add(0, 1));
        }

        moveY((int) playerVelocity.getY());
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    private void jump() {
        if (canJump) {
            playerVelocity = new Point2D(playerVelocity.getX(), -20);
            canJump = false;
        }
    }

    private void moveX(int value) {
        boolean movingRight = value > 0;

        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : platforms) {
                if (entity.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        if (entity.getTranslateX() + radius == platform.getTranslateX()) {
                            moveX(-1);
                            return;
                        }
                    } else {
                        if (entity.getTranslateX() - radius == platform.getTranslateX() + 60) {
                            moveX(1);
                            return;
                        }
                    }
                }
            }
            entity.setTranslateX(entity.getTranslateX() + (movingRight ? 1 : -1));
        }
    }

    private void moveY(int value) {
        boolean movingDown = value > 0;

        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : platforms) {
                if (entity.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingDown) {
                        if (entity.getTranslateY() + radius == platform.getTranslateY()) {
                            entity.setTranslateY(entity.getTranslateY() - 1);
                            canJump = true;
                            playerVelocity = new Point2D(playerVelocity.getX(), - Math.abs(0.85 * playerVelocity.getY()));
                            return;
                        }
                    } else {
                        if (entity.getTranslateY() - radius == platform.getTranslateY() + 60) {
                            return;
                        }
                    }
                }
            }
            entity.setTranslateY(entity.getTranslateY() + (movingDown ? 1 : -1));
        }
    }
}