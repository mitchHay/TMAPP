package com.bignerdranch.android.timemanagement;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import java.util.UUID;

public class TimeActivity extends SingleFragmentActivity {

    private static final String EXTRA_TIME_ID = "com.bignerdranch.android.timemanagement.time_id";

    public static Intent newIntent(Context packageContext, UUID time_id){
        Intent intent = new Intent(packageContext, TimeActivity.class);
        intent.putExtra(EXTRA_TIME_ID, time_id);
        return intent;
    }

    @Override
    protected Fragment createFragment() {

        UUID timeId = (UUID) getIntent().getSerializableExtra(EXTRA_TIME_ID);
        return TimeFragment.newInstance(timeId);

    }

}
