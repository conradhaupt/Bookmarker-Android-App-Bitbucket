package com.conradhaupt.bookmarker.ui;

import android.app.Activity;
import android.preference.PreferenceManager;

import com.conradhaupt.bookmarker.R;

public class ActivityThemeSetter {
	/* Variables */
	// Object Variables
	private int currentThemeResourceID = 0;

	// Static Variables

	/* Methods */
	// Set the theme
	public void updateTheme(Activity activity) {
		// Process theme preference
		boolean inverse = PreferenceManager.getDefaultSharedPreferences(
				activity).getBoolean("preference_theme_colour_inverse", false);
		switch (Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(
				activity).getString("preference_theme_colour", "-100"))) {
		case -100:
			System.out.println("Theme preference not set, defaulting to Red");
		case 0:
			System.out.println("Setting theme as Blue");
			currentThemeResourceID = inverse ? R.style.AppTheme_Blue_Inverse
					: R.style.AppTheme_Blue;
			break;
		case 1:
			System.out.println("Setting theme as Red");
			currentThemeResourceID = inverse ? R.style.AppTheme_Red_Inverse
					: R.style.AppTheme_Red;
			break;
		case 2:
			System.out.println("Setting theme as Green");
			currentThemeResourceID = inverse ? R.style.AppTheme_Green_Inverse
					: R.style.AppTheme_Green;
			break;
		default:
			break;
		}
		// Assign theme
		activity.setTheme(getCurrentThemeResourceID());
	}

	/**
	 * @return the currentThemeResourceID
	 */
	public int getCurrentThemeResourceID() {
		return currentThemeResourceID;
	}
}
