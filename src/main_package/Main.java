package main_package;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
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
                    platform = createPlatform(60 * j, 60 * i, 60);
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

    Node createPlatform(int x, int y, int side) {
        Rectangle rectangle = new Rectangle(x, y, 60, 60);
        rectangle.setFill(Color.DARKGREEN);
        Node platform = rectangle;
        return platform;
    }

    void collisionsWithPlatforms() {

        for (Node platform : platforms) {
            if (platform.intersects(player.getMyNode().getBoundsInParent())) {
                player.getMyNode().setTranslateX(player.getMyNode().getTranslateX() - player.getVelocity().getX());
                player.getMyNode().setTranslateY(player.getMyNode().getTranslateY() - player.getVelocity().getY());
                // from which side the collision occurred:
                // right
                if (platform.contains(player.getMyNode().getBoundsInParent().getMaxX() + player.getVelocity().getX(), player.getMyNode().getBoundsInParent().getMinY())
                        || platform.contains(player.getMyNode().getBoundsInParent().getMaxX() + player.getVelocity().getX(), player.getMyNode().getBoundsInParent().getMaxY())) {
                    player.setCantMoveRight(true);
                    System.out.println("right");
                    System.out.println(("Player 1: " + player.getMyNode().getBoundsInParent().getMaxX() + player.getVelocity().getX()) + " " + (player.getMyNode().getBoundsInParent().getMinY()));
                    System.out.println("Player 2: " + (player.getMyNode().getBoundsInParent().getMaxX() + player.getVelocity().getX()) +" " +  (player.getMyNode().getBoundsInParent().getMaxY()));
                    System.out.println(platform.getBoundsInParent());

                //down
                if (platform.contains(player.getMyNode().getBoundsInParent().getMaxX(), player.getMyNode().getBoundsInParent().getMaxY() + player.getVelocity().getY())
                        || platform.contains(player.getMyNode().getBoundsInParent().getMinX(), player.getMyNode().getBoundsInParent().getMaxY() + player.getVelocity().getY())) {
                    player.setCantMoveDown(true);
                    System.out.println("down");
                    System.exit(1);
                }
                // up
                if (platform.contains(player.getMyNode().getBoundsInParent().getMaxX(), player.getMyNode().getBoundsInParent().getMinY() - player.getVelocity().getY())
                        || platform.contains(player.getMyNode().getBoundsInParent().getMinX(), player.getMyNode().getBoundsInParent().getMinY() - player.getVelocity().getY())) {
                    player.setCantMoveUp(true);
                    System.exit(2);
                }


                }
                // left
                if (platform.contains(player.getMyNode().getBoundsInParent().getMinX() - player.getVelocity().getX(), player.getMyNode().getBoundsInParent().getMinY())
                        || platform.contains(player.getMyNode().getBoundsInParent().getMinX() - player.getVelocity().getX(), player.getMyNode().getBoundsInParent().getMaxY())) {
                    player.setCantMoveLeft(true);
                    System.out.println("left");
                    System.exit(3);
                }
            }
        }
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
        collisionsWithPlatforms();
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }
        player.update();

    }
}
