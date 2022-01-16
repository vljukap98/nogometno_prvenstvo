package playerstate;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Player;

/**
 *
 * @author Luka Jaković
 */
public class InReserveState extends State {

    public InReserveState(Player player) {
        super(player);
    }

    @Override
    public void putPlayerInGame() {
        player.changeState(new InPlayState(player));
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
