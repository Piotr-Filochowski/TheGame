package main_package;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Pocisk. Zabojczy dla gracza lub przeciwnika
 */
public class Bullet {
    private Node entity;

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    /**
     * Predkosc pocisku
     */
    private Point2D velocity;

    /** Tworzy pocisk. Nadaje mu predkosc i kierunek lotu, obliczajac wektor roznicy wspolrzednych celu kuli i jej startu.
     * Wektor jest nastepnie normalizowany oraz mnozony przez 10.
     * @param x wspolrzedna  X miejsca startu kuli
     * @param y wspolrzedna Y miejsca startu kuli
     * @param vecX wspolrzedna X wektora roznicy polorzenia celu i startu kuli
     * @param vecY wspolrzedna Y wektora roznicy polorzenia celu i startu kuli
     */
    public Bullet(double x, double y, double vecX, double vecY) {
        Circle circle = new Circle(5);
        circle.setFill(Color.DARKGOLDENROD);
        entity = circle;
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        velocity = new Point2D(vecX, vecY);
        velocity = velocity.normalize();
        velocity = velocity.multiply(10);
    }

    /**
     * Dodaje predkosc do wspolrzednych polozenia kuli
     */
    public void update() {
        entity.setTranslateX(entity.getTranslateX() + velocity.getX());
        entity.setTranslateY(entity.getTranslateY() + velocity.getY());
    }

    public Node getEntity() {
        return entity;
    }
}
