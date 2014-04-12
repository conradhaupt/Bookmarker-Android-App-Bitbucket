package com.conradhaupt.bookmarker;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that
 * contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface to handle
 * interaction events. Use the {@link SettingsFragment#newInstance} factory
 * method to create an instance of this fragment.
 * 
 */
public class SettingsFragment extends PreferenceFragment {

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 * 
	 * @return A new instance of fragment SettingsFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static SettingsFragment newInstance() {
		SettingsFragment fragment = new SettingsFragment();
		// Bundle args = new Bundle();
		// args.putString(ARG_PARAM1, param1);
		// fragment.setArguments(args);
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
		this.getActivity().getActionBar()
				.setTitle(R.string.fragment_settings_title_drawer_closed);
		this.getActivity().invalidateOptionsMenu();
		super.onResume();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

}
