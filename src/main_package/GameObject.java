package main_package;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameObject {
    Node entity;
    public GameObject(int x, int y, int w, int h, Color color) {
        Rectangle rectangle = new Rectangle(w, h);
        rectangle.setTranslateX(x);
        rectangle.setTranslateY(y);
        rectangle.setFill(color);
        rectangle.getProperties().put("alive", true);
        entity = rectangle;
    }
}
