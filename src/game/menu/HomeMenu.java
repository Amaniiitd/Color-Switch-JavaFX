package game.menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import game.ColorSwitch;
import game.constants.Constants;
import game.ui.Background;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HomeMenu implements Menu {
    public void create(Stage stage, ColorSwitch game) {
        Pane menu = new Pane();
        menu.setPrefSize(Constants.width, Constants.height);

        Background bg = new Background(Constants.width, Constants.height);
        menu.getChildren().add(bg);

        int size = 100;
        int x = Constants.width / 2 - size / 2;
        int y = (int) (Constants.height * 0.6);

        try {
            Image logoImage = new Image(new FileInputStream(Constants.assetsPath + "logo.png"));
            ImageView logoIMView = new ImageView(logoImage);

            int height = 150;
            logoIMView.setFitHeight(height);
            logoIMView.setFitWidth(height * 2);
            Group logo = new Group(logoIMView);

            logo.setLayoutX(0);
            logo.setLayoutY(0);
            logo.setTranslateX(Constants.width / 2 - height);
            logo.setTranslateY(50);

            menu.getChildren().add(logo);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Image playImage = new Image(new FileInputStream(Constants.assetsPath + "play.png"));
            ImageView playIMView = new ImageView(playImage);

            playIMView.setFitHeight(size);
            playIMView.setFitWidth(size);
            Group playButton = new Group(playIMView);

            playButton.setLayoutX(0);
            playButton.setLayoutY(0);
            playButton.setTranslateX(x - (int) (size / 1.75));
            playButton.setTranslateY(y);

            playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mEvent) {
                    if (mEvent.getButton() == MouseButton.PRIMARY) {
                        game.createPlayGround(stage);
                    }
                }
            });

            menu.getChildren().add(playButton);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Image resumeImage = new Image(new FileInputStream(Constants.assetsPath + "resume.png"));
            ImageView resumeIMView = new ImageView(resumeImage);

            resumeIMView.setFitHeight(size);
            resumeIMView.setFitWidth(size);
            Group resumeButton = new Group(resumeIMView);

            resumeButton.setLayoutX(0);
            resumeButton.setLayoutY(0);
            resumeButton.setTranslateX(x + (int) (size / 1.75));
            resumeButton.setTranslateY(y);

            resumeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mEvent) {
                    if (mEvent.getButton() == MouseButton.PRIMARY) {
                        // todo: saved game menu
                        SavedGamesMenu s = new SavedGamesMenu();
                        s.create(stage, game);
                        System.out.println("Saved game menu");
                        // SaveGameState.loadGameState(game);
                    }
                }
            });

            menu.getChildren().add(resumeButton);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        size = 60;
        try {
            Image exitImage = new Image(new FileInputStream(Constants.assetsPath + "exit.png"));
            ImageView exitIMView = new ImageView(exitImage);

            exitIMView.setFitHeight(size);
            exitIMView.setFitWidth(size);
            Group exitButton = new Group(exitIMView);

            exitButton.setLayoutX(0);
            exitButton.setLayoutY(0);
            exitButton.setTranslateX(Constants.width - size - 20);
            exitButton.setTranslateY(20);

            exitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mEvent) {
                    if (mEvent.getButton() == MouseButton.PRIMARY) {
                        System.exit(0);
                    }
                }
            });

            menu.getChildren().add(exitButton);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Image devImage = new Image(new FileInputStream(Constants.assetsPath + "dev.png"));
            ImageView devIMView = new ImageView(devImage);

            devIMView.setFitHeight(85);
            devIMView.setFitWidth(450);
            Group devEmail = new Group(devIMView);

            devEmail.setLayoutX(0);
            devEmail.setLayoutY(0);
            devEmail.setTranslateX(Constants.width / 2 - 450 / 2);
            devEmail.setTranslateY(Constants.height - 120);

            menu.getChildren().add(devEmail);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(menu);
        stage.setScene(scene);
    }

}
