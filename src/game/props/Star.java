package game.props;

import game.Player;
import game.constants.Constants;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Star implements Prop {
    private static final long serialVersionUID = -2869419410572829586L;
    private final static int size = 30;
    private transient Group prop;

    private double x, y;

    public Star(int x, int y) {
        this.x = x - size / 2;
        this.y = y - size / 2;

        this.loadProp();
    }

    public Group getProp() {
        return this.prop;
    }

    public void checkCollision(Player player) {
        if (this.prop.isVisible() && this.prop.getBoundsInParent().intersects(player.getBoundsInParent())) {
            this.prop.setVisible(false);
            player.increaseScore();
        }
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

    public void loadProp() {
        Image image = null;
        try {
            image = new Image(new FileInputStream(Constants.assetsPath + "star.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageView iView = new ImageView(image);

        iView.setFitHeight(size);
        iView.setFitWidth(size);
        prop = new Group(iView);

        prop.setLayoutX(0);
        prop.setLayoutY(0);
        prop.setTranslateX(x);
        prop.setTranslateY(y);
    }
}
