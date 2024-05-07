package com.softarena.checagocoffee.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public  class ConverterBitmap {

    public static byte[] convertToByteArray(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
