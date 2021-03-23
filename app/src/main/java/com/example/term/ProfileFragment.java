package com.example.term;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {
    TextView name,id,parentname,phone;
    Button logout;



    public ProfileFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        name = view.findViewById(R.id.name);
        id = view.findViewById(R.id.id);
        parentname = view.findViewById(R.id.parentname);
        phone = view.findViewById(R.id.phone);
        logout= view.findViewById(R.id.logout);


        Bundle bundle = getArguments();



        name.setText(bundle.getString("name", "Default"));
        id.setText(bundle.getString("id", "Default"));
        parentname.setText(bundle.getString("parent", "Default"));
        phone.setText(bundle.getString("phone", "Default"));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Logging Out!",Toast.LENGTH_SHORT).show();
                //Toast.makeText(,"Logging Out!",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);


            }
        });




        // Inflate the layout for this fragment
        return view;
    }
}