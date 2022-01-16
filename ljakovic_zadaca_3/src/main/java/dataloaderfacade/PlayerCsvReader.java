package dataloaderfacade;

import championshipcomposite.ChampionshipComposite;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Player;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

/**
 *
 * @author Luka Jaković
 */
class PlayerCsvReader extends CsvReader {

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
                    String[] c = row.split(DELIMITER);

                    if (isRowValid(c)) {
                        FootballClub club = (FootballClub) ChampionshipComposite
                                .getInstance()
                                .findComponent(c[0]);

                        if (club != null) {
                            String name = c[1];
                            String positions = c[2];
                            Date dob = parser.tryParseDate(c[3]);

                            Player player = new Player(
                                    club,
                                    name,
                                    positions,
                                    dob
                            );

                            club.addComponentPlayer(player);
                        }
                    }
                } else {
                    calculateColumnCount();
                }

                firstRow = false;
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
                errors += row[1];
            }
            errors += " -- Igrac neispravan, nedostaju neki podaci";
        } else {
            if (ChampionshipComposite.getInstance().findComponent(row[0]) == null) {
                if (row[1] != null) {
                    errors += row[1];
                }
                errors += " -- klub ne postoji ";
            }
            if (parser.tryParseDate(row[3]) == null) {
                if (row[1] != null) {
                    errors += row[1];
                }
                errors += " -- invalidan datum ";
            }
        }

        if (errors.isEmpty()) {
            return true;
        } else {
            errors += " -- redak u datoteci igrača: " + currentRow;
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
