package com.bignerdranch.android.timemanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;
import java.util.UUID;

/**
 * Created by mitchellhayward on 5/10/17.
 */

public class TimePagerActivity extends AppCompatActivity {
    private static final String EXTRA_TIME_ID = "com.bignerdranch.android.timemanagement.time_id";

    private ViewPager mViewPager;
    private List<Time> mTime;

    public static Intent newIntent(Context packageContext, UUID timeId){

        Intent intent = new Intent(packageContext, TimePagerActivity.class);
        intent.putExtra(EXTRA_TIME_ID, timeId);
        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_pager);

        UUID timeId = (UUID) getIntent().getSerializableExtra(EXTRA_TIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.time_view_pager);

        mTime = TimeLab.get(this).getActivity();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Time time = mTime.get(position);
                return TimeFragment.newInstance(time.getId());
            }

            @Override
            public int getCount() {
                return mTime.size();
            }
        });

        for (int i = 0; i < mTime.size(); i++){

            if (mTime.get(i).getId().equals(timeId)){

                mViewPager.setCurrentItem(i);
                break;

            }

        }

    }

}
