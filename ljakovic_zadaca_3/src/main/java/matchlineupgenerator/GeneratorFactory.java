package matchlineupgenerator;

/**
 *
 * @author Luka
 */
public class GeneratorFactory {

    public GeneratingAlgorithm getAlgorithmType(String type) {
        if (type.equals("1")) {
            return new RandomGeneratingAlgorithm();
        } else if (type.equals("2")) {
            return new ClubNameGeneratingAlgorithm();
        } else if (type.equals("3")) {
            return new CoachNameGeneratingAlgorithm();
        }

        return null;
    }
}
