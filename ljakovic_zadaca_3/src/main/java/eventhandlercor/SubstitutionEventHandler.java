package eventhandlercor;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchEvent;
import playerstate.InPlayState;
import playerstate.InReserveState;

/**
 *
 * @author Luka Jaković
 */
public class SubstitutionEventHandler implements EventHandler {

    private EventHandler nextHandler;

    @Override
    public void setNextHandler(EventHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void processEvent(MatchEvent event) {

        if (event.eventType == 20) {
            if (event.player != null && event.substitute != null
                    && event.player.getState().getClass().equals(InPlayState.class)
                    && event.substitute.getState().getClass().equals(InReserveState.class)) {
                event.player.getState().substitutePlayer();
                event.substitute.getState().putPlayerInGame();
            }
        }
    }

}
