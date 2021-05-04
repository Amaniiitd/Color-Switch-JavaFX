package game.props;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

import game.Player;
import game.constants.Colors;
import game.constants.Constants;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class ColorChangeWheel implements Prop {
    private static final long serialVersionUID = -6277803378060351855L;
    private final static int radius = 10;
    private transient Group prop;

    private final int colorToChange;
    private double x, y;

    public ColorChangeWheel(int x, int y, Color colorToChange) {
        this.x = x - radius / 2;
        this.y = y - radius / 2;

        this.loadProp();
        this.colorToChange = Colors.primaryColors.indexOf(colorToChange);
    }

    public ColorChangeWheel(int x, int y) {
        this.x = x - radius / 2;
        this.y = y - radius / 2;

        this.loadProp();
        Random rand = new Random();
        this.colorToChange = rand.nextInt(4);
    }

    public void loadProp() {
        Image colorWheelImage = null;
        try {
            colorWheelImage = new Image(new FileInputStream(Constants.assetsPath + "colorWheel.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageView colorWheel = new ImageView(colorWheelImage);
        colorWheel.setFitHeight(radius * 2);
        colorWheel.setFitWidth(radius * 2);

        prop = new Group(colorWheel);
        prop.setLayoutX(0);
        prop.setLayoutY(0);
        prop.setTranslateX(x);
        prop.setTranslateY(y);
    }

    public Group getProp() {
        return this.prop;
    }

    public void checkCollision(Player player) {
        if (this.prop.isVisible() && this.prop.getBoundsInParent().intersects(player.getBoundsInParent())) {
            this.prop.setVisible(false);
            player.setFill(Colors.primaryColors.get(this.colorToChange));
        }
    }

    public Color getColorToChange() {
        return Colors.primaryColors.get(this.colorToChange);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void moveY(double delta) {
        this.y += delta;
        this.prop.setTranslateY(this.prop.getTranslateY() + delta);
    }
}
