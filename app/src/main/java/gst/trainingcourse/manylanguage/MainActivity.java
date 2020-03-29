package gst.trainingcourse.manylanguage;

import android.app.SearchManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import gst.trainingcourse.manylanguage.fragment.FavouriteFragment;
import gst.trainingcourse.manylanguage.fragment.HomeFragment;
import gst.trainingcourse.manylanguage.fragment.ProfileFragment;
import gst.trainingcourse.manylanguage.lib_Helper.BottomNavigationBehavior;

public class MainActivity extends AppCompatActivity {

    private Fragment mHomeFragment = new HomeFragment();
    private Fragment mFavouriteFragment = new FavouriteFragment();
    private Fragment mProfileFragment = new ProfileFragment();
    private Fragment mDefault = mHomeFragment;
    private BottomNavigationView mBottomNavigationView;
    private CoordinatorLayout.LayoutParams mLayoutParams;
    private Toolbar mToolbar;
    private SearchView mSearchView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottomHome:
                    setupFragment(mHomeFragment);
                    mToolbar.setTitle("Home");
                    break;
                case R.id.bottomFavourite:
                    setupFragment(mFavouriteFragment);
                    mToolbar.setTitle("Favourite");
                    break;
                case R.id.bottomProfile:
                    setupFragment(mProfileFragment);
                    mToolbar.setTitle("Profile");
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initAction();

        //show and hide bottom navigation
        mLayoutParams = (CoordinatorLayout.LayoutParams) mBottomNavigationView.getLayoutParams();
        mLayoutParams.setBehavior(new BottomNavigationBehavior());

        //add fragment Home
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayoutContain, mHomeFragment)
                .commit();

        addFragment(mFavouriteFragment);
        addFragment(mProfileFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) menuItem.getActionView();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayoutContain, fragment)
                .hide(fragment)
                .commit();
    }

    private void setupFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .hide(mDefault)
                .show(fragment)
                .commit();
        mDefault = fragment;
    }

    private void initAction() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initView() {
        mBottomNavigationView = findViewById(R.id.bottomNavigation);
        mToolbar = findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);
    }
}
