package hr.foi.ljakovic.ljakovic_zadaca_1.tablefactory;

import championshipcomposite.ChampionshipComponent;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Match;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import visitor.IChampionshipVisitor;
import visitor.LeagueStandingsVisitor;
import visitor.MatchesPlayedVisitor;

/**
 *
 * @author Luka Jaković
 */
public class LeagueStandingsTable extends Table {

    public LeagueStandingsTable(Integer round) {
        this.round = round;
        this.visitor = new LeagueStandingsVisitor(this.round);
        this.totals = new Integer[]{0, 0, 0, 0, 0, 0, 0};
        this.columnWidths = new Integer[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
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
            System.out.printf("%-" + columnWidths[0] + "s", club.name);
            System.out.printf("%-" + columnWidths[1] + "s", club.headCoach.name);
            System.out.printf("%" + columnWidths[2] + "s", club.matchesPlayed);
            System.out.printf("%" + columnWidths[3] + "s", club.wins);
            System.out.printf("%" + columnWidths[4] + "s", club.defeats);
            System.out.printf("%" + columnWidths[5] + "s", club.draws);
            System.out.printf("%" + columnWidths[6] + "s", club.goals);
            System.out.printf("%" + columnWidths[7] + "s", club.goalsRecieved);
            System.out.printf("%" + columnWidths[8] + "s", club.goals - club.goalsRecieved);
            System.out.printf("%" + columnWidths[9] + "s", club.championshipPoints);
            System.out.println("");
            club.accept(resetVisitor);
        }

        resetMatches();

        printFooter();
    }

    @Override
    public void prepareData() {
        IChampionshipVisitor matchesPlayedVisitor = new MatchesPlayedVisitor(round);

        for (ChampionshipComponent component : components) {
            component.accept(visitor);
            component.accept(matchesPlayedVisitor);
        }
    }

    @Override
    public void printHeader() {
        System.out.printf("%-" + columnWidths[0] + "s", "Klub");
        System.out.printf("%-" + columnWidths[1] + "s", "Trener");
        System.out.printf("%" + columnWidths[2] + "s", "Broj kola");
        System.out.printf("%" + columnWidths[3] + "s", "Pobjeda");
        System.out.printf("%" + columnWidths[4] + "s", "Izgubljenih");
        System.out.printf("%" + columnWidths[5] + "s", "Nerješenih");
        System.out.printf("%" + columnWidths[6] + "s", "Golovi");
        System.out.printf("%" + columnWidths[7] + "s", "Primljeni golovi");
        System.out.printf("%" + columnWidths[8] + "s", "Gol razlika");
        System.out.printf("%" + columnWidths[9] + "s", "Bodovi");
        System.out.println("");
        System.out.println("");
    }

    @Override
    public void printFooter() {
        System.out.println("");
        System.out.printf("%-" + columnWidths[0] + "s", "Ukupno");
        System.out.printf("%-" + columnWidths[1] + "s", "");
        System.out.printf("%" + columnWidths[2] + "s", totals[0]);
        System.out.printf("%" + columnWidths[3] + "s", totals[1]);
        System.out.printf("%" + columnWidths[4] + "s", totals[2]);
        System.out.printf("%" + columnWidths[5] + "s", totals[3]);
        System.out.printf("%" + columnWidths[6] + "s", totals[4]);
        System.out.printf("%" + columnWidths[7] + "s", totals[5]);
        System.out.printf("%" + columnWidths[8] + "s", "");
        System.out.printf("%" + columnWidths[9] + "s", totals[6]);
        System.out.println("");
    }

    private void determineWidths(List<FootballClub> clubs) {
        if (clubs.size() > 0) {
            for (FootballClub club : clubs) {
                if (club.name.length() > columnWidths[0]) {
                    columnWidths[0] = club.name.length() + 5;
                }
                if (club.headCoach.name.length() > columnWidths[1]) {
                    columnWidths[1] = club.headCoach.name.length() + 5;
                }
                columnWidths[2] = "Broj kola".length() + 5;
                columnWidths[3] = "Pobjeda".length() + 5;
                columnWidths[4] = "Izgubljenih".length() + 5;
                columnWidths[5] = "Nerješenih".length() + 5;
                columnWidths[6] = "Golovi".length() + 5;
                columnWidths[7] = "Primljeni golovi".length() + 5;
                columnWidths[8] = "Gol razlika".length() + 5;
                columnWidths[9] = "Bodovi".length() + 5;
            }
        }
    }

    private void determineTotals(List<FootballClub> clubs) {
        for (FootballClub club : clubs) {
            totals[0] += club.matchesPlayed;
            totals[1] += club.wins;
            totals[2] += club.defeats;
            totals[3] += club.draws;
            totals[4] += club.goals;
            totals[5] += club.goalsRecieved;
            totals[6] += club.championshipPoints;
        }
    }

    private void sortFields(List<FootballClub> clubs) {
        Collections.sort(clubs, (Object o1, Object o2) -> {
            Integer x1 = ((FootballClub) o1).championshipPoints;
            Integer x2 = ((FootballClub) o2).championshipPoints;
            Integer c = x2.compareTo(x1);

            if (c != 0) {
                return c;
            }

            x1 = ((FootballClub) o1).goals - ((FootballClub) o1).goalsRecieved;
            x2 = ((FootballClub) o2).goals - ((FootballClub) o2).goalsRecieved;
            c = x2.compareTo(x1);

            if (c != 0) {
                return c;
            }

            x1 = ((FootballClub) o1).goals;
            x2 = ((FootballClub) o2).goals;
            c = x2.compareTo(x1);

            if (c != 0) {
                return c;
            }

            x1 = ((FootballClub) o1).wins;
            x2 = ((FootballClub) o2).wins;
            c = x2.compareTo(x1);

            if (c != 0) {
                return c;
            }

            return c;
        });
    }

    private void resetMatches() {
        for (ChampionshipComponent component : components) {
            if (component.getClass().equals(Match.class)) {
                ((Match) component).resetMatch();
            }
        }
    }

}
