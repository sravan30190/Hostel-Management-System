package com.example.term;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DummyFragment extends Fragment {
    TextView textView;


    public DummyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_dummy, container, false);
        Bundle bundle = getArguments();
       // textView=view.findViewById(R.id.dummytext);
        textView.setText("Welcome\n"+bundle.getString("name", "Default"));



        return view;
    }
}