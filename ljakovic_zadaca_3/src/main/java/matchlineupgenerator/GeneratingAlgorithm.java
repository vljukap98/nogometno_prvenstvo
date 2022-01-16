package matchlineupgenerator;

import championshipcomposite.ChampionshipComposite;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Match;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luka Jaković
 */
public abstract class GeneratingAlgorithm {

    protected Integer maxRoundsNumber = 0;

    protected List<FootballClub> clubs;
    protected List<FootballClub> firstHalf;
    protected List<FootballClub> secondHalf;

    Integer clubCount = ChampionshipComposite.getInstance().clubNumber();

    public GeneratingAlgorithm() {
        firstHalf = new ArrayList<>();
        secondHalf = new ArrayList<>();

        clubs = fillClubs();
    }

    public final List<MatchRound> generateMatchLineup() {
        List<MatchRound> r = new ArrayList<>();

        fillClubHalves();

        generateOddRounds(r);
        generateEvenRounds(r);
        sortLineup(r);

        return r;
    }

    protected abstract void fillClubHalves();

    public final Boolean matchExists(Match match, MatchRound round) {
        Boolean matchExists = false;

        for (Match m : round.getMatches()) {
            if (m.host.equals(match.host) && m.guest.equals(match.guest)) {
                matchExists = true;
            }
        }

        return matchExists;
    }

    public final Boolean clubExistsInRound(FootballClub club1, FootballClub club2, MatchRound round) {
        Boolean matchExists = false;

        for (Match m : round.getMatches()) {
            if (m.host.equals(club1) || m.guest.equals(club2) || m.host.equals(club2) || m.guest.equals(club1)) {
                matchExists = true;
            }
        }

        return matchExists;
    }

    public final Boolean matchInRoundsExits(Match match, List<MatchRound> rounds) {
        Boolean matchExists = false;

        for (MatchRound r : rounds) {
            for (Match m : r.getMatches()) {
                if (m.host.equals(match.host) && m.guest.equals(match.guest)) {
                    matchExists = true;
                }
            }
        }

        return matchExists;
    }

    private List<FootballClub> fillClubs() {
        List<FootballClub> c = new ArrayList<>();

        for (var component : ChampionshipComposite.getInstance().getComponents()) {
            if (component.getClass().equals(FootballClub.class)) {
                c.add((FootballClub) component);
                maxRoundsNumber += 1;
            }
        }

        if (maxRoundsNumber % 2 != 0) {
            maxRoundsNumber -= 1;
        }

        return c;
    }

    private void sortLineup(List<MatchRound> r) {
        r.sort((r1, r2) -> r1.getRound().compareTo(r2.getRound()));
    }

    private void generateOddRounds(List<MatchRound> r) {
        for (int i = 1; i <= clubCount; i += 2) {
            MatchRound matchRound = new MatchRound(i);

            for (FootballClub fc1 : firstHalf) {
                for (FootballClub fc2 : secondHalf) {
                    if (!clubExistsInRound(fc1, fc2, matchRound)) {
                        Match newMatch = new Match(fc1, fc2);
                        newMatch.round = matchRound.getRound();

                        if (!matchInRoundsExits(newMatch, r)) {
                            matchRound.getMatches().add(newMatch);
                        }
                    }
                }
            }

            r.add(matchRound);
        }
    }

    private void generateEvenRounds(List<MatchRound> r) {
        for (int i = 1; i <= clubCount / 2; i++) {
            MatchRound matchRound = r.get(i);

            MatchRound newMatchRound = new MatchRound(i * 2);

            for (Match match : matchRound.getMatches()) {
                Match newMatch = new Match();

                newMatch.host = match.guest;
                newMatch.guest = match.host;
                newMatch.round = newMatchRound.getRound();

                newMatchRound.getMatches().add(newMatch);
            }

            r.add(newMatchRound);
        }
    }
}
