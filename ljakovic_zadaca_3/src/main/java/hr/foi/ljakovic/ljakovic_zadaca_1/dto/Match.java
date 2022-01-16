package hr.foi.ljakovic.ljakovic_zadaca_1.dto;

import championshipcomposite.ChampionshipComponent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import visitor.IChampionshipVisitor;

/**
 *
 * @author Luka Jaković
 */
public class Match implements ChampionshipComponent {

    public Integer number;
    public Integer round;
    public FootballClub host;
    public FootballClub guest;
    public Date startsAt;
    public FootballClub winner;
    public FootballClub loser;
    public Integer hostGoals;
    public Integer guestGoals;
    public List<ChampionshipComponent> lineupEventList;

    public Match() {
    }

    public Match(FootballClub host, FootballClub guest) {
        this.host = host;
        this.guest = guest;
    }

    public Match(Integer number, Integer round, FootballClub host, FootballClub guest, Date startsAt) {
        this.number = number;
        this.round = round;
        this.host = host;
        this.guest = guest;
        this.startsAt = startsAt;
        this.winner = new FootballClub();
        this.loser = new FootballClub();
        this.hostGoals = 0;
        this.guestGoals = 0;
        this.lineupEventList = new ArrayList<>();
    }

    public void resetMatch() {
        this.winner = new FootballClub();
        this.hostGoals = 0;
        this.guestGoals = 0;
    }

    public void addMatchComponent(ChampionshipComponent component) {
        this.lineupEventList.add(component);
    }

    public List<ChampionshipComponent> getLineupsEvents() {
        return this.lineupEventList;
    }

    @Override
    public ChampionshipComponent findComponent(String id) {
        ChampionshipComponent c = null;

        if (!id.matches("[0-9]+")) {
            return c;
        }

        if (number.equals(Integer.parseInt(id))) {
            c = this;
        }

        return c;
    }

    @Override
    public void accept(IChampionshipVisitor visitor) {
        for (ChampionshipComponent championshipComponent : lineupEventList) {
            championshipComponent.accept(visitor);
        }

        visitor.visit(this);
    }

    public List<MatchEvent> yellowPlayer(Player player) {
        List<MatchEvent> yellowPlayer = new ArrayList<>();

        for (ChampionshipComponent championshipComponent : lineupEventList) {
            if (championshipComponent.getClass().equals(MatchEvent.class)) {
                var event = (MatchEvent) championshipComponent;
                if (event.player != null) {
                    if (event.eventType == 10 && event.player.name.equals(player.name)) {
                        yellowPlayer.add(event);
                    }
                }
            }
        }

        return yellowPlayer;
    }

    public Boolean hasEvents() {
        Boolean hasEvents = false;

        for (ChampionshipComponent championshipComponent : lineupEventList) {
            if (championshipComponent.getClass().equals(MatchEvent.class)) {
                hasEvents = true;
                break;
            }
        }

        return hasEvents;
    }

}
