package com.example.kelownaconnect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecentRidesAdapter extends RecyclerView.Adapter<RecentRidesAdapter.RideViewHolder> {

    private List<Ride> rideList;

    public RecentRidesAdapter(List<Ride> rideList) {
        this.rideList = rideList;
    }

    @Override
    public RideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recent_ride, parent, false);
        return new RideViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RideViewHolder holder, int position) {
        Ride ride = rideList.get(position);
        holder.destinationText.setText(ride.getDestination());
        holder.statusText.setText(ride.getStatus());
        holder.timeText.setText(ride.getTime());
    }

    @Override
    public int getItemCount() {
        return rideList.size();
    }

    public static class RideViewHolder extends RecyclerView.ViewHolder {
        public TextView destinationText, statusText, timeText;

        public RideViewHolder(View view) {
            super(view);
            destinationText = view.findViewById(R.id.destinationText);
            statusText = view.findViewById(R.id.statusText);
            timeText = view.findViewById(R.id.timeText);
        }
    }
}
