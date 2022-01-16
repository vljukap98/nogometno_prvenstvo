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
public class ResetVisitor implements IChampionshipVisitor {

    @Override
    public void visit(FootballClub club) {
        club.resetClubData();
    }

    @Override
    public void visit(Match match) {
        match.resetMatch();
    }

    @Override
    public void visit(MatchEvent event) {
    }

    @Override
    public void visit(MatchLineup lineup) {
    }

    @Override
    public void visit(Player player) {
        player.resetGoals();
        player.resetState();
    }

    @Override
    public void visit(ChampionshipComposite composite) {
    }

}
