package com.sloppylinux.mchl.util;

import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.domain.TeamSchedule;
import com.sloppylinux.mchl.domain.WSException;
import com.sloppylinux.mchl.domain.sportspress.LeagueTable;
import com.sloppylinux.mchl.domain.sportspress.PlayerStatistic;
import com.sloppylinux.mchl.domain.sportspress.TeamStatistic;
import com.sloppylinux.mchl.domain.sportspress.TeamTable;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class MCHLWebserviceTest
{
    private MCHLWebservice mchlWebservice;

    @Before
    public void setUp()
    {
        mchlWebservice = MCHLWebservice.getSingleton();
    }


    @Test
    public void testPlayerLookup()
    {
        List<Player> players = mchlWebservice.playerLookup("Kevin Weiss");
        assertNotNull("Player is null", players);
        assertThat("Player Lookup returned less than one result", players.size(), is(greaterThan(0)));
        Player player = players.get(0);
        assertThat("Received unexpected player id", player.getId(), is(3296L));
        assertThat("Player has more than one current team", player.getCurrentTeams().size(), is(1));
    }

    public void testGetSeasons()
    {
    }

    public void testGetDivisionNames()
    {
    }

    public void testGetTeamNames()
    {
    }

    public void testGetTeams()
    {
    }

    @Test
    public void testGetSchedule() throws WSException
    {
        TeamSchedule teamSchedule = mchlWebservice.getSchedule(3283, true);
        assertNotNull("Team Schedule was null", teamSchedule);
        for (Game game : teamSchedule.getGames())
        {
            assertNotNull("Team Schedule contained null game", game);
        }
    }

    @Test
    public void testGetResults() throws WSException
    {
        TeamSchedule teamResults = mchlWebservice.getResults(3283, true);
        assertNotNull("Team Results was null", teamResults);
        for (Game game : teamResults.getGames())
        {
            assertNotNull("Team Schedule contained null game", game);
            assertNotEquals("Failed to parse home score", game.getHomeScore(), -1);
            assertNotEquals("Failed to parse away score", game.getAwayScore(), -1);
        }
    }

    @Test
    public void testGetTeam()
    {
        Team team = mchlWebservice.getTeam(3283, null, true);
        assertNotNull("Team was null", team);
        TeamTable teamTable = team.getTeamTable();
        for(Map.Entry<String,PlayerStatistic> entry : teamTable.getTableData().entrySet())
        {
            assertNotNull("Statistics key is null", entry.getKey());
            assertNotNull("Statistics are null", entry.getValue());
        }
    }

    @Test
    public void testGetStandings() throws WSException
    {
        LeagueTable leagueTable = mchlWebservice.getStandings(117, 115);
        assertNotNull("League table was null", leagueTable);

        for(Map.Entry<String,TeamStatistic> entry : leagueTable.getTableData().entrySet())
        {
            assertNotNull("Statistics key is null", entry.getKey());
            assertNotNull("Statistics are null", entry.getValue());
        }
    }

    public void testGetDivisions()
    {
    }
}
