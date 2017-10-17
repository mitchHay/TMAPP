package com.bignerdranch.android.timemanagement;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.IOException;
import java.security.Permission;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by mitchellhayward on 3/10/17.
 * Student Number: 1092962
 */

public class TimeFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks {

    private static final String ARG_TIME_ID = "time_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final String DIALOG_TIME = "DialogTime";

    private static final int REQUEST_DATE = 0;

    private static final int REQUEST_PHOTO = 2;

    private static final int REQUEST_TIME = 1;

    private TimePickerFragment timePicker;

    private String TAG = "PERMISSION DENIED";
    private String TAG_SUCCESS = "PERMISSION GRANTED";

    private Time mTime;
    private EditText mTitleField;
    private Button mDateButton;
    private Spinner mSpinner;
    private Button mTimeButton;
    private TextView mLocatrButton;

    private GoogleApiClient mClient;

    private Geocoder geocoder;
    private List<Address> addresses;

    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private File mPhotoFile;

    private TimePicker tp;

    public static TimeFragment newInstance(UUID timeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME_ID, timeId);

        TimeFragment fragment = new TimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //mTime = new Time();
        UUID timeId = (UUID) getArguments().getSerializable(ARG_TIME_ID);
        mTime = TimeLab.get(getActivity()).getTime(timeId);
        mClient = new GoogleApiClient.Builder(getActivity()).addApi(LocationServices.API).addConnectionCallbacks(this).build();

        // Get the Current Location of the user

        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        mPhotoFile = TimeLab.get(getActivity()).getPhotoFile(mTime);

    }

    @Override
    public void onPause(){
        super.onPause();

        TimeLab.get(getActivity()).updateTime(mTime);
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

    @Override
    public void onConnected(Bundle connectionHint){
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);

        LocationServices.FusedLocationApi.requestLocationUpdates(mClient, request, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("LOCATION: ", String.valueOf(location));
                mTime.setLat(location.getLatitude());
                mTime.setLong(location.getLongitude());
            }
        });

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Log.d("P STATUS: ", TAG);
        } else {
            Log.d("P STATUS: ", TAG_SUCCESS);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_time, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_activity:
                UUID timeId = (UUID) getArguments().getSerializable(ARG_TIME_ID);
                final TimeLab timeLab = TimeLab.get(getActivity());
                mTime = timeLab.getTime(timeId);

                // Display an Alert

                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete Activity")
                        .setMessage("Delete Current Activity?")
                        .setCancelable(true)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                timeLab.deleteActivity(mTime);
                                Toast toast = Toast.makeText(getActivity(), "Deleting: " + "'" + mTime.getTitle() + "'", Toast.LENGTH_SHORT);
                                toast.show();
                                getActivity().finish();
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
                return true;
            case R.id.complete_activity:
                if (mTime.getTitle() == null){
                    mTitleField.setError("A Title is Required!");
                } else {
                    Toast completeToast = Toast.makeText(getActivity(), "Adding: " + "'" + mTime.getTitle() + "'", Toast.LENGTH_SHORT);
                    completeToast.show();
                    getActivity().finish();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.fragment_time, container, false);

        mTitleField = (EditText)v.findViewById(R.id.time_title);
        mTitleField.setText(mTime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                mTime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDateButton = (Button)v.findViewById(R.id.time_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mTime.getDate());
                dialog.setTargetFragment(TimeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mSpinner = (Spinner)v.findViewById(R.id.time_selection);

        mSpinner.setSelection(mTime.getSpinnerId());

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mTime.setSpinnerId(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mTimeButton = (Button)v.findViewById(R.id.duration_button);

        mTimeButton.setText(String.valueOf(mTime.getHour()) + " HOURS " + String.valueOf(mTime.getMin()) + " MINUTES");

        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newTimePicker(mTime.getHour(), mTime.getMin());
                dialog.setTargetFragment(TimeFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        mLocatrButton = (TextView)v.findViewById(R.id.time_location);
        mLocatrButton.setText("LAT: " + String.valueOf(mTime.getLat()) + " LONG: " + String.valueOf(mTime.getLong()));

        mPhotoButton = (ImageButton)v.findViewById(R.id.time_camera);

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager pm = getActivity().getPackageManager();

        boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(pm) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = FileProvider.getUriForFile(getActivity(), "com.bignerdranch.android.timemanagement.fileprovider", mPhotoFile);
                captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                List<ResolveInfo> cameraActivities = getActivity().getPackageManager().queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);

                for (ResolveInfo activitiy : cameraActivities){
                    getActivity().grantUriPermission(activitiy.activityInfo.packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                }
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView)v.findViewById(R.id.time_photo);

        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mTime.setDate(date);
            updateDate();
        }

        if (requestCode == REQUEST_TIME) {
            int hourOfDay = (int) data.getSerializableExtra(TimePickerFragment.EXTRA_HOUR);
            int minute = (int) data.getSerializableExtra(TimePickerFragment.EXTRA_MIN);
            mTime.setHour(hourOfDay);
            mTime.setMin(minute);
            mTimeButton.setText(String.valueOf(mTime.getHour()) + " HOURS " + String.valueOf(mTime.getMin()) + " MINUTES");
        }
    }

    private void updateDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM d, yyyy");
        mDateButton.setText(sdf.format(mTime.getDate()));
        //mDateButton.setText(mTime.getDate().toString());
    }

}
