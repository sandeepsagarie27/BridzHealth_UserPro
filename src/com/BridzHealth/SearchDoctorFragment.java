package com.BridzHealth;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SearchDoctorFragment extends Fragment {
	Calendar calendar;
	int tab_selection = -1;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.activity_home, container, false);
		return layout;
	}

	@Override
	public void onStart() {
		super.onStart();
		calendar = Calendar.getInstance();
		getActivity().findViewById(R.id.dental_layout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tab_selection = 0;
				setTabSelection();
			}
		});
		getActivity().findViewById(R.id.dermotologist_layout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tab_selection = 1;
				setTabSelection();
			}
		});
		getActivity().findViewById(R.id.psychiatrist_layout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tab_selection = 2;
				setTabSelection();
			}
		});
		getActivity().findViewById(R.id.opthalmology_layout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				tab_selection = 3;
				setTabSelection();
			}
		});
		getActivity().findViewById(R.id.date_layout).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				selectDate();
			}
		});
		getActivity().findViewById(R.id.find_btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (tab_selection == -1) {
					Toast.makeText(getActivity(), "Select the Category to search", Toast.LENGTH_LONG).show();
					return;
				}
				Intent intent = new Intent(getActivity(), DoctorSearchResultsActivity.class);
				intent.putExtra("type", tab_selection);
				intent.putExtra("date", calendar.getTimeInMillis());
				startActivityForResult(intent,101);
			}
		});

		// init
		setDate();
		setTabSelection();

	}

	private void selectDate() {

		DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker arg0, int year, int month, int day) {
				Calendar todayCalender = Calendar.getInstance();
				Calendar tempCalender = Calendar.getInstance();
				tempCalender.set(Calendar.YEAR, year);
				tempCalender.set(Calendar.MONTH, month);
				tempCalender.set(Calendar.DAY_OF_MONTH, day);
				if (todayCalender.getTimeInMillis() > tempCalender.getTimeInMillis()) {
					Toast.makeText(getActivity(), "Selected date is not valid", Toast.LENGTH_LONG).show();
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
		TextView textView = (TextView) getActivity().findViewById(R.id.date_txt);
		textView.setText(dateFormat.format(calendar.getTime()));
	}

	private void setTabSelection() {
		// Dental
		RelativeLayout layout = (RelativeLayout) getActivity().findViewById(R.id.dental_layout);
		layout.setBackgroundColor(getResources().getColor(R.color.white));
		ImageView imageView = (ImageView) getActivity().findViewById(R.id.dental_img);
		imageView.setImageResource(R.drawable.dental_red);
		TextView textView = (TextView) getActivity().findViewById(R.id.dental_txt);
		textView.setTextColor(getResources().getColor(R.color.black));
		if (tab_selection == 0) {
			layout.setBackgroundColor(getResources().getColor(R.color.title_color));
			imageView.setImageResource(R.drawable.dental_white);
			textView.setTextColor(getResources().getColor(R.color.white));
		}
		// DERMOTOLOGIST
		layout = (RelativeLayout) getActivity().findViewById(R.id.dermotologist_layout);
		layout.setBackgroundColor(getResources().getColor(R.color.white));
		imageView = (ImageView) getActivity().findViewById(R.id.dermotologist_img);
		imageView.setImageResource(R.drawable.dermotologist_red);
		textView = (TextView) getActivity().findViewById(R.id.dermotologist_txt);
		textView.setTextColor(getResources().getColor(R.color.black));
		if (tab_selection == 1) {
			layout.setBackgroundColor(getResources().getColor(R.color.title_color));
			imageView.setImageResource(R.drawable.dermotologist_white);
			textView.setTextColor(getResources().getColor(R.color.white));
		}
		// PSYCHIATRIST
		layout = (RelativeLayout) getActivity().findViewById(R.id.psychiatrist_layout);
		layout.setBackgroundColor(getResources().getColor(R.color.white));
		imageView = (ImageView) getActivity().findViewById(R.id.psychiatrist_img);
		imageView.setImageResource(R.drawable.psycriatist_red);
		textView = (TextView) getActivity().findViewById(R.id.psychiatrist_txt);
		textView.setTextColor(getResources().getColor(R.color.black));
		if (tab_selection == 2) {
			layout.setBackgroundColor(getResources().getColor(R.color.title_color));
			imageView.setImageResource(R.drawable.psycriatist_white);
			textView.setTextColor(getResources().getColor(R.color.white));
		}
		// OPTHOLMOLOGY
		layout = (RelativeLayout) getActivity().findViewById(R.id.opthalmology_layout);
		layout.setBackgroundColor(getResources().getColor(R.color.white));
		imageView = (ImageView) getActivity().findViewById(R.id.opthalmology_img);
		imageView.setImageResource(R.drawable.opthomology_red);
		textView = (TextView) getActivity().findViewById(R.id.opthalmology_txt);
		textView.setTextColor(getResources().getColor(R.color.black));
		if (tab_selection == 3) {
			layout.setBackgroundColor(getResources().getColor(R.color.title_color));
			imageView.setImageResource(R.drawable.opthomology_white);
			textView.setTextColor(getResources().getColor(R.color.white));
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 101 && resultCode == 1) {
			MainActivity activity=(MainActivity) getActivity();
			activity.onNavigationDrawerItemSelected(0);
		}
	}
}
