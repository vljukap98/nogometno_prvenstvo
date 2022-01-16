package dataloaderfacade;

/**
 *
 * @author Luka Jaković
 */
class CsvReaderFactory {

    protected CsvReader getReader(String flag) {
        if (flag.equalsIgnoreCase("i")) {
            return new PlayerCsvReader();
        } else if (flag.equalsIgnoreCase("s") || flag.equalsIgnoreCase("ns")) {
            return new MatchLineupCsvReader();
        } else if (flag.equalsIgnoreCase("d") || flag.equalsIgnoreCase("nd")) {
            return new MatchEventCsvReader();
        } else if (flag.equalsIgnoreCase("u") || flag.equalsIgnoreCase("nu")) {
            return new MatchCsvReader();
        } else if (flag.equalsIgnoreCase("k")) {
            return new FootballClubCsvReader();
        }

        return null;
    }
}
