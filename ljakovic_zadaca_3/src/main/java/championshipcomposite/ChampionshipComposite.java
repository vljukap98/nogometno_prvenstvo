package championshipcomposite;

import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Match;
import java.util.ArrayList;
import java.util.List;
import visitor.IChampionshipVisitor;

/**
 *
 * @author Luka Jaković
 */
public class ChampionshipComposite implements ChampionshipComponent {

    private static ChampionshipComposite instance;

    private final List<ChampionshipComponent> championshipComponents = new ArrayList<>();

    private ChampionshipComposite() {
    }

    public static ChampionshipComposite getInstance() {
        if (instance == null) {
            instance = new ChampionshipComposite();
        }

        return instance;
    }

    public List<ChampionshipComponent> getComponents() {
        return this.championshipComponents;
    }

    public void addComponent(ChampionshipComponent component) {
        this.championshipComponents.add(component);
    }

    @Override
    public ChampionshipComponent findComponent(String id) {
        ChampionshipComponent component = null;

        for (ChampionshipComponent championshipComponent : championshipComponents) {
            if ((component = championshipComponent.findComponent(id)) != null) {
                break;
            }
        }

        return component;
    }

    public Match findMatchByRoundAndClubs(Integer round, String club1, String club2) {
        Match match = null;

        for (ChampionshipComponent championshipComponent : championshipComponents) {
            Match m = null;
            if (championshipComponent.getClass().equals(Match.class)) {
                m = (Match) championshipComponent;
                if (m.round.equals(round)
                        && (m.host.club.equalsIgnoreCase(club1)
                        && m.guest.club.equalsIgnoreCase(club2)
                        || m.host.club.equalsIgnoreCase(club2)
                        && m.guest.club.equalsIgnoreCase(club1))) {
                    match = m;
                }
            }

        }

        return match;
    }

    @Override
    public void accept(IChampionshipVisitor visitor) {
        for (ChampionshipComponent championshipComponent : championshipComponents) {
            championshipComponent.accept(visitor);
        }

        visitor.visit(this);
    }

    public Integer clubNumber() {
        Integer clubNumber = 0;

        for (ChampionshipComponent championshipComponent : championshipComponents) {
            if (championshipComponent.getClass().equals(FootballClub.class)) {
                clubNumber += 1;
            }
        }

        return clubNumber;
    }
}
