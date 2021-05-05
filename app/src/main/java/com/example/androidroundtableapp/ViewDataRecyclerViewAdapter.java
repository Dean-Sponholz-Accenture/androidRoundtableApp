package com.example.androidroundtableapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ViewDataRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private ArrayList<SongObject> mSongObjectArrayList;

    public ViewDataRecyclerViewAdapter(ArrayList<SongObject> songObjects) {
        this.mSongObjectArrayList = songObjects;
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.view_holder_view_data;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        holder.songName.setText(mSongObjectArrayList.get(position).getSongTitle());
        holder.artistName.setText(mSongObjectArrayList.get(position).getArtist());
    }

    @Override
    public int getItemCount() {
        return mSongObjectArrayList.size();
    }
}

class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView songName;
    public TextView artistName;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        songName = itemView.findViewById(R.id.view_holder_song);
        artistName = itemView.findViewById(R.id.view_holder_artist);
    }

}