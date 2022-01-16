package eventhandlercor;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchEvent;
import playerstate.InPlayState;

/**
 *
 * @author Luka Jaković
 */
public class GoalEventHandler implements EventHandler {

    private EventHandler nextHandler;

    @Override
    public void setNextHandler(EventHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void processEvent(MatchEvent event) {

        if (event.eventType == 1 || event.eventType == 2) {
            if (event.player != null && event.player.getState().getClass().equals(InPlayState.class)) {
                event.player.goals += 1;
            }
        } else {
            nextHandler.processEvent(event);
        }
    }

}
