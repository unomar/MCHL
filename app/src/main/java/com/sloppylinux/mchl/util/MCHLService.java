package com.sloppylinux.mchl.util;

import com.sloppylinux.mchl.domain.retrofit.Event;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MCHLService {

    int MAX_RESULTS = 50;

    @GET("teams")
    Call<List<Team>> listTeams();

    @GET("teams/{team}")
    Call<Team> getTeam(@Path(value = "team") long teamId);

    @GET("events")
    Call<List<Event>> listEvents();

    @GET("events/{event}")
    Call<Event> getEvent(@Path(value = "event") long eventId);

    @GET("players")
    Call<List<Player>> listPlayers(@Query("per_page") Integer perPage, @Query("page") Integer page);

    @GET("players/{player}")
    Call<Player> getPlayer(@Path(value = "player") long playerId);
}
