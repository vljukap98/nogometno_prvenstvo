package hr.foi.ljakovic.ljakovic_zadaca_1.dto;

import championshipcomposite.ChampionshipComponent;
import visitor.IChampionshipVisitor;

/**
 *
 * @author Luka Jaković
 */
public class HeadCoach extends Person {

    public HeadCoach(String name) {
        this.name = name;
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
    }

    public Integer vowelNumber() {
        Integer counter = 0;

        for (int i = 0; i < this.name.length(); i++) {
            if (name.charAt(i) == 'a' || name.charAt(i) == 'e'
                    || name.charAt(i) == 'i'
                    || name.charAt(i) == 'o'
                    || name.charAt(i) == 'u') {

                counter++;
            }
        }

        return counter;
    }
}
