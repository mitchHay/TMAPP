package com.bignerdranch.android.timemanagement;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.UUID;

/**
 * Created by mitchellhayward on 9/10/17.
 */

public class LocatrFragment extends SupportMapFragment {
    private static final String ARG_MAP_ID = "com.bignerdranch.android.timemanagement.map_id";
    private GoogleApiClient mClient;

    private GoogleMap mMap;

    private Time mTime;

    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    public static LocatrFragment newInstance(UUID timeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_MAP_ID, timeId);
        LocatrFragment fragment = new LocatrFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        UUID timeId = (UUID) getArguments().getSerializable(ARG_MAP_ID);
        mTime = TimeLab.get(getActivity()).getTime(timeId);

        mClient = new GoogleApiClient.Builder(getActivity()).addApi(LocationServices.API).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                //getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        }).build();

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                updateUI();
            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();

        getActivity().invalidateOptionsMenu();
        mClient.connect();
    }

    @Override
    public void onStop(){
        super.onStop();

        mClient.disconnect();
    }

    private boolean hasLocationPermission(){
        int result = ContextCompat.checkSelfPermission(getActivity(), LOCATION_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void updateUI(){
        if (mMap == null){
            return;
        }

        if (mTime.getLat() != 0.0 && mTime.getLong() != 0.0) {

            LatLng itemPoint = new LatLng(mTime.getLat(), mTime.getLong());

            LatLngBounds bounds = new LatLngBounds.Builder().include(itemPoint).build();

            CameraUpdate zoomUpdate = CameraUpdateFactory.newLatLngZoom(itemPoint, 18.5f);

            mMap.addMarker(new MarkerOptions().position(itemPoint).title(mTime.getFullAddress()));

            mMap.animateCamera(zoomUpdate);
        }
    }

}
