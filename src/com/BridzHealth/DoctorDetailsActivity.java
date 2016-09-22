package com.BridzHealth;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DoctorDetailsActivity extends ActionBarActivity {
	Calendar dateCalendar = Calendar.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setTitle("");
		actionBar.setIcon(R.drawable.logo_white);
		setContentView(R.layout.activity_doctor_details);

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
		findViewById(R.id.prev_btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dateCalendar.add(Calendar.DAY_OF_MONTH, -1);
				setDateLayout();

			}
		});
		findViewById(R.id.next_btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dateCalendar.add(Calendar.DAY_OF_MONTH, 1);
				setDateLayout();

			}
		});
		setDateLayout();
	}

	protected void setDateLayout() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
		SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd MMM");
		Calendar tempCalendar = Calendar.getInstance();
		TextView textView = (TextView) findViewById(R.id.date_txt);
		textView.setText(dateFormat.format(dateCalendar.getTime()));
		findViewById(R.id.prev_btn).setVisibility(View.VISIBLE);
		if (dateFormat.format(dateCalendar.getTime()).equals(dateFormat.format(tempCalendar.getTime()))) {
			textView.setText("Today, " + shortDateFormat.format(dateCalendar.getTime()));
			findViewById(R.id.prev_btn).setVisibility(View.INVISIBLE);
		}
		tempCalendar.add(Calendar.DAY_OF_MONTH, 1);
		if (dateFormat.format(dateCalendar.getTime()).equals(dateFormat.format(tempCalendar.getTime()))) {
			textView.setText("Tomorrow, " + shortDateFormat.format(dateCalendar.getTime()));
		}
		//
		LinearLayout time_slots_ll_layout = (LinearLayout) findViewById(R.id.time_slots_ll_layout);
		int numberOfSlots = 10;
		long startTime = System.currentTimeMillis();
		long endTime = (long) (startTime + (3 * 60 * 60 * 1000));
		long slotTime = (endTime - startTime) / numberOfSlots;
		time_slots_ll_layout.removeAllViews();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(startTime);
		SimpleDateFormat slotDateFormat = new SimpleDateFormat("hh:mm a");
		for (int i = 0; i < numberOfSlots; i++) {
			View view = getLayoutInflater().inflate(R.layout.time_slot_text_view, null);
			final long time = startTime + (slotTime * i);
			calendar.setTimeInMillis(time);
			textView = (TextView) view.findViewById(R.id.time_txt);
			textView.setText(slotDateFormat.format(calendar.getTime()));
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(DoctorDetailsActivity.this, ConformBookingActivity.class);
					intent.putExtra("date", dateCalendar.getTimeInMillis());
					intent.putExtra("time", time);
					startActivityForResult(intent, 101);
				}
			});
			time_slots_ll_layout.addView(view);

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 101 && resultCode == 1) {
			setResult(1);
			finish();
		}
	}
}
