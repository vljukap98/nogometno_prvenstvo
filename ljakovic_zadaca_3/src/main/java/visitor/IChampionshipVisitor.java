package visitor;

import championshipcomposite.ChampionshipComposite;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Match;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchEvent;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchLineup;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Player;

/**
 *
 * @author Luka
 */
public interface IChampionshipVisitor {

    public void visit(ChampionshipComposite composite);

    public void visit(FootballClub club);

    public void visit(Match match);

    public void visit(MatchEvent event);

    public void visit(MatchLineup lineup);

    public void visit(Player player);
}
