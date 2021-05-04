package game.obstacles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.Player;
import game.constants.Colors;
import game.constants.Constants;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;

public class RingObstacle implements Obstacle {
    private static final long serialVersionUID = -2049085706964233926L;
    private final int radius;
    private final int stroke;
    private final int animationTime;

    private transient Group obstacle;
    private double x, y;

    private List<Integer> colors;

    public RingObstacle(int x, int y, int radius) {
        this.x = x;
        this.y = y;

        this.radius = radius;
        this.stroke = Constants.obstacleStroke;
        this.animationTime = Constants.obstacleAnimationTime;

        List<Color> colors = new ArrayList<>();
        colors.add(Colors.yellow);
        colors.add(Colors.purple);
        colors.add(Colors.blue);
        colors.add(Colors.pink);

        Collections.shuffle(colors);
        this.colors = new ArrayList<>();
        for (Color color : colors) {
            this.colors.add(Colors.primaryColors.indexOf(color));
        }

        this.loadObstacle();
    }

    public RingObstacle(int x, int y, int radius, int stroke, int animationTime, List<Color> colors) {
        this.x = x;
        this.y = y;

        this.radius = radius;
        this.stroke = stroke;
        this.animationTime = animationTime;

        this.colors = new ArrayList<>();
        for (Color color : colors) {
            this.colors.add(Colors.primaryColors.indexOf(color));
        }

        this.loadObstacle();
    }

    public void loadObstacle() {
        Shape arc1, arc2, arc3, arc4;
        arc1 = generateArc(Colors.primaryColors.get(colors.get(0)), 1);
        arc2 = generateArc(Colors.primaryColors.get(colors.get(1)), 2);
        arc3 = generateArc(Colors.primaryColors.get(colors.get(2)), 3);
        arc4 = generateArc(Colors.primaryColors.get(colors.get(3)), 4);

        this.obstacle = new Group(arc1, arc2, arc3, arc4);
        this.obstacle.setLayoutX(0);
        this.obstacle.setLayoutY(0);
        this.obstacle.setTranslateX(x);
        this.obstacle.setTranslateY(y);
    }

    private Shape generateArc(Color color, int quadrant) {
        Arc arc = new Arc(0, 0, this.radius, this.radius, 0, 360);

        arc.setFill(null);
        arc.setStroke(Color.BLACK);
        arc.setStrokeWidth(stroke);
        arc.setStrokeType(StrokeType.OUTSIDE);
        arc.setStrokeLineCap(StrokeLineCap.BUTT);

        Rectangle mask;
        switch (quadrant) {
            case 1:
                mask = new Rectangle(0, -(radius + stroke), radius + stroke, radius + stroke);
                break;
            case 2:
                mask = new Rectangle(0, 0, radius + stroke, radius + stroke);
                break;
            case 3:
                mask = new Rectangle(-(radius + stroke), 0, radius + stroke, radius + stroke);
                break;
            case 4:
                mask = new Rectangle(-(radius + stroke), -(radius + stroke), radius + stroke, radius + stroke);
                break;
            default:
                System.out.println("[!] Game has encountered a runtime error. It will most likely crash soon");
                return null;
        }

        Shape arc25 = Shape.intersect(arc, mask);
        arc25.setFill(color);

        return arc25;
    }

    public boolean checkCollision(Player player) {
        return this.checkCollision(player.getTranslateX(), player.getTranslateY(),
                player.getBoundsInParent().getHeight(), (Color) player.getFill());
    }

    public boolean checkCollision(double actorX, double actorY, double actorHeight, Color actorColor) {
        x = this.obstacle.getTranslateX();
        y = this.obstacle.getTranslateY();

        double delta = Math.sqrt((actorX - x) * (actorX - x) + (actorY - y) * (actorY - y));
        if (delta - this.radius - this.stroke - actorHeight / 2 >= 0) {
            // Outside of ring
            return false;
        } else if (delta + actorHeight / 2 <= this.radius) {
            // Inside of ring
            return false;
        } else {
            // Touching ring!!!
            int ind = (int) (this.obstacle.getRotate() % 360) / 90;

            if (actorY > y) {
                // actor in lower half
                if (ind % 2 == 0) {
                    ind++;
                } else {
                    ind--;
                }
            } else {
                // actor in upper half
                if (ind % 2 == 0) {
                    ind = Math.floorMod(ind - 1, 4);
                } else {
                    ind = Math.floorMod(ind + 1, 4);
                }
            }
            if (((Shape) this.obstacle.getChildren().get(ind)).getFill() == actorColor) {
                // I sleep
                return false;
            } else {
                // real shit
                return true;
            }
        }
    }

    public void animate(double frameTime) {
        this.move(frameTime, false);
    }

    public void move(double frameTime, boolean reversed) {
        if (reversed) {
            this.obstacle.setRotate((this.obstacle.getRotate() - (frameTime / this.animationTime) * 360) % 360);
            if (this.obstacle.getRotate() < 0) {
                this.obstacle.setRotate(this.obstacle.getRotate() + 360);
            }
        } else {
            this.obstacle.setRotate((this.obstacle.getRotate() + (frameTime / this.animationTime) * 360) % 360);
        }
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
