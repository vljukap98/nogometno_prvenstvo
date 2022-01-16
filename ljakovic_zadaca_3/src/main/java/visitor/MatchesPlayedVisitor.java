package visitor;

import championshipcomposite.ChampionshipComposite;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Match;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchEvent;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchLineup;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Player;

/**
 *
 * @author Luka Jaković
 */
public class MatchesPlayedVisitor implements IChampionshipVisitor {

    private final Integer round;

    public MatchesPlayedVisitor(Integer round) {
        this.round = round;
    }

    @Override
    public void visit(FootballClub club) {
    }

    @Override
    public void visit(Match match) {
        if (match.hasEvents() && match.round <= round) {
            if (match.guest.matchesPlayed < match.round) {
                match.guest.matchesPlayed = match.round;
            }
            if (match.host.matchesPlayed < match.round) {
                match.host.matchesPlayed = match.round;
            }
        }
    }

    @Override
    public void visit(MatchEvent event) {
        Match match = (Match) ChampionshipComposite
                .getInstance()
                .findComponent(event.matchNumber.toString());

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
