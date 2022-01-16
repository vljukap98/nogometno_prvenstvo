package scoreboardobserver;

/**
 *
 * @author Luka Jaković
 */
public interface ISubject {

    void attach(IObserver observer);

    void detach(IObserver observer);

    void notifyObservers();

}
