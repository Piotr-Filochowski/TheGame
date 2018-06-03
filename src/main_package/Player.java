package main_package;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Player extends GameObject{

    private boolean isCollidingWithPlatform;
    private int movingSpeeedX;
    public Player(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color, (new Point2D(0,0)));
        movingSpeeedX = 4;
        isCollidingWithPlatform = false;
    }

    public void beginMove(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.RIGHT){
            velocity = (new Point2D(movingSpeeedX, velocity.getY()));
            System.out.println(velocity);
        }
        if(keyEvent.getCode() == KeyCode.LEFT){
            velocity = (new Point2D(-movingSpeeedX, velocity.getY()));
            System.out.println(velocity);
        }
    }

    public void endMove(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.RIGHT){
            if(velocity.getX() == movingSpeeedX)velocity = velocity.subtract(movingSpeeedX, 0);
        } else if (keyEvent.getCode() == KeyCode.LEFT){
            if(velocity.getX() == -movingSpeeedX)velocity = velocity.add(movingSpeeedX, 0);
        }
    }

    public boolean isCollidingWithPlatform() {
        return isCollidingWithPlatform;
    }

    public void setCollidingWithPlatform(boolean collidingWithPlatform) {
        isCollidingWithPlatform = collidingWithPlatform;
    }

    public void update(){
        super.update();
        if(!isCollidingWithPlatform){
            velocity = velocity.add(0, 0.1);
        } else velocity = velocity.subtract(0, velocity.getY());
    }
}
