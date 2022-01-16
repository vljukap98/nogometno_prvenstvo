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
public class ClubResultsVisitor implements IChampionshipVisitor {

    private final Integer round;

    public ClubResultsVisitor(Integer round) {
        this.round = round;
    }

    @Override
    public void visit(FootballClub club) {
    }

    @Override
    public void visit(Match match) {
        if (match.guestGoals > match.hostGoals) {
            match.winner = match.guest;
        } else if (match.guestGoals < match.hostGoals) {
            match.winner = match.host;
        } else {
            match.winner = null;
        }
    }

    @Override
    public void visit(MatchEvent event) {
        Match match = (Match) ChampionshipComposite
                .getInstance()
                .findComponent(event.matchNumber.toString());

        if (match.round <= round) {
            if (event.eventType == 1 || event.eventType == 2) {
                if (event.footballClub.equals(match.guest)) {
                    match.guestGoals += 1;
                }
                if (event.footballClub.equals(match.host)) {
                    match.hostGoals += 1;
                }
            } else if (event.eventType == 3) {
                if (event.footballClub.equals(match.guest)) {
                    match.hostGoals += 1;
                }
                if (event.footballClub.equals(match.host)) {
                    match.guestGoals += 1;
                }
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
