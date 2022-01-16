package visitor;

import championshipcomposite.ChampionshipComponent;
import championshipcomposite.ChampionshipComposite;
import eventhandlercor.EventHandler;
import eventhandlercor.GoalEventHandler;
import eventhandlercor.RedCardEventHandler;
import eventhandlercor.SubstitutionEventHandler;
import eventhandlercor.YellowCardEventHandler;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Match;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchEvent;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchLineup;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Player;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luka Jaković
 */
public class CardVisitor implements IChampionshipVisitor {

    private final Integer round;

    private final EventHandler goalHandler = new GoalEventHandler();
    private final EventHandler yellowCardHandler = new YellowCardEventHandler();
    private final EventHandler redCardHandler = new RedCardEventHandler();
    private final EventHandler subHandler = new SubstitutionEventHandler();

    public CardVisitor(Integer round) {
        this.round = round;

        goalHandler.setNextHandler(yellowCardHandler);
        yellowCardHandler.setNextHandler(redCardHandler);
        redCardHandler.setNextHandler(subHandler);
    }

    @Override
    public void visit(FootballClub club) {
    }

    @Override
    public void visit(Match match) {
        List<MatchEvent> events = new ArrayList<>();

        for (ChampionshipComponent el : match.getLineupsEvents()) {
            if (el.getClass().equals(MatchEvent.class)) {
                events.add((MatchEvent) el);
            }
        }

        for (MatchEvent event : events) {
            if (match.round <= round) {
                goalHandler.processEvent(event);
            }
        }
    }

    @Override
    public void visit(MatchEvent event) {
    }

    @Override
    public void visit(MatchLineup lineup) {
        if (lineup.type.equalsIgnoreCase("s")) {
            lineup.player.getState().putPlayerInGame();
        } else if (lineup.type.equalsIgnoreCase("p")) {
            lineup.player.getState().putPlayerAsReserve();
        }
    }

    @Override
    public void visit(Player player) {
    }

    @Override
    public void visit(ChampionshipComposite composite) {
    }

}
