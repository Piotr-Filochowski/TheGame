package main_package;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    LevelData data = new LevelData();
    ArrayList<Node> platforms = new ArrayList<Node>();
    ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
    Player player;

    public static void main(String[] args) {
        launch(args);
    }

    public Scene createScene() {
        Node platform;
        Rectangle rect;
        Pane root = new Pane();
        Scene sceneOne = new Scene(root, 60 * data.getLevel1()[0].length(), 60 * data.getLevel1().length);
        sceneOne.setFill(Color.BLACK);
        player = new Player(300, 0, 30, 50, Color.AQUA);
        root.getChildren().add(player.getMyNode());
        for (int i = 0; i < data.getLevel1().length; i++) {
            for (int j = 0; j < data.getLevel1()[i].length(); j++) {
                if (data.getLevel1()[i].charAt(j) == '1') {
                    rect = new Rectangle(60 * j, 60 * i, 60, 60);
                    rect.setFill(Color.DARKGREEN);
                    rect.getProperties().put("alive", true);// po co to?
                    platform = rect;
                    platforms.add(platform);
                    root.getChildren().add(platform);
                }
            }
        }
        sceneOne.setOnKeyPressed(player::beginMove);
        sceneOne.setOnKeyReleased(player::endMove);
        return sceneOne;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("The Game");
        primaryStage.setScene(createScene());
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
        primaryStage.show();
        primaryStage.getScene().getRoot().requestLayout();
    }

    private void update() {

        int jebak = 1;
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }
        player.update();
        //fucking collisions:

        double pX, pY, playerX, playerY;
        playerX = player.myNode.getTranslateX() + 15;
        playerY = player.myNode.getTranslateY() + 25;

        for (Node platform : platforms) {

            pX = platform.getLayoutX() + 30;
            pY = platform.getLayoutY() + 30;
            System.out.println("pX = " + pX + "pY = " + pY);
            System.out.println("playerX = " + playerX + "playerY = " + playerY);
            if((Math.abs(pX - playerX) < 45 ) && Math.abs(pY - playerY) < 55) {
                player.setCollidingWithPlatform(true);
                System.out.println("error");
            }
        }
    }
}


//platform.getTranslateX() <= player.getMyNode().getTranslateX() &&
//                player.getMyNode().getTranslateX() <= (platform.getTranslateX() + 60) &&
//                platform.getTranslateY() <= player.getMyNode().getTranslateY() &&
//                player.getMyNode().getTranslateY() <= (platform.getTranslateY() + 60)