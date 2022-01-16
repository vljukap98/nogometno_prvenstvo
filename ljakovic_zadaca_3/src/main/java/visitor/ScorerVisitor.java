package visitor;

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

/**
 *
 * @author Luka Jaković
 */
public class ScorerVisitor implements IChampionshipVisitor {

    private final Integer round;
    private final EventHandler goalHandler = new GoalEventHandler();
    private final EventHandler yellowCardHandler = new YellowCardEventHandler();
    private final EventHandler redCardHandler = new RedCardEventHandler();
    private final EventHandler subHandler = new SubstitutionEventHandler();

    public ScorerVisitor(Integer round) {
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
    }

    @Override
    public void visit(MatchEvent event) {
        Match match = (Match) ChampionshipComposite
                .getInstance()
                .findComponent(event.matchNumber.toString());

        if (match.round <= this.round) {
            goalHandler.processEvent(event);
        }

        if (event.eventType == 99) {
            for (var p : match.guest.players) {
                p.resetState();
            }
            for (var p : match.host.players) {
                p.resetState();
            }
        }
    }

    @Override
    public void visit(MatchLineup lineup) {
        Match match = (Match) ChampionshipComposite
                .getInstance()
                .findComponent(lineup.matchNumber.toString());

        if (match.round <= this.round) {
            if (lineup.type.equalsIgnoreCase("s")) {
                lineup.player.getState().putPlayerInGame();
            } else if (lineup.type.equalsIgnoreCase("p")) {
                lineup.player.getState().putPlayerAsReserve();
            }
        }
    }

    @Override
    public void visit(Player player) {
    }

    @Override
    public void visit(ChampionshipComposite composite) {
    }

}
