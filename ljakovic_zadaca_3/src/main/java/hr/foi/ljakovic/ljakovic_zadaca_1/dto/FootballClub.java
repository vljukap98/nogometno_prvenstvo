package hr.foi.ljakovic.ljakovic_zadaca_1.dto;

import championshipcomposite.ChampionshipComponent;
import java.util.ArrayList;
import java.util.List;
import visitor.IChampionshipVisitor;

/**
 *
 * @author Luka Jaković
 */
public class FootballClub implements ChampionshipComponent {

    public String club;
    public String name;
    public HeadCoach headCoach;
    public List<Player> players;

    public Integer yellowCards;
    public Integer secondYellowCards;
    public Integer redCards;
    public Integer cardsTotal;

    public Integer matchesPlayed;
    public Integer wins;
    public Integer defeats;
    public Integer draws;
    public Integer goals;
    public Integer goalsRecieved;
    public Integer championshipPoints;

    public FootballClub() {
    }

    public FootballClub(String club, String name, HeadCoach headCoach) {
        this.club = club;
        this.name = name;
        this.headCoach = headCoach;
        this.players = new ArrayList<>();

        this.yellowCards = 0;
        this.secondYellowCards = 0;
        this.redCards = 0;
        this.cardsTotal = 0;

        this.matchesPlayed = 0;
        this.wins = 0;
        this.defeats = 0;
        this.draws = 0;
        this.goals = 0;
        this.goalsRecieved = 0;
        this.championshipPoints = 0;
    }

    public void resetClubData() {
        this.yellowCards = 0;
        this.secondYellowCards = 0;
        this.redCards = 0;
        this.cardsTotal = 0;
        this.matchesPlayed = 0;
        this.wins = 0;
        this.defeats = 0;
        this.draws = 0;
        this.goals = 0;
        this.goalsRecieved = 0;
        this.championshipPoints = 0;
    }

    public void addComponentPlayer(Player player) {
        this.players.add(player);
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public ChampionshipComponent findPlayer(String id) {
        ChampionshipComponent c = null;

        for (ChampionshipComponent player : players) {
            if ((c = player.findComponent(id)) != null) {
                break;
            }
        }

        return c;
    }

    @Override
    public ChampionshipComponent findComponent(String id) {
        ChampionshipComponent c = null;

        if (club.equals(id)) {
            c = this;
        }

        return c;
    }

    @Override
    public void accept(IChampionshipVisitor visitor) {
        for (Player player : players) {
            player.accept(visitor);
        }

        visitor.visit(this);
    }

    public Integer charNumber() {
        return this.name.length();
    }

}
