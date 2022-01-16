package matchlineupgenerator;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;

/**
 *
 * @author Luka Jaković
 */
public class ClubNameGeneratingAlgorithm extends GeneratingAlgorithm {

    public ClubNameGeneratingAlgorithm() {
        super();
    }

    @Override
    protected void fillClubHalves() {
        sortClubsByName();

        for (int i = 0; i < clubCount / 2; i++) {
            firstHalf.add(clubs.get(i));
        }

        for (FootballClub club : clubs) {
            if (!firstHalf.contains(club)) {
                secondHalf.add(club);
            }
        }
    }

    private void sortClubsByName() {
        clubs.sort((c1, c2) -> c1.name.compareTo(c2.name));
    }

}
