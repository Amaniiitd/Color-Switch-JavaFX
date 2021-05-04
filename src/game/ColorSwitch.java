package game;

import game.constants.Colors;
import game.constants.Constants;
import game.menu.GameOverMenu;
import game.menu.HomeMenu;

import game.obstacles.DoubleRingObstacle;
import game.obstacles.Obstacle;
import game.obstacles.RingObstacle;
import game.obstacles.StripObstacle;
import game.obstacles.TripleRingObstacle;

import game.physics.PhysicsEngine;
import game.props.ColorChangeWheel;
import game.props.Prop;
import game.props.Star;

import game.storage.GameState;
import game.storage.GameStorage;
import game.ui.Background;
import game.ui.GamePlayOverlay;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

/*
 *
 *
 *
 * Note to TA: There is a revive button on GameOverScreen, it will only be visible if you score more than or equal to 5
 *
 *
 *
 */

// "vmArgs": "--module-path /usr/lib/javafx/lib --add-modules javafx.base,javafx.graphics"

public class ColorSwitch extends Application {
	public GameState state;
	public GameStorage storage;

	public ColorSwitch() {
		this.state = new GameState();
	}

	public void removeUnusableEntities() {
		for (int i = state.obstacles.size() - 1; i >= 0; i--) {
			if (state.obstacles.get(i).getObstacle().getBoundsInParent().getMinY() > state.height + 50) {
				state.playGround.getChildren().remove(state.obstacles.get(i).getObstacle());
				state.obstacles.remove(i);
			}
		}

		for (int i = state.props.size() - 1; i >= 0; i--) {
			if (!state.props.get(i).getProp().isVisible()) {
				state.playGround.getChildren().remove(state.props.get(i).getProp());
				state.props.remove(i);
			}
		}
	}

	private boolean addStar(int x, int y) {
		switch (state.rand.nextInt(50) % 2) {
			case 1:
				Star star = new Star(x, y);
				state.props.add(star);

				state.playGround.getChildren().add(star.getProp());
				return true;
		}

		return false;
	}

	private void addColorBall(int x, int y, Color color) {
		ColorChangeWheel cw = new ColorChangeWheel(x, y, color);
		state.props.add(cw);

		state.playGround.getChildren().add(cw.getProp());
		state.player.setFutureSafeColor(color);
	}

	private void addColorBall(int x, int y) {
		ColorChangeWheel cw = new ColorChangeWheel(x, y);
		state.props.add(cw);

		state.playGround.getChildren().add(cw.getProp());
		state.player.setFutureSafeColor(cw.getColorToChange());
	}

	private void addObstacle(int obsType) {
		if (state.obstacles.size() > 0
				&& state.obstacles.get(state.obstacles.size() - 1).getObstacle().getBoundsInParent().getMinY() < 0) {
			return;
		}

		Color safeColor = (Color) state.player.getFill();
		switch (obsType) {
			case 1:
				StripObstacle sObstacle = new StripObstacle(state.width, 10, 0, safeColor);
				int y;
				y = -(int) sObstacle.getObstacle().getBoundsInParent().getHeight();
				y -= (state.rand.nextInt(51) + 175);
				sObstacle.setY(y);
				if (sObstacle.getObstacle().getBoundsInParent().getMaxY() < 0) {
					state.playGround.getChildren().add(sObstacle.getObstacle());
					state.obstacles.add(sObstacle);
				}
				break;
			case 2:
				RingObstacle rObstacle = new RingObstacle(state.width / 2, 0, 100);
				y = -(int) rObstacle.getObstacle().getBoundsInParent().getHeight();
				y -= (state.rand.nextInt(51) + 50);

				rObstacle.setY(y);
				if (rObstacle.getObstacle().getBoundsInParent().getMaxY() < 0) {
					state.playGround.getChildren().add(rObstacle.getObstacle());
					state.obstacles.add(rObstacle);

					if (!this.addStar(state.width / 2, y)) {
						if (state.rand.nextInt(5) == 3) {
							this.addColorBall(state.width / 2, y);
						}
					}
				}
				break;
			case 3:
				DoubleRingObstacle dRingObstacle = new DoubleRingObstacle(state.width / 2, 0, 100, safeColor);
				y = -(int) dRingObstacle.getObstacle().getBoundsInParent().getHeight();

				y -= (state.rand.nextInt(51) + 50);
				dRingObstacle.setY(y);
				if (dRingObstacle.getObstacle().getBoundsInParent().getMaxY() < 0) {
					state.playGround.getChildren().add(dRingObstacle.getObstacle());
					state.obstacles.add(dRingObstacle);

					if (dRingObstacle.getSafeColor() != state.player.getFutureSafeColor()) {
						this.addColorBall(state.width / 2,
								(int) dRingObstacle.getObstacle().getBoundsInParent().getMaxY(),
								dRingObstacle.getSafeColor());

						// Shift this obstacle 40px upwards
						dRingObstacle.setY(dRingObstacle.getObstacle().getTranslateY() - 40);
						y -= 40;
					}

					this.addStar(state.width / 2, y);
				}
				break;
			case 4:
				TripleRingObstacle tRingObstacle = new TripleRingObstacle(state.width / 2, 0, 100, safeColor);
				y = -(int) tRingObstacle.getObstacle().getBoundsInParent().getHeight();

				y -= (state.rand.nextInt(51) + 50);
				tRingObstacle.setY(y);
				if (tRingObstacle.getObstacle().getBoundsInParent().getMaxY() < 0) {
					state.playGround.getChildren().add(tRingObstacle.getObstacle());
					state.obstacles.add(tRingObstacle);

					if (tRingObstacle.getSafeColor() != state.player.getFutureSafeColor()) {
						this.addColorBall(state.width / 2,
								(int) tRingObstacle.getObstacle().getBoundsInParent().getMaxY(),
								tRingObstacle.getSafeColor());

						// Shift this obstacle 40px upwards
						tRingObstacle.setY(tRingObstacle.getObstacle().getTranslateY() - 40);
						y -= 40;
					}
					this.addStar(state.width / 2, y);
				}
				break;

		}
	}

