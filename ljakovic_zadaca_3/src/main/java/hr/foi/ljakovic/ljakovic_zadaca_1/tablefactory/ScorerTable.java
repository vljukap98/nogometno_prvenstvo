package hr.foi.ljakovic.ljakovic_zadaca_1.tablefactory;

import championshipcomposite.ChampionshipComponent;
import championshipcomposite.ChampionshipComposite;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Player;
import java.util.ArrayList;
import java.util.List;
import visitor.ScorerVisitor;

/**
 *
 * @author Luka Jaković
 */
public class ScorerTable extends Table {

    public ScorerTable(Integer round) {
        this.round = round;
        this.visitor = new ScorerVisitor(round);
        this.totals = new Integer[]{0};
        this.columnWidths = new Integer[]{1, 1, 1};
    }

    @Override
    public void printTable() {
        List<FootballClub> clubs = new ArrayList<>();
        List<Player> players = new ArrayList<>();

        prepareData();

        for (ChampionshipComponent component : components) {
            if (component.getClass().equals(FootballClub.class)) {
                clubs.add((FootballClub) component);
            }
        }

        for (FootballClub club : clubs) {
            club.getPlayers().forEach((p) -> {
                if (p.goals != 0) {
                    players.add(p);
                }
            });
        }

        players.sort((p1, p2) -> p2.goals.compareTo(p1.goals));

        determineWidths(players);
        printHeader();

        for (Player player : players) {
            System.out.printf("%-" + columnWidths[0] + "s", player.name);
            System.out.printf("%-" + columnWidths[1] + "s", player.club.name);
            System.out.printf("%" + columnWidths[2] + "s", player.goals);
            System.out.println("");

            totals[0] += player.goals;
        }

        ChampionshipComposite.getInstance().accept(resetVisitor);

        printFooter();
    }

    @Override
    public void prepareData() {
        for (ChampionshipComponent component : components) {
            component.accept(visitor);
        }
    }

    private void determineWidths(List<Player> players) {
        if (players.size() > 0) {
            for (Player player : players) {
                if (player.name.length() > columnWidths[0]) {
                    columnWidths[0] = player.name.length() + 5;
                }
                if (player.club.name.length() > columnWidths[1]) {
                    columnWidths[1] = player.club.name.length() + 5;
                }
                columnWidths[2] = "Golovi".length() + 5;
            }
        }
    }

    @Override
    public void printHeader() {
        System.out.printf("%-" + columnWidths[0] + "s", "Igrač");
        System.out.printf("%-" + columnWidths[1] + "s", "Klub");
        System.out.printf("%" + columnWidths[2] + "s", "Golovi");
        System.out.println("");
        System.out.println("");
    }

    @Override
    public void printFooter() {
        System.out.println("");
        System.out.printf("%-" + columnWidths[0] + "s", "Ukupno");
        System.out.printf("%-" + columnWidths[1] + "s", "");
        System.out.printf("%" + columnWidths[2] + "s", totals[0]);
        System.out.println("");
    }

}
