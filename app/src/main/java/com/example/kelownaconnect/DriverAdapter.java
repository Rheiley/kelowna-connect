package com.example.kelownaconnect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverViewHolder> {
    private Context context;
    private List<Driver> drivers;
    private OnDriverClickListener listener;

    public interface OnDriverClickListener {
        void onDriverClick(Driver driver);  // Update this interface to handle card clicks as well
    }

    public DriverAdapter(Context context, List<Driver> drivers, OnDriverClickListener listener) {
        this.context = context;
        this.drivers = drivers;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<Driver> newDrivers) {
        this.drivers = new ArrayList<>(newDrivers); // Update the list
        notifyDataSetChanged(); // Refresh RecyclerView
    }

    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_driver_card, parent, false);
        return new DriverViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(DriverViewHolder holder, int position) {
        Driver driver = drivers.get(position);

        holder.nameTextView.setText(driver.getName());
        holder.ratingTextView.setText("Rating: " + driver.getRating());
        holder.costTextView.setText("Cost: $" + driver.getEstimatedCost());
        holder.etaTextView.setText("ETA: " + driver.getEta());
        holder.vehicleTextView.setText("Vehicle: " + driver.getVehicle());

        // Set the click listener for the "Book" button
        holder.bookButton.setOnClickListener(v -> listener.onDriverClick(driver));

        // Set the click listener for the entire card (itemView)
        holder.itemView.setOnClickListener(v -> listener.onDriverClick(driver));
    }

    @Override
    public int getItemCount() {
        return drivers.size();
    }

    public static class DriverViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView ratingTextView;
        TextView costTextView;
        TextView etaTextView;
        TextView vehicleTextView;
        Button bookButton;

        public DriverViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.driverName);
            ratingTextView = itemView.findViewById(R.id.driverRating);
            costTextView = itemView.findViewById(R.id.driverCost);
            etaTextView = itemView.findViewById(R.id.driverEta);
            vehicleTextView = itemView.findViewById(R.id.driverVehicle);
            bookButton = itemView.findViewById(R.id.bookButton);
        }
    }
}

