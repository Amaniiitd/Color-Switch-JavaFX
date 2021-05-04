package game.obstacles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import game.Player;
import game.constants.Colors;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class TripleRingObstacle implements Obstacle {
    private static final long serialVersionUID = 811802409180852354L;

    private RingObstacle innerRing, middleRing, outerRing;

    private final int safeColor;
    private transient Group obstacle;

    private double x, y;

    public TripleRingObstacle(int x, int y, int radius, Color safeColor) {
        this.x = x;
        this.y = y;

        List<Color> colorsForInnerRing = new ArrayList<>();
        colorsForInnerRing.add(Colors.purple);
        colorsForInnerRing.add(Colors.yellow);
        colorsForInnerRing.add(Colors.blue);
        colorsForInnerRing.add(Colors.pink);

        List<Color> colorsForMiddleRing = new ArrayList<>(colorsForInnerRing);
        List<Color> colorsForOuterRing = new ArrayList<>(colorsForInnerRing);

        Collections.shuffle(colorsForInnerRing);
        Collections.shuffle(colorsForMiddleRing);
        Collections.shuffle(colorsForOuterRing);

        Random rand = new Random();
        if (rand.nextInt(5) != 3) {
            // No color wheel will spawn
            colorsForInnerRing.set(2, safeColor);
            colorsForMiddleRing.set(1, safeColor);
            colorsForOuterRing.set(2, safeColor);

            this.safeColor = Colors.primaryColors.indexOf(safeColor);
        } else {
            // Color wheel will have to spawn
            this.safeColor = Colors.primaryColors.indexOf(colorsForMiddleRing.get(1));

            colorsForInnerRing.set(2, Colors.primaryColors.get(this.safeColor));
            colorsForOuterRing.set(2, Colors.primaryColors.get(this.safeColor));
        }

        this.innerRing = new RingObstacle(x, y, radius, 10, 5, colorsForInnerRing);
        this.middleRing = new RingObstacle(x, y, radius + 20, 10, 5, colorsForMiddleRing);
        this.outerRing = new RingObstacle(x, y, radius + 40, 10, 5, colorsForOuterRing);

        obstacle = new Group(innerRing.getObstacle(), middleRing.getObstacle(), outerRing.getObstacle());
    }

    public void loadObstacle() {
        innerRing.loadObstacle();
        middleRing.loadObstacle();
        outerRing.loadObstacle();

        this.obstacle = new Group(innerRing.getObstacle(), middleRing.getObstacle(), outerRing.getObstacle());
        this.obstacle.setTranslateY(y);
        // this.obstacle.setTranslateX(x);
    }

    public boolean checkCollision(Player player) {
        double actorX = player.getTranslateX();
        double actorY = player.getTranslateY() - this.obstacle.getTranslateY();
        double actorHeight = player.getBoundsInParent().getHeight();
        Color actorColor = (Color) player.getFill();

        boolean isColliding = innerRing.checkCollision(actorX, actorY, actorHeight, actorColor)
                || middleRing.checkCollision(actorX, actorY, actorHeight, actorColor)
                || outerRing.checkCollision(actorX, actorY, actorHeight, actorColor);

        return isColliding;
    }

    public void animate(double frameTime) {
        this.innerRing.animate(frameTime);
        this.middleRing.move(frameTime, true);
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
