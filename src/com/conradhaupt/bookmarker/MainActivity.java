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

import com.conradhaupt.bookmarker.sqlite.helper.BookDatabaseHelper;
import com.conradhaupt.bookmarker.ui.ActivityThemeSetter;
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
	public ActivityThemeSetter aActivityThemeSetter = new ActivityThemeSetter();

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
			System.out.println("Activity is not new");
			// Open Books then Settings Fragment
			openBooksFragment();
			openSettingsFragment();
		} else {
			// Open Books Fragment, activity is new
			System.out.println("Activity is new");
			openBooksFragment();
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mActionBarDrawerToggle.syncState();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		try {
			outState.putBoolean("settingsFragment", this.getFragmentManager()
					.findFragmentByTag("fSettingsFragment") != null ? true
					: false);
		} catch (Exception e) {
			System.out.println(e);
		}
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
		System.out.println(BookDatabaseHelper.CREATE_TABLE_BOOKMARKS);
		System.out.println(BookDatabaseHelper.CREATE_TABLE_BOOKS);
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
			openBooksFragment();
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
		// Process theme change from preference
		aActivityThemeSetter.updateTheme(this);
	}

	private void initViewVariables() {
		// Assign variables
		mDrawerLayout = (DrawerLayout) this.findViewById(R.id.activity_drawer);
		mDrawer = (ListView) this.findViewById(R.id.activity_drawer_listview);
		mFrameLayout = (FrameLayout) this.findViewById(R.id.activity_frame);

		// Assign ActionBarDrawerToggle values
		TypedArray a = getActionBar().getThemedContext()
				.obtainStyledAttributes(
						aActivityThemeSetter.getCurrentThemeResourceID(),
						new int[] { R.attr.ic_navigation });
		int attributeResourceId = a.getResourceId(0, 0);
		a.recycle();

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

		// If the fragment is null then instantiate
		if (this.fSettingsFragment == null) {
			this.fSettingsFragment = SettingsFragment.newInstance();
		}

		// Pop or open the fragment
		if (this.getFragmentManager().findFragmentByTag("fSettingsFragment") == null) {
			System.out.println("fSettingsFragment has not been added, adding");
			this.getFragmentManager()
					.beginTransaction()
					.replace(R.id.activity_frame, fSettingsFragment,
							"fSettingsFragment")
					.addToBackStack("fSettingsFragment").commit();
		} else {
			System.out.println("fSettingsFragment exists, popping");
			this.getFragmentManager().popBackStack("fSettingsFragment", 0);
		}
	}

	private void openBooksFragment() {

		// If the fragment is null then instantiate
		if (this.fBooksFragment == null) {
			this.fBooksFragment = BooksFragment.newInstance();
		}

		// Pop or open the fragment
		if (this.getFragmentManager().findFragmentByTag("fBooksFragment") == null) {
			System.out.println("fBooksFragment has not been added, adding");
			this.getFragmentManager()
					.beginTransaction()
					.replace(R.id.activity_frame, fBooksFragment,
							"fBooksFragment").addToBackStack("fBooksFragment")
					.commit();
		} else {
			System.out.println("fBooksFragment exists, popping");
			this.getFragmentManager().popBackStack("fBooksFragment", 0);
		}
	}
}
