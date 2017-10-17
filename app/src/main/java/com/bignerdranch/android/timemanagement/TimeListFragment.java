package com.bignerdranch.android.timemanagement;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by mitchellhayward on 3/10/17.
 */

public class TimeListFragment extends Fragment {

    private RecyclerView mTimeRecyclerView;
    private TimeAdapter mAdapter;

    private Geocoder geocoder;
    private List<Address> addresses;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_time_list, container, false);

        mTimeRecyclerView = (RecyclerView) view.findViewById(R.id.time_recycler_view);
        mTimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_time_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_activity:
                Time time = new Time();
                TimeLab.get(getActivity()).addActivity(time);
                Intent intent = TimePagerActivity.newIntent(getActivity(), time.getId());
                startActivity(intent);
                return true;
            case R.id.user_settings:
                Time settingsTime = new Time();
                TimeLab.get(getActivity()).addSettings(settingsTime);
                Intent settingsIntent = SettingsActivity.newIntent(getActivity(), settingsTime.getNewId());
                startActivity(settingsIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {

        TimeLab timeLab = TimeLab.get(getActivity());
        List<Time> time = timeLab.getActivity();

        if (mAdapter == null) {
            mAdapter = new TimeAdapter(time);
            mTimeRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setTime(time);
           mAdapter.notifyDataSetChanged();
        }

    }

    private class TimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mLocationView;

        private Time mTime;

        public TimeHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.list_item_time, parent, false));

            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.list_time_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.list_time_date);
            mLocationView = (TextView) itemView.findViewById(R.id.list_time_location);

        }

        private void bind(Time time){

            mTime = time;

            mTitleTextView.setText(mTime.getTitle());
            mLocationView.setText("LOCATION: " + String.valueOf(mTime.getLat()) + ", " + String.valueOf(mTime.getLong()));

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM d, yyyy");
            mDateTextView.setText(sdf.format(mTime.getDate()));

            //mDateTextView.setText(mTime.getDate().toString());

        }

        @Override
        public void onClick(View view) {
            Intent intent = TimePagerActivity.newIntent(getActivity(), mTime.getId());
            startActivity(intent);
            //Toast.makeText(getActivity(), mTime.getTitle() + " selected", Toast.LENGTH_SHORT).show();
        }
    }


    private class TimeAdapter extends RecyclerView.Adapter<TimeHolder>{

        private List<Time> mTime;

        public TimeAdapter(List<Time> time){

            mTime = time;

        }

        @Override
        public TimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new TimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TimeHolder holder, int position) {
            Time time = mTime.get(position);
            holder.bind(time);
        }

        @Override
        public int getItemCount() {
            return mTime.size();
        }

        public void setTime(List<Time> time){
            mTime = time;
        }

    }

}
