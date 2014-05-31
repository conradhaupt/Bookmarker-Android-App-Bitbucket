package com.conradhaupt.bookmarker;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.conradhaupt.bookmarker.ui.NavDrawerAdapter;

public class MainActivity extends FragmentActivity implements
		OnItemClickListener {

	/* Variables */
	// View Variables
	private DrawerLayout mDrawerLayout;
	private FrameLayout mFrameLayout;
	private ListView mDrawer;
	private ActionBarDrawerToggle mActionBarDrawerToggle;

	// Operational Variables
	private NavDrawerAdapter<String> mDrawerAdapter;

	// Value Variables
	private String[] mDrawerTitles;
	public String currentTitle = "";
	public int aCurrentThemeResourceID = 0;

	// Fragment Variables
	private SettingsFragment fSettingsFragment;
	private BooksFragment fBooksFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.initPreCreate();
		setContentView(R.layout.activity_main);
		this.initViewVariables();
		this.initDrawerAdapter();
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Process saved instance bundle
		if (savedInstanceState != null
				&& savedInstanceState.getBoolean("settingsFragment")) {
			// Open Books then Settings Fragment
			openBooksFragment(true);
			openSettingsFragment();
		} else {
			// Open Books Fragment, activity is new
			openBooksFragment(true);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mActionBarDrawerToggle.syncState();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		// try {
		// outState.putBoolean(
		// "settingsFragment",
		// this.getFragmentManager()
		// .getBackStackEntryAt(
		// this.getFragmentManager()
		// .getBackStackEntryCount() - 1)
		// .getName().equals("fSettingsFragment") ? true
		// : false);
		// } catch (Exception e) {
		// System.out.println(e);
		// }
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mActionBarDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		try {
			if (this.getFragmentManager().getBackStackEntryCount() > 1) {
				this.getFragmentManager().popBackStack();
				return;
			}
		} catch (Exception e) {

		}
		super.onBackPressed();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			// Process books
			openBooksFragment(false);
			mDrawerAdapter.setCurrentPosition(position);
			mDrawerLayout.closeDrawers();
			break;
		case 1:
			// Process Settings
			openSettingsFragment();
			mDrawerAdapter.setCurrentPosition(position);
			mDrawerLayout.closeDrawers();
			break;
		default:
			System.out.println("Drawer: Item clicked that isn't processed");
			break;
		}

	}

	private void initPreCreate() {
		// Process theme preference
		boolean inverse = PreferenceManager.getDefaultSharedPreferences(this)
				.getBoolean("preference_theme_colour_inverse", false);
		switch (Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(
				this).getString("preference_theme_colour", "-100"))) {
		case -100:
			System.out.println("Theme preference not set, defaulting to Red");
		case 0:
			System.out.println("Setting theme as Blue");
			aCurrentThemeResourceID = inverse ? R.style.AppTheme_Blue_Inverse
					: R.style.AppTheme_Blue;
			break;
		case 1:
			System.out.println("Setting theme as Red");
			aCurrentThemeResourceID = inverse ? R.style.AppTheme_Red_Inverse
					: R.style.AppTheme_Red;
			break;
		case 2:
			System.out.println("Setting theme as Green");
			aCurrentThemeResourceID = inverse ? R.style.AppTheme_Green_Inverse
					: R.style.AppTheme_Green;
			break;
		default:
			break;
		}
		// Assign theme
		setTheme(aCurrentThemeResourceID);
	}

	private void initViewVariables() {
		// Assign variables
		mDrawerLayout = (DrawerLayout) this.findViewById(R.id.activity_drawer);
		mDrawer = (ListView) this.findViewById(R.id.activity_drawer_listview);
		mFrameLayout = (FrameLayout) this.findViewById(R.id.activity_frame);

		// Assign ActionBarDrawerToggle values
		TypedArray a = getActionBar().getThemedContext()
				.obtainStyledAttributes(aCurrentThemeResourceID,
						new int[] { R.attr.ic_navigation });
		int attributeResourceId = a.getResourceId(0, 0);

		// Instantiate ActionBarToggle
		mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				attributeResourceId, R.string.activity_main_title_drawer_open,
				R.string.fragment_main_title_drawer_closed) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				// Assign the new title to the actionBar
				getActionBar().setTitle(currentTitle);

				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				// Store the current actionBar title if necessary
				if (!currentTitle.equals(getActionBar().getTitle())) {
					currentTitle = getActionBar().getTitle().toString();
				}

				// Change the actionBar title
				getActionBar().setTitle(
						getResources().getString(
								R.string.activity_main_title_drawer_open));
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
	}

	private void initDrawerAdapter() {
		this.mDrawerTitles = getResources().getStringArray(R.array.menu);
		mDrawerAdapter = new NavDrawerAdapter<String>(this,
				R.layout.simple_list_item_1, this.mDrawerTitles);
		mDrawer.setAdapter(mDrawerAdapter);
		mDrawer.setOnItemClickListener(this);
	}

	private void openSettingsFragment() {
		if (this.fSettingsFragment == null) {
			this.fSettingsFragment = SettingsFragment.newInstance();
		}
		if (!(this
				.getFragmentManager()
				.getBackStackEntryAt(
						this.getFragmentManager().getBackStackEntryCount() - 1)
				.getName().equals("fSettingsFragment"))) {
			System.out
					.println("Current fragment was not SettingsFragment, transitioning");
			this.getFragmentManager()
					.beginTransaction()
					.replace(R.id.activity_frame, fSettingsFragment,
							"SettingsFragment")
					.addToBackStack("fSettingsFragment").commit();
		} else {
			System.out
					.println("Current fragment is SettingsFragment, leaving as is");
		}
	}

	private void openBooksFragment(boolean force) {

		if (this.fBooksFragment == null) {
			this.fBooksFragment = BooksFragment.newInstance();
		}

		if (!force && this.getFragmentManager().getBackStackEntryCount() > 1) {
			this.getFragmentManager().popBackStack();
			System.out
					.println("Current fragment was not BooksFragment, transitioning");
			return;
		}
		if (force) {
			System.out
					.println("Current fragment was not BooksFragment, transitioning");
			this.getFragmentManager()
					.beginTransaction()
					.replace(R.id.activity_frame, fBooksFragment,
							"BooksFragment").addToBackStack("fBooksFragment")
					.commit();
		} else {
			System.out
					.println("Current fragment is BooksFragment, leaving as is");
		}
	}

}
