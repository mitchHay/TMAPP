package com.bignerdranch.android.timemanagement;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by mitchellhayward on 9/10/17.
 */

public class LocatrFragment extends Fragment {

    private ImageView mImageView;
    private GoogleApiClient mClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;

    private Location location;

    public static LocatrFragment newInstance(){
        return new LocatrFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mClient = new GoogleApiClient.Builder(getActivity()).addApi(LocationServices.API).build();
    }

    @Override
    public void onStart(){
        super.onStart();

        mClient.connect();
    }

    @Override
    public void onStop(){
        super.onStop();

        mClient.disconnect();
    }


}
