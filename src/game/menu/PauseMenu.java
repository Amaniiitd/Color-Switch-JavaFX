package game.menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import game.ColorSwitch;
import game.constants.Constants;
import game.storage.GameStorage;
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

public class PauseMenu implements Menu {
    public void create(Stage stage, ColorSwitch game) {
        Pane menu = new Pane();
        menu.setPrefSize(Constants.width, Constants.height);

        Background bg = new Background(Constants.width, Constants.height);
        menu.getChildren().add(bg);

        try {
            Image logoImage = new Image(new FileInputStream(Constants.assetsPath + "logo.png"));
            ImageView logoIMView = new ImageView(logoImage);

            int height = 150;
            logoIMView.setFitHeight(height);
            logoIMView.setFitWidth(height * 2);
            Group logo = new Group(logoIMView);

            logo.setLayoutX(0);
            logo.setLayoutY(0);
            logo.setTranslateY(20);
            logo.setTranslateX(Constants.width / 2 - height);

            menu.getChildren().add(logo);
        } catch (FileNotFoundException e) {
            System.out.println("[!] Cannot load logo image");
            e.printStackTrace();
        }

        try {
            Image homeImage = new Image(new FileInputStream(Constants.assetsPath + "home.png"));
            ImageView homeIMView = new ImageView(homeImage);

            int size = 60;
            homeIMView.setFitHeight(size);
            homeIMView.setFitWidth(size);
            Group homeButton = new Group(homeIMView);

            homeButton.setLayoutX(0);
            homeButton.setLayoutY(0);
            homeButton.setTranslateX(20);
            homeButton.setTranslateY(20);

            homeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mEvent) {
                    if (mEvent.getButton() == MouseButton.PRIMARY) {
                        HomeMenu homeMenu = new HomeMenu();
                        homeMenu.create(stage, game);
                    }
                }
            });

            menu.getChildren().add(homeButton);
        } catch (FileNotFoundException e) {
            System.out.println("[!] Cannot load home button image");
            e.printStackTrace();
        }

        try {
            Image playImage = new Image(new FileInputStream(Constants.assetsPath + "play.png"));
            ImageView playIMView = new ImageView(playImage);

            int size = 150;
            playIMView.setFitHeight(size);
            playIMView.setFitWidth(size);
            Group playButton = new Group(playIMView);

            int x = Constants.width / 2;
            playButton.setLayoutX(0);
            playButton.setLayoutY(0);
            playButton.setTranslateX(x - size / 2);
            playButton.setTranslateY(Constants.height * 0.5);

            Scene playGround = stage.getScene();
            playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mEvent) {
                    if (mEvent.getButton() == MouseButton.PRIMARY) {
                        stage.setScene(playGround);
                        game.state.gamePaused = false;
                    }
                }
            });

            menu.getChildren().add(playButton);
        } catch (FileNotFoundException e) {
            System.out.println("[!] Cannot load play button image");
            e.printStackTrace();
        }

        int size = 125;
        int y = (int) (Constants.height * 0.8);

        int x = Constants.width / 2 + (int) (size / 1.75);
        try {
            Image saveImage = new Image(new FileInputStream(Constants.assetsPath + "save.png"));
            ImageView saveIMView = new ImageView(saveImage);

            saveIMView.setFitHeight(size);
            saveIMView.setFitWidth(size);

            Group saveButton = new Group(saveIMView);
            saveButton.setLayoutX(0);
            saveButton.setLayoutY(0);
            saveButton.setTranslateX(x - size / 2);
            saveButton.setTranslateY(y - size / 2);

            saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mEvent) {
                    if (mEvent.getButton() == MouseButton.PRIMARY) {
                        game.storage.savedGames.add(game.state);
                        GameStorage.saveGameStorage(game.storage);
                    }
                }
            });

            menu.getChildren().add(saveButton);
        } catch (FileNotFoundException e) {
            System.out.println("[!] Cannot load save button image");
            e.printStackTrace();
        }

        x = Constants.width / 2 - (int) (size / 1.75);
        try {
            Image image = new Image(new FileInputStream(Constants.assetsPath + "restart.png"));
            ImageView iView = new ImageView(image);

            iView.setFitHeight(size);
            iView.setFitWidth(size);
            Group playBtn = new Group(iView);

            playBtn.setLayoutX(0);
            playBtn.setLayoutY(0);
            playBtn.setTranslateX(x - size / 2);
            playBtn.setTranslateY(y - size / 2);

            playBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mEvent) {
                    if (mEvent.getButton() == MouseButton.PRIMARY) {
                        game.state.gamePaused = false;
                        game.createPlayGround(stage);
                    }
                }
            });

            menu.getChildren().add(playBtn);
        } catch (FileNotFoundException e) {
            System.out.println("[!] Cannot load restart button image");
            e.printStackTrace();
        }

        Scene scene = new Scene(menu);
        stage.setScene(scene);
    }

}
