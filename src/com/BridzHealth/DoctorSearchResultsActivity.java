package com.BridzHealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DoctorSearchResultsActivity extends AppCompatActivity {
	Calendar dateCalendar = Calendar.getInstance();
	private DoctorsListAdapter adapter;
	public static Doctor selectedDoctor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setTitle("");
		actionBar.setIcon(R.drawable.logo_white);
		dateCalendar.setTimeInMillis(getIntent().getLongExtra("date", System.currentTimeMillis()));
		setContentView(R.layout.activity_doctor_search_results);
		//
		RecyclerView listView = (RecyclerView) findViewById(R.id.doctors_list);
		listView.setHasFixedSize(true);
		// use a linear layout manager
		LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
		listView.setLayoutManager(mLayoutManager);
		adapter = new DoctorsListAdapter();
		listView.setAdapter(adapter);
		findViewById(R.id.prev_btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dateCalendar.add(Calendar.DAY_OF_MONTH, -1);
				setDoctorsLayout();

			}
		});
		findViewById(R.id.next_btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dateCalendar.add(Calendar.DAY_OF_MONTH, 1);
				setDoctorsLayout();

			}
		});
		setDoctorsLayout();
	}

	@Override
	protected void onResume() {
		selectedDoctor = null;
		super.onResume();
	}

	private void setDoctorsLayout() {
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

		ArrayList<Doctor> arrayList = new ArrayList<Doctor>();
		Doctor doctor1 = new Doctor();
		doctor1.setDoctor_name("Madhu");
		doctor1.setOverall_experience("16 years");
		doctor1.setClinic_name("Ocimum Clinics");
		doctor1.setSpecialty("Dental");
		doctor1.setEducation("Periodontics");
		doctor1.setAddress("12th Cross, 1st Main road, Malleswaram, Bengaluru, Karnataka 560003");
		arrayList.add(doctor1);
		Doctor doctor2 = new Doctor();
		doctor2.setDoctor_name("Chakradar Raju");
		doctor2.setOverall_experience("16 years");
		doctor2.setClinic_name("Bright smiles");
		doctor2.setSpecialty("Dental");
		doctor2.setEducation("Orthodontist");
		doctor2.setAddress(
				"New BEL Rd, Amarjyothi Layout, Raj Mahal Vilas 2nd Stage, Sanjaynagar, Bengaluru, Karnataka 560094");
		arrayList.add(doctor2);
		adapter.setList(arrayList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.doctor_search_results, menu);
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

	class DoctorsListAdapter extends Adapter<DoctorsListAdapter.ViewHolder> {
		ArrayList<Doctor> list = new ArrayList<Doctor>();

		public class ViewHolder extends RecyclerView.ViewHolder {
			// each data item is just a string in this case
			public View v;
			public ImageView photo_img, star_1, star_2, star_3, star_4, star_5;
			public TextView doctor_name_txt, specialization_txt, hospital_name_txt, slot_time_txt, address_txt,
					next_available_txt;
			public LinearLayout star_layout, time_slots_ll_layout;
			public HorizontalScrollView time_slots_hsv_layout;

			public ViewHolder(View v) {
				super(v);
				this.v = v;
			}
		}

		public void setList(ArrayList<Doctor> list) {
			this.list = list;
			notifyDataSetChanged();
		}

		@Override
		public int getItemCount() {
			return list.size();
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, int pos) {
			final Doctor doctor = list.get(pos);
			holder.next_available_txt.setVisibility(View.GONE);
			holder.time_slots_hsv_layout.setVisibility(View.GONE);
			int numberOfSlots = 10;
			long startTime = System.currentTimeMillis();
			long endTime = (long) (startTime + (3 * 60 * 60 * 1000));
			long slotTime = (endTime - startTime) / numberOfSlots;
			holder.slot_time_txt.setText((slotTime / (60 * 1000)) + " mins");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(startTime);

			holder.time_slots_hsv_layout.setVisibility(View.VISIBLE);
			holder.time_slots_ll_layout.removeAllViews();

			SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
			for (int i = 0; i < numberOfSlots; i++) {
				View view = getLayoutInflater().inflate(R.layout.time_slot_text_view, null);
				final long time = startTime + (slotTime * i);
				calendar.setTimeInMillis(time);
				TextView textView = (TextView) view.findViewById(R.id.time_txt);
				textView.setText(dateFormat.format(calendar.getTime()));
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						selectedDoctor = doctor;
						Intent intent = new Intent(DoctorSearchResultsActivity.this, ConformBookingActivity.class);
						intent.putExtra("date", dateCalendar.getTimeInMillis());
						intent.putExtra("time", time);
						startActivityForResult(intent, 101);
					}
				});
				holder.time_slots_ll_layout.addView(view);

			}
			holder.doctor_name_txt.setText(doctor.getDoctor_name());
			holder.specialization_txt.setText(doctor.getEducation());
			holder.hospital_name_txt.setText(doctor.getClinic_name());
			holder.photo_img.setImageResource(R.drawable.doctor_icon);
			holder.address_txt.setText(doctor.getAddress());
			holder.v.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					selectedDoctor = doctor;
					Intent intent = new Intent(DoctorSearchResultsActivity.this, DoctorDetailsActivity.class);
					startActivityForResult(intent, 101);

				}
			});
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, final int pos) {
			// create a new view
			LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.doctors_list_item,
					parent, false);

			// set the view's size, margins, paddings and layout parameters
			ViewHolder vh = new ViewHolder(v);
			vh.v = v;
			vh.photo_img = (ImageView) v.findViewById(R.id.photo_img);
			vh.star_1 = (ImageView) v.findViewById(R.id.star_1);
			vh.star_2 = (ImageView) v.findViewById(R.id.star_2);
			vh.star_3 = (ImageView) v.findViewById(R.id.star_3);
			vh.star_4 = (ImageView) v.findViewById(R.id.star_4);
			vh.star_5 = (ImageView) v.findViewById(R.id.star_5);
			vh.doctor_name_txt = (TextView) v.findViewById(R.id.doctor_name_txt);
			vh.specialization_txt = (TextView) v.findViewById(R.id.specialization_txt);
			vh.hospital_name_txt = (TextView) v.findViewById(R.id.hospital_name_txt);
			vh.slot_time_txt = (TextView) v.findViewById(R.id.slot_time_txt);
			vh.address_txt = (TextView) v.findViewById(R.id.address_txt);
			vh.next_available_txt = (TextView) v.findViewById(R.id.next_available_txt);
			vh.star_layout = (LinearLayout) v.findViewById(R.id.star_layout);
			vh.time_slots_ll_layout = (LinearLayout) v.findViewById(R.id.time_slots_ll_layout);
			vh.time_slots_hsv_layout = (HorizontalScrollView) v.findViewById(R.id.time_slots_hsv_layout);
			return vh;
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
