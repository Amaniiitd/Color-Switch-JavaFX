package game.obstacles;

import java.io.Serializable;

import game.Player;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public interface Obstacle extends Serializable {
    public Group getObstacle();

    public boolean checkCollision(Player player);

    public void animate(double frameTime);

    public Color getSafeColor();

    public double getX();

    public double getY();

    public void moveY(double delta);

    public void setY(double y);

    public void loadObstacle();
}
