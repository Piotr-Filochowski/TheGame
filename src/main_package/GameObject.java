package main_package;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameObject {
    Node myNode;
    Point2D velocity;
    public Node getMyNode() {
        return myNode;
    }

    public GameObject(int x, int y, int width, int height, Color color, Point2D velocity) {
        Rectangle rect = new Rectangle(x, y, width, height);
        rect.setFill(color);
        myNode = rect;
        myNode.setLayoutX(x);
        myNode.setLayoutY(y);
        this.velocity = velocity;
    }
    public void update(){
//        myNode.relocate(myNode.getTranslateX() + velocity.getX(), myNode.getTranslateY() + velocity.getY());
//        myNode.setTranslateX(myNode.getTranslateX() + velocity.getX());
//        myNode.setTranslateY(myNode.getTranslateY() + velocity.getY());
        myNode.setLayoutX(myNode.getLayoutX() + velocity.getX());
        myNode.setLayoutY(myNode.getLayoutY() + velocity.getY());
        System.out.println(myNode.getLayoutX() + "  " + myNode.getLayoutY());
        System.out.println("velocity: " + velocity);
        System.out.println("");
    }


}
