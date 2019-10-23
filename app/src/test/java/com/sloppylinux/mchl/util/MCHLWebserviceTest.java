package com.sloppylinux.mchl.util;

import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.domain.TeamSchedule;
import com.sloppylinux.mchl.domain.Player;

import org.junit.Before;
import org.junit.Test;

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
        Player player = mchlWebservice.playerLookup("Kevin", "Weiss");
        assertNotNull("Player is null", player);
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
    public void testGetSchedule() {
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
