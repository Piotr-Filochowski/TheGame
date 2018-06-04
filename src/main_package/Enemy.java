package main_package;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy {

    Node entity;

    public Enemy(double x, double y) {
        entity = new Rectangle(40, 40, Color.CORAL);
        entity.setTranslateX(x + 10);
        entity.setTranslateY(y + 20);

    }

    void shoot (int x, int y){

    }

    public Node getEntity() {
        return entity;
    }
}
