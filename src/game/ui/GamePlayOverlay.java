package game.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import game.ColorSwitch;
import game.constants.Constants;
import game.menu.PauseMenu;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GamePlayOverlay implements Serializable {
    private static final long serialVersionUID = -7577080061785523527L;
    private final Group ui;

    public GamePlayOverlay(ColorSwitch game, Stage stage) {
        Text score = new Text(35, 80, "6942");
        score.setFill(Color.WHITE);
        score.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.REGULAR, 60));

        this.ui = new Group(score);
        try {
            Image image = new Image(new FileInputStream(Constants.assetsPath + "pause.png"));
            ImageView iView = new ImageView(image);

            int h = 60;
            iView.setFitWidth(h);
            iView.setFitHeight(h);
            Group logo = new Group(iView);

            logo.setLayoutX(0);
            logo.setLayoutY(0);
            logo.setTranslateY(20);
            logo.setTranslateX(Constants.width - h - 20);

            logo.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mEvent) {
                    if (mEvent.getButton() == MouseButton.PRIMARY) {
                        // Pause game
                        game.state.gamePaused = true;
                        PauseMenu pauseMenu = new PauseMenu();
                        pauseMenu.create(stage, game);
                    }
                }
            });
            this.ui.getChildren().add(logo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Group getUI() {
        return this.ui;
    }

    public void setScore(String score) {
        ((Text) this.ui.getChildren().get(0)).setText(score);

        this.ui.toFront();
    }
}
