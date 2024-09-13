package com.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class LeagueRanking {

    public static Map<String, Integer> pointsByTeam(String gamesResults) {
        Map<String, Integer> allTeams = new HashMap<String, Integer>();
        String[] games = gamesResults.split("\\R");

        for (String game : games) {

            String[] gameTeams = game.split(", ");

            String team1 = gameTeams[0];
            Integer team1Goals = Integer.parseInt(team1.substring(team1.lastIndexOf(" ") + 1).trim());
            String team1Name = team1.substring(0, team1.lastIndexOf(" ")).trim();

            String team2 = gameTeams[1];
            Integer team2Goals = Integer.parseInt(team2.substring(team2.lastIndexOf(" ") + 1).trim());
            String team2Name = team2.substring(0, team2.lastIndexOf(" ")).trim();

            // Add both teams if they don't already exist in the map
            if (!allTeams.containsKey(team1Name)) {
                allTeams.put(team1Name, 0);
            }

            if (!allTeams.containsKey(team2Name)) {
                allTeams.put(team2Name, 0);
            }

            if (team1Goals > team2Goals) {
                // Add 3 points to the winning team
                allTeams.put(team1Name, allTeams.get(team1Name) + 3);
            } else if (team1Goals < team2Goals) {
                // Add 3 points to the winning team
                allTeams.put(team2Name, allTeams.get(team2Name) + 3);
            } else {
                // Add 1 point to both teams
                allTeams.put(team1Name, allTeams.get(team1Name) + 1);
                allTeams.put(team2Name, allTeams.get(team2Name) + 1);
            }
        }

        return allTeams;
    }

    public static List<Entry<String, Integer>> sortTeamsByPoints(Map<String, Integer> teamsPoints) {
        List<Entry<String, Integer>> sortedList = new ArrayList<>(teamsPoints.entrySet());
        // Override sort method to sort values (points in league) by descending order,
        // and keys in ascending order (alphabetically)
        Collections.sort(sortedList, new Comparator<Entry<String, Integer>>() {
            @Override
            public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
                // Sort alphabetically if both teams have the same points
                if (e1.getValue() == e2.getValue()) {
                    return e1.getKey().compareTo(e2.getKey());
                }
                // Sort in descending order of points
                return e2.getValue().compareTo(e1.getValue());
            }
        });

        return sortedList;
    }

    public static String setRankInLeague(List<Entry<String, Integer>> list) {
        int ranking = 1;
        int temp1 = 1;
        int temp2 = 0;
        int lastPoints = list.get(0).getValue();
        String singularPlural;

        String finalRanking = "";

        for (Entry<String, Integer> en : list) {
            if (!(lastPoints == en.getValue())) {
                temp1 = ranking;
                ranking = ranking--;
            } else {
                temp2 = ranking;
                ranking = temp1;
            }
            singularPlural = en.getValue().equals(new Integer(1)) ? "pt" : "pts";
            finalRanking += ranking + ". " + en.getKey() + ", " + en.getValue() + " " + singularPlural + System.lineSeparator();

            if (!(lastPoints == en.getValue())) {
                ranking = temp1;
            } else if(temp2 != 0){
                ranking = temp2;
            }
            
            ranking++;

            lastPoints = en.getValue();
        }
        return finalRanking;
    }

    public static String readFile(String filename) {
        BufferedReader reader;
        String allLines = "";

        try {
            reader = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = reader.readLine();
            }
            allLines = sb.toString();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allLines;
    }

    public static void main(String[] args) throws Exception {
        if (args.length==0) {
            System.out.println("Please add file name as argument.");
            System.exit(0);
        }

        String filename = args[0];

        String input = readFile(filename);

        String result = setRankInLeague(sortTeamsByPoints(pointsByTeam(input)));
        System.out.println(result);
    }
}