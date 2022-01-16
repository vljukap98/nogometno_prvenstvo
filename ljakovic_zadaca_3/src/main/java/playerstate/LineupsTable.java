package playerstate;

import championshipcomposite.ChampionshipComponent;
import championshipcomposite.ChampionshipComposite;
import eventhandlercor.EventHandler;
import eventhandlercor.GoalEventHandler;
import eventhandlercor.RedCardEventHandler;
import eventhandlercor.SubstitutionEventHandler;
import eventhandlercor.YellowCardEventHandler;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.FootballClub;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.Match;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchEvent;
import hr.foi.ljakovic.ljakovic_zadaca_1.dto.MatchLineup;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import visitor.ResetVisitor;

/**
 *
 * @author Luka Jaković
 */
public class LineupsTable {

    private List<MatchLineup> hostLineup = new ArrayList<>();
    private List<MatchLineup> guestLineup = new ArrayList<>();

    private final EventHandler goalHandler = new GoalEventHandler();
    private final EventHandler yellowCardHandler = new YellowCardEventHandler();
    private final EventHandler redCardHandler = new RedCardEventHandler();
    private final EventHandler subHandler = new SubstitutionEventHandler();

    private final EnumMap<PositionEnum, ArrayList<String>> hostLineupMap = new EnumMap<>(PositionEnum.class);
    private final EnumMap<PositionEnum, ArrayList<String>> guestLineupMap = new EnumMap<>(PositionEnum.class);

    public LineupsTable() {
        goalHandler.setNextHandler(yellowCardHandler);
        yellowCardHandler.setNextHandler(redCardHandler);
        redCardHandler.setNextHandler(subHandler);
    }

    public void handleRequest(Integer round, String club1, String club2) {
        List<MatchLineup> lineups = new ArrayList<>();
        List<MatchEvent> events = new ArrayList<>();

        FootballClub host = (FootballClub) ChampionshipComposite.getInstance().findComponent(club1);
        FootballClub guest = (FootballClub) ChampionshipComposite.getInstance().findComponent(club2);

        if (host == null || guest == null) {
            System.out.println("Could not find clubs!");
            return;
        }

        Match match = ChampionshipComposite.getInstance().findMatchByRoundAndClubs(round, club1, club2);

        if (match == null) {
            System.out.println("Could not find requested match!");
            return;
        }

        fillMatchLineups(lineups, match);
        fillMatchEvents(events, match);

        System.out.println("Početni sastavi:");
        System.out.println(host.name);
        processStartingLineupsHost(hostLineup);
        System.out.println(guest.name);
        processStartingLineupsGuest(guestLineup);
        System.out.println("");

        clearMaps();

        System.out.println("Završni sastavi:");
        System.out.println(host.name);
        processEndingLineupsHost(hostLineup, events);
        System.out.println(guest.name);
        processEndingLineupsGuest(guestLineup, events);
        System.out.println("");

        ChampionshipComposite.getInstance().accept(new ResetVisitor());
    }

    private void fillMatchLineups(List<MatchLineup> lineups, Match match) {
        List<ChampionshipComponent> components = match.getLineupsEvents();

        for (ChampionshipComponent championshipComponent : components) {
            if (championshipComponent.getClass().equals(MatchLineup.class)) {
                lineups.add((MatchLineup) championshipComponent);
            }
        }

        for (MatchLineup lineup : lineups) {
            if (lineup.club.equals(match.host)) {
                hostLineup.add(lineup);
            } else if (lineup.club.equals(match.guest)) {
                guestLineup.add(lineup);
            }
        }
    }

    private void fillMatchEvents(List<MatchEvent> events, Match match) {
        List<ChampionshipComponent> components = match.getLineupsEvents();

        for (ChampionshipComponent championshipComponent : components) {
            if (championshipComponent.getClass().equals(MatchEvent.class)) {
                events.add((MatchEvent) championshipComponent);
            }
        }
    }

    private void processStartingLineupsHost(List<MatchLineup> hostLineup) {
        for (MatchLineup matchLineup : hostLineup) {
            if (matchLineup.type.equalsIgnoreCase("s")) {
                matchLineup.player.getState().putPlayerInGame();
            } else if (matchLineup.type.equalsIgnoreCase("p")) {
                matchLineup.player.getState().putPlayerAsReserve();
            }
        }

        initializeMap(hostLineupMap);
        sortByPositions(hostLineup, hostLineupMap);
        printMap(hostLineupMap);
    }

