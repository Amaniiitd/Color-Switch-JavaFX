package game.menu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import game.ColorSwitch;
import game.constants.Colors;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameOverMenu implements Menu {
    public void create(Stage stage, ColorSwitch game) {
        if (game.getPlayer().getScore() > game.getHighScore()) {
            game.state.bestScore = game.getPlayer().getScore();
        }

        Font font = null;
        try {
            font = Font.loadFont(new FileInputStream(Constants.assetsPath + "blissfulthinking.otf"), 60);
        } catch (FileNotFoundException e) {
            System.out.println("[!] Cannot load game font");
            e.printStackTrace();
        }

        Pane menu = new Pane();
        menu.setPrefSize(Constants.width, Constants.height);

        // Set Menu background
        Background bg = new Background(Constants.width, Constants.height);
        menu.getChildren().add(bg);

        // Add logo
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

        // Add home button
        try {
            Image homeImage = new Image(new FileInputStream(Constants.assetsPath + "home.png"));
            ImageView homeIMView = new ImageView(homeImage);

            int size = 60;
            homeIMView.setFitWidth(size);
            homeIMView.setFitHeight(size);

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

        // Add horizontal bar & score
        Rectangle bar = new Rectangle(0, 220, Constants.width, 70);
        bar.setFill(Colors.grey);

        Text score = new Text(Constants.width / 2, 275, "SCORE");
        score.setFill(Color.WHITE);
        if (font != null) {
            score.setFont(font);
        }

        score.setTranslateX(-score.getBoundsInParent().getWidth() / 2);
        menu.getChildren().add(bar);
        menu.getChildren().add(score);

        score = new Text(Constants.width / 2, 350, "" + game.getPlayer().getScore());
        score.setFill(Color.WHITE);
        if (font != null) {
            score.setFont(font);
        }

        score.setTranslateX(-score.getBoundsInParent().getWidth() / 2);
        menu.getChildren().add(score);

        bar = new Rectangle(0, 370, Constants.width, 70);
        bar.setFill(Colors.orange);
        score = new Text(Constants.width / 2, 425, "BEST SCORE");
        score.setFill(Color.WHITE);
        if (font != null) {
            score.setFont(font);
        }

        score.setTranslateX(-score.getBoundsInParent().getWidth() / 2);
        menu.getChildren().add(bar);
        menu.getChildren().add(score);

        score = new Text(Constants.width / 2, 500, "" + game.getHighScore());
        score.setFill(Color.WHITE);
        if (font != null) {
            score.setFont(font);
        }

        score.setTranslateX(-score.getBoundsInParent().getWidth() / 2);
        menu.getChildren().add(score);

        // Add revive button
        int x = -1;
        if (game.getPlayer().getScore() >= 5) {
            int size = 125;
            x = Constants.width / 2 + (int) (size / 1.75);
            int y = (int) (Constants.height * 0.8);
            try {
                Image reviveImage = new Image(new FileInputStream(Constants.assetsPath + "revive.png"));
                ImageView reviveIMView = new ImageView(reviveImage);

                reviveIMView.setFitHeight(size);
                reviveIMView.setFitWidth(size);

                Group reviveButton = new Group(reviveIMView);
                reviveButton.setLayoutX(0);
                reviveButton.setLayoutY(0);
                reviveButton.setTranslateX(x - size / 2);
                reviveButton.setTranslateY(y - size / 2);

                Scene playGround = stage.getScene();
                reviveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mEvent) {
                        if (mEvent.getButton() == MouseButton.PRIMARY) {
                            stage.setScene(playGround);

                            game.state.gameLoop.start();
                            game.state.gamePaused = false;
                            game.getPlayer().revive();
                            game.state.physicsEngine.applyForceOnPlayer();
                        }
                    }
                });

                menu.getChildren().add(reviveButton);
            } catch (FileNotFoundException e) {
                System.out.println("[!] Cannot load revive button image");
                e.printStackTrace();
            }

            // Set x for restart button
            x = Constants.width / 2 - (int) (size / 1.75);
        }

        // Add restart button
        int size = 125;
        if (x == -1) {
            x = Constants.width / 2;
        }
        int y = (int) (Constants.height * 0.8);
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

        // Set scene
        Scene scene = new Scene(menu);
        stage.setScene(scene);
    }

}
