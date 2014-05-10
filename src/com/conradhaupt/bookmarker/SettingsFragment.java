package com.conradhaupt.bookmarker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;

@SuppressLint("ValidFragment")
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
		} else if (preference.getKey().equals("preference_about_copyright")) {
			AboutDialog aboutDialog = new AboutDialog(AboutDialog.MODE_ROOT);
			aboutDialog.show(getFragmentManager(), "AboutDialogRoot");
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

	/* Classes */
	public class AboutDialog extends DialogFragment {

		/* Variables */
		// Object Variables
		public final int mode;

		// Static Variables
		public static final int MODE_ROOT = 0;
		public static final int MODE_LIBRARY = 1;

		/* Methods */
		public AboutDialog(int mode) {
			this.mode = mode;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Instantiate dialog builder
			AlertDialog.Builder builder = new AlertDialog.Builder(
					this.getActivity());

			// Get the layout inflater
			LayoutInflater inflater = getActivity().getLayoutInflater();

			// Inflate the dialog layout
			builder.setView(
					inflater.inflate(
							mode == MODE_ROOT ? R.layout.dialog_settings_about
									: R.layout.dialog_settings_about_library,
							null)).setNeutralButton(
					R.string.dialog_settings_about_close,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// Close dialog
							AboutDialog.this.getDialog().cancel();
						}
					});

			// Return dialog
			return builder.create();
		}

	}
}
