package com.softarena.checagocoffee.Acitivity.Scanning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.softarena.checagocoffee.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScanCompleteActivity extends AppCompatActivity {
    private TextView textViewContent;
    private ImageView imageViewProduct;
    private String contents="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_complete);
        textViewContent=findViewById(R.id.tv_content);
        imageViewProduct=findViewById(R.id.img_product);
        Intent results=getIntent();
        contents=results.getStringExtra("contents");
        if (getIntent().getStringExtra("from")==null){
            if (contents!=null ) {
                textViewContent.setText(contents);
                String pathImage=results.getStringExtra("path");
                //Glide.with(this).load(pathImage).into(imageViewProduct);
                byte[] byteArray = getIntent().getByteArrayExtra("qr");
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                imageViewProduct.setImageBitmap(bmp);

            }
        }
    }
}