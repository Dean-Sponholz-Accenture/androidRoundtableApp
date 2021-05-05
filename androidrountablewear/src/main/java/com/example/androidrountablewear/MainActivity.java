package com.example.androidrountablewear;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.Set;

import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;


public class MainActivity extends WearableActivity implements DataClient.OnDataChangedListener {

    private WearableRecyclerView recyclerView;
    private static final String COUNT_KEY = "sendMessage";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (WearableRecyclerView) findViewById(R.id.wearRecyclerView);
        recyclerView.setHasFixedSize(true);
        // To align the edge children (first and last) with the center of the screen
        recyclerView.setEdgeItemsCenteringEnabled(true);
        recyclerView.setLayoutManager(
                new WearableLinearLayoutManager(this));
        //recyclerView.setAdapter(new WearableRecyclerViewAdapter(fragmentResultArrayList));
        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Instantiates clients without member variables, as clients are inexpensive to create and
        // won't lose their listeners. (They are cached and shared between GoogleApi instances.)
        Wearable.getDataClient(this).addListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Wearable.getDataClient(this).removeListener(this);
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        Toast.makeText(getApplicationContext(), "Data Recieved", Toast.LENGTH_LONG).show();
        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/sendPlaylistArraylist") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    dataMap.remove("timestamp");
                    ArrayList<String> trimmedKeys = new ArrayList<>(dataMap.keySet());
                    ArrayList<SongObject> displayList = new ArrayList<>();
                    for (int i = 0; i < trimmedKeys.size(); i++){
                        SongObject object = new SongObject(trimmedKeys.get(i), dataMap.getString(trimmedKeys.get(i)));
                        displayList.add(object);
                    }
                    recyclerView.setAdapter(new WearableRecyclerViewAdapter(displayList));
                    //Toast.makeText(getApplicationContext(), String.valueOf(dataMap.getInt(COUNT_KEY)), Toast.LENGTH_LONG).show();
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }
}
