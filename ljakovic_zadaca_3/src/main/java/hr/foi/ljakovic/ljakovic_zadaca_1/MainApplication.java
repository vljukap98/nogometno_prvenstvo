package hr.foi.ljakovic.ljakovic_zadaca_1;

import dataloaderfacade.DataLoaderFacade;
import hr.foi.ljakovic.ljakovic_zadaca_1.tablefactory.Table;
import hr.foi.ljakovic.ljakovic_zadaca_1.tablefactory.TableFactory;
import java.util.Scanner;
import matchlineupgenerator.Caretaker;
import matchlineupgenerator.GeneratorRequest;
import playerstate.LineupsTable;
import scoreboardobserver.ObserverRequest;

/**
 *
 * @author Luka Jaković
 */
public class MainApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DataLoaderFacade.getInstance().loadInitialFiles(args);

        startUserInput();
    }

    private static void startUserInput() {
        Integer championshipRound = 0;
        String resultsClub = "";
        String userInput = "";
        Scanner sc = new Scanner(System.in);
        TableFactory factory = new TableFactory();

        do {
            System.out.println("Enter parameters, type 'exit' to exit program ");
            userInput = sc.nextLine();
            String[] userParameters = userInput.split(" ");

            if (userParameters[0].equalsIgnoreCase("T") || userParameters[0].equalsIgnoreCase("S")
                    || userParameters[0].equalsIgnoreCase("K") || userParameters[0].equalsIgnoreCase("R")) {
                switch (userParameters.length) {
                    case 1:
                        championshipRound = Integer.MAX_VALUE;
                        break;
                    case 2:
                        if (userParameters[0].equalsIgnoreCase("R")) {
                            resultsClub = userParameters[1];
                            championshipRound = Integer.MAX_VALUE;
                        } else {
                            championshipRound = Integer.parseInt(userParameters[1]);
                        }
                        break;
                    case 3:
                        resultsClub = userParameters[1];
                        championshipRound = Integer.parseInt(userParameters[2]);
                        break;
                    default:
                }
                Table table = factory.getTable(userParameters[0], championshipRound, resultsClub);
                table.printTable();
            } else if (userParameters[0].equalsIgnoreCase("NU") || userParameters[0].equalsIgnoreCase("NS")
                    || userParameters[0].equalsIgnoreCase("ND")) {
                DataLoaderFacade.getInstance().loadAdditionalFile(userParameters);
            } else if (userParameters[0].equalsIgnoreCase("D")) {
                championshipRound = Integer.parseInt(userParameters[1]);
                String club1 = userParameters[2];
                String club2 = userParameters[3];
                Integer seconds = Integer.parseInt(userParameters[4]);

                ObserverRequest req = new ObserverRequest();
                req.handleRequest(championshipRound, club1, club2, seconds);
            } else if (userParameters[0].equalsIgnoreCase("SU")) {
                championshipRound = Integer.parseInt(userParameters[1]);
                String club1 = userParameters[2];
                String club2 = userParameters[3];

                LineupsTable req = new LineupsTable();
                req.handleRequest(championshipRound, club1, club2);
            } else if (userParameters[0].equalsIgnoreCase("GR")) {
                String algorithmNumber = userParameters[1];

                GeneratorRequest req = new GeneratorRequest();

                req.handleRequest(algorithmNumber);
            } else if (userParameters[0].equalsIgnoreCase("IG")) {
                Caretaker.getInstance().printHistory();
            } else if (userParameters[0].equalsIgnoreCase("VR")) {
                Integer validLineup = Integer.parseInt(userParameters[1]);

                Caretaker.getInstance().setValidLineup(validLineup);
            } else if (userParameters[0].equalsIgnoreCase("IR")) {
                String validLineup = userParameters[1];

                Caretaker.getInstance().printClubMatches(validLineup);
            } else if (userParameters[0].equalsIgnoreCase("IK")) {
                Integer round = Integer.parseInt(userParameters[1]);

                Caretaker.getInstance().printRoundMatches(round);
            } else if (userInput.equals("exit")) {
                return;
            } else {
                System.out.println("Wrong parameters!");
            }

        } while (!userInput.equalsIgnoreCase("exit"));
    }
}
