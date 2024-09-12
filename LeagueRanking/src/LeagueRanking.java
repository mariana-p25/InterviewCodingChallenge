

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class LeagueRanking {

    private static Map<String, Integer> pointsByTeam(String gamesResults){
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
            if(!allTeams.containsKey(team1Name)){
                allTeams.put(team1Name, 0);
            }

            if(!allTeams.containsKey(team2Name)){
                allTeams.put(team2Name, 0);
            }

            if(team1Goals > team2Goals){
                // Add 3 points to the winning team
                allTeams.put(team1Name, allTeams.get(team1Name) + 3);
            } else if(team1Goals < team2Goals){
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

    private static void sortTeamsByPoints(Map<String, Integer> teamsPoints){
        List<Entry<String, Integer>> orderedList = new ArrayList<>(teamsPoints.entrySet());
        // Override sort method to sort values (points in league) by descending order, and keys in ascending order (alphabetically)
        Collections.sort(orderedList, new Comparator<Entry<String, Integer>>() {
                @Override
                public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
                    // Sort alphabetically if both teams have the same points
                    if(e1.getValue() == e2.getValue()){
                        return e1.getKey().compareTo(e2.getKey());
                    }
                    // Sort in descending order of points
                    return e2.getValue().compareTo(e1.getValue());
                }
            }
        );

        for(int i=0;i<orderedList.size();i++){
            String team = orderedList.get(i).getKey();
            Integer points = orderedList.get(i).getValue();
            String singularPlural = points != 1 ? "pts" : "pt";
            System.out.println(i+1 + ". " + team + ", " + points + " " + singularPlural);
        }
    }

    public static void main(String[] args) throws Exception {
        String input = "Lions 3, Snakes 3\nTarantulas 1, FC Awesome 0\nLions 1, FC Awesome 1\nTarantulas 3, Snakes 1\nLions 4, Grouches 0";
        Map<String, Integer> teamsPoints = pointsByTeam(input);
        sortTeamsByPoints(teamsPoints);
    }
}