package game.obstacles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.Player;
import game.constants.Colors;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class StripObstacle implements Obstacle {
    private static final long serialVersionUID = 6980048251937405872L;
    private final int width;
    private final int height;

    private long tick = 0;

    private transient Group obstacle;
    private List<Integer> colors;

    private double x, y;

    public StripObstacle(int width, int height, int y, Color safeColor) {
        this.width = width;
        this.height = height;

        List<Color> colors = new ArrayList<>();
        colors.add(Colors.purple);
        colors.add(Colors.yellow);
        colors.add(Colors.blue);
        colors.add(Colors.pink);

        Collections.shuffle(colors);
        this.colors = new ArrayList<>();
        for (Color color : colors) {
            this.colors.add(Colors.primaryColors.indexOf(color));
        }

        this.x = -0.5 * width;
        this.y = y;

        this.loadObstacle();
    }

    public void loadObstacle() {
        this.obstacle = new Group();
        for (int idx = 0; idx < 8; idx++) {
            Rectangle strip = new Rectangle(idx * width / 4, 0, width / 4, height);
            strip.setFill(Colors.primaryColors.get(colors.get(idx % 4)));

            this.obstacle.getChildren().add(strip);
        }

        this.obstacle.setLayoutX(0);
        this.obstacle.setLayoutY(0);
        this.obstacle.setTranslateY(y);
        this.obstacle.setTranslateX(-0.5 * width);
    }

    public boolean checkCollision(Player player) {
        int idx = (int) Math.ceil((-this.obstacle.getTranslateX() * 4) / width) + 1;
        Color color = (Color) ((Rectangle) this.obstacle.getChildren().get(idx)).getFill();

        if (player.getBoundsInParent().intersects(this.obstacle.getBoundsInParent())) {
            if (color != player.getFill()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void animate(double frameTime) {
        this.obstacle.setTranslateX(width * ((Math.sin(tick++ * frameTime) / 2 - 0.5)));
        this.x = this.obstacle.getTranslateX();
    }

    public Group getObstacle() {
        return obstacle;
    }

    public Color getSafeColor() {
        return null;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void moveY(double delta) {
        this.y += delta;
        this.obstacle.setTranslateY(this.obstacle.getTranslateY() + delta);
    }

    public void setY(double y) {
        this.y = y;
        this.obstacle.setTranslateY(y);
    }
}
