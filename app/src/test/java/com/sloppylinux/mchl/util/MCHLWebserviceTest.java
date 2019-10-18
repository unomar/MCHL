package com.sloppylinux.mchl.util;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class MCHLWebserviceTest
{
    private MCHLWebservice mchlWebservice;

    @Before
    void setUp() {
        mchlWebservice = new MCHLWebservice();
    }


    @Test
    void testPlayerLookup()
    {
        Integer playerId = mchlWebservice.playerLookup("Kevin", "Weiss");
        assertNotNull("Player ID is null", playerId);
        assertThat("Received unexpected player id", playerId, is(Integer.valueOf(3296)));
    }

    void testGetSeasons() {
    }

    void testGetDivisionNames() {
    }

    void testGetTeamNames() {
    }

    void testGetTeams() {
    }

    void testGetSchedule() {
    }

    void testGetTeam() {
    }

    void testGetStandings() {
    }

    void testGetDivisions() {
    }
}
