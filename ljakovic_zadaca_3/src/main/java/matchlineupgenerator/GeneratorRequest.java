package matchlineupgenerator;

/**
 *
 * @author Luka
 */
public class GeneratorRequest {

    public void handleRequest(String type) {
        GeneratorFactory factory = new GeneratorFactory();
        MatchesLineup lineup = new MatchesLineup();

        if (factory.getAlgorithmType(type) != null) {
            lineup.setGeneratingAlgorithm(factory.getAlgorithmType(type));
            lineup.executeGeneratingAlgorithm();

            lineup.setMatchNumber(Caretaker.getInstance().lastMementoIndex());
            Caretaker.getInstance().setMatchesLineup(lineup);
            Caretaker.getInstance().saveMemento();
        } else {
            System.out.println("Wrong input parameters!");
        }
    }
}
