package game.storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import game.Player;
import game.PlayerState;
import game.constants.Constants;
import game.obstacles.Obstacle;
import game.physics.PhysicsEngine;
import game.props.Prop;
import game.ui.GamePlayOverlay;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameState implements Serializable {
    private static final long serialVersionUID = 887925291505808173L;

    public final int width = Constants.width, height = Constants.height;
    public double frameTime;

    public int bestScore = 0;
    public boolean gamePaused = false;

    public transient Stage stage;
    public transient Player player;
    public transient Pane playGround;
    public transient AnimationTimer gameLoop;
    public transient GamePlayOverlay overlay;

    public ArrayList<Prop> props;
    public ArrayList<Obstacle> obstacles;

    public transient PhysicsEngine physicsEngine;

    public PlayerState playerState = null;
    public Random rand;

}
