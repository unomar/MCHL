package com.sloppylinux.mchl.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sloppylinux.mchl.domain.Division;
import com.sloppylinux.mchl.domain.Game;
import com.sloppylinux.mchl.domain.Player;
import com.sloppylinux.mchl.domain.Team;
import com.sloppylinux.mchl.domain.TeamSchedule;
import com.sloppylinux.mchl.domain.WSException;
import com.sloppylinux.mchl.domain.sportspress.Event;
import com.sloppylinux.mchl.domain.sportspress.LeagueTable;
import com.sloppylinux.mchl.domain.sportspress.Venue;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
    private static final String SOAP_ACTION = "http://ourmchl.com/wp-json/sportspress/v2/";
    private static final String GET_SEASONS = "tables";
    private static final String GET_TEAMS = "teams";
    private static final String GET_STANDINGS = "tables";
    private static final String GET_SCHEDULE = "events";
    private static final String GET_PLAYER_STATS = "players";
    private static final String NAMESPACE = "http://www.omchl.com/services";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "MM/dd/yyyy hh:mm a");
    private static List<Division> divisions;
    private static List<Venue> venues = null;
    private final Logger LOG = Logger.getLogger(MCHLWebservice.class.getName());
    private MCHLService mchlService = null;

    public MCHLWebservice()
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
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

    public static void init()
    {
        if (divisions == null)
        {
            divisions = new ArrayList<Division>();
            divisions.add(new Division("MCHL"));
            divisions.add(new Division("Intermediate"));
            divisions.add(new Division("IntermediateB"));
            divisions.add(new Division("Novice"));
        }
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

    public static Team getTeam(String season, String division, String teamName, Context context, boolean forceRefresh) throws WSException
    {
//		Team team = null;
//		String cachedName = "Team" + season + teamName;
//
//		if (!forceRefresh)
//		{
//			team = (Team) ObjectCache.readCache(cachedName, context);
//		}
//
//		if (team == null)
//		{
//			team = new Team();
//			Map<String, String> params = new HashMap<String, String>();
//			params.put(SEASON_PARAM, season);
//			params.put(TEAM_PARAM, teamName);
//
//			List<Team> teams = getStandings(season, division);
//			Collections.sort(teams);
//			for (int i =0; i < teams.size(); i++)
//			{
//				Team currTeam = teams.get(i);
//				if (currTeam.getName().equals(teamName))
//				{
//					team = currTeam;
//					team.setRank(i+1);
//				}
//			}
//
//			SoapObject playerStats = performCall(GET_PLAYER_STATS, params);
//			SoapObject goalieStats = performCall(GET_GOALIE_STATS, params);
//
//			for (int i = 0; i < playerStats.getPropertyCount(); i++)
//			{
//				SoapObject playerSoap = (SoapObject) playerStats.getProperty(i);
//				Player player = new Player();
//				player.setFirstName(playerSoap.getProperty("firstName")
//						.toString());
//				player.setLastName(playerSoap.getProperty("lastName")
//						.toString());
//				player.setNumber(Integer.parseInt(playerSoap.getProperty(
//						"jerseyNumber").toString()));
//				player.setGamesPlayed(Integer.parseInt(playerSoap.getProperty(
//						"gamesPlayed").toString()));
//				int goals = Integer.parseInt(playerSoap.getProperty("goals")
//						.toString());
//				int assists = Integer.parseInt(playerSoap
//						.getProperty("assists").toString());
//				player.setGoals(goals);
//				player.setAssists(assists);
//				player.setPoints(goals + assists);
//				player.setPims(Integer.parseInt(playerSoap.getProperty(
//						"penaltyMinutes").toString()));
//
//				team.addPlayer(player);
//			}
//
//			if (goalieStats.getPropertyCount() > 0)
//			{
//				SoapObject soapGoalie = (SoapObject) goalieStats.getProperty(0);
//				String firstName = soapGoalie.getProperty("firstName")
//						.toString();
//				String lastName = soapGoalie.getProperty("lastName").toString();
//
//				int gamesPlayed = Integer.parseInt(soapGoalie.getProperty(
//						"gamesPlayed").toString());
//				int goalsAgainst = Integer.parseInt(soapGoalie.getProperty(
//						"goalsAgainst").toString());
//				int shots = Integer.parseInt(soapGoalie.getProperty("shots")
//						.toString());
//				float gaa = Float.parseFloat(soapGoalie.getProperty("gaa")
//						.toString());
//				String saveP = soapGoalie.getProperty("savePercentage")
//						.toString();
//				saveP = saveP.replace("%", "");
//				float savePercentage = Float.parseFloat(saveP);
//				Goalie goalie = new Goalie(firstName, lastName, gamesPlayed,
//						goalsAgainst, shots, gaa, savePercentage);
//				team.setGoalie(goalie);
//			}
//
//			// Write our assembled team to cache
//			ObjectCache.writeCache(cachedName, team, context);
//		}
//
//		return team;
        return null;
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
            if (playerResult != null && playerResult.body() != null && playerResult.body().size() == 1)
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
     * @throws WSException If an error occurs
     */
    public TeamSchedule getSchedule(Integer teamId,
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
                Call<List<Event>> eventCall = mchlService.listTeamEvents(team.getName(), team.getCurrentSeason(), team.getCurrentLeague(), null);
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
     * Get the name of a venue
     *
     * @param venueIds A list of ids, that should really only ever contain one venue id
     * @return The name of the venue
     * @throws WSException If an error occurs
     */
    private String getVenueName(List<Long> venueIds) throws WSException
    {
        if (venueIds == null || venueIds.size() != 1)
        {
            throw new WSException("Cannot get a single venue name from multiple ids.");
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
        throw new WSException("Could not lookup venue name for id: " + venueId);
    }

//	private  SoapObject performCall(String methodName,
//			Map<String, String> params) throws WSException
//	{
//		SoapObject resultData = null;
//		try
//		{
//
//			SoapObject request = new SoapObject(NAMESPACE, methodName);
//
//			if (params != null)
//			{
//				for (String key : params.keySet())
//				{
//					request.addProperty(key, params.get(key));
//				}
//			}
//
//			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//					SoapEnvelope.VER11);
//			envelope.dotNet = true;
//			envelope.setOutputSoapObject(request);
//
//			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
//
//			androidHttpTransport.call(SOAP_ACTION + methodName, envelope);
//
//			resultData = (SoapObject) envelope.getResponse();
//
//			System.out.println(resultData);
//		}
//		catch (Exception e)
//		{
//			System.out.println(e.getMessage());
//			throw new WSException("Caught exception connecting to server", e);
//		}
//		return resultData;
//	}

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
}
