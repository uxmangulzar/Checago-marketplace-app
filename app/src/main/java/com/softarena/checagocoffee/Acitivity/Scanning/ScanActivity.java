package com.softarena.checagocoffee.Acitivity.Scanning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.utils.Contents;
import com.softarena.checagocoffee.utils.ConverterBitmap;
import com.softarena.checagocoffee.utils.QRCodeEncoder;

public class ScanActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 0x0000c0de;
    public static final String MyPREFERENCES = "MyPrefsssss" ;
    public static final String Beep = "beep";
    public static final String Vibrate = "vibrate";
    public static final String Web = "web";
    private String vibration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        SharedPreferences mPrefs = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE); //add key
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        String beep = mPrefs.getString(Beep, "disabled");
        vibration = mPrefs.getString(Vibrate, "disabled");
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setBarcodeImageEnabled(true);
       // integrator.setCaptureActivity(CaptureActivityPortrait.class);
        integrator.setOrientationLocked(true);
        integrator.setCameraId(0);
        if (beep.equals("disabled")){
            integrator.setBeepEnabled(false);
        }else {
            integrator.setBeepEnabled(true);
        }
        integrator.setPrompt("Scan QR Code");
        integrator.setTimeout(150000);
        integrator.initiateScan();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (scanResult != null) {
                    // handle scan result
//                    if (vibration.equals("disabled")){
//
//                    }else {
//                        //vibrate();
//                    }
                    String contents = scanResult.getContents();
                    String pathImage = scanResult.getBarcodeImagePath();
                    int qrCodeDimention = 500;
                    QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(contents, null,
                            Contents.Type.TEXT, BarcodeFormat.QR_CODE.toString(), qrCodeDimention,0);
                    try {
                        Bitmap bitmap1 = qrCodeEncoder.encodeAsBitmap();
                        byte[] bytes= ConverterBitmap.convertToByteArray(bitmap1);

                        Intent intentProduct = new Intent(this, ScanCompleteActivity.class);
                        intentProduct.putExtra("contents", contents);
                        intentProduct.putExtra("path", pathImage);
                        intentProduct.putExtra("qr", bytes);
                        startActivity(intentProduct);
                        finish();
                        Toast.makeText(this, ""+contents, Toast.LENGTH_SHORT).show();
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                }
                // else continue with any other code you need in the method
            }else {
                finish();
            }
        }
    }




}
