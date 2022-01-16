package playerstate;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Player;

/**
 *
 * @author Luka Jaković
 */
public class InPlayState extends State {

    public InPlayState(Player player) {
        super(player);
    }

    @Override
    public void putPlayerInGame() {
        //do nothing
    }

    @Override
    public void putPlayerAsReserve() {
        //do nothing
    }

    @Override
    public void substitutePlayer() {
        player.changeState(new ReplacedState(player));
    }

    @Override
    public void excludePlayerFromGame() {
        player.changeState(new ExcludedFromGameState(player));
    }

}
