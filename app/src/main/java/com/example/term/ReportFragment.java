package com.example.term;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import static android.view.View.VISIBLE;


public class ReportFragment extends Fragment {
    EditText sub,msg;
    ProgressBar progressBar;
    Button report;

    String id,msg1,sub1;

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        IP i=new IP();
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        sub=view.findViewById(R.id.subject);
        msg=view.findViewById(R.id.msg);
        progressBar=view.findViewById(R.id.progress1);
        report=view.findViewById(R.id.reportbutton);
        Bundle bundle = getArguments();
        id = bundle.getString("id", "Default");
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg1=String.valueOf(msg.getText());
                sub1=String.valueOf(sub.getText());
                msg.setText("");
                sub.setText("");
                if(!sub1.equals("") && !msg1.equals(""))
                {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[3];
                            field[0] = "id";
                            field[1] = "sub";
                            field[2] = "msg";
                            //Creating array for data
                            String[] data = new String[3];
                            data[0] = id;
                            data[1] = sub1;
                            data[2] = msg1;
                            PutData putData = new PutData("http://"+i.getIp()+"/Termpaper/report.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    //End ProgressBar (Set visibility to GONE)
                                    progressBar.setVisibility(View.GONE);
                                    if(result.equals("Recorded Successfully"))
                                    {


                                        Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();






                                    }
                                    else
                                    {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                            //End Write and Read data with URL
                        }
                    });

                }
                else
                {
                    Toast.makeText(getActivity(),"Enter Valid Credential",Toast.LENGTH_SHORT).show();
                }
            }

        });




        return view;
    }


}