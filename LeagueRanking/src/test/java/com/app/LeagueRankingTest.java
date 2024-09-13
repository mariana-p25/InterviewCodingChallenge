package com.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

public class LeagueRankingTest {

    @Test
    public void testPoints() {
        Map<String, Integer> result = LeagueRanking.pointsByTeam("Lions 3, Snakes 3\nTarantulas 1, FC Awesome 0\nLions 1, FC Awesome 1\nTarantulas 3, Snakes 1\nLions 4, Grouches 0");
            
        assertEquals(result.get("Tarantulas"), 6);
        assertEquals(result.get("Grouches"), 0);
    }

    @Test
    public void testSort() {
        Map<String, Integer> teamsPoints = new HashMap<String, Integer>();
        teamsPoints.put("Tarantulas", 5);
        teamsPoints.put("Lions", 4);
        teamsPoints.put("Tigers", 0);
        teamsPoints.put("Manchester United", 6);
        teamsPoints.put("Manchester City", 6);

        List<Entry<String, Integer>> result = LeagueRanking.sortTeamsByPoints(teamsPoints);
        
        // Test order alphabetical (and by points)
        assertEquals(result.get(0).getKey(), "Manchester City");
        assertEquals(result.get(1).getKey(), "Manchester United");
        
        assertEquals(result.get(result.size()-1).getKey(), "Tigers");
    }

    @Test
    public void testRank() {
        String result = LeagueRanking.setRankInLeague(LeagueRanking.sortTeamsByPoints(LeagueRanking.pointsByTeam("Lions 3, Snakes 3\nTarantulas 1, FC Awesome 0\nLions 1, FC Awesome 1\nTarantulas 3, Snakes 1\nLions 4, Grouches 0")));
        String firstLine = result.substring(0, result.indexOf(System.lineSeparator()));
        assertEquals(firstLine, "1. Tarantulas, 6 pts");
    }
}