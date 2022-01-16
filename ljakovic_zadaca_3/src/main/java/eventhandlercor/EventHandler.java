package eventhandlercor;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchEvent;

/**
 *
 * @author Luka Jaković
 */
public interface EventHandler {

    public void setNextHandler(EventHandler nextHandler);

    public void processEvent(MatchEvent event);
}
