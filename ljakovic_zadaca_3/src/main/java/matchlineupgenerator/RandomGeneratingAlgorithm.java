package matchlineupgenerator;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import java.util.Random;

/**
 *
 * @author Luka Jaković
 */
public class RandomGeneratingAlgorithm extends GeneratingAlgorithm {

    private final Random rand;
    private Integer randNum;

    public RandomGeneratingAlgorithm() {
        super();
        rand = new Random();
    }

    @Override
    protected void fillClubHalves() {
        while (firstHalf.size() < clubCount / 2) {
            randNum = rand.nextInt(clubCount);

            if (!firstHalf.contains(clubs.get(randNum))) {
                firstHalf.add(clubs.get(randNum));
            }
        }

        for (FootballClub club : clubs) {
            if (!firstHalf.contains(club)) {
                secondHalf.add(club);
            }
        }
    }

}
