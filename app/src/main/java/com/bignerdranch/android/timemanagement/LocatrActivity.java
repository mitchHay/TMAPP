package com.bignerdranch.android.timemanagement;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.UUID;

/**
 * Created by mitchellhayward on 9/10/17.
 */

public class LocatrActivity extends SingleFragmentActivity {
    private static final int REQUEST_ERROR = 0;

    private GoogleMap mMap;

    private static final String EXTRA_MAP_ID = "com.bignerdranch.android.timemanagement.map_id";

    public static Intent newIntent(Context packageContext, UUID time_id){
        Intent intent = new Intent(packageContext, LocatrActivity.class);
        intent.putExtra(EXTRA_MAP_ID, time_id);
        return intent;
    }

    @Override
    protected Fragment createFragment(){

        UUID timeId = (UUID) getIntent().getSerializableExtra(EXTRA_MAP_ID);
        return LocatrFragment.newInstance(timeId);
    }

    @Override
    protected void onResume(){
        super.onResume();

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (errorCode != ConnectionResult.SUCCESS){
            Dialog errorDialog = apiAvailability.getErrorDialog(this, errorCode, REQUEST_ERROR, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    finish();
                }
            });

            errorDialog.show();
        }
    }

}
