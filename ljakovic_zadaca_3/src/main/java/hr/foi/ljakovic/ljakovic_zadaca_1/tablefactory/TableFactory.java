package hr.foi.ljakovic.ljakovic_zadaca_1.tablefactory;

/**
 *
 * @author Luka Jaković
 */
public class TableFactory {

    public Table getTable(String type, Integer round, String club) {
        if (type.equalsIgnoreCase("S")) {
            return new ScorerTable(round);
        } else if (type.equalsIgnoreCase("T")) {
            return new LeagueStandingsTable(round);
        } else if (type.equalsIgnoreCase("K")) {
            return new CardsTable(round);
        } else if (type.equalsIgnoreCase("R")) {
            return new ClubResultsTable(round, club);
        }

        return null;
    }

}
