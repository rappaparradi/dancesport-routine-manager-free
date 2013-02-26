/*
 * Copyright (C) 2010 The Android Open Source Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.rappasocial.routinemanager.free.client;

import com.rappasocial.webservices.DrupalJSONServerNetworkUtilityBase;

import android.accounts.Account;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

import com.rappasocial.routinemanager.authenticator.free.AuthenticatorActivity;
import com.rappasocial.routinemanager.free.DBHelper;
import com.rappasocial.routinemanager.free.ExtendedApplication;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Provides utility methods for communicating with the server.
 */
public class NetworkUtilities extends DrupalJSONServerNetworkUtilityBase {

	public static final String PARAM_UPDATED = "timestamp";
	public static final String FETCH_FRIEND_UPDATES_URI = BASE_URL
			+ "/fetch_friend_updates";
	public static final String FETCH_STATUS_URI = BASE_URL + "/fetch_status";
	public static final String POST_FIGURES_URI = BASE_URL
			+ "/figures/post.json";
	static ExtendedApplication extApp;

	/**
	 * Attempts to authenticate the user credentials on a service.
	 * 
	 * @param username
	 *            The user's username
	 * @param password
	 *            The user's password to be authenticated
	 * @param handler
	 *            The main UI thread's handler instance.
	 * @param context
	 *            The caller Activity's context
	 * 
	 * @return Thread The thread on which the network mOperations are executed.
	 */
	public static Thread attemptAuth(final String username,
			final String password, final Handler handler, final Context context) {
		final Runnable runnable = new Runnable() {
			public void run() {
				boolean result = authenticate(username, password, handler,
						context);
				sendResult(result, handler, context);
			}
		};
		// run on background thread.
		return DrupalJSONServerNetworkUtilityBase.performOnBackgroundThread(runnable);
	}

	/**
	 * Fetches the list of friend data updates from the server
	 * 
	 * @param account
	 *            The account being synced.
	 * @param authtoken
	 *            The authtoken stored in AccountManager for this account
	 * @param lastUpdated
	 *            The last time that sync was performed
	 * @return list The list of updates received from the server.
	 */
	public static List<User> fetchFriendUpdates(Account account,
			String authtoken, Date lastUpdated) throws JSONException,
			ParseException, IOException, AuthenticationException {
		final ArrayList<User> friendList = new ArrayList<User>();
		final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(PARAM_USERNAME, account.name));
		params.add(new BasicNameValuePair(PARAM_PASSWORD, authtoken));
		if (lastUpdated != null) {
			final SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm");
			formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			params.add(new BasicNameValuePair(PARAM_UPDATED, formatter
					.format(lastUpdated)));
		}
		Log.i(TAG, params.toString());

		HttpEntity entity = null;
		entity = new UrlEncodedFormEntity(params);
		final HttpPost post = new HttpPost(FETCH_FRIEND_UPDATES_URI);
		post.addHeader(entity.getContentType());
		post.setEntity(entity);
		maybeCreateHttpClient();

		final HttpResponse resp = mHttpClient.execute(post);
		final String response = EntityUtils.toString(resp.getEntity());

