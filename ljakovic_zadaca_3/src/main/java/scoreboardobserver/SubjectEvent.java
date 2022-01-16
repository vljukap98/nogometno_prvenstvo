package scoreboardobserver;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Match;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Luka Jaković
 */
public class SubjectEvent implements ISubject {

    private Integer hostGoals = 0;
    private Integer guestGoals = 0;

    private final Match match;

    private final Integer timeGapInSeconds;
    private List<MatchEvent> events;
    private List<MatchEvent> eventsHost = new ArrayList<>();
    private List<MatchEvent> eventsGuest = new ArrayList<>();

    private List<IObserver> observers = new ArrayList<>();

    private List<ScoreboardRow> rows = new ArrayList<>();

    public MatchEvent currentEvent;

    public FootballClub host;
    public FootballClub guest;

    public SubjectEvent(Match match, Integer seconds) {
        this.timeGapInSeconds = seconds;
        this.match = match;
        this.host = match.host;
        this.guest = match.guest;
    }

    @Override
    public void attach(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(this);
        }
    }

    public void setEvents(List<MatchEvent> events) {
        this.events = events;
    }

    public void setEventHost(MatchEvent event) {
        this.eventsHost.add(event);
    }

    public void setEventGuest(MatchEvent event) {
        this.eventsGuest.add(event);
    }

    public void processAllEvents() {
        for (MatchEvent event : events) {
            try {
                currentEvent = event;
                notifyObservers();
                processCurrentEvent();
                printScoreboard();
                clearUneccessaryRows();
                Thread.sleep(timeGapInSeconds * 1000);
            } catch (Exception ex) {
                System.out.println("Something went wrong: " + ex.getMessage());
            }
        }
    }

    private void processCurrentEvent() {
        if (!eventsHost.isEmpty()) {
            if (eventsHost.get(eventsHost.size() - 1).equals(currentEvent)) {
                processHostEvent();
            }
        }
        if (!eventsGuest.isEmpty()) {
            if (eventsGuest.get(eventsGuest.size() - 1).equals(currentEvent)) {
                processGuestEvent();
            }
        }
    }

    private void printScoreboard() {
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.printf("%-40s", host.name + ":" + hostGoals);
        System.out.printf("%-1s", "|");
        System.out.printf("%-5s", currentEvent.minute + "'");
        System.out.printf("%-1s", "|");
        System.out.printf("%40s", guest.name + ":" + guestGoals);
        System.out.println("");
        System.out.println("---------------------------------------------------------------------------------------");

        for (ScoreboardRow row : rows) {
            System.out.printf("%-40s", row.hostData);
            System.out.printf("%-1s", "|");
            System.out.printf("%-5s", "");
            System.out.printf("%-1s", "|");
            System.out.printf("%40s", row.guestData);
            System.out.println("");
        }
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("");

    }

    private void processHostEvent() {
        ScoreboardRow row = new ScoreboardRow();
        if (currentEvent.eventType == 1 || currentEvent.eventType == 2) {
            hostGoals += 1;
            row.hostData = currentEvent.player.name + " " + currentEvent.minute + "'";
            row.eventType = currentEvent.eventType;
        } else if (currentEvent.eventType == 3) {
            guestGoals += 1;
            row.guestData = currentEvent.player.name + " " + currentEvent.minute + "'";
            row.eventType = currentEvent.eventType;
        } else if (currentEvent.eventType == 10) {
            row.hostData = "ŽUTI KARTON: " + currentEvent.player.name;
            row.eventType = currentEvent.eventType;
        } else if (currentEvent.eventType == 11) {
            row.hostData = "CRVENI KARTON: " + currentEvent.player.name;
            row.eventType = currentEvent.eventType;
        } else if (currentEvent.eventType == 20) {
            row.eventType = currentEvent.eventType;
            row.hostData = "ZAMJENA: " + currentEvent.player.name + "<>" + currentEvent.substitute.name;
        }
        this.rows.add(row);
    }

    private void processGuestEvent() {
        ScoreboardRow row = new ScoreboardRow();
        if (currentEvent.eventType == 1 || currentEvent.eventType == 2) {
            guestGoals += 1;
            row.guestData = currentEvent.player.name + " " + currentEvent.minute + "'";
            row.eventType = currentEvent.eventType;
        } else if (currentEvent.eventType == 3) {
            guestGoals += 1;
            row.hostData = currentEvent.player.name + " " + currentEvent.minute + "'";
            row.eventType = currentEvent.eventType;
        } else if (currentEvent.eventType == 10) {
            row.guestData = "ŽUTI KARTON: " + currentEvent.player.name;
            row.eventType = currentEvent.eventType;
        } else if (currentEvent.eventType == 11) {
            row.guestData = "CRVENI KARTON: " + currentEvent.player.name;
            row.eventType = currentEvent.eventType;
        } else if (currentEvent.eventType == 20) {
            row.eventType = currentEvent.eventType;
            row.guestData = "ZAMJENA: " + currentEvent.player.name + "<>" + currentEvent.substitute.name;
        }
        this.rows.add(row);
    }

    private void clearUneccessaryRows() {
        Iterator<ScoreboardRow> iterator = rows.iterator();

        while (iterator.hasNext()) {
            ScoreboardRow row = iterator.next();

            if (row.eventType == 10 || row.eventType == 11 || row.eventType == 20) {
                iterator.remove();
            }
        }
    }
}
