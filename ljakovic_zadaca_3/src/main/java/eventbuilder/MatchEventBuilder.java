package eventbuilder;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchEvent;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Player;

/**
 *
 * @author Luka Jaković
 */
public class MatchEventBuilder implements IMatchEventBuilder {

    private MatchEvent matchEvent;

    public MatchEventBuilder() {

    }

    @Override
    public MatchEvent build() {
        return this.matchEvent;
    }

    @Override
    public MatchEventBuilder buildMatchEvent(Integer matchNumber, String minute, Integer type) {
        this.matchEvent = new MatchEvent();
        this.matchEvent.matchNumber = matchNumber;
        this.matchEvent.minute = minute;
        this.matchEvent.eventType = type;

        return this;
    }

    @Override
    public MatchEventBuilder buildClubPlayer(FootballClub club, Player player) {
        this.matchEvent.footballClub = club;
        this.matchEvent.player = player;

        return this;
    }

    @Override
    public MatchEventBuilder buildSubstitute(Player player) {
        this.matchEvent.substitute = player;

        return this;
    }

}
