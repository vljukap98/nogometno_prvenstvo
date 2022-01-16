package playerstate;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Player;

/**
 *
 * @author Luka Jaković
 */
public class ReplacedState extends State {

    public ReplacedState(Player player) {
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
        //do nothing
    }

    @Override
    public void excludePlayerFromGame() {
        //do nothing
    }

}
