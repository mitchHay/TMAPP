package com.bignerdranch.android.timemanagement;

import android.support.v4.app.Fragment;

/**
 * Created by mitchellhayward on 3/10/17.
 */

public class TimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){

        return new TimeListFragment();

    }

}