		if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// Succesfully connected to the samplesyncadapter server and
			// authenticated.
			// Extract friends data in json format.
			final JSONArray friends = new JSONArray(response);
			Log.d(TAG, response);
			for (int i = 0; i < friends.length(); i++) {
				friendList.add(User.valueOf(friends.getJSONObject(i)));
			}
		} else {
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
				Log.e(TAG,
						"Authentication exception in fetching remote contacts");
				throw new AuthenticationException();
			} else {
				Log.e(TAG,
						"Server error in fetching remote contacts: "
								+ resp.getStatusLine());
				throw new IOException();
			}
		}
		return friendList;  
	}

	public static boolean post_figures(Account account, String authtoken,
			Date lastUpdated, Context context) throws JSONException,
			ParseException, IOException, AuthenticationException {
		extApp = (ExtendedApplication) context.getApplicationContext();

		JSONObject allFiguresToSend = prepareJsonAllFigures();
//
//		JSONObject jsonsub = new JSONObject();
//		jsonsub.put("uid", "");
//		jsonsub.put("name", "botafogo");
//		jsonsub.put("description", "");
//		jsonsub.put("dance_id", "1");
//		JSONArray jsonArr = new JSONArray();
//		jsonArr.put(jsonsub);
//		JSONObject json = new JSONObject();
//		json.put("fig_array", jsonArr);

//		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

//		params.add(new BasicNameValuePair(PARAM_USERNAME, account.name));
//		params.add(new BasicNameValuePair(PARAM_PASSWORD, authtoken));
//		params.add(new BasicNameValuePair("fig_array", json.toString()));
		JSONObject json_result = prepareAndSendHttpDSRMPost(POST_FIGURES_URI,
				authtoken, allFiguresToSend.toString());

		if (json_result == null) {
			// if (is406) {
			//
			// Log.d(TAG, "already logged in");
			// is406 = false;
			// return true;
			//
			// } else {

			Log.d(TAG, "post figure");
			return false;

			// }

		} else {
			Log.d(TAG, "successful post figure");
			return true;
		}
	}

	/**
	 * Fetches status messages for the user's friends from the server
	 * 
	 * @param account
	 *            The account being synced.
	 * @param authtoken
	 *            The authtoken stored in the AccountManager for the account
	 * @return list The list of status messages received from the server.
	 */
	public static List<User.Status> fetchFriendStatuses(Account account,
			String authtoken) throws JSONException, ParseException,
			IOException, AuthenticationException {

		final ArrayList<User.Status> statusList = new ArrayList<User.Status>();
		final ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(PARAM_USERNAME, account.name));
		params.add(new BasicNameValuePair(PARAM_PASSWORD, authtoken));

		HttpEntity entity = null;
		entity = new UrlEncodedFormEntity(params);
		final HttpPost post = new HttpPost(FETCH_STATUS_URI);
		post.addHeader(entity.getContentType());
		post.setEntity(entity);
		maybeCreateHttpClient();

		final HttpResponse resp = mHttpClient.execute(post);
		final String response = EntityUtils.toString(resp.getEntity());

		if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// Extract friends data in json format.
			final JSONArray statuses = new JSONArray(response);
			for (int i = 0; i < statuses.length(); i++) {
				statusList.add(User.Status.valueOf(statuses.getJSONObject(i)));
			}
		} else {
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_UNAUTHORIZED) {
				Log.e(TAG,
						"Authentication exception in fetching friend status list");
				throw new AuthenticationException();
			} else {
				Log.e(TAG, "Server error in fetching friend status list");
				throw new IOException();
			}
		}
		return statusList;
	}

	/**
	 * Sends the authentication response from server back to the caller main UI
	 * thread through its handler.
	 * 
	 * @param result
	 *            The boolean holding authentication result
	 * @param handler
	 *            The main UI thread's handler instance.
	 * @param context
	 *            The caller Activity's context.
	 */
	protected static void sendResult(final Boolean result,
			final Handler handler, final Context context) {
		if (handler == null || context == null) {
			return;
		}
		handler.post(new Runnable() {
			public void run() {
				((AuthenticatorActivity) context)
						.onAuthenticationResult(result);
			}
		});
	}

	public static JSONObject prepareJsonAllFigures() {

		
		JSONArray jsonArr = new JSONArray();

		String selection = null; 

		Cursor c = null;

		c = extApp.db.query(DBHelper.DB_TABLE_FIGURES, null, selection,
				null, null, null, null);

		try {

			if (c != null) {
				if (c.moveToFirst()) {

					do {
						
						JSONObject jsonsingleFigObject = new JSONObject();

						jsonsingleFigObject
								.put("_id",
										c.getInt(c
												.getColumnIndex(DBHelper.COLUMN_FIGURES_ID)));
						jsonsingleFigObject
								.put("_id_global",
										c.getInt(c
												.getColumnIndex(DBHelper.COLUMN_FIGURES_ID_GLOBAL)));
						jsonsingleFigObject
								.put("name",
										c.getString(c
												.getColumnIndex(DBHelper.COLUMN_FIGURES_NAME)));
						jsonsingleFigObject
								.put("dance_id",
										c.getInt(c
												.getColumnIndex(DBHelper.COLUMN_FIGURES_DANCE_ID)));
						jsonsingleFigObject
								.put("yt_id",
										c.getString(c
												.getColumnIndex(DBHelper.COLUMN_FIGURES_YT_ID)));
						jsonsingleFigObject
								.put("created_on",
										c.getLong(c
												.getColumnIndex(DBHelper.COLUMN_FIGURES_CREATED_ON)));
						jsonsingleFigObject
								.put("modified_on",
										c.getLong(c
												.getColumnIndex(DBHelper.COLUMN_FIGURES_MODIFIED_ON)));
						jsonsingleFigObject
								.put("del_mark",
										c.getInt(c
												.getColumnIndex(DBHelper.COLUMN_FIGURES_DEL_MARK)));

						jsonArr.put(jsonsingleFigObject);

					} while (c.moveToNext());
				}
				c.close();
			}

			JSONObject jsonOut = new JSONObject();
			jsonOut.put("fig_array", jsonArr);

		

		return jsonOut;
		
		} catch (JSONException e) {
			Log.e(TAG, "unable to encode JSON for http request");
			return null;
		}
		
	}
}
