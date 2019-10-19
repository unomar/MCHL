package com.sloppylinux.mchl.util;

import com.sloppylinux.mchl.domain.Event;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MCHLService {

    @GET("teams")
    Call<List<Team>> listTeams();

    @GET("teams/{team}")
    Call<Team> getTeam(@Path(value = "team") int teamId);

    @GET("events")
    Call<List<Event>> listEvents();

    @GET("events/{event}")
    Call<Event> getEvent(@Path(value = "event") int eventId);

    @GET("players")
    Call<List<Player>> listPlayers();

    @GET("players/{player}")
    Call<Player> getPlayer(@Path(value = "player") int playerId);
}
