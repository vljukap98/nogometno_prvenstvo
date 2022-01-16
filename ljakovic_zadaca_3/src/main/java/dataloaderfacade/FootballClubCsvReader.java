package dataloaderfacade;

import championshipcomposite.ChampionshipComposite;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.HeadCoach;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Luka Jaković
 */
class FootballClubCsvReader extends CsvReader {

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
                        String club = c[0];
                        String clubName = c[1];
                        HeadCoach headCoach = new HeadCoach(c[2]);

                        FootballClub fc = new FootballClub(
                                club,
                                clubName,
                                headCoach
                        );

                        ChampionshipComposite.getInstance().addComponent(fc);
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

        if (row.length != columnCount) {
            if (row[1] != null) {
                errors += row;
            }
            errors += " -- Klub neispravan, nedostaju neki podaci";
        } else {
            if (ChampionshipComposite.getInstance().findComponent(row[0]) != null) {
                errors += " -- klub već postoji";
            }
        }

        if (errors.isEmpty()) {
            return true;
        } else {
            errors += " -- redak u datoteci klubova: " + currentRow;
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
