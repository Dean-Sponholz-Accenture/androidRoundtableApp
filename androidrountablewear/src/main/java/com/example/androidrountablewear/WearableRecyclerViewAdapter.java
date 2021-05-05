package com.example.androidrountablewear;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WearableRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private ArrayList<SongObject> mSongObjectArrayList;

    public WearableRecyclerViewAdapter(ArrayList<SongObject> songObjects) {
        this.mSongObjectArrayList = songObjects;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.view_holder_wear;
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