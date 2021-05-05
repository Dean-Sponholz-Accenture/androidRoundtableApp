package com.example.androidroundtableapp;

import android.os.Parcel;
import android.os.Parcelable;

public class SongObject implements Parcelable {

    private String songTitle;
    private String artist;

    public SongObject(String mSongTitle, String mArtist){
        songTitle = mSongTitle;
        artist = mArtist;
    }

    protected SongObject(Parcel in) {
        songTitle = in.readString();
        artist = in.readString();
    }

    public static final Creator<SongObject> CREATOR = new Creator<SongObject>() {
        @Override
        public SongObject createFromParcel(Parcel in) {
            return new SongObject(in);
        }

        @Override
        public SongObject[] newArray(int size) {
            return new SongObject[size];
        }
    };

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String mSongTitle) {
        this.songTitle = mSongTitle;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String mArtist) {
        this.artist = mArtist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(songTitle);
        parcel.writeString(artist);
    }
}
