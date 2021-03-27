package com.example.term;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class LoginActivity extends AppCompatActivity {
    String username, password;
    TextInputEditText user, pass;
    ProgressBar progressBar;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        IP i=new IP();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getSupportActionBar().hide();
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.buttonLogin);
        progressBar = findViewById(R.id.progress);
        pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        pass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=String.valueOf(user.getText());
                password=String.valueOf(pass.getText());

                //Toast.makeText(getApplicationContext(),password,Toast.LENGTH_SHORT).show();

                if(!username.equals("") && !password.equals(""))
                {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;

                            PutData putData = new PutData("http://"+i.getIp()+"/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    //End ProgressBar (Set visibility to GONE)
                                    if(result.equals("success"))
                                    {

                                        cookie c=new cookie();
                                        String url="http://"+i.getIp()+"/readTest.php?username="+username;
                                        FetchData fetchData = new FetchData(url);
                                        if (fetchData.startFetch()) {
                                            if (fetchData.onComplete()) {
                                                String result1 = fetchData.getResult();

                                                String[] str = result1.split(";", 4);
                                                c.setId(str[0]);
                                                c.setName(str[1]);
                                                c.setParentName(str[2]);
                                                c.setPhone(str[3]);


                                                //End ProgressBar (Set visibility to GONE)

                                            }
                                        }
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getApplicationContext(),Homepage.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("id",c.getId());
                                        bundle.putString("name", c.getName());
                                        bundle.putString("parent", c.getParentName());
                                        bundle.putString("phone", c.getPhone());

                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        finish();

                                    }
                                    else
                                    {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                            //End Write and Read data with URL
                        }
                    });

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Enter Valid Credential",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}