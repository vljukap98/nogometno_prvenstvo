package dataloaderfacade;

import championshipcomposite.ChampionshipComposite;
import eventbuilder.IMatchEventBuilder;
import eventbuilder.MatchEventBuilder;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Match;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchEvent;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Player;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Luka Jaković
 */
class MatchEventCsvReader extends CsvReader {

    private final IMatchEventBuilder builder = new MatchEventBuilder();
    private MatchEvent matchEvent = new MatchEvent();

    @Override
    protected void loadData(String path) {
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(path),
                            "UTF-8")
            );

            while ((row = reader.readLine()) != null) {
                currentRow += 1;

                if (!firstRow) {
                    String[] c = row.split(";");

                    if (isRowValid(c)) {
                        Integer matchNumber = parser.tryParseInt(c[0]);
                        String minute = c[1];
                        Integer eventType = parser.tryParseInt(c[2]);

                        Match match = (Match) ChampionshipComposite
                                .getInstance()
                                .findComponent(matchNumber.toString());

                        switch (eventType) {
                            case 0, 99 -> {
                                matchEvent = builder
                                        .buildMatchEvent(matchNumber, minute, eventType)
                                        .build();
                            }
                            case 1, 2, 3, 10, 11 -> {
                                FootballClub club
                                        = (FootballClub) ChampionshipComposite
                                                .getInstance()
                                                .findComponent(c[3]);
                                Player player
                                        = (Player) club
                                                .findPlayer(c[4]);

                                matchEvent = builder
                                        .buildMatchEvent(matchNumber, minute, eventType)
                                        .buildClubPlayer(club, player)
                                        .build();
                            }
                            case 20 -> {
                                FootballClub club
                                        = (FootballClub) ChampionshipComposite
                                                .getInstance()
                                                .findComponent(c[3]);
                                Player player
                                        = (Player) club
                                                .findPlayer(c[4]);
                                Player substitute
                                        = (Player) club
                                                .findPlayer(c[5]);

                                matchEvent = builder
                                        .buildMatchEvent(matchNumber, minute, eventType)
                                        .buildClubPlayer(club, player)
                                        .buildSubstitute(substitute)
                                        .build();
                            }
                            default -> {

                            }
                        }

                        match.addMatchComponent(matchEvent);

                    }
                } else {
                    calculateColumnCount();
                    firstRow = false;
                }
            }

        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    @Override
    Boolean isRowValid(String[] row) {

        String errors = "";

        if (row.length > 3) {
            if (ChampionshipComposite.getInstance().findComponent(row[3]) == null) {
                if (row[3] != null) {
                    errors += row[3];
                }
                errors += " -- klub ne postoji ";
            }
        }

        if (row.length < 3) {
            errors += " -- događaju nedostaju podaci";
        } else {
            if (ChampionshipComposite.getInstance().findComponent(row[0]) == null) {
                errors += " -- utakmica za dogadaj ne postoji";
            } else {

                switch (parser.tryParseInt(row[2])) {
                    case 1, 2, 3, 10, 11 -> {
                        if (row.length != 5) {
                            errors += " -- događaju nedostaju podaci";
                        }
                    }
                    case 20 -> {
                        if (row.length != 6) {
                            errors += " -- događaju nedostaju podaci";
                        }
                    }
                }
            }
        }

        if (errors.isEmpty()) {
            return true;
        } else {
            errors += " -- redak u datoteci događaja: " + currentRow;
            System.out.println(errors);

            return false;
        }
    }

    @Override
    protected void calculateColumnCount() {
        String[] c = row.split(";");

        columnCount = c.length;
    }

}
