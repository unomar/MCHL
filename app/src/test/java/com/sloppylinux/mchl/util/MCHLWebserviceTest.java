package com.sloppylinux.mchl.util;

import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.TeamSchedule;
import com.sloppylinux.mchl.domain.WSException;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class MCHLWebserviceTest
{
    private MCHLWebservice mchlWebservice;

    @Before
    public void setUp() {
        mchlWebservice = new MCHLWebservice();
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

    public void testGetSeasons() {
    }

    public void testGetDivisionNames() {
    }

    public void testGetTeamNames() {
    }

    public void testGetTeams() {
    }

    @Test
    public void testGetSchedule() throws WSException {
        TeamSchedule teamSchedule = mchlWebservice.getSchedule(3283, true);
        assertNotNull("Team Schedule was null", teamSchedule);
        for (Game game : teamSchedule.getGames())
        {
            assertNotNull("Team Schedule contained null game", game);
        }
    }

    void testGetTeam() {
    }

    void testGetStandings() {
    }

    void testGetDivisions() {
    }
}
