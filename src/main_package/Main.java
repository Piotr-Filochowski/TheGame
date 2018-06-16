package main_package;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;


public class Main extends Application {

    int offsetBackup;
    int levelCounter = 1;
    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    private ArrayList<Node> platforms = new ArrayList<Node>();
    private ArrayList<Bullet> bulletsPlayer = new ArrayList<Bullet>();
    private ArrayList<Bullet> bulletsEnemy = new ArrayList<Bullet>();
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    

    private int levelWidth = 1800;
    private Player player = new Player(100, 300, Color.DARKRED, platforms, keys, levelWidth);
    private Pane appRoot = new Pane();
    private Pane gameRoot = new Pane();

    private LevelData levelData = new LevelData();

    public static void main(String[] args) {
        launch(args);
    }

    void initLevel(int levelNumber, Scene scene) {
        offsetBackup = 0;
        appRoot = new Pane();
        gameRoot = new Pane();
        scene.setRoot(appRoot);
        String[] tmpLevelData = new String[]{}; // initialising just for being sure that it will be initialized
        if (levelNumber == 1) {
            tmpLevelData = levelData.getLevel1();
        } else if (levelNumber == 2) {
            tmpLevelData = levelData.getLevel2();
        }
        levelWidth = tmpLevelData[0].length() * 60;
        for (int i = 0; i < tmpLevelData.length; i++) {
            String line = tmpLevelData[i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case '0':
                        break;
                    case '1':
                        Node platform = createPlatform(j * 60, i * 60, 60, 60, Color.DARKGREEN);
                        platforms.add(platform);
                        break;
                    case '2':
                        Enemy enemy = new Enemy(j * 60, i * 60, bulletsEnemy, platforms, gameRoot, player);
                        gameRoot.getChildren().add(enemy.getEntity());
                        enemies.add(enemy);
                        break;
                }
            }
        }
        gameRoot.getChildren().add(player.getEntity());
        player.getEntity().translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();

            if (offset > 640 && offset < levelWidth - 640) {
                gameRoot.setLayoutX(-(offset - 640));
                offsetBackup = -(offset - 640);
            }
        });
        appRoot.getChildren().addAll(gameRoot);
    }

    private void update(Scene scene) {

        if (enemies.size() == 0) {
            System.out.println(levelCounter);
            platforms.clear();
            enemies.clear();
            bulletsPlayer.clear();
            bulletsEnemy.clear();
            initLevel(levelCounter, scene);
            levelCounter++;
            if (levelCounter == 3) levelCounter = 1;
        }

        player.move();
        updatePlayerBullets();
        updateEnemiesBullets();
        for (Enemy enemy : enemies) enemy.shoot();
    }

    private void updateEnemiesBullets() {
        ArrayList<Bullet> deadBullet = new ArrayList<Bullet>();
        for (Bullet bullet : bulletsEnemy) {
            bullet.update();
            for (Node platform : platforms) {

                if (platform.getBoundsInParent().intersects(bullet.getEntity().getBoundsInParent())) {
                    gameRoot.getChildren().remove(bullet.getEntity());
                    deadBullet.add(bullet);
                }
            }
            if (player.getEntity().getBoundsInParent().intersects(bullet.getEntity().getBoundsInParent())) {
                System.exit(1);
            }
        }
        bulletsEnemy.removeAll(deadBullet);
    }

    private void updatePlayerBullets() {
        ArrayList<Bullet> deadBullets = new ArrayList<Bullet>();
        ArrayList<Enemy> deadEnemy = new ArrayList<Enemy>();

        for (Bullet bullet : bulletsPlayer) {
            bullet.update();
            for (Node platform : platforms) {
                if (platform.getBoundsInParent().intersects(bullet.getEntity().getBoundsInParent())) {
                    gameRoot.getChildren().remove(bullet.getEntity());
                    deadBullets.add(bullet);
                }
            }
        }
        for (Bullet bullet : bulletsPlayer) {
            for (Enemy enemy : enemies) {
                if (enemy.getEntity().getBoundsInParent().intersects(bullet.getEntity().getBoundsInParent())) {
                    gameRoot.getChildren().remove(enemy.getEntity());
                    deadEnemy.add(enemy);
                    gameRoot.getChildren().remove(bullet.getEntity());
                    deadBullets.add(bullet);
                }
            }
        }
        bulletsPlayer.removeAll(deadBullets);
        enemies.removeAll(deadEnemy);
    }

    private Node createPlatform(int x, int y, int w, int h, Color color) {
        Rectangle entity = new Rectangle(w, h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);
        gameRoot.getChildren().add(entity);
        return entity;
    }

    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        initLevel1();
        Scene scene = new Scene(appRoot, 1280, 700);
        scene.setFill(Color.BLACK);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        primaryStage.setTitle("The Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        scene.setOnMouseClicked((MouseEvent m) -> {
            Bullet bullet = player.shoot(m.getX() - offsetBackup, m.getY());
            bulletsPlayer.add(bullet);
            gameRoot.getChildren().add(bullet.getEntity());
        });
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(scene);

            }
        };
        timer.start();
    }
}