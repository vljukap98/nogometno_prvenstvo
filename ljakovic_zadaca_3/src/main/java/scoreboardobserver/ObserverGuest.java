package scoreboardobserver;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;

/**
 *
 * @author Luka Jaković
 */
public class ObserverGuest implements IObserver {

    private FootballClub guest;

    public ObserverGuest(FootballClub guest) {
        this.guest = guest;
    }

    @Override
    public void update(ISubject subject) {
        if (((SubjectEvent) subject).currentEvent.footballClub != null) {
            if (((SubjectEvent) subject).currentEvent.footballClub.equals(guest)) {
                ((SubjectEvent) subject).setEventGuest(((SubjectEvent) subject).currentEvent);
            }
        }
    }

}
