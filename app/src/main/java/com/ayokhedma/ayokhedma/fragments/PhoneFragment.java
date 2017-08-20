package com.ayokhedma.ayokhedma.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayokhedma.ayokhedma.R;
import com.ayokhedma.ayokhedma.models.ObjectModel;
import com.ayokhedma.ayokhedma.ui.ObjectActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneFragment extends Fragment {
    private List<String> phones = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listView;
    private String link;
    private ObjectModel object;
    private Gson gson;
    ObjectActivity objectActivity;
    String id;
    private TextView emptyView;
    ProgressDialog progress;


    public PhoneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone, container, false);
        listView = (ListView) view.findViewById(R.id.phone_list);
        emptyView = (TextView) view.findViewById(android.R.id.empty);

        progress = new ProgressDialog(getActivity());
        progress.setMessage("Please wait ....");
        progress.setMax(100);
        progress.show();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        objectActivity = (ObjectActivity) getActivity();
        id = objectActivity.getIntent().getStringExtra("id");

        getData();
    }


    private void getData(){

    }

}
