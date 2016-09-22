package com.BridzHealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {
	private ViewPager viewPager;
	private MyViewPagerAdapter myViewPagerAdapter;
	private LinearLayout dotsLayout;
	private TextView[] dots;
	private Button btnSkip, btnNext;
	private SharedPreferences preferences;

	private String[] slideTitles = { "Slide 1", "Slide 2", "Slide 3", "Slide 4" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		// Checking for first time launch - before calling setContentView()
		preferences = getSharedPreferences("meditrix", MODE_PRIVATE);

		setContentView(R.layout.activity_welcome);

		viewPager = (ViewPager) findViewById(R.id.view_pager);
		dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
		btnSkip = (Button) findViewById(R.id.btn_skip);
		btnNext = (Button) findViewById(R.id.btn_next);

		// adding bottom dots
		addBottomDots(0);

		myViewPagerAdapter = new MyViewPagerAdapter();
		viewPager.setAdapter(myViewPagerAdapter);
		viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

		btnSkip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				launchHomeScreen();
			}
		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// checking for last page
				// if last page home screen will be launched
				int current = getItem(+1);
				if (current < slideTitles.length) {
					// move to next screen
					viewPager.setCurrentItem(current);
				} else {
					launchHomeScreen();
				}
			}
		});
	}

	private void addBottomDots(int currentPage) {
		dots = new TextView[slideTitles.length];

		dotsLayout.removeAllViews();
		for (int i = 0; i < dots.length; i++) {
			dots[i] = new TextView(this);
			dots[i].setText(Html.fromHtml("&#8226;"));
			dots[i].setTextSize(35);
			dots[i].setTextColor(getResources().getColor(R.color.gray));
			dotsLayout.addView(dots[i]);
		}

		if (dots.length > 0)
			dots[currentPage].setTextColor(getResources().getColor(R.color.title_color));
	}

	private int getItem(int i) {
		return viewPager.getCurrentItem() + i;
	}

	private void launchHomeScreen() {

		preferences.edit().putBoolean("isFirstTime", false).commit();
		if (preferences != null && preferences.contains("user") && preferences.contains("pass")) {
			new LoginAsyncTask(preferences.getString("user", ""), preferences.getString("pass", "")).execute();
		} else {
			Intent mainIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
			startActivity(mainIntent);
			finish();
		}
	}

	// viewpager change listener
	ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			addBottomDots(position);

			// changing the next button text 'NEXT' / 'GOT IT'
			if (position == slideTitles.length - 1) {
				// last page. make button text to GOT IT
				btnNext.setText(getString(R.string.start));
				btnSkip.setVisibility(View.GONE);
			} else {
				// still pages are left
				btnNext.setText(getString(R.string.next));
				btnSkip.setVisibility(View.VISIBLE);
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	};

	/**
	 * View pager adapter
	 */
	public class MyViewPagerAdapter extends PagerAdapter {
		private LayoutInflater layoutInflater;

		public MyViewPagerAdapter() {
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layoutInflater.inflate(R.layout.welcome_slide, container, false);
			ImageView imageView = (ImageView) view.findViewById(R.id.slide_img);
			imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
					(int) ((getWindowManager().getDefaultDisplay().getHeight() / 2) * 1.2)));
			imageView.setScaleType(ScaleType.FIT_CENTER);
			TextView textView = (TextView) view.findViewById(R.id.slide_title);
			textView.setText(slideTitles[position]);
			container.addView(view);

			return view;
		}

		@Override
		public int getCount() {
			return slideTitles.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object obj) {
			return view == obj;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			View view = (View) object;
			container.removeView(view);
		}
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
				Toast.makeText(WelcomeActivity.this, "Invalid Details!", Toast.LENGTH_LONG).show();
				Intent mainIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
				startActivity(mainIntent);
				finish();
				return;
			} else {

				Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
				startActivity(mainIntent);
				finish();
			}

		}
	}
}
