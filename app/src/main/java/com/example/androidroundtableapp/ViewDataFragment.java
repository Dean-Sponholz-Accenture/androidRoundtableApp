package com.example.androidroundtableapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewDataFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<SongObject> fragmentResultArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_data, container, false);

        // Add the following lines to create RecyclerView
        recyclerView = view.findViewById(R.id.viewDataHandheld);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        getParentFragmentManager().setFragmentResultListener("songPlaylistKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {

                fragmentResultArrayList = bundle.getParcelableArrayList("bundleKey");
                // Do something with the result
                recyclerView.setAdapter(new ViewDataRecyclerViewAdapter(fragmentResultArrayList));
            }
        });


        return view;
    }
}

