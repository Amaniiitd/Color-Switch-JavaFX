package game.obstacles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import game.Player;
import game.constants.Colors;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class DoubleRingObstacle implements Obstacle {
    private static final long serialVersionUID = 3487710094653574281L;

    private RingObstacle innerRing, outerRing;

    private final int safeColor;
    private transient Group obstacle;

    private double x, y;

    public DoubleRingObstacle(int x, int y, int radius, Color safeColor) {
        this.x = x;
        this.y = y;

        List<Color> colorsForInnerRing = new ArrayList<>();
        colorsForInnerRing.add(Colors.purple);
        colorsForInnerRing.add(Colors.yellow);
        colorsForInnerRing.add(Colors.blue);
        colorsForInnerRing.add(Colors.pink);

        List<Color> colorsForOuterRing = new ArrayList<>(colorsForInnerRing);
        Collections.shuffle(colorsForInnerRing);
        Collections.shuffle(colorsForOuterRing);

        Random rand = new Random();
        if (rand.nextInt(5) != 3) {
            // No color wheel will spawn
            colorsForInnerRing.set(1, safeColor);
            colorsForOuterRing.set(2, safeColor);

            this.safeColor = Colors.primaryColors.indexOf(safeColor);
        } else {
            // Color wheel will have to spawn
            this.safeColor = Colors.primaryColors.indexOf(colorsForInnerRing.get(1));
            colorsForOuterRing.set(2, Colors.primaryColors.get(this.safeColor));
        }

        this.innerRing = new RingObstacle(x, y, radius, 10, 5, colorsForInnerRing);
        this.outerRing = new RingObstacle(x, y, radius + 20, 10, 5, colorsForOuterRing);

        this.obstacle = new Group(innerRing.getObstacle(), outerRing.getObstacle());
    }

    public void loadObstacle() {
        innerRing.loadObstacle();
        outerRing.loadObstacle();

        this.obstacle = new Group(innerRing.getObstacle(), outerRing.getObstacle());
        this.obstacle.setTranslateY(y);
    }

    public boolean checkCollision(Player player) {
        double actorX = player.getTranslateX();
        double actorY = player.getTranslateY() - this.obstacle.getTranslateY();
        double actorHeight = player.getBoundsInParent().getHeight();
        Color actorColor = (Color) player.getFill();

        boolean isColliding = this.innerRing.checkCollision(actorX, actorY, actorHeight, actorColor)
                || this.outerRing.checkCollision(actorX, actorY, actorHeight, actorColor);
        return isColliding;
    }

    public void animate(double frameTime) {
        this.innerRing.move(frameTime, true);
        this.outerRing.animate(frameTime);
    }

    public Group getObstacle() {
        return this.obstacle;
    }

    public Color getSafeColor() {
        return Colors.primaryColors.get(this.safeColor);
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
