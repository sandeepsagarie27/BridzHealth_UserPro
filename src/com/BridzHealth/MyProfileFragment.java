package com.BridzHealth;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyProfileFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_registration, container, false);
		view.findViewById(R.id.password_edit).setVisibility(View.GONE);
		view.findViewById(R.id.conform_password_edit).setVisibility(View.GONE);
		TextView textView=(TextView) view.findViewById(R.id.create_account_btn);
		textView.setText("Save Changes");
		textView=(TextView) view.findViewById(R.id.title_text);
		textView.setText("My Profile");
		return view;
	}

}
