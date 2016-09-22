package com.BridzHealth;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
	Calendar calendar;
	int tab_selection = -1;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar=getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setTitle("");
		actionBar.setIcon(R.drawable.logo_white);
		setContentView(R.layout.activity_home);
		calendar = Calendar.getInstance();
		findViewById(R.id.dental_layout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tab_selection = 0;
				setTabSelection();
			}
		});
		findViewById(R.id.dermotologist_layout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tab_selection = 1;
				setTabSelection();
			}
		});
		findViewById(R.id.psychiatrist_layout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tab_selection = 2;
				setTabSelection();
			}
		});
		findViewById(R.id.opthalmology_layout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tab_selection = 3;
				setTabSelection();
			}
		});
		findViewById(R.id.date_layout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				selectDate();
			}
		});
		findViewById(R.id.find_btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (tab_selection == -1) {
					Toast.makeText(HomeActivity.this, "Select the Category to search", Toast.LENGTH_LONG).show();
					return;
				}
				Intent intent = new Intent(HomeActivity.this, DoctorSearchResultsActivity.class);
				intent.putExtra("type", tab_selection);
				intent.putExtra("date", calendar.getTimeInMillis());
				startActivity(intent);
			}
		});

		// init
		setDate();
		setTabSelection();
	}

	private void selectDate() {

		DatePickerDialog datePickerDialog = new DatePickerDialog(HomeActivity.this, new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker arg0, int year, int month, int day) {
				Calendar todayCalender = Calendar.getInstance();
				Calendar tempCalender = Calendar.getInstance();
				tempCalender.set(Calendar.YEAR, year);
				tempCalender.set(Calendar.MONTH, month);
				tempCalender.set(Calendar.DAY_OF_MONTH, day);
				if (todayCalender.getTimeInMillis() > tempCalender.getTimeInMillis()) {
					Toast.makeText(HomeActivity.this, "Selected date is not valid", Toast.LENGTH_LONG).show();
					selectDate();
					return;
				}
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, month);
				calendar.set(Calendar.DAY_OF_MONTH, day);
				setDate();
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		datePickerDialog.show();

	}

	public void setDate() {
		TextView textView = (TextView) findViewById(R.id.date_txt);
		textView.setText(dateFormat.format(calendar.getTime()));
	}

	private void setTabSelection() {
		// Dental
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.dental_layout);
		layout.setBackgroundColor(getResources().getColor(R.color.white));
		ImageView imageView = (ImageView) findViewById(R.id.dental_img);
		imageView.setImageResource(R.drawable.dental_red);
		TextView textView = (TextView) findViewById(R.id.dental_txt);
		textView.setTextColor(getResources().getColor(R.color.black));
		if (tab_selection == 0) {
			layout.setBackgroundColor(getResources().getColor(R.color.title_color));
			imageView.setImageResource(R.drawable.dental_white);
			textView.setTextColor(getResources().getColor(R.color.white));
		}
		// DERMOTOLOGIST
		layout = (RelativeLayout) findViewById(R.id.dermotologist_layout);
		layout.setBackgroundColor(getResources().getColor(R.color.white));
		imageView = (ImageView) findViewById(R.id.dermotologist_img);
		imageView.setImageResource(R.drawable.dermotologist_red);
		textView = (TextView) findViewById(R.id.dermotologist_txt);
		textView.setTextColor(getResources().getColor(R.color.black));
		if (tab_selection == 1) {
			layout.setBackgroundColor(getResources().getColor(R.color.title_color));
			imageView.setImageResource(R.drawable.dermotologist_white);
			textView.setTextColor(getResources().getColor(R.color.white));
		}
		// PSYCHIATRIST
		layout = (RelativeLayout) findViewById(R.id.psychiatrist_layout);
		layout.setBackgroundColor(getResources().getColor(R.color.white));
		imageView = (ImageView) findViewById(R.id.psychiatrist_img);
		imageView.setImageResource(R.drawable.psycriatist_red);
		textView = (TextView) findViewById(R.id.psychiatrist_txt);
		textView.setTextColor(getResources().getColor(R.color.black));
		if (tab_selection == 2) {
			layout.setBackgroundColor(getResources().getColor(R.color.title_color));
			imageView.setImageResource(R.drawable.psycriatist_white);
			textView.setTextColor(getResources().getColor(R.color.white));
		}
		// OPTHOLMOLOGY
		layout = (RelativeLayout) findViewById(R.id.opthalmology_layout);
		layout.setBackgroundColor(getResources().getColor(R.color.white));
		imageView = (ImageView) findViewById(R.id.opthalmology_img);
		imageView.setImageResource(R.drawable.opthomology_red);
		textView = (TextView) findViewById(R.id.opthalmology_txt);
		textView.setTextColor(getResources().getColor(R.color.black));
		if (tab_selection == 3) {
			layout.setBackgroundColor(getResources().getColor(R.color.title_color));
			imageView.setImageResource(R.drawable.opthomology_white);
			textView.setTextColor(getResources().getColor(R.color.white));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
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
