package com.example.androidroundtableapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;

public class EnterDataFragment extends Fragment implements DataClient.OnDataChangedListener {

    private Button mSubmitButton, mSyncButton;
    DataClient mDataClient;
    private static final String COUNT_KEY = "sendMessage";
    private EditText enterTitleEditText;
    private EditText enterArtistEditText;
    private ArrayList<SongObject> mSongObjectArraylist = new ArrayList<>();
    boolean wasSubmitClicked = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_enter_data, container, false);
        mSubmitButton = v.findViewById(R.id.submitSongButton);
        mSyncButton = v.findViewById(R.id.syncDataButton);
        enterTitleEditText = v.findViewById(R.id.enterTitleEditText);
        enterArtistEditText = v.findViewById(R.id.enterArtistEditText);
        mSyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterArtistEditText.getText().clear();
                enterTitleEditText.getText().clear();
                sendDataToWear(mSongObjectArraylist);
            }
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SongObject s = new SongObject(enterTitleEditText.getText().toString(), enterArtistEditText.getText().toString());
                mSongObjectArraylist.add(s);
                enterArtistEditText.getText().clear();
                enterTitleEditText.getText().clear();
            }
        });

        return v;
    }

    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {
    }

    private void sendDataToWear(ArrayList<SongObject> songObjects) {
        mDataClient = Wearable.getDataClient(requireContext());

        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/sendPlaylistArraylist").setUrgent();
        //timestamp must be there for data to send
        putDataMapReq.getDataMap().putLong("timestamp", System.currentTimeMillis());
        for (int i = 0; i < mSongObjectArraylist.size(); i++){
            putDataMapReq.getDataMap().putString(mSongObjectArraylist.get(i).getSongTitle(), mSongObjectArraylist.get(i).getArtist());
        }
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        putDataReq.setUrgent();
        Task<DataItem> dataItemTask = Wearable.getDataClient(requireContext()).putDataItem(putDataReq);

        dataItemTask.addOnSuccessListener(new OnSuccessListener<DataItem>() {
            @Override
            public void onSuccess(DataItem dataItem) {
                //Toast.makeText(getContext(), "Successful Data Sync", Toast.LENGTH_LONG).show();
                fireNotification();
            }
        });
        dataItemTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(e.toString(), "onFailure: ");
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        Bundle result = new Bundle();
        result.putParcelableArrayList("bundleKey", mSongObjectArraylist);
        getParentFragmentManager().setFragmentResult("songPlaylistKey", result);
    }

    private void fireNotification(){

        // The channel ID of the notification.
        final String mNotificationChannelId = "my_channel_01";

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(requireContext());
        // Support for Android Oreo: Notification Channels
        NotificationChannel channel = new NotificationChannel(
                "my_channel_01",
                "Channel_name_to_be_displayed_in_Settings",
                NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(requireContext(), mNotificationChannelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Playlist Synced")
                        .setContentText("Your playlist was synced to your watch");

        // Issue the notification with notification manager.
        notificationManager.notify(1, notificationBuilder.build());
    }
}