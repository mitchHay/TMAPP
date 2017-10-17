package com.bignerdranch.android.timemanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.UUID;

/**
 * Created by mitchellhayward on 9/10/17.
 */

public class SettingsActivity extends SingleFragmentActivity {

    private static final String EXTRA_SETTINGS_ID = "com.bignerdranch.android.timemanagement.settings_id";

    public static Intent newIntent(Context packageContext, UUID time_id){
        Intent intent = new Intent(packageContext, SettingsActivity.class);
        intent.putExtra(EXTRA_SETTINGS_ID, time_id);
        Log.d("ID: ", time_id.toString());
        return intent;
    }

    @Override
    protected Fragment createFragment() {

        UUID timeId = (UUID) getIntent().getSerializableExtra(EXTRA_SETTINGS_ID);

        Log.d("NAME ID: ", timeId.toString());

        return SettingsFragment.newInstance(timeId);

    }
}
