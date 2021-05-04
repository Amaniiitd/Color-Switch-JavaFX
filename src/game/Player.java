package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player extends Circle {
    private int score;
    private Color color;
    private double velocity;
    private boolean dead = false;
    private Color futureSafeColor;

    Player(double x, double y, double radius, Color color) {
        super(0, 0, radius, color);
        System.out.println("col: " + color.toString());

        this.setTranslateX(x);
        this.setTranslateY(y);
        this.color = color;

        this.score = 0;
        this.velocity = 0;
        this.futureSafeColor = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
        this.setFill(color);
        System.out.println("col: " + color.toString());
    }

    public void increaseVelocity(double delta) {
        this.velocity += delta;
    }

    public double getVelocity() {
        return this.velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void pushUp(double antiGravity) {
        this.velocity = -antiGravity;
    }

    public void move() {
        if (!dead) {
            this.setTranslateY(this.getTranslateY() + this.velocity);
        }
    }

    public void increaseScore() {
        this.score++;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void die() {
        this.setVisible(false);
        this.dead = true;
    }

    public boolean isDead() {
        return this.dead;
    }

    public void revive() {
        this.score -= 5;
        this.dead = false;
        this.setVisible(true);

        this.velocity = 0;
    }

    public Color getFutureSafeColor() {
        return this.futureSafeColor;
    }

    public void setFutureSafeColor(Color color) {
        this.futureSafeColor = color;
    }

}