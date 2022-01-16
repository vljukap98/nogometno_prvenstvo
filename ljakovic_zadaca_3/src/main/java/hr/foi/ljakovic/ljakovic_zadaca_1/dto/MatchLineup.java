package hr.foi.ljakovic.ljakovic_zadaca_1.dto;

import championshipcomposite.ChampionshipComponent;
import visitor.IChampionshipVisitor;

/**
 *
 * @author Luka Jaković
 */
public class MatchLineup implements ChampionshipComponent {

    public Integer matchNumber;
    public FootballClub club;
    public String type;
    public Player player;
    public String position;

    public MatchLineup() {
    }

    public MatchLineup(Integer matchNumber, FootballClub club, String type, Player player, String position) {
        this.matchNumber = matchNumber;
        this.club = club;
        this.type = type;
        this.player = player;
        this.position = position;
    }

    @Override
    public ChampionshipComponent findComponent(String id) {
        ChampionshipComponent c = null;

        if (!id.matches("[0-9]+")) {
            return c;
        }

        if (matchNumber.equals(Integer.parseInt(id))) {
            c = this;
        }

        return c;
    }

    @Override
    public void accept(IChampionshipVisitor visitor) {
        visitor.visit(this);
    }

}
