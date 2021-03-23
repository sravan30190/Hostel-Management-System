package com.example.term;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.view.View.VISIBLE;

public class HomeFragment extends Fragment {
    TextView nameholder;
    ProgressBar pro;
    LinearLayout permissionform,parentlinear,wardenlinear,allset,parentDeny,WardenDeny;
    EditText expectedintime,expectedouttime,reason;
    Button permissionbutton,parentmsg,cancelrequestinparent,cancelrequestinwarden,cancelrequest,qr,refresh,cancelrequestpd,cancelrequestwd;
    String expectedout,expectedin,reasonstr;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundleHome = new Bundle();
        cookie c=new cookie();
        IP i=new IP();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cancelrequestwd=view.findViewById(R.id.cancelrequestwd);
        cancelrequestpd=view.findViewById(R.id.cancelrequestpd);
        WardenDeny=view.findViewById(R.id.WardenDeny);
        parentDeny=view.findViewById(R.id.parentDeny);
        refresh=view.findViewById(R.id.refresh);
        expectedintime=view.findViewById(R.id.expectedintime);
        expectedouttime=view.findViewById(R.id.expectedouttime);
        nameholder=view.findViewById(R.id.nameholder);
        pro=view.findViewById(R.id.progressinpermission);
        permissionform=view.findViewById(R.id.permssionform);
        permissionbutton=view.findViewById(R.id.permissionbutton);
        reason=view.findViewById(R.id.reason);
        parentlinear=view.findViewById(R.id.parentlinear);
        wardenlinear=view.findViewById(R.id.wardenlinear);
        parentmsg=view.findViewById(R.id.parentmsg);
        allset=view.findViewById(R.id.allset);
        cancelrequestinparent=view.findViewById(R.id.cancelrequestinparent);
        cancelrequestinwarden=view.findViewById(R.id.cancelrequestinwarden);
        cancelrequest=view.findViewById(R.id.cancelrequest);
        qr=view.findViewById(R.id.qr);

        Bundle bundle3 = new Bundle();

        Bundle bundle=getArguments();

        bundleHome.putString("id",bundle.getString("id", "Default"));
        bundleHome.putString("name", bundle.getString("name", "Default"));
        bundleHome.putString("parent", bundle.getString("parent", "Default"));
        bundleHome.putString("phone", bundle.getString("phone", "Default"));



        expectedintime.setInputType(InputType.TYPE_NULL);
        expectedouttime.setInputType(InputType.TYPE_NULL);

        expectedouttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(expectedouttime);
            }
        });

        expectedintime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(expectedintime);
            }
        });


        nameholder.setText("Hi "+bundle.getString("name", "Default"));

        pro.setVisibility(View.VISIBLE);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable()
        {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[1];
                field[0] = "id";
                //Creating array for data
                String[] data = new String[1];
                data[0] = bundle.getString("id", "Default");
                PutData putData = new PutData("http://"+i.getIp()+"/Termpaper/token.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        //End ProgressBar (Set visibility to GONE)
                        if (result.equals("success"))
                        {

                            String url="http://"+i.getIp()+"/Termpaper/readToken.php?id="+ bundle.getString("id", "Default");
                            FetchData fetchData = new FetchData(url);
                            if (fetchData.startFetch()) {
                                if (fetchData.onComplete()) {
                                    String result1 = fetchData.getResult();
                                    String[] str = result1.split(";", 3);
                                    c.setTid(str[0]);
                                    if(str[1].equals("0"))
                                    {
                                        parentlinear.setVisibility(VISIBLE);
                                    }
                                    if(str[1].equals("2"))
                                    {
                                        parentDeny.setVisibility(VISIBLE);
                                    }
                                    if(str[2].equals("2"))
                                    {
                                        WardenDeny.setVisibility(VISIBLE);
                                    }

                                    if(str[1].equals("1") && str[2].equals("0"))
                                    {
                                        wardenlinear.setVisibility(VISIBLE);
                                    }
                                    if(str[1].equals("1") && str[2].equals("1"))
                                    {
                                        allset.setVisibility(VISIBLE);
                                    }

                                }
                            }
                            pro.setVisibility(View.GONE);

                        }
                        else{
                            pro.setVisibility(View.GONE);
                            permissionform.setVisibility(View.VISIBLE);


                        }

                    }
                }
                //End Write and Read data with URL
            }
        });


        permissionbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                expectedout=String.valueOf(expectedouttime.getText());
                expectedin=String.valueOf(expectedintime.getText());
                reasonstr=String.valueOf(reason.getText());
                pro.setVisibility(VISIBLE);
                if(!expectedout.equals("") && !expectedin.equals("") && !reasonstr.equals(""))
                {
                    //Start ProgressBar first (Set visibility VISIBLE)

                    Handler handler1 = new Handler(Looper.getMainLooper());
                    handler1.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[5];
                            field[0] = "expectedout";
                            field[1] = "expectedin";
                            field[2] = "reason";
                            field[3]="studentID";
                            field[4]="phone";
                            //Creating array for data
                            String[] data = new String[5];
                            data[0] = expectedout;
                            data[1] = expectedin;
                            data[2] = reasonstr;
                            data[3] = bundle.getString("id", "Default");
                            data[4] = bundle.getString("phone", "Default");
                            PutData putData = new PutData("http://"+i.getIp()+"/Termpaper/permissionform.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    //End ProgressBar (Set visibility to GONE)

                                    if(result.equals("success"))
                                    {
                                        String url="http://"+i.getIp()+"/Termpaper/readToken.php?id="+ bundle.getString("id", "Default");
                                        FetchData fetchData = new FetchData(url);
                                        if (fetchData.startFetch()) {
                                            if (fetchData.onComplete()) {
                                                String result1 = fetchData.getResult();
                                                String[] str = result1.split(";", 3);

                                                bundle3.putString("id",bundle.getString("id", "Default"));
                                                bundle3.putString("name", bundle.getString("name", "Default"));
                                                bundle3.putString("parent", bundle.getString("parent", "Default"));
                                                bundle3.putString("phone", bundle.getString("phone", "Default"));
                                                bundle3.putString("tid", str[0]);


                                            }
                                        }

                                        pro.setVisibility(View.GONE);

                                        Intent intent=new Intent(getActivity(),sms.class);
                                        intent.putExtras(bundle3);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        pro.setVisibility(View.GONE);
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
                    pro.setVisibility(View.GONE);
                    Toast.makeText(getActivity(),"Enter Valid Credential",Toast.LENGTH_SHORT).show();
                }

            }
        });

        parentmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro.setVisibility(VISIBLE);
                Handler handler3 = new Handler(Looper.getMainLooper());
                handler3.post(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://"+i.getIp()+"/Termpaper/readToken.php?id="+ bundle.getString("id", "Default");
                        FetchData fetchData = new FetchData(url);
                        if (fetchData.startFetch()) {
                            if (fetchData.onComplete()) {
                                String result1 = fetchData.getResult();
                                String[] str = result1.split(";", 3);

                                bundle3.putString("id",bundle.getString("id", "Default"));
                                bundle3.putString("name", bundle.getString("name", "Default"));
                                bundle3.putString("parent", bundle.getString("parent", "Default"));
                                bundle3.putString("phone", bundle.getString("phone", "Default"));
                                bundle3.putString("tid", str[0]);


                            }
                        }

                        pro.setVisibility(View.GONE);

                        Intent intent=new Intent(getActivity(),sms.class);
                        intent.putExtras(bundle3);
                        startActivity(intent);

                    }
                });

            }
        });

        cancelrequestinparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro.setVisibility(VISIBLE);
                Handler handler4 = new Handler(Looper.getMainLooper());
                handler4.post(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://"+i.getIp()+"/Termpaper/readToken.php?id="+ bundle.getString("id", "Default");
                        FetchData fetchData = new FetchData(url);
                        if (fetchData.startFetch())
                        {
                            if (fetchData.onComplete()) {
                                String result1 = fetchData.getResult();
                                String[] str = result1.split(";", 3);

                                String url1="http://"+i.getIp()+"/Termpaper/cancel.php?tid="+str[0];
                                FetchData fetchData1 = new FetchData(url1);
                                if (fetchData1.startFetch()) {
                                    if (fetchData1.onComplete()) {
                                        String result = fetchData1.getResult();

                                        if (result.equals("success")) {
                                            pro.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), "Ticket Cancel Successful", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(getActivity(),Homepage.class);
                                            intent.putExtras(bundleHome);
                                            startActivity(intent);



                                        } else {
                                            pro.setVisibility(View.GONE);

                                            Toast.makeText(getActivity(), "Ticket Cancellation Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }


                            }
                        }


                        pro.setVisibility(View.GONE);


                    }
                });


            }
        });



        cancelrequestinwarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro.setVisibility(VISIBLE);
                Handler handler4 = new Handler(Looper.getMainLooper());
                handler4.post(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://"+i.getIp()+"/Termpaper/readToken.php?id="+ bundle.getString("id", "Default");
                        FetchData fetchData = new FetchData(url);
                        if (fetchData.startFetch())
                        {
                            if (fetchData.onComplete()) {
                                String result1 = fetchData.getResult();
                                String[] str = result1.split(";", 3);

                                String url1="http://"+i.getIp()+"/Termpaper/cancel.php?tid="+str[0];
                                FetchData fetchData1 = new FetchData(url1);
                                if (fetchData1.startFetch()) {
                                    if (fetchData1.onComplete()) {
                                        String result = fetchData1.getResult();

                                        if (result.equals("success")) {
                                            pro.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), "Ticket Cancel Successful", Toast.LENGTH_SHORT).show();

                                            Intent intent=new Intent(getActivity(),Homepage.class);
                                            intent.putExtras(bundleHome);
                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(getActivity(), "Ticket Cancellation Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                            }
                        }

                        pro.setVisibility(View.GONE);


                    }
                });


            }
        });

        cancelrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro.setVisibility(VISIBLE);
                Handler handler4 = new Handler(Looper.getMainLooper());
                handler4.post(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://"+i.getIp()+"/Termpaper/readToken.php?id="+ bundle.getString("id", "Default");
                        FetchData fetchData = new FetchData(url);
                        if (fetchData.startFetch())
                        {
                            if (fetchData.onComplete()) {
                                String result1 = fetchData.getResult();
                                String[] str = result1.split(";", 3);

                                String url1="http://"+i.getIp()+"/Termpaper/cancel.php?tid="+str[0];
                                FetchData fetchData1 = new FetchData(url1);
                                if (fetchData1.startFetch()) {
                                    if (fetchData1.onComplete()) {
                                        String result = fetchData1.getResult();

                                        if (result.equals("success")) {
                                            pro.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), "Ticket Cancel Successful", Toast.LENGTH_SHORT).show();

                                            Intent intent=new Intent(getActivity(),Homepage.class);
                                            intent.putExtras(bundleHome);
                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(getActivity(), "Ticket Cancellation Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                            }
                        }

                        pro.setVisibility(View.GONE);


                    }
                });


            }
        });

        cancelrequestpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro.setVisibility(VISIBLE);
                Handler handler4 = new Handler(Looper.getMainLooper());
                handler4.post(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://"+i.getIp()+"/Termpaper/readToken.php?id="+ bundle.getString("id", "Default");
                        FetchData fetchData = new FetchData(url);
                        if (fetchData.startFetch())
                        {
                            if (fetchData.onComplete()) {
                                String result1 = fetchData.getResult();
                                String[] str = result1.split(";", 3);

                                String url1="http://"+i.getIp()+"/Termpaper/cancel.php?tid="+str[0];
                                FetchData fetchData1 = new FetchData(url1);
                                if (fetchData1.startFetch()) {
                                    if (fetchData1.onComplete()) {
                                        String result = fetchData1.getResult();

                                        if (result.equals("success")) {
                                            pro.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), "Ticket Cancel Successful", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(getActivity(),Homepage.class);
                                            intent.putExtras(bundleHome);
                                            startActivity(intent);



                                        } else {
                                            pro.setVisibility(View.GONE);

                                            Toast.makeText(getActivity(), "Ticket Cancellation Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }


                            }
                        }


                        pro.setVisibility(View.GONE);


                    }
                });


            }
        });

        cancelrequestwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pro.setVisibility(VISIBLE);
                Handler handler4 = new Handler(Looper.getMainLooper());
                handler4.post(new Runnable() {
                    @Override
                    public void run() {
                        String url="http://"+i.getIp()+"/Termpaper/readToken.php?id="+ bundle.getString("id", "Default");
                        FetchData fetchData = new FetchData(url);
                        if (fetchData.startFetch())
                        {
                            if (fetchData.onComplete()) {
                                String result1 = fetchData.getResult();
                                String[] str = result1.split(";", 3);

                                String url1="http://"+i.getIp()+"/Termpaper/cancel.php?tid="+str[0];
                                FetchData fetchData1 = new FetchData(url1);
                                if (fetchData1.startFetch()) {
                                    if (fetchData1.onComplete()) {
                                        String result = fetchData1.getResult();

                                        if (result.equals("success")) {
                                            pro.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), "Ticket Cancel Successful", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(getActivity(),Homepage.class);
                                            intent.putExtras(bundleHome);
                                            startActivity(intent);



                                        } else {
                                            pro.setVisibility(View.GONE);

                                            Toast.makeText(getActivity(), "Ticket Cancellation Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }


                            }
                        }


                        pro.setVisibility(View.GONE);


                    }
                });


            }
        });

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=c.getTid()+","+c.getPhone();
                Bundle qrbundle=new Bundle();
                qrbundle.putString("url",url);
                Intent qrintent=new Intent(getActivity(),QR.class);
                qrintent.putExtras(qrbundle);
                startActivity(qrintent);
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Homepage.class);
                intent.putExtras(bundleHome);
                startActivity(intent);

            }
        });



        return view;
    }



    private void showDateTimeDialog(EditText expectedouttime) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd HH:mm");

                        expectedouttime.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(getActivity(),timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };
        new DatePickerDialog(getActivity(),dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


}