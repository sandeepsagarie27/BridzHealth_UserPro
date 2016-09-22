package com.BridzHealth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class FlashScreen extends AppCompatActivity {

	private SharedPreferences preferences;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flashscreen);
		getSupportActionBar().hide();

		preferences = getSharedPreferences("meditrix", MODE_PRIVATE);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				if (preferences != null && preferences.getBoolean("isFirstTime", true)) {
					Intent mainIntent = new Intent(FlashScreen.this, WelcomeActivity.class);
					startActivity(mainIntent);
					finish();
					return;

				}
				if (preferences != null && preferences.contains("user") && preferences.contains("pass")) {
					new LoginAsyncTask(preferences.getString("user", ""), preferences.getString("pass", "")).execute();
				} else {
					Intent mainIntent = new Intent(FlashScreen.this, LoginActivity.class);
					startActivity(mainIntent);
					finish();
				}

			}
		}, 2000);

	}

	class LoginAsyncTask extends AsyncTask<Void, Void, Object> {
		String user, password;

		/**
		 * 
		 */
		public LoginAsyncTask(String user, String password) {
			this.user = user;
			this.password = password;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(java.lang.Object[])
		 */
		@Override
		protected Object doInBackground(Void... params) {
			return ResponseParser.getResponseParser().loginUser(user, password);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			if (result == null) {
				Toast.makeText(FlashScreen.this, "Invalid Details!", Toast.LENGTH_LONG).show();
				Intent mainIntent = new Intent(FlashScreen.this, LoginActivity.class);
				startActivity(mainIntent);
				finish();
				return;
			} else {

				Intent mainIntent = new Intent(FlashScreen.this, MainActivity.class);
				startActivity(mainIntent);
				finish();
			}

		}
	}
}
