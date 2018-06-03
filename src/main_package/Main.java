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


        player = new Player(300, 0, 30, 50, Color.AQUA);
        root.getChildren().add(player.getMyNode());
        for (int i = 0; i < data.getLevel1().length; i++) {
            for (int j = 0; j < data.getLevel1()[i].length(); j++) {
                if (data.getLevel1()[i].charAt(j) == '1') {
                    platform = new Rectangle( 60, 60, Color.GREEN);
                    platform.setTranslateX(60 * j);
                    platform.setTranslateY(60 * i);
                    platforms.add(platform);
                    root.getChildren().add(platform);
                }
            }
        }
        Scene sceneOne = new Scene(root, 60 * data.getLevel1()[0].length(), 60 * data.getLevel1().length);
        sceneOne.setOnKeyPressed(player::beginMove);
        sceneOne.setOnKeyReleased(player::endMove);
        sceneOne.setFill(Color.BLACK);
        return sceneOne;
    }

    Node createPlatform(int posX, int posY, int side){
        Node platform ;

//        return platform;
        return null;
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

        double pX, pY, playerX, playerY;
        playerX = player.myNode.getTranslateX() + 30;
        playerY = player.myNode.getTranslateY() + 25;
        player.setCollidingWithPlatform(false);
        for (Node platform : platforms) {
            pX = platform.getTranslateX() + 30;
            pY = platform.getTranslateY() + 30;
            if((Math.abs(pX - playerX) < 60 ) && (Math.abs(pY - playerY) < 55)) {
                player.setCollidingWithPlatform(true);
                System.out.println("Platform: " + pX + ", " + pY);
                System.out.println("Player: " + playerX + ", " + playerY);
            }
        }

    }
}



//Kolizja od boku oddznielna od kolizji od dołu i oddzielna od kolizji od góry