package com.example.term;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QR extends AppCompatActivity {

    String context;
    private ImageView qrCodeIV;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r);
        Bundle bundle = getIntent().getExtras();
        context=bundle.getString("url", "Default");
        qrCodeIV = findViewById(R.id.idIVQrcode);

        if(TextUtils.isEmpty(context)) {

            Toast.makeText(QR.this, "Enter some text to generate QR Code", Toast.LENGTH_SHORT).show();
        }else {

            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

            Display display = manager.getDefaultDisplay();

            Point point = new Point();
            display.getSize(point);

            int width = point.x;
            int height = point.y;

            int dimen = width < height ? width : height;
            dimen = dimen * 3 / 4;

            qrgEncoder = new QRGEncoder(context, null, QRGContents.Type.TEXT, dimen);
            try {

                bitmap = qrgEncoder.encodeAsBitmap();

                qrCodeIV.setImageBitmap(bitmap);
            } catch (WriterException e) {

                Toast.makeText(QR.this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        }



    }
}