	private void addObstacle() {
		if (state.obstacles.size() > 0
				&& state.obstacles.get(state.obstacles.size() - 1).getObstacle().getBoundsInParent().getMinY() < 0) {
			return;
		}

		int obstacleType = state.rand.nextInt(100);
		obstacleType = (obstacleType % 4) + 1;

		this.addObstacle(obstacleType);
	}

	public void updateGame() {
		this.addObstacle();

		state.overlay.setScore(state.player.getScore() + "");

		for (Obstacle obs : state.obstacles) {
			obs.animate(state.frameTime);
			if (!state.player.isDead() && obs.checkCollision(state.player)) {
				state.gamePaused = true;
				state.player.die();
				if (state.player.getScore() > state.bestScore) {
					state.bestScore = state.player.getScore();
				}

				state.gameLoop.stop();
				GameOverMenu gameOverMenu = new GameOverMenu();
				gameOverMenu.create(state.stage, this);
			}
		}

		for (Prop prop : state.props) {
			prop.checkCollision(state.player);
		}

		if (state.player.getTranslateY() > state.height) {
			state.gamePaused = true;
			state.player.die();
			if (state.player.getScore() > state.bestScore) {
				state.bestScore = state.player.getScore();
			}

			state.gameLoop.stop();
			GameOverMenu gameOverMenu = new GameOverMenu();
			gameOverMenu.create(state.stage, this);
		}

		state.player.toFront();
	}

	private void addBasicObstacles() {
		// Add first Obstacle
		RingObstacle rObstacle = new RingObstacle(state.width / 2, 0, 100);
		int y = (int) rObstacle.getObstacle().getBoundsInParent().getHeight() / 2 + 50;

		rObstacle.setY(y);
		state.playGround.getChildren().add(rObstacle.getObstacle());
		state.obstacles.add(rObstacle);
		this.addStar(state.width / 2, y);
	}

	public void createPlayGround(Stage stage) {
		state.gamePaused = false;
		state.obstacles = new ArrayList<>();
		state.props = new ArrayList<>();

		state.player = new Player(state.width / 2, 400, 10, Colors.yellow);

		state.rand = new Random();

		state.physicsEngine = new PhysicsEngine(state.player, state.height, state.obstacles, state.props);
		state.frameTime = state.physicsEngine.getFrameTime();
		state.bestScore = storage.bestScore;

		state.playGround = new Pane();
		state.playGround.setPrefSize(state.width, state.height);

		Background bg = new Background(state.width, state.height);
		state.playGround.getChildren().add(bg);

		state.playGround.getChildren().add(state.player);

		state.overlay = new GamePlayOverlay(this, state.stage);
		state.playGround.getChildren().add(state.overlay.getUI());

		state.gameLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (!state.gamePaused) {
					state.physicsEngine.physics();
					removeUnusableEntities();

					updateGame();
				}
			}
		};
		state.gameLoop.start();

		Scene scene = new Scene(state.playGround);
		scene.setOnMouseClicked(e -> {
			switch (e.getButton()) {
				case PRIMARY:
					if (!state.gamePaused) {
						state.physicsEngine.applyForceOnPlayer();
					}
					break;
				default:
					break;
			}
		});

		stage.setScene(scene);
		this.addBasicObstacles();
	}

	@Override
	public void start(Stage stage) throws Exception {
		state.stage = stage;
		HomeMenu homeMenu = new HomeMenu();
		homeMenu.create(stage, this);

		stage.setTitle(Constants.gameTitle);
		stage.show();

		storage = GameStorage.loadGameStorage();
		if (storage == null) {
			storage = new GameStorage();
		}

		while (storage.savedGames.size() > 4) {
			storage.savedGames.remove(0);
		}
	}

	public static void main(String args[]) {
		launch(args);
	}

	public Player getPlayer() {
		return state.player;
	}

	public int getHighScore() {
		storage.bestScore = state.bestScore;
		return state.bestScore;
	}

}
