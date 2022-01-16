package matchlineupgenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Luka Jaković
 */
public class MatchesLineup {

    private GeneratingAlgorithm generatingStrategy;

    private Integer matchLineupNumber;
    private Date createdAt;
    private Boolean isValid;

    private List<MatchRound> rounds;

    public MatchesLineup() {
        this.rounds = new ArrayList<>();
        this.isValid = false;
        this.createdAt = new Date();
    }

    public void setGeneratingAlgorithm(GeneratingAlgorithm generatingStrat) {
        this.generatingStrategy = generatingStrat;
    }

    public void executeGeneratingAlgorithm() {
        this.rounds = generatingStrategy.generateMatchLineup();
    }

    public Memento createMemento() {
        return new Memento(this, this.matchLineupNumber, this.createdAt, this.isValid, this.rounds);
    }

    public void setMatchNumber(Integer matchNumber) {
        this.matchLineupNumber = matchNumber;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public void setMatches(List<MatchRound> matches) {
        this.rounds = matches;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public List<MatchRound> getRounds() {
        return rounds;
    }

}
