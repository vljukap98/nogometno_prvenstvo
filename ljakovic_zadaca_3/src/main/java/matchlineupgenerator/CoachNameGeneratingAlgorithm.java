package matchlineupgenerator;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import java.util.Collections;

/**
 *
 * @author Luka Jaković
 */
public class CoachNameGeneratingAlgorithm extends GeneratingAlgorithm {

    public CoachNameGeneratingAlgorithm() {
        super();
    }

    @Override
    protected void fillClubHalves() {
        sortClubsByNameAndCoachName();

        for (int i = 0; i < clubCount / 2; i++) {
            firstHalf.add(clubs.get(i));
        }

        for (FootballClub club : clubs) {
            if (!firstHalf.contains(club)) {
                secondHalf.add(club);
            }
        }
    }

    private void sortClubsByNameAndCoachName() {
        Collections.sort(clubs, (Object o1, Object o2) -> {
            Integer x1 = ((FootballClub) o1).charNumber();
            Integer x2 = ((FootballClub) o2).charNumber();
            Integer c = x1.compareTo(x2);

            if (c != 0) {
                return c;
            }

            x1 = ((FootballClub) o1).headCoach.vowelNumber();
            x2 = ((FootballClub) o2).headCoach.vowelNumber();
            c = x2.compareTo(x1);

            return c;
        });
    }

}
