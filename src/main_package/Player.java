package main_package;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * Gracz. Sterowany przez urzytkownika klawiatura oraz myszka.
 */
public class Player  {

    private Node entity;
    private double radius;
    public Node getEntity() {
        return entity;
    }

    private Point2D playerVelocity = new Point2D(0, 0);
    private boolean canJump = true;
    /**
     * Gracz posiada mozliwosc sprawdzenia kolizji z platformami
     */
    private ArrayList<Node> platforms = new ArrayList<Node>();

    public Player(int x, int y, Color color, ArrayList<Node> platforms) {
        Circle circle = new Circle(25);
        radius = circle.getRadius();
        circle.setTranslateX(x);
        circle.setTranslateY(y);
        circle.setFill(color);
        entity = circle;
        this.platforms = platforms;
    }

    /**
     * Skakanie poprzez zmiane wspolrzednej Y predkosci gracza
     */
    public void jumpPlayer() {
        if (canJump) {
            playerVelocity = new Point2D(playerVelocity.getX(), -20);
            canJump = false;
        }
    }


    /**
     * Poruszanie sie w linii X gracza, wraz ze sprawdzeniem ewentualnej kolizji z platformami
     * @param value
     */
    public void moveX(int value) {
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

    public Point2D getVelocity() {
        return playerVelocity;
    }

    public void setVelocity(Point2D playerVelocity) {
        this.playerVelocity = playerVelocity;
    }

    /**
     * Przesowa gracza we wspolrzednej Y w dol lub w gore (w zaleznosci czy aktualnie skacze, czy spada(movingDown))
     * @param value
     */
    public void moveY(int value) {
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

    /**
     * Strzela tworzac pccisk w miejscu gracza, oraz nadajac mu kierunek lotu wskazany przez kursor myszy.
     * @param sceneX
     * @param sceneY
     * @return
     */
    public Bullet shoot(double sceneX, double sceneY) {
        Bullet bullet = new Bullet(entity.getTranslateX(), entity.getTranslateY(),sceneX - entity.getTranslateX(), sceneY - entity.getTranslateY());
        return bullet;
    }
}
