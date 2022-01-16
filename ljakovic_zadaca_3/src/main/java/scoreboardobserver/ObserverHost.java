package scoreboardobserver;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;

/**
 *
 * @author Luka Jaković
 */
public class ObserverHost implements IObserver {

    private FootballClub host;

    public ObserverHost(FootballClub host) {
        this.host = host;
    }

    @Override
    public void update(ISubject subject) {
        if (((SubjectEvent) subject).currentEvent.footballClub != null) {
            if (((SubjectEvent) subject).currentEvent.footballClub.equals(host)) {
                ((SubjectEvent) subject).setEventHost(((SubjectEvent) subject).currentEvent);
            }
        }
    }

}
