package com.example.kelownaconnect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecentRidesAdapter extends RecyclerView.Adapter<RecentRidesAdapter.RideViewHolder> {

    private List<Ride> rideList;
    private Context context;

    public RecentRidesAdapter(Context context, List<Ride> rideList) {
        this.context = context;
        this.rideList = rideList;
    }

    @NonNull
    @Override
    public RideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recent_ride, parent, false);
        return new RideViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RideViewHolder holder, int position) {
        Ride ride = rideList.get(position);
        holder.destinationText.setText(ride.getPickupLocation() + " to " + ride.getDestination());
        holder.statusText.setText(ride.getStatus());
        holder.timeText.setText(ride.getTime());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RideDetailsActivity.class);
            context.startActivity(intent);
        });
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
