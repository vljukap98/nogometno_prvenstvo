package scoreboardobserver;

import championshipcomposite.ChampionshipComponent;
import championshipcomposite.ChampionshipComposite;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Match;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luka Jaković
 */
public class ObserverRequest {

    public void handleRequest(Integer round, String hostClub, String guestClub, Integer seconds) {
        FootballClub host = (FootballClub) ChampionshipComposite.getInstance().findComponent(hostClub);
        FootballClub guest = (FootballClub) ChampionshipComposite.getInstance().findComponent(guestClub);

        if (host == null || guest == null) {
            System.out.println("Could not find clubs!");
            return;
        }

        Match match = ChampionshipComposite.getInstance().findMatchByRoundAndClubs(round, hostClub, guestClub);

        if (match == null) {
            System.out.println("Could not find requested match!");
            return;
        }

        List<MatchEvent> matchEvents = new ArrayList<>();
        fillEventList(matchEvents, match);

        SubjectEvent subject = new SubjectEvent(match, seconds);
        ObserverHost observerHost = new ObserverHost(match.host);
        ObserverGuest observerGuest = new ObserverGuest(match.guest);

        subject.attach(observerHost);
        subject.attach(observerGuest);
        subject.setEvents(matchEvents);

        subject.processAllEvents();
    }

    private void fillEventList(List<MatchEvent> matchEvents, Match match) {
        List<ChampionshipComponent> components = match.getLineupsEvents();

        for (ChampionshipComponent championshipComponent : components) {
            if (championshipComponent.getClass().equals(MatchEvent.class)) {
                matchEvents.add((MatchEvent) championshipComponent);
            }
        }
    }
}