    private void processStartingLineupsGuest(List<MatchLineup> guestLineup) {
        for (MatchLineup matchLineup : guestLineup) {
            if (matchLineup.type.equalsIgnoreCase("s")) {
                matchLineup.player.getState().putPlayerInGame();
            } else if (matchLineup.type.equalsIgnoreCase("p")) {
                matchLineup.player.getState().putPlayerAsReserve();
            }
        }

        initializeMap(guestLineupMap);
        sortByPositions(guestLineup, guestLineupMap);
        printMap(guestLineupMap);
    }

    private void sortByPositions(List<MatchLineup> lineups, EnumMap<PositionEnum, ArrayList<String>> lineupsMap) {
        for (MatchLineup lineup : lineups) {
            if (lineup.player.getState().getClass().equals(InPlayState.class)) {
                switch (lineup.position) {
                    case "G" -> lineupsMap.get(PositionEnum.G).add(lineup.player.name);

                    case "B" -> lineupsMap.get(PositionEnum.B).add(lineup.player.name);
                    case "LB" -> lineupsMap.get(PositionEnum.LB).add(lineup.player.name);
                    case "DB" -> lineupsMap.get(PositionEnum.DB).add(lineup.player.name);
                    case "CB" -> lineupsMap.get(PositionEnum.CB).add(lineup.player.name);

                    case "V" -> lineupsMap.get(PositionEnum.V).add(lineup.player.name);
                    case "LDV" -> lineupsMap.get(PositionEnum.LDV).add(lineup.player.name);
                    case "DDV" -> lineupsMap.get(PositionEnum.DDV).add(lineup.player.name);
                    case "CDV" -> lineupsMap.get(PositionEnum.CDV).add(lineup.player.name);
                    case "LV" -> lineupsMap.get(PositionEnum.LV).add(lineup.player.name);
                    case "DV" -> lineupsMap.get(PositionEnum.DV).add(lineup.player.name);
                    case "CV" -> lineupsMap.get(PositionEnum.CV).add(lineup.player.name);
                    case "LOV" -> lineupsMap.get(PositionEnum.LOV).add(lineup.player.name);
                    case "DOV" -> lineupsMap.get(PositionEnum.DOV).add(lineup.player.name);
                    case "COV" -> lineupsMap.get(PositionEnum.COV).add(lineup.player.name);

                    case "N" -> lineupsMap.get(PositionEnum.N).add(lineup.player.name);
                    case "LN" -> lineupsMap.get(PositionEnum.LN).add(lineup.player.name);
                    case "DN" -> lineupsMap.get(PositionEnum.DN).add(lineup.player.name);
                    case "CN" -> lineupsMap.get(PositionEnum.CN).add(lineup.player.name);
                }
            }
        }
    }

    private void initializeMap(EnumMap<PositionEnum, ArrayList<String>> map) {
        for (PositionEnum p : PositionEnum.values()) {
            map.put(p, new ArrayList<>());
        }
    }

    private void clearMaps() {
        hostLineupMap.clear();
        guestLineupMap.clear();
    }

    private void printMap(EnumMap<PositionEnum, ArrayList<String>> map) {
        map.entrySet().forEach(entry -> {
            PositionEnum key = entry.getKey();
            List<String> values = entry.getValue();

            if (values.size() > 0) {
                System.out.println(key.toString() + ":");
                for (String value : values) {
                    System.out.println("\t" + value);
                }
            }
        });
    }

    private void processEndingLineupsHost(List<MatchLineup> hostLineup, List<MatchEvent> events) {
        for (MatchEvent event : events) {
            goalHandler.processEvent(event);
        }

        initializeMap(hostLineupMap);
        sortByPositions(hostLineup, hostLineupMap);
        printMap(hostLineupMap);
    }

    private void processEndingLineupsGuest(List<MatchLineup> guestLineup, List<MatchEvent> events) {
        for (MatchEvent event : events) {
            goalHandler.processEvent(event);
        }

        initializeMap(guestLineupMap);
        sortByPositions(guestLineup, guestLineupMap);
        printMap(guestLineupMap);
    }

}
