package main_package;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Bullet {

    private Node entity;
    private Point2D velocity;

    public Bullet(double x, double y, double vecX, double vecY, Color color) {
        Circle circle = new Circle(5);
        circle.setFill(color);
        entity = circle;
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        velocity = new Point2D(vecX, vecY);
        velocity = velocity.normalize();
        velocity = velocity.multiply(10);
    }

    public void update() {
        entity.setTranslateX(entity.getTranslateX() + velocity.getX());
        entity.setTranslateY(entity.getTranslateY() + velocity.getY());
    }

    public Node getEntity() {
        return entity;
    }
}
