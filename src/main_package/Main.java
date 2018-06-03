package main_package;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Main extends Application {

    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();

    private ArrayList<Node> platforms = new ArrayList<Node>();
    private ArrayList<Node> coins = new ArrayList<Node>();

    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();
    private Pane uiRoot = new Pane();

    private Player player;


    private int levelWidth;

    private boolean dialogEvent = false;
    private boolean running = true;

    public static void main(String[] args) {
        launch(args);
    }

    private void initContent() {
        Rectangle bg = new Rectangle(1280, 720);
        LevelData levelData = new LevelData();

        levelWidth = levelData.getlevel1()[0].length() * 60;

        for (int i = 0; i < levelData.getlevel1().length; i++) {
            String line = levelData.getlevel1()[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        Node platform = createPlatform(j * 60, i * 60, 60, 60, Color.DARKGREEN);
                        platforms.add(platform);
                        break;
                    case '2':
                        Node coin = createPlatform(j * 60, i * 60, 60, 60, Color.GOLD);
                        coins.add(coin);
                        break;
                }
            }
        }

        player = new Player(0, 600, 40, 40, Color.DARKMAGENTA, platforms);
        gameRoot.getChildren().add(player.getEntity());
        player.getEntity().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();

            if (offset > 640 && offset < levelWidth - 640) {
                gameRoot.setLayoutX(-(offset - 640));
            }
        });

        appRoot.getChildren().addAll(bg, gameRoot, uiRoot);
    }

    private void update() {
        if (isPressed(KeyCode.W) && player.getEntity().getTranslateY() >= 5) {
            player.jumpPlayer();
        }

        if (isPressed(KeyCode.A) && player.getEntity().getTranslateX() >= 5) {
            player.movePlayerX(-5);
        }

        if (isPressed(KeyCode.D) && player.getEntity().getTranslateX() + 40 <= levelWidth - 5) {
            player.movePlayerX(5);
        }

        if (player.getPlayerVelocity().getY() < 10) {
            player.setPlayerVelocity(player.getPlayerVelocity().add(0, 1));
        }

        player.movePlayerY((int) player.getPlayerVelocity().getY());

        for (Node coin : coins) {
            if (player.getEntity().getBoundsInParent().intersects(coin.getBoundsInParent())) {
                coin.getProperties().put("alive", false);
                dialogEvent = true;
                running = false;
            }
        }

        for (Iterator<Node> it = coins.iterator(); it.hasNext(); ) {
            Node coin = it.next();
            if (!(Boolean) coin.getProperties().get("alive")) {
                it.remove();
                gameRoot.getChildren().remove(coin);
            }
        }
    }

    private Node createPlatform(int x, int y, int w, int h, Color color) {
        Rectangle entity = new Rectangle(w, h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);
        entity.getProperties().put("alive", true);
        gameRoot.getChildren().add(entity);
        return entity;
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initContent();
        Scene scene = new Scene(appRoot);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        primaryStage.setTitle("The Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }
}