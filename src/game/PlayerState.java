package game;

import java.io.Serializable;

import game.constants.Colors;

public class PlayerState implements Serializable {
    private static final long serialVersionUID = 19443851967169512L;

    private double x, y;
    private int color, futureSafeColor;

    private double velocity;
    private int score;
    private double radius;

    public static PlayerState getPlayerState(Player player) {
        PlayerState playerState = new PlayerState();

        playerState.x = player.getTranslateX();
        playerState.y = player.getTranslateY();

        playerState.color = Colors.primaryColors.indexOf(player.getColor());
        playerState.futureSafeColor = Colors.primaryColors.indexOf(player.getFutureSafeColor());

        playerState.velocity = player.getVelocity();
        playerState.score = player.getScore();

        playerState.radius = player.getRadius();

        System.out.println("save: " + playerState.color);
        return playerState;
    }

    public int getColor() {
        return this.color;
    }

    public static Player loadPlayerState(PlayerState playerState) {
        Player player = new Player(playerState.x, playerState.y, playerState.radius,
                Colors.primaryColors.get(playerState.color));

        player.setVelocity(playerState.velocity);
        player.setScore(playerState.score);
        player.setFutureSafeColor(Colors.primaryColors.get(playerState.futureSafeColor));

        player.setColor(Colors.primaryColors.get(playerState.color));
        System.out.println("load: " + playerState.color);

        return player;
    }
}
