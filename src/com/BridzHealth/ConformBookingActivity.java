package com.BridzHealth;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ConformBookingActivity extends AppCompatActivity {
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
		setContentView(R.layout.activity_conform_booking);
		if (DoctorSearchResultsActivity.selectedDoctor != null) {
			TextView textView = (TextView) findViewById(R.id.doctor_name_txt);
			textView.setText(DoctorSearchResultsActivity.selectedDoctor.getDoctor_name());
			textView = (TextView) findViewById(R.id.specialization_txt);
			textView.setText(DoctorSearchResultsActivity.selectedDoctor.getEducation());
			textView = (TextView) findViewById(R.id.hospital_name_txt);
			textView.setText(DoctorSearchResultsActivity.selectedDoctor.getClinic_name());
			textView = (TextView) findViewById(R.id.address_txt);
			textView.setText(DoctorSearchResultsActivity.selectedDoctor.getAddress());
		}
		findViewById(R.id.conform_btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ConformBookingActivity.this, BookingConformedActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("date", dateCalendar.getTimeInMillis());
				intent.putExtra("time", timeCalendar.getTimeInMillis());
				startActivityForResult(intent, 101);

			}
		});
		setDateText();
	}

	private void setDateText() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd MMM");
		Calendar tempCalendar = Calendar.getInstance();
		TextView textView = (TextView) findViewById(R.id.date_txt);
		textView.setText(dateFormat.format(dateCalendar.getTime()) + " - " + timeFormat.format(timeCalendar.getTime())
				+ "\nwith");
		if (dateFormat.format(dateCalendar.getTime()).equals(dateFormat.format(tempCalendar.getTime()))) {
			textView.setText("Today, " + shortDateFormat.format(dateCalendar.getTime()) + " - "
					+ timeFormat.format(timeCalendar.getTime()) + "\nwith");
		}
		tempCalendar.add(Calendar.DAY_OF_MONTH, 1);
		if (dateFormat.format(dateCalendar.getTime()).equals(dateFormat.format(tempCalendar.getTime()))) {
			textView.setText("Tomorrow, " + shortDateFormat.format(dateCalendar.getTime()) + " - "
					+ timeFormat.format(timeCalendar.getTime()) + "\nwith");
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.conform_booking, menu);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 101 && resultCode == 1) {
			setResult(1);
			finish();
		}
	}
}
