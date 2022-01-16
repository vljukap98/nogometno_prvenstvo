package eventbuilder;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchEvent;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Player;

/**
 *
 * @author Luka Jaković
 */
public interface IMatchEventBuilder {

    public MatchEvent build();

    public IMatchEventBuilder buildMatchEvent(Integer matchNumber, String minute, Integer type);

    public IMatchEventBuilder buildClubPlayer(FootballClub club, Player player);

    public IMatchEventBuilder buildSubstitute(Player player);

}
