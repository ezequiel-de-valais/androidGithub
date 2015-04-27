package com.example.ezequieldevalais.retrofitexample;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class RepositoriesActivity extends ActionBarActivity {
/*
    private static final String TAG = "Eze" ;
    private static final String API = "https://api.github.com";
    private RestAdapter restAdapter;
    private String userName ;
    ActionBarActivity activity= this;
    private Intent intent;
    @InjectView(R.id.txtUserName) TextView txtUserName;
    @InjectView(R.id.progress_wheel) ProgressWheel progressWheel;
    @InjectView(R.id.listRepositories) ListView githubRepositories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);
        intent = getIntent();
        ButterKnife.inject(this);

        restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();

        setName();
        fillRepositryList();

    }


    private void fillRepositryList() {
        githubAPI git = restAdapter.create(githubAPI.class);
        git.getRepositories(userName,new RepositoriesCallback());
    }

    public void setName(){
        userName = intent.getStringExtra(UserActivity.EXTRA_MESSAGE);
        Log.v(TAG,"in repository activity, message:" + userName);
        txtUserName.setText(userName);
    }


    private class RepositoriesCallback implements Callback<List<Repository>> {

        @Override
        public void success(List<Repository> repositories, Response response) {

            GithubRepositoryArrayAdapter chapterListAdapter = new GithubRepositoryArrayAdapter(repositories,activity);
            githubRepositories = (ListView)findViewById(R.id.listRepositories);
            progressWheel.stopSpinning();
            githubRepositories.setAdapter(chapterListAdapter);

            for (Repository repository : repositories) {
                Log.v(TAG,repository.getFullName());
            }
        }

        @Override
        public void failure(RetrofitError error) {
            Log.e(TAG,error.getMessage());
        }
    }
    */

    //@InjectView(R.id.toolbar)
    //Toolbar toolbar;
    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @InjectView(R.id.pager)
    ViewPager pager;

    private MyPagerAdapter adapter;
    private Drawable oldBackground = null;
    private int currentColor;
    private SystemBarTintManager mTintManager;
    private Intent intent;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        intent = getIntent();
        userName = intent.getStringExtra(UserActivity.EXTRA_MESSAGE); //TODO: cambiar user por la selectUser

        //setSupportActionBar(toolbar);
        // create our manager instance after the content view is set
        mTintManager = new SystemBarTintManager(this);
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

        tabs.setOnTabReselectedListener(new PagerSlidingTabStrip.OnTabReselectedListener() {
            @Override
            public void onTabReselected(int position) {
                Toast.makeText(RepositoriesActivity.this, "Tab reselected: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"User", "Repositories" };
        //        Class<CardFragment>[] cardFragments = new Class<CardFragment>[] {
//                AnOtherSuperAwesomeCardFragment.class, SuperAwesomeCardFragment.class
//        };
       // private final List<Class<? extends CardFragment>> fragmentos = Arrays.asList(AnOtherSuperAwesomeCardFragment.class, SuperAwesomeCardFragment.class);
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
                return RepositoriesFragment.newInstance(position, userName);
            }
            return UserCardFragment.newInstance(position, userName);

//            Class<CardFragment> aClassFragment = (Class<CardFragment>) fragmentos.get(position);
//            return aClassFragment.newInstance(position);
        }
    }

}
