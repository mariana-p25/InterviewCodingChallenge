import java.util.HashMap;
import java.util.Map;

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

            if(team1Goals > team2Goals){
                // Add 3 points to the winning team
                if(allTeams.containsKey(team1Name)){
                    allTeams.put(team1Name, allTeams.get(team1Name) + 3);
                } else {
                    allTeams.put(team1Name, 3);
                }

                // Check if the losing team exists and add them with 0 points
                if(!allTeams.containsKey(team2Name)){
                    allTeams.put(team2Name, 0);
                }

            } else if(team1Goals < team2Goals){
                // Add 3 points to the winning team
                if(allTeams.containsKey(team2Name)){
                    allTeams.put(team2Name, allTeams.get(team2Name) + 3);

                } else {
                    allTeams.put(team2Name, 3);
                }

                // Check if the losing team exists and add them with 0 points
                if(!allTeams.containsKey(team1Name)){
                    allTeams.put(team1Name, 0);
                }

            } else {
                // Add 1 point to both teams
                if(allTeams.containsKey(team1Name)){
                    allTeams.put(team1Name, allTeams.get(team1Name) + 1);

                } else {
                    allTeams.put(team1Name, 1);
                }
                if(allTeams.containsKey(team2Name)){
                    allTeams.put(team2Name, allTeams.get(team2Name) + 1);

                } else {
                    allTeams.put(team2Name, 1);
                }
            }
        }

        return allTeams;
    }

    private static void sortTeamsByPoints(Map<String, Integer> teamsPoints){
        System.out.println("***" +teamsPoints);
    }

    public static void main(String[] args) throws Exception {
        String input = "Lions 3, Snakes 3\nTarantulas 1, FC Awesome 0\nLions 1, FC Awesome 1\nTarantulas 3, Snakes 1\nLions 4, Grouches 0";
        Map<String, Integer> teamsPoints = pointsByTeam(input);
        sortTeamsByPoints(teamsPoints);
    }
}