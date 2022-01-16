package hr.foi.ljakovic.ljakovic_zadaca_1.tablefactory;

import championshipcomposite.ChampionshipComponent;
import championshipcomposite.ChampionshipComposite;
import java.util.List;
import visitor.IChampionshipVisitor;
import visitor.ResetVisitor;

/**
 *
 * @author Luka Jaković
 */
public abstract class Table {

    protected Integer round;
    protected Integer[] columnWidths;
    protected Integer[] totals;
    protected IChampionshipVisitor visitor;
    protected IChampionshipVisitor resetVisitor = new ResetVisitor();

    protected final List<ChampionshipComponent> components
            = ChampionshipComposite
                    .getInstance()
                    .getComponents();

    public abstract void printTable();

    public abstract void prepareData();

    public abstract void printHeader();

    public abstract void printFooter();
}
