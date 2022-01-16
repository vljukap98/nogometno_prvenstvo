package playerstate;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Player;

/**
 *
 * @author Luka Jaković
 */
public abstract class State {

    Player player;

    State(Player player) {
        this.player = player;
    }

    public abstract void putPlayerInGame();

    public abstract void putPlayerAsReserve();

    public abstract void substitutePlayer();

    public abstract void excludePlayerFromGame();
}
