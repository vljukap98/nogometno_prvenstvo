package eventhandlercor;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchEvent;
import playerstate.InPlayState;

/**
 *
 * @author Luka Jaković
 */
public class RedCardEventHandler implements EventHandler {

    private EventHandler nextHandler;

    @Override
    public void setNextHandler(EventHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void processEvent(MatchEvent event) {

        if (event.eventType == 11 && event.player.getState().getClass().equals(InPlayState.class)) {
            event.footballClub.redCards += 1;
            event.footballClub.cardsTotal += 1;
            event.player.getState().excludePlayerFromGame();
        } else {
            nextHandler.processEvent(event);
        }
    }

}
