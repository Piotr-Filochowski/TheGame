package main_package;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Player  {

    private Node entity;
    private double radius;
    public Node getEntity() {
        return entity;
    }

    private Point2D playerVelocity = new Point2D(0, 0);
    private boolean canJump = true;
    private ArrayList<Node> platforms = new ArrayList<Node>();
    public Player(int x, int y, int w, int h, Color color, ArrayList<Node> platforms) {
        Circle circle = new Circle(25);
        radius = circle.getRadius();
        circle.setTranslateX(x);
        circle.setTranslateY(y);
        circle.setFill(color);
        circle.getProperties().put("alive", true);
        entity = circle;
        this.platforms = platforms;
    }

    public void jumpPlayer() {
        if (canJump) {
            playerVelocity = playerVelocity.add(0, -30);
            canJump = false;
        }
    }

    public void movePlayerX(int value) {
        boolean movingRight = value > 0;

        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : platforms) {
                if (entity.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingRight) {
                        if (entity.getTranslateX() + radius == platform.getTranslateX()) {
                            return;
                        }
                    } else {
                        if (entity.getTranslateX() - radius == platform.getTranslateX() + 60) {
                            return;
                        }
                    }
                }
            }
            entity.setTranslateX(entity.getTranslateX() + (movingRight ? 1 : -1));
        }
    }

    public Point2D getPlayerVelocity() {
        return playerVelocity;
    }

    public void setPlayerVelocity(Point2D playerVelocity) {
        this.playerVelocity = playerVelocity;
    }

    public void movePlayerY(int value) {
        boolean movingDown = value > 0;

        for (int i = 0; i < Math.abs(value); i++) {
            for (Node platform : platforms) {
                if (entity.getBoundsInParent().intersects(platform.getBoundsInParent())) {
                    if (movingDown) {
                        if (entity.getTranslateY() + radius == platform.getTranslateY()) {
                            entity.setTranslateY(entity.getTranslateY() - 1);
                            canJump = true;
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

    public Bullet shoot(double sceneX, double sceneY) {
        Bullet bullet = new Bullet(entity.getTranslateX(), entity.getTranslateY(),sceneX - entity.getTranslateX(), sceneY - entity.getTranslateY());
        return bullet;
    }
}
