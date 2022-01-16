package matchlineupgenerator;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Match;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luka Jaković
 */
public class MatchRound {

    private Integer round;
    private List<Match> matches;

    public MatchRound(Integer round) {
        this.round = round;
        this.matches = new ArrayList<>();
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

}
