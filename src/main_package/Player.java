package main_package;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Player extends GameObject{

    private boolean cantMoveLeft, cantMoveRight, cantMoveUp, cantMoveDown;
    private int movingSpeedX;
    public Player(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color, (new Point2D(0,0)));
        movingSpeedX = 1;
        cantMoveDown = false;
        cantMoveUp = false;
        cantMoveLeft = false;
        cantMoveRight = false;
    }

    public void beginMove(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.RIGHT){
            velocity = (new Point2D(movingSpeedX, velocity.getY()));
        }
        if(keyEvent.getCode() == KeyCode.LEFT){
            velocity = (new Point2D(-movingSpeedX, velocity.getY()));
        }
    }

    public void endMove(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.RIGHT){
            if(velocity.getX() == movingSpeedX)velocity = velocity.subtract(movingSpeedX, 0);
        } else if (keyEvent.getCode() == KeyCode.LEFT){
            if(velocity.getX() == -movingSpeedX)velocity = velocity.add(movingSpeedX, 0);
        }
    }

    public boolean isCantMoveLeft() {
        return cantMoveLeft;
    }

    public void setCantMoveLeft(boolean cantMoveLeft) {
        this.cantMoveLeft = cantMoveLeft;
    }

    public boolean isCantMoveRight() {
        return cantMoveRight;
    }

    public void setCantMoveRight(boolean cantMoveRight) {
        this.cantMoveRight = cantMoveRight;
    }

    public boolean isCantMoveUp() {
        return cantMoveUp;
    }

    public void setCantMoveUp(boolean cantMoveUp) {
        this.cantMoveUp = cantMoveUp;
    }

    public boolean isCantMoveDown() {
        return cantMoveDown;
    }

    public void setCantMoveDown(boolean cantMoveDown) {
        this.cantMoveDown = cantMoveDown;
    }

    public void update(){
        super.update();
        if(!cantMoveDown){
            if(velocity.getY() < 0.9){
                velocity = velocity.add(0, 0.1);
            }
        } else velocity = velocity.subtract(0, velocity.getY());
    }
}
