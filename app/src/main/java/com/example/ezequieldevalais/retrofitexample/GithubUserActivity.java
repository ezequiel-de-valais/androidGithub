package com.example.ezequieldevalais.retrofitexample;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class GithubUserActivity extends ActionBarActivity {
    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @InjectView(R.id.pager)
    ViewPager pager;

    private MyPagerAdapter adapter;
    private Intent intent;
    public static String userName;
    private String TAG = "Eze";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_user);
        ButterKnife.inject(this);

        intent = getIntent();
        userName = intent.getStringExtra(SelectUserActivity.EXTRA_MESSAGE);
        //setSupportActionBar(toolbar);
        // create our manager instance after the content view is set
        //mTintManager = new SystemBarTintManager(this);
        // enable status bar tint
        //mTintManager.setStatusBarTintEnabled(true);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        pager.setCurrentItem(0);
//        changeColor(getResources().getColor(R.color.green));
        tabs.setBackgroundColor(Color.rgb(0,204,204));
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
            if (position == 1){
                return RepositoriesFragment.newInstance(userName);
            }
            return UserCardFragment.newInstance(userName);
        }
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_contact:
//                QuickContactFragment.newInstance().show(getSupportFragmentManager(), "QuickContactFragment");
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    private void changeColor(int newColor) {
//        tabs.setBackgroundColor(newColor);
//        mTintManager.setTintColor(newColor);
//        change ActionBar color just if an ActionBar is available
//        Drawable colorDrawable = new ColorDrawable(newColor);
//        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
//        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
//        if (oldBackground == null) {
//            getSupportActionBar().setBackgroundDrawable(ld);
//        } else {
//            TransitionDrawable td = new TransitionDrawable(new Drawable[]{oldBackground, ld});
//            getSupportActionBar().setBackgroundDrawable(td);
//            td.startTransition(200);
//        }
//
//        oldBackground = ld;
//        currentColor = newColor;
//    }

//    public void onColorClicked(View v) {
//        int color = Color.parseColor(v.getTag().toString());
//        changeColor(color);
//    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt("currentColor", currentColor);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        currentColor = savedInstanceState.getInt("currentColor");
//        changeColor(currentColor);
//    }



}