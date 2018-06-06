package main_package;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Enemy {

    int shooterCount;
    Node entity;
    private ArrayList<Node> platforms = new ArrayList<Node>();

    public Enemy(double x, double y) {
        entity = new Rectangle(40, 40, Color.CORAL);
        entity.setTranslateX(x + 10);
        entity.setTranslateY(y + 20);
        this.platforms = platforms;
        shooterCount = 0;
    }


    Bullet shoot(double sceneX, double sceneY) {
        Bullet bullet = new Bullet(entity.getTranslateX(), entity.getTranslateY(), sceneX - entity.getTranslateX(), sceneY - entity.getTranslateY(), Color.GOLD);
        return bullet;
    }

    public Node getEntity() {
        return entity;
    }
}
