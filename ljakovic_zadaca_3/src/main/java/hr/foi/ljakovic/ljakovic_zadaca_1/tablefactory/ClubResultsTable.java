package hr.foi.ljakovic.ljakovic_zadaca_1.tablefactory;

import championshipcomposite.ChampionshipComponent;
import championshipcomposite.ChampionshipComposite;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Match;
import java.util.ArrayList;
import java.util.List;
import visitor.ClubResultsVisitor;

/**
 *
 * @author Luka Jaković
 */
public class ClubResultsTable extends Table {

    private final String club;

    public ClubResultsTable(Integer round, String club) {
        this.round = round;
        this.club = club;
        this.visitor = new ClubResultsVisitor(round);
        this.columnWidths = new Integer[]{1, 1, 1, 1, 1};
    }

    @Override
    public void printTable() {
        if (club.equals("")) {
            return;
        }
        if (ChampionshipComposite.getInstance().findComponent(club) == null) {
            System.out.println("Ne postoji taj klub");
            return;
        }

        List<Match> matches = new ArrayList<>();

        prepareData();

        for (ChampionshipComponent component : components) {
            if (component.getClass().equals(Match.class)) {
                matches.add((Match) component);
            }
        }

        determineWidths(matches);
        printHeader();

        for (Match match : matches) {
            if ((match.guest.club.equalsIgnoreCase(club) || match.host.club.equalsIgnoreCase(club)) && match.round <= round) {
                System.out.printf("%-" + columnWidths[0] + "s", match.round);
                System.out.printf("%-" + columnWidths[1] + "s", match.startsAt);
                System.out.printf("%-" + columnWidths[2] + "s", match.host.name);
                System.out.printf("%-" + columnWidths[3] + "s", match.guest.name);
                System.out.printf("%-" + columnWidths[4] + "s", match.hostGoals + ":" + match.guestGoals);
                System.out.println("");
            }

            match.accept(resetVisitor);
        }
    }

    @Override
    public void prepareData() {
        for (ChampionshipComponent component : components) {
            component.accept(visitor);
        }
    }

    private void determineWidths(List<Match> matches) {
        if (matches.size() > 0) {
            for (Match match : matches) {
                columnWidths[0] = "Kolo".length() + 5;
                if (match.startsAt.toString().length() > columnWidths[1]) {
                    columnWidths[1] = match.startsAt.toString().length() + 5;
                }
                if (match.host.name.length() > columnWidths[2]) {
                    columnWidths[2] = match.host.name.length() + 5;
                }
                if (match.guest.name.length() > columnWidths[3]) {
                    columnWidths[3] = match.guest.name.length() + 5;
                }
                columnWidths[4] = "Rezultat".length() + 5;
            }
        }
    }

    @Override
    public void printHeader() {
        System.out.printf("%-" + columnWidths[0] + "s", "Kolo");
        System.out.printf("%-" + columnWidths[1] + "s", "Vrijeme početka");
        System.out.printf("%-" + columnWidths[2] + "s", "Domaćin");
        System.out.printf("%-" + columnWidths[3] + "s", "Gost");
        System.out.printf("%-" + columnWidths[4] + "s", "Rezultat");
        System.out.println("");
        System.out.println("");
    }

    @Override
    public void printFooter() {
    }

}
