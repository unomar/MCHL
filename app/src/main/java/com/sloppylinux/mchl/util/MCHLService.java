package com.sloppylinux.mchl.util;

import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.domain.sportspress.Event;
import com.sloppylinux.mchl.domain.sportspress.League;
import com.sloppylinux.mchl.domain.sportspress.LeagueTable;
import com.sloppylinux.mchl.domain.sportspress.TeamTable;
import com.sloppylinux.mchl.domain.sportspress.Venue;
import com.sloppylinux.mchl.domain.sportspress.Season;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MCHLService
{
    @GET("teams")
    Call<List<Team>> listTeams();

    @GET("teams")
    Call<List<Team>> getTeams(@Query("include") List<Long> teamIds);

//    @GET("lists/{listId}")
//    Call<TeamTable> getTeamStats(@Path(value = "listId") long listId);

    @GET("lists")
    Call<List<TeamTable>> getTeamStats(@Query("seasons") Long seasonId, @Query("leagues") Long leagueId, @Query("search") String teamName);

    @GET("events?order=asc")
    Call<List<Event>> listAllEvents();

    @GET("events?order=desc&per_page=100")
    Call<List<Event>> listTeamResults(@Query("search") String teamName, @Query("seasons") Long seasonId, @Query("leagues") Long leagueId, @Query("before") String date);

    @GET("events?order=asc&per_page=100")
    Call<List<Event>> listTeamSchedule(@Query("search") String teamName, @Query("seasons") Long seasonId, @Query("leagues") Long leagueId, @Query("after") String date);

    @GET("events/{event}")
    Call<Event> getEvent(@Path(value = "event") long eventId);

    @GET("venues")
    Call<List<Venue>> getVenues();

    @GET("players")
    Call<List<Player>> lookupPlayer(@Query("search") String lastName);

    @GET("players/{player}")
    Call<Player> getPlayer(@Path(value = "player") long playerId);

    @GET("tables")
    Call<List<LeagueTable>> getLeagueTable(@Query("seasons") Long seasonId, @Query("leagues") Long leagueId);

    @GET("leagues")
    Call<List<League>> getLeagues();

    @GET("seasons?orderby=id&order=desc")
    Call<List<Season>> getSeasons();
}
