package game.storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import game.ColorSwitch;
import game.PlayerState;
import game.constants.Colors;
import game.obstacles.Obstacle;
import game.physics.PhysicsEngine;
import game.props.Prop;
import game.ui.Background;
import game.ui.GamePlayOverlay;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GameStorage implements Serializable {
    private static final long serialVersionUID = -4087912484950931549L;

    public List<GameState> savedGames;
    public int bestScore = 0;

    public GameStorage() {

        savedGames = new ArrayList<>();
    }

    public static void saveGameStorage(GameStorage storage) {
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("saveGame");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (GameState state : storage.savedGames) {
                if (state.player != null) {
                    System.out.println("qq");
                    state.playerState = PlayerState.getPlayerState(state.player);
                }
            }
            out.writeObject(storage);
            out.close();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static GameStorage loadGameStorage() {
        GameStorage storage;
        try {
            FileInputStream fileIn = new FileInputStream("saveGame");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            storage = (GameStorage) in.readObject();
            System.out.println(storage.savedGames.size());
            in.close();
            fileIn.close();
        } catch (IOException i) {
            // i.printStackTrace();
            return new GameStorage();
        } catch (ClassNotFoundException c) {
            // c.printStackTrace();
            return new GameStorage();
        }

        return storage;
    }

    public void loadGameState(ColorSwitch game, int idx) {
        GameState oldState = game.state;
        if (oldState.gameLoop != null) {
            oldState.gameLoop.stop();
        }

        GameState state = this.savedGames.get(idx);
        game.state = state;
        game.state.stage = oldState.stage;

        state.player = PlayerState.loadPlayerState(state.playerState);

        state.physicsEngine = new PhysicsEngine(state.player, state.height, state.obstacles, state.props);

        state.bestScore = oldState.bestScore;

        state.playGround = new Pane();
        state.playGround.setPrefSize(state.width, state.height);

        Background bg = new Background(state.width, state.height);
        state.playGround.getChildren().add(bg);

        state.playGround.getChildren().add(state.player);

        state.overlay = new GamePlayOverlay(game, state.stage);
        state.playGround.getChildren().add(state.overlay.getUI());

        state.gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!state.gamePaused) {
                    state.physicsEngine.physics();
                    game.removeUnusableEntities();

                    game.updateGame();
                }
            }
        };
        state.gameLoop.start();

        Scene scene = new Scene(state.playGround);
        scene.setOnMouseClicked(e -> {
            switch (e.getButton()) {
                case PRIMARY:
                    if (!state.gamePaused) {
                        state.physicsEngine.applyForceOnPlayer();
                    }
                    break;
                default:
                    break;
            }
        });

        state.stage.setScene(scene);

        for (Prop prop : state.props) {
            prop.loadProp();
            state.playGround.getChildren().add(prop.getProp());
        }

        for (Obstacle obstacle : state.obstacles) {
            obstacle.loadObstacle();
            state.playGround.getChildren().add(obstacle.getObstacle());
        }

        state.gamePaused = false;
        state.player.setColor(Colors.primaryColors.get(state.playerState.getColor()));
    }

}
