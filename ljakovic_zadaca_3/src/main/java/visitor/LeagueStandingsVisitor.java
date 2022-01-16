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
public class LeagueStandingsVisitor implements IChampionshipVisitor {

    private final Integer round;

    public LeagueStandingsVisitor(Integer round) {
        this.round = round;
    }

    @Override
    public void visit(FootballClub club) {
    }

    @Override
    public void visit(Match match) {
        if (match.hasEvents() && match.round <= round) {
            if (match.guestGoals > match.hostGoals) {
                match.winner = match.guest;
                match.loser = match.host;

                match.winner.wins += 1;
                match.loser.defeats += 1;

                match.winner.goals += match.guestGoals;
                match.loser.goals += match.hostGoals;

                match.winner.goalsRecieved += match.hostGoals;
                match.loser.goalsRecieved += match.guestGoals;

                match.winner.championshipPoints += 3;
            } else if (match.guestGoals < match.hostGoals) {
                match.winner = match.host;
                match.loser = match.guest;

                match.winner.wins += 1;
                match.loser.defeats += 1;

                match.winner.goals += match.hostGoals;
                match.loser.goals += match.guestGoals;

                match.winner.goalsRecieved += match.guestGoals;
                match.loser.goalsRecieved += match.hostGoals;

                match.winner.championshipPoints += 3;
            } else {
                match.winner = null;
                match.loser = null;

                match.guest.draws += 1;
                match.host.draws += 1;

                match.guest.goals += match.guestGoals;
                match.host.goals += match.hostGoals;

                match.guest.goalsRecieved += match.hostGoals;
                match.host.goalsRecieved += match.guestGoals;

                match.guest.championshipPoints += 1;
                match.host.championshipPoints += 1;
            }
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
            if (event.eventType == 99) {
                for (var p : match.guest.players) {
                    p.resetState();
                }
                for (var p : match.host.players) {
                    p.resetState();
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
