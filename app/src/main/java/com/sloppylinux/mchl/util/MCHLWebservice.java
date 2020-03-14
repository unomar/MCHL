package com.sloppylinux.mchl.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.domain.TeamSchedule;
import com.sloppylinux.mchl.domain.sportspress.Event;
import com.sloppylinux.mchl.domain.sportspress.LeagueTable;
import com.sloppylinux.mchl.domain.sportspress.TeamTable;
import com.sloppylinux.mchl.domain.sportspress.Venue;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MCHLWebservice
{
    /**
     * Called when the activity is first created.
     */
    private static final String SOAP_ACTION = "https://ourmchl.com/wp-json/sportspress/v2/";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";

    private static List<Venue> venues = null;
    private final Logger LOG = Logger.getLogger(MCHLWebservice.class.getName());
    private MCHLService mchlService = null;
    private static final long timeToLive = 86400000L; // One day...customizable?

    private static final MCHLWebservice singleton = new MCHLWebservice();

    private SimpleDateFormat iso8601Formatter = new SimpleDateFormat(DATE_FORMAT);

    public static MCHLWebservice getSingleton() {
        return singleton;
    }

    private MCHLWebservice()
    {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(Integer.class, new JsonDeserializer<Integer>() {
            @Override
            public Integer deserialize(JsonElement arg0, Type arg1,
                                       JsonDeserializationContext arg2) throws JsonParseException
            {
                Integer intVal = null;

                Gson g = new Gson();
                String intString = g.fromJson(arg0, String.class);
                if (StringUtils.isNumeric(intString))
                {
                    intVal = Integer.valueOf(intString);
                }

                return intVal;
            }
        });

        Gson gson = gsonBuilder
                .setDateFormat(DATE_FORMAT)
                .create();

        // Enable this block for DEBUG logging using retrofit
//		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor((msg)-> { LOG.info(msg); });
//		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//		OkHttpClient client = new OkHttpClient.Builder()
//				.addInterceptor(interceptor)
//				.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SOAP_ACTION)
                .addConverterFactory(GsonConverterFactory.create(gson))
//				.client(client)
                .build();

        mchlService = retrofit.create(MCHLService.class);
    }

    public static String[] getSeasons()
    {
        return new String[0];
    }

    /**
     * Fetch and persist player info
     * @param player The player to fetch
     * @param context The application context
     */
    public void fetchLatestPlayerInfo(Player player, Context context)
    {
        if (player != null && player.requiresUpdate())
        {
            player.clear();
            for (Long teamId : player.getCurrentTeams())
            {
                Team team = getTeam(teamId);
                player.getPlayerTeams().add(team);
                player.getPlayerSchedule().add(this.getSchedule(teamId));
                player.getPlayerResults().add(this.getResults(teamId));
                player.getLeagueTables().add(this.getStandings(team.getCurrentSeason(), team.getCurrentLeague()));
            }

            player.setExpiration(new Date().getTime() + timeToLive);
            Config config = new Config(context);
            config.setPlayer(player);
            config.storeValues();
        }
    }

    /**
     * Get a team by id
     * @param teamId The team id of the team to fetch
     * @return The populated team or null
     */
    Team getTeam(long teamId)
    {
		Team team = null;
		    try
            {
            Response<Team> teamResponse = mchlService.getTeam(teamId).execute();
            if (teamResponse != null && teamResponse.body() != null)
            {
                team = teamResponse.body();

                // Query team stats
                Response<TeamTable> statResponse = mchlService.getTeamStats(team.getListId()).execute();
                if (statResponse != null && statResponse.body() != null)
                {
                    team.setTeamTable(statResponse.body());
                }

            }
            }
            catch (IOException e)
            {
                LOG.warning("Caught exception performing player search." + e.getMessage());
            }

		return team;
    }

    /**
     * Get the standings for a season/league
     * @param seasonId The season ID
     * @param leagueId The league ID
     * @return The standings
     */
    LeagueTable getStandings(long seasonId, long leagueId)
    {
        LeagueTable leagueTable = null;
        try
        {
            Call<List<LeagueTable>> call = mchlService.getLeagueTable(seasonId, leagueId);
            Response<List<LeagueTable>> result = call.execute();
            if (result != null && result.body() != null && result.body().size() == 1)
            {
                leagueTable = result.body().get(0);
            }
        }
        catch (IOException e)
        {
            LOG.warning("Caught exception attempting to lookup standings for season "+ seasonId + " and league " + leagueId + ".  " + e.getMessage());
        }
        return leagueTable;
    }

    /**
     * Lookup a player by name.
     * Can be first, last or combination.  Will return a list of Players matching the query string.
     *
     * @param name The player name to query
     * @return A list of players matching the query
     */
    public List<Player> playerLookup(String name)
    {
        List<Player> players = null;

        try
        {
            Call<List<Player>> playerCall = mchlService.lookupPlayer(name);
            Response<List<Player>> playerResult = playerCall.execute();
            if (playerResult != null && playerResult.body() != null)
            {
                players = playerResult.body();
            }
        }
        catch (IOException e)
        {
            LOG.warning("Caught exception performing player search." + e.getMessage());
        }
        return players;
    }

    /**
     * Get the schedule for a team
     *
     * @param teamId The id of the team to query
     * @return The TeamSchedule
     */
    public TeamSchedule getSchedule(Long teamId)
    {
        TeamSchedule teamSchedule = null;
        try
        {
            Call<Team> teamCall = mchlService.getTeam(teamId);
            Response<Team> teamResponse = teamCall.execute();
            if (teamResponse != null && teamResponse.body() != null)
            {
                Team team = teamResponse.body();
                LOG.info("Querying schedule for team: " + team.getName());
                Call<List<Event>> eventCall = mchlService.listTeamSchedule(team.getName(), team.getCurrentSeason(), team.getCurrentLeague(), getIso8601Date(null));
                Response<List<Event>> eventResponses = eventCall.execute();
                List<Event> events = eventResponses.body();
                if (events != null) {
                    LOG.info("Got " + events.size() + " events");
                    teamSchedule = new TeamSchedule();
                    for (Event event : events) {
                        String eventString = event.getName();
                        if (StringUtils.isNotBlank(eventString)) {
                            String venueName = getVenueName(event.getVenueIds());
                            LOG.info("Creating Game for event [" + event.getId() + "] as " + eventString + " at " + venueName);
                            String[] teamNames = eventString.split(" vs ");

                            Game game = new Game(teamNames[0], teamNames[1], event.getEventDate(), 0, 0, venueName);
                            teamSchedule.getGames().add(game);
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            LOG.warning("Caught exception querying team schedule." + e.getMessage());
        }
        return teamSchedule;
    }

    /**
     * Get the schedule for a team
     *
     * @param teamId The id of the team to query
     * @return The TeamSchedule
     */
    TeamSchedule getResults(Long teamId)
    {
        TeamSchedule teamSchedule = null;
        try
        {
            Call<Team> teamCall = mchlService.getTeam(teamId);
            Response<Team> teamResponse = teamCall.execute();
            if (teamResponse != null && teamResponse.body() != null)
            {
                Team team = teamResponse.body();
                LOG.info("Querying results for team: " + team.getName());
                Call<List<Event>> eventCall = mchlService.listTeamResults(team.getName(), team.getCurrentSeason(), team.getCurrentLeague(), getIso8601Date(null));
                Response<List<Event>> eventResponses = eventCall.execute();
                List<Event> events = eventResponses.body();
                if (events != null) {
                    LOG.info("Got " + events.size() + " events");
                    teamSchedule = new TeamSchedule();
                    for (Event event : events) {
                        String eventString = event.getName();
                        if (StringUtils.isNotBlank(eventString)) {
                            String venueName = getVenueName(event.getVenueIds());
                            LOG.info("Creating Game for event [" + event.getId() + "] as " + eventString + " at " + venueName);
                            String[] teamNames = eventString.split(" vs ");
                            int homeScore = 0;
                            int awayScore = 0;
                            if (event.getResults() != null && event.getResults().size() == 2) {
                                homeScore = safeParseInt(event.getResults().get(0));
                                awayScore = safeParseInt(event.getResults().get(1));

                                LOG.info("Final score: " + teamNames[0] + " " + homeScore + " - " + awayScore + " " + teamNames[1]);
                            }

                            Game game = new Game(teamNames[0], teamNames[1], event.getEventDate(), homeScore, awayScore, venueName);
                            game.setResult(Game.Result.fromString(event.getOutcome().get(teamId)));
                            teamSchedule.getGames().add(game);
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            LOG.warning("Caught exception querying team schedule." + e.getMessage());
        }
        return teamSchedule;
    }

    /**
     * Get the name of a venue
     *
     * @param venueIds A list of ids, that should really only ever contain one venue id
     * @return The name of the venues
     */
    private String getVenueName(List<Long> venueIds)
    {
        String venueName = "Unknown";
        if (venueIds == null || venueIds.size() != 1)
        {
            LOG.warning("Cannot get a single venue name from multiple ids.");
        }
        else {
            if (venues == null) {
                populateVenues();
            }

            Long venueId = venueIds.get(0);
            for (Venue venue : venues) {
                if (venue.getId() == venueId) {
                    return venue.getName();
                }
            }

            LOG.warning("Could not lookup venue name for id: " + venueId);
        }
        return venueName;
    }

    /**
     * Populate the list of venues
     */
    private void populateVenues()
    {
        if (venues == null) {
            Call<List<Venue>> venueCall = mchlService.getVenues();
            try {
                Response<List<Venue>> venueResponse = venueCall.execute();
                if (venueResponse != null) {
                    venues = venueResponse.body();
                }
            } catch (IOException e) {
                LOG.warning("Caught exception querying team schedule." + e.getMessage());
            }
        }
    }

    /**
     * Get an ISO-8601 formatted date string.
     * @param date The date to format or null for current time
     * @return The ISO-8601 formatted date string
     */
    private String getIso8601Date(Date date)
    {
        if (date == null)
        {
            date = new Date();
        }
        return iso8601Formatter.format(date);
    }

    /**
     * Safely parse a String value as an integer.
     * @param string The string to parse
     * @return The integer value or -1 if invalid
     */
    private int safeParseInt(String string)
    {
        int retVal = -1;
        if (string != null)
        {
            try
            {
                retVal = Integer.parseInt(string);
            }
            catch (NumberFormatException e)
            {
                LOG.fine("Unable to parse int from: " + string);
            }
        }
        return retVal;
    }
}
