package game.physics;

import java.util.ArrayList;

import game.Player;
import game.obstacles.Obstacle;
import game.props.Prop;

public class PhysicsEngine {

    private final int gravity = 10;// 20;
    private final int antiGravity = 5;// 8;
    private final int fps = 60;
    private final double frameTime;

    private transient Player player;
    private final int windowHeight;
    private transient ArrayList<Prop> props;
    private transient ArrayList<Obstacle> obstacles;

    public PhysicsEngine(Player player, int height, ArrayList<Obstacle> obstacles, ArrayList<Prop> props) {
        this.props = props;
        this.player = player;
        this.windowHeight = height;
        this.obstacles = obstacles;

        this.frameTime = 1f / fps;
    }

    public void setProps(ArrayList<Prop> props) {
        this.props = props;
    }

    public void setObstacles(ArrayList<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void moveCamera() {
        if (player.getTranslateY() <= 0.5 * windowHeight) {
            int delta = (int) (0.5 * windowHeight - player.getTranslateY());

            for (Obstacle obs : obstacles) {
                obs.moveY(delta * frameTime * 2);
            }

            for (Prop prop : props) {
                prop.moveY(delta * frameTime * 2);
            }

            player.setTranslateY(player.getTranslateY() + (delta) * frameTime * 2);
        }
    }

    private void applyGravity() {
        player.increaseVelocity(gravity * frameTime);
        player.move();
    }

    private void stablizeFPS(long timeToSleep) {
        if (timeToSleep > 0) {
            try {
                Thread.sleep(timeToSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void physics() {
        long timeToSleep = System.currentTimeMillis();

        this.applyGravity();
        this.moveCamera();

        timeToSleep = (long) (frameTime * 1000) - (System.currentTimeMillis() - timeToSleep);
        this.stablizeFPS(timeToSleep);
    }

    public void applyForceOnPlayer() {
        player.pushUp(antiGravity);
    }

    public double getFrameTime() {
        return this.frameTime;
    }
}
