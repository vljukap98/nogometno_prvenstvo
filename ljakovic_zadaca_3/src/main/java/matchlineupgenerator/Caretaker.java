package matchlineupgenerator;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Match;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luka Jaković
 */
public class Caretaker {

    private MatchesLineup matchesLineup = new MatchesLineup();

    private final List<Memento> history = new ArrayList<>();

    private static Caretaker instance;

    private Caretaker() {
    }

    public static Caretaker getInstance() {
        if (instance == null) {
            instance = new Caretaker();
        }

        return instance;
    }

    public void undo(Integer number) {
        for (Memento memento : history) {
            if (memento.getMementoNumber().equals(number)) {
                memento.restore();
            }
        }
    }

    public Integer lastMementoIndex() {
        return this.history.size();
    }

    public void printHistory() {
        System.out.printf("%-15s", "Br. sastava");
        System.out.printf("%-30s", "Sastav kreiran");
        System.out.printf("%-10s", "Kolo");
        System.out.printf("%-30s", "Domacin");
        System.out.printf("%-30s", "Gost");
        System.out.println("");
        System.out.println("");

        for (Memento memento : history) {
            for (MatchRound round : memento.getRounds()) {
                for (Match match : round.getMatches()) {
                    System.out.printf("%-15s", memento.getMementoNumber());
                    System.out.printf("%-30s", memento.getCreatedAtFormatted());
                    System.out.printf("%-10s", round.getRound());
                    System.out.printf("%-30s", match.host.name);
                    System.out.printf("%-30s", match.guest.name);

                    System.out.println("");
                }
            }
        }
    }

    public void saveMemento() {
        this.history.add(this.matchesLineup.createMemento());
    }

    public void setMatchesLineup(MatchesLineup matchesLineup) {
        this.matchesLineup = matchesLineup;
    }

    public void setValidLineup(Integer lineupNumber) {
        if (lineupNumber >= history.size()) {
            return;
        }

        if (hasValidMatchLineup()) {
            for (Memento memento : history) {
                undo(memento.getMementoNumber());
            }
        }

        for (Memento memento : history) {
            if (memento.getMementoNumber().equals(lineupNumber)) {
                memento.getMatchesLineup().setIsValid(true);
            }
        }
    }

    private Boolean hasValidMatchLineup() {
        Boolean hasValidLineup = false;

        for (Memento memento : history) {
            if (memento.getMatchesLineup().getIsValid() == true) {
                hasValidLineup = true;
            }
        }

        return hasValidLineup;
    }

    public void printClubMatches(String club) {
        System.out.printf("%-15s", "Br. sastava");
        System.out.printf("%-30s", "Sastav kreiran");
        System.out.printf("%-10s", "Kolo");
        System.out.printf("%-30s", "Domacin");
        System.out.printf("%-30s", "Gost");
        System.out.println("");
        System.out.println("");

        for (Memento memento : history) {
            if (memento.getMatchesLineup().getIsValid() == true) {
                for (MatchRound round : memento.getMatchesLineup().getRounds()) {
                    for (Match match : round.getMatches()) {
                        if (match.guest.club.equalsIgnoreCase(club)
                                || match.host.club.equalsIgnoreCase(club)) {
                            System.out.printf("%-15s", memento.getMementoNumber());
                            System.out.printf("%-30s", memento.getCreatedAtFormatted());
                            System.out.printf("%-10s", round.getRound());
                            System.out.printf("%-30s", match.host.name);
                            System.out.printf("%-30s", match.guest.name);

                            System.out.println("");
                        }
                    }
                }
            }
        }
    }

    public void printRoundMatches(Integer r) {
        System.out.printf("%-15s", "Br. sastava");
        System.out.printf("%-30s", "Sastav kreiran");
        System.out.printf("%-10s", "Kolo");
        System.out.printf("%-30s", "Domacin");
        System.out.printf("%-30s", "Gost");
        System.out.println("");
        System.out.println("");

        for (Memento memento : history) {
            if (memento.getMatchesLineup().getIsValid() == true) {
                for (MatchRound round : memento.getMatchesLineup().getRounds()) {
                    for (Match match : round.getMatches()) {
                        if (match.round.equals(r)) {
                            System.out.printf("%-15s", memento.getMementoNumber());
                            System.out.printf("%-30s", memento.getCreatedAtFormatted());
                            System.out.printf("%-10s", round.getRound());
                            System.out.printf("%-30s", match.host.name);
                            System.out.printf("%-30s", match.guest.name);

                            System.out.println("");
                        }
                    }
                }
            }
        }
    }
}
