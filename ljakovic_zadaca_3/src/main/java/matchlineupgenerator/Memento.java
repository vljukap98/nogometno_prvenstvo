package matchlineupgenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Luka Jaković
 */
public class Memento {

    private MatchesLineup matchesLineup;

    private Integer mementoNumber;
    private Date createdAt;
    private Boolean isValid;
    private List<MatchRound> rounds;

    public Memento(MatchesLineup matchesLineup, Integer mementoNumber, Date createdAt, Boolean isValid, List<MatchRound> rounds) {
        this.matchesLineup = matchesLineup;
        this.mementoNumber = mementoNumber;
        this.createdAt = createdAt;
        this.isValid = isValid;
        this.rounds = rounds;
    }

    public Integer getMementoNumber() {
        return mementoNumber;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public String getCreatedAtFormatted() {
        SimpleDateFormat format = new SimpleDateFormat("dd. MM. yyyy. HH:mm:ss");
        return format.format(createdAt);
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public List<MatchRound> getRounds() {
        return rounds;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public void restore() {
        this.matchesLineup.setIsValid(this.isValid);
    }

    public MatchesLineup getMatchesLineup() {
        return matchesLineup;
    }

}
