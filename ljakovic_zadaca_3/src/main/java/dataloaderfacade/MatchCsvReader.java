package dataloaderfacade;

import championshipcomposite.ChampionshipComposite;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Match;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

/**
 *
 * @author Luka Jaković
 */
class MatchCsvReader extends CsvReader {

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
                        Integer round = parser.tryParseInt(c[1]);
                        FootballClub host = (FootballClub) ChampionshipComposite
                                .getInstance()
                                .findComponent(c[2]);
                        FootballClub guest = (FootballClub) ChampionshipComposite
                                .getInstance()
                                .findComponent(c[3]);
                        Date startsAt = parser.tryParseDateTime(c[4]);

                        Match match = new Match(
                                matchNumber,
                                round,
                                host,
                                guest,
                                startsAt
                        );

                        ChampionshipComposite.getInstance().addComponent(match);
                    }

                } else {
                    calculateColumnCount();
                    firstRow = false;
                };
            }

        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    @Override
    Boolean isRowValid(String[] row) {
        String errors = "";

        if (row.length != columnCount) {
            if (row[0] != null) {
                errors += row[0];
            }
            errors += " -- Utakmica neispravna, nedostaju neki podaci";
        }

        if (errors.isEmpty()) {
            return true;
        } else {
            errors += " -- redak u datoteci utakmica: " + currentRow;
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
