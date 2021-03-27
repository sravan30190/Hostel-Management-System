package com.example.term;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

public class sms extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    cookie c=new cookie();
    String msg;
    IP i=new IP();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        Bundle bundle = getIntent().getExtras();
        c.setId(bundle.getString("id", "Default"));
        c.setName(bundle.getString("name", "Default"));
        c.setParentName(bundle.getString("parent", "Default"));
        c.setPhone(bundle.getString("phone", "Default"));
        c.setTid(bundle.getString("tid", "Default"));
        msg="Your child has applied for outing. please click on the link below to respond.\nhttp://"+i.getIp()+"/parent.php?tid="+c.getTid();
        sendSMSMessage();
    }
    private void sendSMSMessage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS))
            {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch(requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(c.getPhone(), null, msg, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }

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
        }


    }

}