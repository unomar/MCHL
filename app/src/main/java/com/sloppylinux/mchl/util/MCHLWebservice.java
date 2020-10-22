package com.sloppylinux.mchl.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.domain.TeamSchedule;
import com.sloppylinux.mchl.domain.sportspress.Event;
import com.sloppylinux.mchl.domain.sportspress.League;
import com.sloppylinux.mchl.domain.sportspress.LeagueTable;
import com.sloppylinux.mchl.domain.sportspress.TeamTable;
import com.sloppylinux.mchl.domain.sportspress.Venue;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MCHLWebservice
{
    public static final String ALL_STARS = "all-stars";
    public static final String NETWORK_ERROR = "Error occurred attempting to contact MCHL website";
    public static final long NETWORK_TIMEOUT_SECONDS = 60l;
    /**
     * Called when the activity is first created.
     */
    private static final String SOAP_ACTION = "https://ourmchl.com/wp-json/sportspress/v2/";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";
    private static final long timeToLive = 86400000L; // One day...customizable?
    private static final MCHLWebservice singleton = new MCHLWebservice();
    private static List<Venue> venues = null;
    private final Logger LOG = Logger.getLogger(MCHLWebservice.class.getName());
    private MCHLService mchlService = null;
    private SimpleDateFormat iso8601Formatter = new SimpleDateFormat(DATE_FORMAT);

    private MCHLWebservice()
    {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(Integer.class, (JsonDeserializer<Integer>) (arg0, arg1, arg2) ->
        {
            Integer intVal = null;

            Gson g = new Gson();
            String intString = g.fromJson(arg0, String.class);
            if (StringUtils.isNumeric(intString))
            {
                intVal = Integer.valueOf(intString);
            }

            return intVal;
        });

        Gson gson = gsonBuilder
                .setDateFormat(DATE_FORMAT)
                .create();

        // Enable this block for DEBUG logging using retrofit
//		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor((msg)-> { LOG.info(msg); });
//		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
//				.addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SOAP_ACTION)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        mchlService = retrofit.create(MCHLService.class);
    }

    public static MCHLWebservice getSingleton()
    {
        return singleton;
    }

    /**
     * Fetch and persist player info
     *
     * @param player  The player to fetch
     * @param context The application context
     * @throws WebserviceException If an error occurs
     */
    public void updateConfig(Player player, Context context) throws WebserviceException
    {
        if (player != null && player.requiresUpdate())
        {
            player = fetchPlayer(player);

            // TODO: Null check and safeguard this better
            long seasonId = (player.getSeasons() != null && !player.getSeasons().isEmpty())
                    ? player.getSeasons().get(player.getSeasons().size() - 1)
                    : getTeam(player.getTeams().get(0)).getCurrentSeason();
            List<LeagueTable> leagueTables = fetchLatestLeagueTables(seasonId);

            player.setExpiration(new Date().getTime() + timeToLive);
            Config config = new Config(context);
            config.setPlayer(player);
            config.setLeagueTables(leagueTables);
            config.storeValues();
        }
    }

    /**
     * Fetch Player Info from the backend API
     *
     * @param player The player object to populate
     * @return The populated player object
     * @throws WebserviceException If an error occurs
     */
    private Player fetchPlayer(Player player) throws WebserviceException
    {
        try
        {
            Response<Player> playerCall = this.mchlService.getPlayer(player.getPlayerId()).execute();
            if (playerCall != null)
            {
                player = playerCall.body();
            }
            for (Long teamId : player.getCurrentTeams())
            {
                Team team = this.getTeam(teamId);
                if (team != null)
                {
                    team.setLeagueTable(this.getStandings(team.getCurrentSeason(), team.getCurrentLeague()));
                    player.getPlayerTeams().add(team);
                    player.getPlayerSchedule().add(this.getSchedule(teamId));
                    player.getPlayerResults().add(this.getResults(teamId));
                }
            }
        } catch (IOException e)
        {
            LOG.warning("Caught exception performing updating player info." + e.getMessage());
            throw new WebserviceException(NETWORK_ERROR, "Caught IOException in fetchPlayer()", e);
        }

        return player;
    }

    /**
     * Fetch and persist league tables
     *
     * @param seasonId The season ID
     */
    public List<LeagueTable> fetchLatestLeagueTables(long seasonId) throws WebserviceException
    {
        List<LeagueTable> leagueTables = new ArrayList<>();
        for (League league : this.getLeagues())
        {
            // Ignore All-Star standings
            if (!ALL_STARS.equals(league.getSlug()))
            {
                LeagueTable standings = getStandings(seasonId, league.getId());
                if (standings != null)
                {
                    standings.setName(league.getName());
                    standings.setExpiration(new Date().getTime() + timeToLive);
                    leagueTables.add(standings);
                } else
                {
                    LOG.info("Skipping league table for " + league.getName());
                }
            }
        }

        return leagueTables;
    }

    /**
     * Get a team by id
     *
     * @param teamId The team id of the team to fetch
     * @return The populated team or null
     * @throws WebserviceException If a communication error occurs
     */
    public Team getTeam(long teamId) throws WebserviceException
    {
        // Check the Cache first to avoid multiple expensive WebService calls
        String cacheKey = Constants.TEAM_KEY + teamId;
        Team team = (Team) TemporaryCache.getInstance().get(cacheKey);
        if (team == null)
        {
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
                    // Store the object in the cache for later retrieval
                    TemporaryCache.getInstance().put(cacheKey, team);
                }
            } catch (IOException e)
            {
                LOG.warning("Caught exception performing player search." + e.getMessage());
                throw new WebserviceException(NETWORK_ERROR, "Caught IOException in getTeams()", e);
            }
        } else
        {
            LOG.info("Retrieved team from cache!");
        }

        return team;
    }

    /**
     * Get the standings for a season/league
     *
     * @param seasonId The season ID
     * @param leagueId The league ID
     * @return The standings
     */
    public LeagueTable getStandings(long seasonId, long leagueId) throws WebserviceException
    {
        String cacheKey = "leagueTable-S" + seasonId + "L" + leagueId;
        // Check the cache first to avoid the expensive WebService call
        LeagueTable leagueTable = (LeagueTable) TemporaryCache.getInstance().get(cacheKey);
        if (leagueTable == null)
        {
            try
            {
                Call<List<LeagueTable>> call = mchlService.getLeagueTable(seasonId, leagueId);
                Response<List<LeagueTable>> result = call.execute();
                if (result != null && result.body() != null && result.body().size() == 1)
                {
                    leagueTable = result.body().get(0);
                    // Cache the retrieved LeagueTable
                    TemporaryCache.getInstance().put(cacheKey, leagueTable);
                }
            } catch (IOException e)
            {
                LOG.warning("Caught exception attempting to lookup standings for season " + seasonId + " and league " + leagueId + ".  " + e.getMessage());
                throw new WebserviceException(NETWORK_ERROR, "Caught IOException retrieving Standings", e);
            }
        } else
        {
            LOG.info("Retrieved LeagueTable from cache");
        }
        return leagueTable;
    }

    /**
     * Lookup a player by name.
     * Can be first, last or combination.  Will return a list of Players matching the query string.
     *
     * @param name The player name to query
     * @return A list of players matching the query
     * @throws WebserviceException If a communcation error occurs
     */
    public List<Player> playerLookup(String name) throws WebserviceException
    {
        List<Player> players = new ArrayList<>();

        try
        {
            Call<List<Player>> playerCall = mchlService.lookupPlayer(name);
            Response<List<Player>> playerResult = playerCall.execute();
            if (playerResult != null && playerResult.body() != null)
            {
                players = playerResult.body();
            }
        } catch (IOException e)
        {
            LOG.warning("Caught exception performing player search." + e.getMessage());
            throw new WebserviceException(NETWORK_ERROR, "Caught IOException in playerLookup()", e);
        }
        return players;
    }

    /**
     * Get the schedule for a team
     *
     * @param teamId The id of the team to query
     * @return The TeamSchedule
     */
    public TeamSchedule getSchedule(Long teamId) throws WebserviceException
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
                if (events != null)
                {
                    LOG.info("Got " + events.size() + " events");
                    teamSchedule = new TeamSchedule();
                    for (Event event : events)
                    {
                        String eventString = event.getName();
                        if (StringUtils.isNotBlank(eventString))
                        {
                            String venueName = getVenueName(event.getVenueIds());
                            LOG.info("Creating Game for event [" + event.getId() + "] as " + eventString + " at " + venueName);
                            String[] teamNames = eventString.split(" vs ");

                            // TODO: Revert this and pull Team info on demand
                            Team homeTeam = new Team(teamNames[0], team.getCurrentSeason(), team.getCurrentLeague());
                            homeTeam.setId(event.getTeamIds().get(0));
                            Team awayTeam = new Team(teamNames[1], team.getCurrentSeason(), team.getCurrentLeague());
                            awayTeam.setId(event.getTeamIds().get(1));
                            Game game = new Game(homeTeam, awayTeam, event.getEventDate(), 0, 0, venueName);
                            teamSchedule.getGames().add(game);
                        }
                    }
                }
            }
        } catch (IOException e)
        {
            String errorMessage = "Caught exception querying team schedule.";
            LOG.warning(errorMessage + e.getMessage());
            throw new WebserviceException(NETWORK_ERROR, errorMessage, e);
        }
        return teamSchedule;
    }

    /**
     * Get the schedule for a team
     *
     * @param teamId The id of the team to query
     * @return The TeamSchedule
     */
    TeamSchedule getResults(Long teamId) throws WebserviceException
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
                if (events != null)
                {
                    LOG.info("Got " + events.size() + " events");
                    teamSchedule = new TeamSchedule();
                    for (Event event : events)
                    {
                        String eventString = event.getName();
                        if (StringUtils.isNotBlank(eventString))
                        {
                            String venueName = getVenueName(event.getVenueIds());
                            LOG.info("Creating Game for event [" + event.getId() + "] as " + eventString + " at " + venueName);
                            String[] teamNames = eventString.split(" vs ");
                            int homeScore = 0;
                            int awayScore = 0;
                            if (event.getResults() != null && event.getResults().size() == 2)
                            {
                                homeScore = Utils.safeParseInt(event.getResults().get(0));
                                awayScore = Utils.safeParseInt(event.getResults().get(1));

                                LOG.info("Final score: " + teamNames[0] + " " + homeScore + " - " + awayScore + " " + teamNames[1]);
                            }

                            // TODO: Revert this and pull Team info on demand
                            Team homeTeam = new Team(teamNames[0], team.getCurrentSeason(), team.getCurrentLeague()); //getTeam(event.getTeamIds().get(0));
                            homeTeam.setId(event.getTeamIds().get(0));
                            Team awayTeam = new Team(teamNames[1], team.getCurrentSeason(), team.getCurrentLeague()); //getTeam(event.getTeamIds().get(1));
                            awayTeam.setId(event.getTeamIds().get(1));
                            Game game = new Game(homeTeam, awayTeam, event.getEventDate(), homeScore, awayScore, venueName);
                            game.setResult(Game.Result.fromString(event.getOutcome().get(teamId)));
                            teamSchedule.getGames().add(game);
                        }
                    }
                }
            }
        } catch (IOException e)
        {
            String errorMessage = "Caught exception querying team results.";
            LOG.warning(errorMessage + e.getMessage());
            throw new WebserviceException(NETWORK_ERROR, errorMessage, e);
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
        } else
        {
            if (venues == null)
            {
                populateVenues();
            }

            Long venueId = venueIds.get(0);
            for (Venue venue : venues)
            {
                if (venue.getId() == venueId)
                {
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
        if (venues == null)
        {
            Call<List<Venue>> venueCall = mchlService.getVenues();
            try
            {
                Response<List<Venue>> venueResponse = venueCall.execute();
                if (venueResponse != null)
                {
                    venues = venueResponse.body();
                }
            } catch (IOException e)
            {
                LOG.warning("Caught exception querying team schedule." + e.getMessage());
            }
        }
    }

    /**
     * Get the list of leagues.
     *
     * @return A list of Leagues
     */
    private List<League> getLeagues()
    {
        List<League> leagues = new ArrayList<>();
        Call<List<League>> leagueCall = mchlService.getLeagues();
        try
        {
            Response<List<League>> venueResponse = leagueCall.execute();
            if (venueResponse != null)
            {
                leagues.addAll(venueResponse.body());
            }
        } catch (IOException e)
        {
            LOG.warning("Caught exception querying team schedule." + e.getMessage());
        }
        return leagues;
    }

    /**
     * Get an ISO-8601 formatted date string.
     *
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
}
