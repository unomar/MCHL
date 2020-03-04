package com.sloppylinux.mchl.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.sloppylinux.mchl.domain.Division;
import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.domain.TeamSchedule;
import com.sloppylinux.mchl.domain.WSException;
import com.sloppylinux.mchl.domain.sportspress.Event;
import com.sloppylinux.mchl.domain.sportspress.LeagueTable;
import com.sloppylinux.mchl.domain.sportspress.TeamTable;
import com.sloppylinux.mchl.domain.sportspress.Venue;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
    private static final String GET_SEASONS = "tables";
    private static final String GET_TEAMS = "teams";
    private static final String GET_STANDINGS = "tables";
    private static final String GET_SCHEDULE = "events";
    private static final String GET_PLAYER_STATS = "players";
    private static final String NAMESPACE = "http://www.omchl.com/services";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";

    private static List<Venue> venues = null;
    private final Logger LOG = Logger.getLogger(MCHLWebservice.class.getName());
    private MCHLService mchlService = null;

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

    public static String[] getSeasons() throws WSException
    {
        return new String[0];
    }

    public static String[] getDivisionNames(String season)
    {
        String[] retVal = {"Novice", "Intermediate", "MCHL"};
        return retVal;
    }

    public static String[] getTeamNames(String season, String division) throws WSException
    {
//		List<String> teamNames = new ArrayList<String>();
//		Map<String, String> params = new HashMap<String, String>();
//		params.put(SEASON_PARAM, season);
//		params.put(DIVISION_PARAM, division);
//
//		SoapObject soap = performCall(GET_TEAMS, params);
//
//		for (int i = 0; i < soap.getPropertyCount(); i++)
//		{
//			SoapObject team = (SoapObject) soap.getProperty(i);
//			teamNames.add(team.getProperty(0).toString());
//		}
//
//		return teamNames.toArray(new String[0]);
        return null;
    }

    public static List<Team> getTeams(String season, String division) throws WSException
    {
//		ArrayList<Team> teams = new ArrayList<Team>();
//		Map<String, String> params = new HashMap<String, String>();
//		params.put(SEASON_PARAM, season);
//		params.put(DIVISION_PARAM, division);
//
//		SoapObject soap2 = performCall(GET_TEAMS, params);
//
//		for (int i = 0; i < soap2.getPropertyCount(); i++)
//		{
//			SoapObject teamSoap = (SoapObject) soap2.getProperty(i);
//			Team team = new Team(teamSoap.getProperty(0).toString());
//			teams.add(team);
//		}
//
//		return teams;
        return Collections.EMPTY_LIST;
    }

    public Team getTeam(int teamId, Context context, boolean forceRefresh)
    {
		Team team = null;
		String cachedName = "Team" + teamId;

		if (!forceRefresh)
		{
			team = (Team) ObjectCache.readCache(cachedName, context);
		}

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

                // Write our assembled team to cache
                // TODO: Re-enable with context
                //ObjectCache.writeCache(cachedName, team, context);
            }
            }
            catch (IOException e)
            {
                LOG.warning("Caught exception performing player search." + e.getMessage());
            }
		}

		return team;
    }

    public LeagueTable getStandings(long seasonId, long leagueId) throws WSException
    {
        try
        {
            Call<List<LeagueTable>> call = mchlService.getLeagueTable(seasonId, leagueId);
            Response<List<LeagueTable>> result = call.execute();
            if (result != null && result.body() != null && result.body().size() == 1)
            {
                return result.body().get(0);
            }
        }
        catch (IOException e)
        {
            LOG.warning("Caught exception attempting to lookup standings for season "+ seasonId + " and league " + leagueId + ".  " + e.getMessage());
        }

        throw new WSException("Unable to lookup standings.");
    }

    /**
     * Get a list of all divisions and all teams in each division
     *
     * @param season The season to use
     * @return ArrayList of all data
     * @throws WSException
     */
    public static ArrayList<Division> getDivisions(String season,
                                                   Context context, boolean forceRefresh) throws WSException
    {
//        ArrayList<Division> divisions = new ArrayList<Division>();
//        for (String div : getDivisionNames(season))
//        {
//            String cachedName = "Division" + season + div;
//
//            Division division = null;
//
//            if (!forceRefresh)
//            {
//                division = (Division) ObjectCache.readCache(cachedName, context);
//            }
//
//            if (division == null)
//            {
//                division = new Division(div);
//                for (Team team : getStandings(season, div))
//                {
//                    division.addTeam(team);
//                }
//
//                // Write out our division to cache
//                ObjectCache.writeCache(cachedName, division, context);
//            }
//            divisions.add(division);
//        }
//        return divisions;
        return null;
    }

    /**
     * @param strVal
     * @return
     */
    private static int getInteger(String strVal)
    {
        int retVal;
        try
        {
            retVal = Integer.parseInt(strVal);
        }
        catch (NumberFormatException e)
        {
            retVal = -1;
        }

        return retVal;
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
     * @param teamId       The id of the team to query
     * @param forceRefresh If we should ignore cached values
     * @return The TeamSchedule
     */
    public TeamSchedule getSchedule(Long teamId,
                                    boolean forceRefresh)
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

                        Game game = new Game(teamNames[0], teamNames[1], event.getEventDate(), 0, 0, venueName);
                        teamSchedule.getGames().add(game);
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
     * @param teamId       The id of the team to query
     * @param forceRefresh If we should ignore cached values
     * @return The TeamSchedule
     * @throws WSException If an error occurs
     */
    public TeamSchedule getResults(Integer teamId,
                                    boolean forceRefresh) throws WSException
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
                Call<List<Event>> eventCall = mchlService.listTeamResults(team.getName(), team.getCurrentSeason(), team.getCurrentLeague(), getIso8601Date(null));
                Response<List<Event>> eventResponses = eventCall.execute();
                List<Event> events = eventResponses.body();
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
                            homeScore = safeParseInt(event.getResults().get(0));
                            awayScore = safeParseInt(event.getResults().get(1));

                            LOG.info("Final score: " + teamNames[0] + " " + homeScore + " - " + awayScore + " " + teamNames[1]);
                        }

                        Game game = new Game(teamNames[0], teamNames[1], event.getEventDate(), homeScore, awayScore, venueName);
                        teamSchedule.getGames().add(game);
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
     * @return The name of the venue
     * @throws WSException If an error occurs
     */
    private String getVenueName(List<Long> venueIds)
    {
        String venueName = "Unknown";
        if (venueIds == null || venueIds.size() != 1)
        {
            LOG.warning("Cannot get a single venue name from multiple ids.");
        }
        if (this.venues == null)
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

        return venueName;
    }

    /**
     * Populate the list of venues
     */
    private void populateVenues()
    {
        Call<List<Venue>> venueCall = mchlService.getVenues();
        try
        {
            Response<List<Venue>> venueResponse = venueCall.execute();
            if (venueResponse != null)
            {
                this.venues = venueResponse.body();
            }
        }
        catch (IOException e)
        {
            LOG.warning("Caught exception querying team schedule." + e.getMessage());
        }
    }

    private String getIso8601Date(Date date)
    {
        if (date == null)
        {
            date = new Date();
        }
        return iso8601Formatter.format(date);
    }

    private int safeParseInt(String string)
    {
        int retVal = -1;
        if (string != null)
        {
            try
            {
                retVal = Integer.valueOf(string);
            }
            catch (NumberFormatException e)
            {
                LOG.fine("Unable to parse int from: " + string);
            }
        }
        return retVal;
    }
}
