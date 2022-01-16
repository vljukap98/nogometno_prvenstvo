package hr.foi.ljakovic.ljakovic_zadaca_1.tablefactory;

import championshipcomposite.ChampionshipComponent;
import championshipcomposite.ChampionshipComposite;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import visitor.CardVisitor;

/**
 *
 * @author Luka Jaković
 */
public class CardsTable extends Table {

    public CardsTable(Integer round) {
        this.round = round;
        this.visitor = new CardVisitor(this.round);
        this.totals = new Integer[]{0, 0, 0, 0};
        this.columnWidths = new Integer[]{1, 1, 1, 1, 1};
    }

    @Override
    public void printTable() {
        List<FootballClub> clubs = new ArrayList<>();

        prepareData();

        for (ChampionshipComponent component : components) {
            if (component.getClass().equals(FootballClub.class)) {
                clubs.add((FootballClub) component);
            }
        }

        sortFields(clubs);
        determineWidths(clubs);
        printHeader();
        determineTotals(clubs);

        for (FootballClub club : clubs) {
            if (club.yellowCards != 0 || club.secondYellowCards != 0 || club.redCards != 0) {
                System.out.printf("%-" + columnWidths[0] + "s", club.name);
                System.out.printf("%" + columnWidths[1] + "s", club.yellowCards);
                System.out.printf("%" + columnWidths[2] + "s", club.secondYellowCards);
                System.out.printf("%" + columnWidths[3] + "s", club.redCards);
                System.out.printf("%" + columnWidths[4] + "s", club.cardsTotal);
                System.out.println("");

            }
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

    private void determineWidths(List<FootballClub> clubs) {
        if (clubs.size() > 0) {
            for (FootballClub club : clubs) {
                if (club.name.length() > columnWidths[0]) {
                    columnWidths[0] = club.name.length() + 5;
                }
                columnWidths[1] = "Prvi žuti".length() + 5;
                columnWidths[2] = "Drugi žuti".length() + 5;
                columnWidths[3] = "Crveni".length() + 5;
                columnWidths[4] = "Ukupno".length() + 5;
            }
        }
    }

    @Override
    public void printHeader() {
        System.out.printf("%-" + columnWidths[0] + "s", "Klub");
        System.out.printf("%" + columnWidths[1] + "s", "Prvi žuti");
        System.out.printf("%" + columnWidths[2] + "s", "Drugi žuti");
        System.out.printf("%" + columnWidths[3] + "s", "Crveni");
        System.out.printf("%" + columnWidths[4] + "s", "Ukupno");
        System.out.println("");
        System.out.println("");
    }

    @Override
    public void printFooter() {
        System.out.println("");
        System.out.printf("%-" + columnWidths[0] + "s", "Ukupno");
        System.out.printf("%" + columnWidths[1] + "s", totals[0]);
        System.out.printf("%" + columnWidths[2] + "s", totals[1]);
        System.out.printf("%" + columnWidths[3] + "s", totals[2]);
        System.out.printf("%" + columnWidths[4] + "s", totals[3]);
        System.out.println("");
    }

    private void determineTotals(List<FootballClub> clubs) {
        for (FootballClub club : clubs) {
            totals[0] += club.yellowCards;
            totals[1] += club.secondYellowCards;
            totals[2] += club.redCards;
            totals[3] += club.cardsTotal;
        }
    }

    private void sortFields(List<FootballClub> clubs) {
        Collections.sort(clubs, (Object o1, Object o2) -> {
            Integer x1 = ((FootballClub) o1).cardsTotal;
            Integer x2 = ((FootballClub) o2).cardsTotal;
            Integer c = x2.compareTo(x1);

            if (c != 0) {
                return c;
            }

            x1 = ((FootballClub) o1).redCards;
            x2 = ((FootballClub) o2).redCards;
            c = x2.compareTo(x1);

            if (c != 0) {
                return c;
            }

            x1 = ((FootballClub) o1).secondYellowCards;
            x2 = ((FootballClub) o2).secondYellowCards;
            c = x2.compareTo(x1);

            return c;
        });
    }

}
