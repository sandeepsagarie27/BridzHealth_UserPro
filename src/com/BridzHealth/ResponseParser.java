/**
 * 
 */
package com.BridzHealth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

/**
 * @author Sandeep Sagar Kottamaddi
 * 
 */
public class ResponseParser {
	private static ResponseParser xmlResponseParser;
	private String IMAGE_URL = "http://localites.deifytechcenter.com/images/";

	public static ResponseParser getResponseParser() {
		if (xmlResponseParser == null)
			xmlResponseParser = new ResponseParser();
		return xmlResponseParser;
	}

	private InputStream getStream(String paramString) {
		try {
			HttpURLConnection localHttpURLConnection = (HttpURLConnection) new URL(paramString).openConnection();
			localHttpURLConnection.setRequestMethod("GET");
			localHttpURLConnection.setDoInput(true);
			localHttpURLConnection.connect();
			InputStream localInputStream = localHttpURLConnection.getInputStream();
			return localInputStream;
		} catch (Exception localException) {
			Log.e("Error :", localException.getMessage());
		}
		return null;
	}

	// convert InputStream to String
	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	public Object loginUser(String email, String password) {
		String str1 = "http://deepclinics.org/portal/secure/wsencore.php?case=login&username=" + email + "&password="
				+ password;
		// String str1 =
		// "http://deepclinics.org/portal/secure/wsencore.php?case=login&username="
		// + "42085" + "&password="
		// + "Success";
		Log.e("URL :", str1);

		InputStream localInputStream = getStream(str1);
		try {
			JSONObject reader = new JSONObject(getStringFromInputStream(localInputStream));
			if (reader != null && reader.getInt("result") == 1) {
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

}
