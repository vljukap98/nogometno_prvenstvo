package dataloaderfacade;

import championshipcomposite.ChampionshipComposite;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Match;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchLineup;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Player;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Luka Jaković
 */
class MatchLineupCsvReader extends CsvReader {

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
                        String type = c[2];
                        String position = c[4];

                        FootballClub club = (FootballClub) ChampionshipComposite
                                .getInstance()
                                .findComponent(c[1]);
                        Player player = (Player) club.findPlayer(c[3]);

                        MatchLineup matchLineup = new MatchLineup(
                                matchNumber,
                                club,
                                type,
                                player,
                                position
                        );

                        Match match = (Match) ChampionshipComposite
                                .getInstance()
                                .findComponent(c[0]);

                        match.addMatchComponent(matchLineup);

                    }
                } else {
                    calculateColumnCount();
                    firstRow = false;
                }
            }

        } catch (IOException e) {
            System.out.println("Pogreska...");
        }
    }

    @Override
    Boolean isRowValid(String[] row) {
        String errors = "";

        try {

            if (row.length != columnCount) {
                if (row[3] != null) {
                    errors += "Za igraca: " + row[3];
                }
                errors += " -- Sastav neispravan, nedostaju neki podaci";
            } else {
                FootballClub club = (FootballClub) ChampionshipComposite.getInstance().findComponent(row[1]);
                if (club == null) {
                    errors += " -- klub ne postoji";
                } else {
                    if (club.findPlayer(row[3]) == null) {
                        if (row[3] != null) {
                            errors += "Za igraca: " + row[3];
                        }
                        errors += " -- igrac ne postoji za taj klub";
                    }
                }
                if (ChampionshipComposite.getInstance().findComponent(row[0]) == null) {
                    errors += " -- utakmica za sastav ne postoji";
                }
            }

        } catch (Exception e) {
            System.out.println("Pogreska...");
        }

        if (errors.isEmpty()) {
            return true;
        } else {
            errors += " -- redak u datoteci sastava: " + currentRow;
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
