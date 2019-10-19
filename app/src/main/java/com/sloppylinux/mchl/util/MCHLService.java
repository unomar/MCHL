package com.sloppylinux.mchl.util;

import com.sloppylinux.mchl.domain.Event;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.Team;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MCHLService {

    int MAX_RESULTS = 50;

    @GET("teams")
    Call<List<Team>> listTeams();

    @GET("teams/{team}")
    Call<Team> getTeam(@Path(value = "team") int teamId);

    @GET("events")
    Call<List<Event>> listEvents();

    @GET("events/{event}")
    Call<Event> getEvent(@Path(value = "event") int eventId);

    @GET("players")
    Call<List<Player>> listPlayers(@Query("page") Integer page);

    @GET("players/{player}")
    Call<Player> getPlayer(@Path(value = "player") int playerId);
}
