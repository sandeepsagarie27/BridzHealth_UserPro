package com.BridzHealth;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class BookingConformedActivity extends AppCompatActivity {
	Calendar dateCalendar = Calendar.getInstance();
	Calendar timeCalendar = Calendar.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setTitle("");
		actionBar.setIcon(R.drawable.logo_white);
		dateCalendar.setTimeInMillis(getIntent().getLongExtra("date", System.currentTimeMillis()));
		timeCalendar.setTimeInMillis(getIntent().getLongExtra("time", System.currentTimeMillis()));
		setContentView(R.layout.activity_booking_conformed);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
		TextView textView = (TextView) findViewById(R.id.conformation_txt);
		textView.setText(
				"We have forwarded your booking for an appointment on\n" + dateFormat.format(dateCalendar.getTime())
						+ " at " + timeFormat.format(timeCalendar.getTime()) + "\nwith");
		textView = (TextView) findViewById(R.id.doctor_txt);
		textView.setText(DoctorSearchResultsActivity.selectedDoctor.getDoctor_name() + ", "
				+ DoctorSearchResultsActivity.selectedDoctor.getClinic_name());
		setResult(1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.booking_conformed, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
