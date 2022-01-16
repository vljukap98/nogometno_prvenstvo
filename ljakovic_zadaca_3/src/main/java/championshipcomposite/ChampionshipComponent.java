package championshipcomposite;

import visitor.IChampionshipVisitor;

/**
 *
 * @author Luka Jaković
 */
public interface ChampionshipComponent {

    public ChampionshipComponent findComponent(String id);

    public void accept(IChampionshipVisitor visitor);

}
