package hr.foi.ljakovic.ljakovic_zadaca_1.dto;

import championshipcomposite.ChampionshipComponent;
import java.util.Date;
import playerstate.ReadyState;
import playerstate.State;
import visitor.IChampionshipVisitor;

/**
 *
 * @author Luka Jaković
 */
public class Player extends Person {

    public FootballClub club;
    public String positions;
    public Date dob;
    public Integer goals;
    public Integer yellowCards;
    public Integer redCards;

    private State state;

    public Player() {
    }

    public Player(FootballClub club, String name, String positions, Date dob) {
        this.club = club;
        this.name = name;
        this.positions = positions;
        this.dob = dob;
        this.goals = 0;
        this.state = new ReadyState(this);
    }

    public void resetGoals() {
        this.goals = 0;
    }

    public void resetState() {
        this.state = new ReadyState(this);
    }

    @Override
    public ChampionshipComponent findComponent(String id) {
        ChampionshipComponent club = null;

        if (name.equals(id)) {
            club = this;
        }

        return club;
    }

    @Override
    public void accept(IChampionshipVisitor visitor) {
        visitor.visit(this);
    }

    public State getState() {
        return state;
    }

    public void changeState(State state) {
        this.state = state;
    }

}
