package com.softarena.checagocoffee.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class BitmapRecycler {

    public static void recycleBitmap(ImageView imageView){
        Drawable drawable = imageView.getDrawable();
        if(drawable!=null && BitmapDrawable.class.isAssignableFrom(drawable.getClass())) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            if(bitmap != null && !bitmap.isRecycled())
                bitmap.recycle();
        }
    }
}
