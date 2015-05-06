package com.example.ezequieldevalais.retrofitexample;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;

import com.astuetz.PagerSlidingTabStrip;
import com.example.ezequieldevalais.retrofitexample.model.Constants;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class GithubUserActivity extends ActionBarActivity {
    @InjectView(R.id.tabs) PagerSlidingTabStrip tabs;
    @InjectView(R.id.pager) ViewPager pager;

    private MyPagerAdapter adapter;
    private Intent intent;
    private static String userName ="";
    private static final String TAG = Constants.TAG;
    private static final String INTENT_RECEIVE_GITHUB_USER= Constants.INTENT_GITHUB_USER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_user);
        ButterKnife.inject(this);

        intent = getIntent();
        userName = intent.getStringExtra(INTENT_RECEIVE_GITHUB_USER);
        tabs.setTextColor(Color.WHITE);
        tabs.setIndicatorColor(Color.WHITE);
        tabs.setBackgroundColor(Color.GRAY);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.setCurrentItem(0);
        tabs.setOnTabReselectedListener(new PagerSlidingTabStrip.OnTabReselectedListener() {
            @Override
            public void onTabReselected(int position) {
                Log.v(TAG, "Tab reselected: " + position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"User", "Repositories" };
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            CardFragment card;
            if (position == 1) {
                card =  RepositoriesFragment.newInstance(userName);
            } else {
                card = UserCardFragment.newInstance(userName);
            }
            return card;
        }
    }

}
