package hr.foi.ljakovic.ljakovic_zadaca_1.dto;

import championshipcomposite.ChampionshipComponent;
import visitor.IChampionshipVisitor;

/**
 *
 * @author Luka Jaković
 */
public class MatchEvent implements ChampionshipComponent {

    public Integer matchNumber;
    public String minute;
    public Integer eventType;
    public FootballClub footballClub;
    public Player player;
    public Player substitute;

    public MatchEvent() {
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
