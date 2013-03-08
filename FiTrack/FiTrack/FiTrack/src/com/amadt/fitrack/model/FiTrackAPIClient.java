package com.amadt.fitrack.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amadt.fitrack.model.RestClient.Parameter;
import com.amadt.fitrack.model.RestClient.RESTException;


public class FiTrackAPIClient {

	private static FiTrackAPIClient instance = null;
	private static final String apiUrl = "https://datamachine.net:8000/fitrack/api";

	private String email;
	private String username;
	private String description;
	private String sessionToken;
	
	Context context = null;
	
	public void setContext(Context context) {
		this.context = context;
	}
	
	private SharedPreferences getPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(this.context);
	}
	
	private FiTrackAPIClient() {
		// private constructor
	}
	
	public static FiTrackAPIClient getInstance(Context context)
	{
		if (instance == null) {
			instance = new FiTrackAPIClient();
		}
		
		instance.setContext(context);
		
		return instance;
	}
	
	///////
	// User functions
	///////	
	public String createUser(String username, String email, String password) throws RESTException
	{
		final String method = "/user/create";
		
		username = username.trim();
		email = email.trim();
		
		Parameter [] params = { 
					new Parameter("username", username), 
					new Parameter("password", password),
					new Parameter("email", email)
					};
		
		JSONObject json = RestClient.execute(apiUrl, method, params);
		
		try {
			return json.getString("message");
		} catch (JSONException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public String resetPassword(String email) throws RESTException {
		final String method = "/user/reset_password";
		
		Parameter [] params = { new Parameter("email", email) };
		
		JSONObject json = RestClient.execute(apiUrl, method, params);
		//
		try {
			return json.getString("message");
		} catch (JSONException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public String changePassword(String currentPassword, String newPassword) throws RESTException {
		final String method = "/user/change_password";
		
		Parameter [] params = {
				new Parameter("token", getSessionToken()),
				new Parameter("password", currentPassword),
				new Parameter("newpassword", newPassword)
		};
		
		JSONObject json = RestClient.execute(apiUrl, method, params);
		
		try {
			return json.getString("message");
		} catch (JSONException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public String emailUsername(String email) throws RESTException {
		final String method = "/user/get_username";
		
		Parameter [] params = { new Parameter("email", email) };
		
		JSONObject json = RestClient.execute(apiUrl, method, params);
		
		try {
			return json.getString("message");
		} catch (JSONException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public String login(String username, String password) throws RESTException {
		final String method = "/user/login";
		
		Parameter [] params = {
			new Parameter("username", username.trim()),
			new Parameter("password", password.trim())
		};
		
		JSONObject json = RestClient.execute(apiUrl, method, params);

		try {
			setUsername(json.getString("username"));
			setSessionToken(json.getString("token"));
			setEmail(json.getString("email"));
			setDescription(json.getString("description"));
			return json.getString("message");
		} catch (Exception e) {
			e.printStackTrace();
			setSessionToken(null);
			return e.getMessage();
		}
	}
	
	public String editAccountDetails(String email, String description) throws RESTException {
		final String method = "/user/edit_account";
		
		Parameter [] params = {
			new Parameter("token", getSessionToken()),
			new Parameter("email", email),
			new Parameter("description", description)
		};
		
		JSONObject json = RestClient.execute(apiUrl, method, params);
		
		try {
			String message = json.getString("message");
			setDescription(description);
			setEmail(email);
			return message;
		} catch (JSONException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	//////////////////////////////////////////
	// Box functions
	/////////////////////////////////////////
	public String createBox(String boxId, String boxName, String boxDescription) throws RESTException {
		final String method = "/box/create";
				
		Parameter [] params = {
				new Parameter("token", this.getSessionToken()),
				new Parameter("id", boxId),
				new Parameter("name", boxName),	
				new Parameter("description", boxDescription)
		};
		
		JSONObject json = RestClient.execute(apiUrl, method, params);
		
		try {
			return json.getString("message");
		} catch (JSONException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public String editBoxDetails(String boxId, String boxName, String boxDescription) throws RESTException {
		final String method = "/box/edit";
		Parameter [] params = {
			new Parameter("token", getSessionToken()),
			new Parameter("id", boxId),
			new Parameter("name", boxName),	
			new Parameter("description", boxDescription)
		};
		
		JSONObject json = RestClient.execute(apiUrl, method, params);
		
		try {
			return json.getString("message");
		} catch (JSONException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public Box getBox(String boxId) throws RESTException {
		final String method = "/box/get";
		
		Parameter [] params = {
			new Parameter("id", boxId)
		};
		

		JSONObject json = RestClient.execute(apiUrl, method, params);
		
		
		Box box = new Box();
		
		try {
			box.setDescription(json.getString("description"));
			box.setName(json.getString("name"));
			box.setId(json.getString("id"));
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return box;
	}
	
	public String scheduleWOD(String boxId, Date date, String wodName) throws RESTException {
		final String method = "/box/schedule";
		
		String dateString = String.format("%d-%d-%d", date.getYear(), date.getMonth(), date.getDate());
		
		Parameter [] params = {
			new Parameter("date", dateString),
			new Parameter("id", boxId),
			new Parameter("wod", "" + wodName),
			new Parameter("token", getSessionToken())
		};
		
		JSONObject json = RestClient.execute(apiUrl, method, params);
		
		try {
			return json.getString("message");
		} catch (JSONException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public Box [] getFollowedBoxes() throws RESTException {
		final String method = "/box/followed";
		Parameter [] params = {
			new Parameter("token", getSessionToken()),
		};
		
		JSONObject json = RestClient.execute(apiUrl, method, params);
		
		ArrayList<Box> boxes = new ArrayList<Box>();
		
		try {			
			JSONArray jsonArray = json.getJSONArray("boxes");
			
			for (int i=0; i < jsonArray.length(); ++i) {
				JSONObject obj = jsonArray.getJSONObject(i);
				
				Box box = new Box();
				
				box.setRemoteId(obj.getInt("table_id"));
				box.setId(obj.getString("box_id"));
				box.setName(obj.getString("name"));
				box.setDescription(obj.getString("description"));
				
				boxes.add(box);
			}
		}
		catch (Exception e) {
			throw( new RESTException(e.getMessage()) );
		}
		
		return boxes.toArray(new Box[boxes.size()]);
	}
	
	public Box [] getOwnedBoxes() throws RESTException {
		final String method = "/box/owned";
		Parameter [] params = {
			new Parameter("token", getSessionToken()),
		};
		
		JSONObject json = RestClient.execute(apiUrl, method, params);
		
		List<Box> boxes = new ArrayList<Box>();
		
		try {
			
			JSONArray jsonArray = json.getJSONArray("boxes");
			
			for (int i=0; i < jsonArray.length(); ++i) {
				JSONObject obj = jsonArray.getJSONObject(i);
				
				Box box = new Box();
				
				box.setId(obj.getString("id"));
				box.setName(obj.getString("name"));
				box.setDescription(obj.getString("description"));
				
				boxes.add(box);
			}
		}
		catch (Exception e) {
			throw(new RESTException(e.getMessage()));
		}
		
		return boxes.toArray(new Box[boxes.size()]);
	}

	public String followBox(String boxId) throws RESTException {
		final String method = "/box/follow";
		Parameter [] params = {
			new Parameter("token", getSessionToken()),
			new Parameter("id", boxId.trim())
		};
		
		JSONObject json = RestClient.execute(apiUrl, method, params);
		
		try {
			return json.getString("message");
		} catch (JSONException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public String stopFollowingBox(String boxId) throws RESTException {
		final String method = "/box/unfollow";
		Parameter [] params = {
			new Parameter("token", getSessionToken()),
			new Parameter("id", "" + boxId)
		};
		
		JSONObject json = RestClient.execute(apiUrl, method, params);
		
		try {
			return json.getString("message");
		} catch (JSONException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public String deleteBox(String boxId) throws RESTException {
		final String method = "/box/delete";
		Parameter [] params = {
			new Parameter("token", getSessionToken()),
			new Parameter("id", boxId)
		};
		
		JSONObject json = RestClient.execute(apiUrl, method, params);
		
		try {
			return json.getString("message");
		} catch (JSONException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public Box [] getTodaysWODs() throws RESTException {
		Calendar cal = Calendar.getInstance();
		
		String dateString = String.format("%d-%d-%d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
		
		final String method = "/box/wods";
		Parameter [] params = {
			new Parameter("token", getSessionToken()),
			new Parameter("date",  dateString)
		};
		
		ArrayList<Box> boxSchedule = new ArrayList<Box>();
		
		JSONObject json = RestClient.execute(apiUrl, method, params);
		
		try {
			JSONArray boxes = json.getJSONArray("schedule");
			for (int i = 0; i < boxes.length(); ++i) {
				JSONObject obj = boxes.getJSONObject(i);
				
				Box box = new Box();
				
				box.setName(obj.getString("box_name"));
				box.setId(obj.getString("box_id"));
				box.setScheduledWod(obj.getString("wod_name"));
				boxSchedule.add(box);
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return boxSchedule.toArray(new Box[boxSchedule.size()]);
	}

	public String getSessionToken() {
		SharedPreferences prefs = getPreferences();
		if (prefs != null) {
			return prefs.getString("token", "");
		}
		
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		SharedPreferences prefs = getPreferences();
		if (prefs != null) {
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("token", sessionToken);
			editor.commit();
		}
		
		this.sessionToken = sessionToken.trim();
	}

	public String getUsername() {
		SharedPreferences prefs = getPreferences();
		if (prefs != null) {
			return prefs.getString("username", "");
		}
		
		return username;
	}

	public void setUsername(String username) {
		SharedPreferences prefs = getPreferences();
		if (prefs != null) {
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("username", username);
			editor.commit();
		}
		
		this.username = username;
	}

	public String getEmail() {
		SharedPreferences prefs = getPreferences();
		if (prefs != null) {
			return prefs.getString("email", "");
		}
		
		return email;
	}

	public void setEmail(String email) {
		SharedPreferences prefs = getPreferences();
		if (prefs != null) {
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("email", email);
			editor.commit();
		}
		
		this.email = email;
	}

	public String getDescription() {
		SharedPreferences prefs = getPreferences();
		if (prefs != null) {
			return prefs.getString("description", "");
		}

		return description;
	}

	public void setDescription(String description) {
		SharedPreferences prefs = getPreferences();
		if (prefs != null) {
			SharedPreferences.Editor editor = prefs.edit();
			editor.putString("description", description);
			editor.commit();
		}

		this.description = description;
	}
}
