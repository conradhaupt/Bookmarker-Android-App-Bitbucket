package com.conradhaupt.bookmarker;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
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

import com.conradhaupt.bookmarker.backend.NavDrawerAdapter;

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

	// Fragment Variables
	private SettingsFragment fSettingsFragment;
	private BooksFragment fBooksFragment;

	private void initViewVariables() {// Assign variables
		mDrawerLayout = (DrawerLayout) this.findViewById(R.id.activity_drawer);
		mDrawer = (ListView) this.findViewById(R.id.activity_drawer_listview);
		mFrameLayout = (FrameLayout) this.findViewById(R.id.activity_frame);
		mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_navigation_drawer,
				R.string.activity_main_title_drawer_open,
				R.string.activity_main_title_drawer_closed) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if (getFragmentManager().getBackStackEntryCount() <= 1) {
					getActionBar()
							.setTitle(
									getResources()
											.getString(
													R.string.activity_main_title_drawer_closed));
				} else {
					getActionBar()
							.setTitle(
									getResources()
											.getString(
													R.string.fragment_settings_title_drawer_closed));
				}
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mActionBarDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onResume() {
		super.onResume();
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		try {
			outState.putBoolean(
					"settingsFragment",
					this.getFragmentManager()
							.getBackStackEntryAt(
									this.getFragmentManager()
											.getBackStackEntryCount() - 1)
							.getName().equals("fSettingsFragment") ? true
							: false);
		} catch (Exception e) {
			System.out.println(e);
		}
		super.onSaveInstanceState(outState);
	}

}
