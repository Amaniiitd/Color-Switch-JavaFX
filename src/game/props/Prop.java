package game.props;

import java.io.Serializable;

import game.Player;
import javafx.scene.Group;

public interface Prop extends Serializable {
    public Group getProp();

    public void checkCollision(Player player);

    public double getX();

    public double getY();

    public void moveY(double delta);

    public void loadProp();

}
