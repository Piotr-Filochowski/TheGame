package main_package;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameObject {
    protected Node myNode;
    protected Point2D velocity;

    public void setMyNode(Node myNode) {
        this.myNode = myNode;
    }

    public Node getMyNode() {
        return myNode;
    }

    public GameObject(int x, int y, int width, int height, Color color, Point2D velocity) {
        Rectangle rect = new Rectangle(width, height, color);
        myNode = rect;
        myNode.setTranslateX(x);
        myNode.setTranslateY(y);
        this.velocity = velocity;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public void update(){
//        myNode.relocate(myNode.getTranslateX() + velocity.getX(), myNode.getTranslateY() + velocity.getY());
       myNode.setTranslateX(myNode.getTranslateX() + velocity.getX());
       myNode.setTranslateY(myNode.getTranslateY() + velocity.getY());
    }


}
