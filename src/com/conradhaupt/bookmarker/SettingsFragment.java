package com.conradhaupt.bookmarker;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingsFragment extends PreferenceFragment {

	public static SettingsFragment newInstance() {
		SettingsFragment fragment = new SettingsFragment();
		return fragment;
	}

	public SettingsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.main_preferences);
		this.getActivity().setTitle("Settings");
		this.setHasOptionsMenu(true);

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_settings, menu);
	}

	@Override
	public void onResume() {
		// Set actionBar title
		this.getActivity().getActionBar()
				.setTitle(R.string.fragment_settings_title_drawer_closed);
		this.getActivity().invalidateOptionsMenu();
		super.onResume();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		// Handle restart preference clicks
		if (preference.getKey().equals("preference_about_restart")) {
			this.getActivity().recreate();
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
}
