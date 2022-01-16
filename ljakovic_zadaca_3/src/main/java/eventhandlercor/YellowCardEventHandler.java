package eventhandlercor;

import championshipcomposite.ChampionshipComposite;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Match;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchEvent;
import playerstate.InPlayState;

/**
 *
 * @author Luka Jaković
 */
public class YellowCardEventHandler implements EventHandler {

    private EventHandler nextHandler;

    @Override
    public void setNextHandler(EventHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void processEvent(MatchEvent event) {
        Match match = (Match) ChampionshipComposite.getInstance().findComponent(event.matchNumber.toString());

        if (event.eventType == 10) {
            if (event.player != null && event.player.getState().getClass().equals(InPlayState.class)) {
                var currentMatchEvents = match.yellowPlayer(event.player);

                if (currentMatchEvents.size() == 1) {
                    event.footballClub.yellowCards += 1;
                    event.footballClub.cardsTotal += 1;
                }
                if (currentMatchEvents.size() > 1) {
                    if (!currentMatchEvents.get(currentMatchEvents.size() - 1).minute.equals(event.minute)) {
                        event.footballClub.secondYellowCards += 1;
                        event.footballClub.cardsTotal += 1;
                        event.player.getState().excludePlayerFromGame();
                    } else {
                        event.footballClub.yellowCards += 1;
                        event.footballClub.cardsTotal += 1;
                    }
                }
            }
        } else {
            nextHandler.processEvent(event);
        }
    }

}
