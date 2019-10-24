package com.sloppylinux.mchl.util;

import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.domain.sportspress.Event;
import com.sloppylinux.mchl.domain.sportspress.Venue;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MCHLService
{

    int MAX_RESULTS = 50;

    @GET("teams")
    Call<List<Team>> listTeams();

    @GET("teams/{team}")
    Call<Team> getTeam(@Path(value = "team") long teamId);

    @GET("events")
    Call<List<Event>> listAllEvents();

    @GET("events")
    Call<List<Event>> listTeamEvents(@Query("search") String teamName, @Query("seasons") Long seasonId, @Query("leagues") Long leagueId, @Query("status") String status);

    @GET("events/{event}")
    Call<Event> getEvent(@Path(value = "event") long eventId);

    @GET("venues")
    Call<List<Venue>> getVenues();

    @GET("players")
    Call<List<Player>> lookupPlayer(@Query("search") String lastName);

    @GET("players/{player}")
    Call<Player> getPlayer(@Path(value = "player") long playerId);
}
