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

public class SavedGamesMenu implements Menu {
    public void create(Stage stage, ColorSwitch game) {
        Pane menu = new Pane();
        menu.setPrefSize(Constants.width, Constants.height);

        // Set Menu background
        Background bg = new Background(Constants.width, Constants.height);
        menu.getChildren().add(bg);

        try {
            Image homeImage = new Image(new FileInputStream(Constants.assetsPath + "home.png"));
            ImageView homeIMView = new ImageView(homeImage);

            int size = 60;
            homeIMView.setFitHeight(size);
            homeIMView.setFitWidth(size);
            Group homeButton = new Group(homeIMView);

            homeButton.setLayoutX(0);
            homeButton.setLayoutY(0);
            homeButton.setTranslateX(10);
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
            Image titleImage = new Image(new FileInputStream(Constants.assetsPath + "saveGameTitle.png"));
            ImageView titleIMView = new ImageView(titleImage);

            titleIMView.setFitHeight(85);
            titleIMView.setFitWidth(450);
            Group title = new Group(titleIMView);

            title.setLayoutX(0);
            title.setLayoutY(0);
            title.setTranslateX(Constants.width / 2 - 450 / 2);
            title.setTranslateY(20);

            title.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent mEvent) {
                    if (mEvent.getButton() == MouseButton.PRIMARY) {
                        HomeMenu homeMenu = new HomeMenu();
                        homeMenu.create(stage, game);
                    }
                }
            });

            menu.getChildren().add(title);
        } catch (FileNotFoundException e) {
            System.out.println("[!] Cannot load home button image");
            e.printStackTrace();
        }

        for (int idx = 0; idx < game.storage.savedGames.size(); idx++) {
            try {

                System.out.println("slot " + idx);
                Image logoImage = new Image(new FileInputStream(Constants.assetsPath + "slot" + (idx + 1) + ".png"));
                ImageView logoIMView = new ImageView(logoImage);

                int height = 85;
                logoIMView.setFitHeight(height);
                logoIMView.setFitWidth(345);
                Group logo = new Group(logoIMView);

                logo.setLayoutX(0);
                logo.setLayoutY(0);
                logo.setTranslateY(20 + (85 + 20) * (idx + 1));
                logo.setTranslateX(Constants.width / 2 - 345 / 2);

                int i = idx;
                logo.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent mEvent) {
                        game.storage.loadGameState(game, i);
                    }
                });

                menu.getChildren().add(logo);
            } catch (FileNotFoundException e) {
                System.out.println("[!] Cannot load slot image");
                e.printStackTrace();
            }
        }

        // Set scene
        Scene scene = new Scene(menu);
        stage.setScene(scene);
    }

}
