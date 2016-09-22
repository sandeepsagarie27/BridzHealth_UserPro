package com.BridzHealth;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MedicalReportsFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		TextView textView = (TextView) inflater.inflate(R.layout.nav_list_item, container, false);
		textView.setText("Medical Reports");
		return textView;
	}

}
