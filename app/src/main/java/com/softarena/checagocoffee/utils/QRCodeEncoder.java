package com.softarena.checagocoffee.utils;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

public final class QRCodeEncoder {
    private static  int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    private static final int RED = 0xFFED1B24;
    private static final int BLUE = 0xFF0808F4;
    private static final int YELLOW = 0xFFF4FB06;
    private static final int SKYBLUE = 0xFF15F0DF;
    private static final int GREEN = 0xFF0CF14B;
    private static int color ;
    private int colorID=0;

    private int dimension = Integer.MIN_VALUE;
    private String contents = null;
    private String displayContents = null;
    private String title = null;
    private BarcodeFormat format = null;
    private boolean encoded = false;

    public QRCodeEncoder(String data, Bundle bundle, String type, String format, int dimension,int colorID) {
        this.colorID=colorID;
        this.dimension = dimension;
        encoded = encodeContents(data, bundle, type, format);
        if (colorID==0){
            color=WHITE;
        }else if (colorID==1){
            color=SKYBLUE;
        }else if (colorID==2){
            color=GREEN;
        }else if (colorID==3){
            color=RED;
        }else if (colorID==4){
            color=YELLOW;
        }else if (colorID==5){
            color=BLUE;
        }else {
            color=WHITE;
        }
    }

    public String getContents() {
        return contents;
    }

    public String getDisplayContents() {
        return displayContents;
    }

    public String getTitle() {
        return title;
    }

    private boolean encodeContents(String data, Bundle bundle, String type, String formatString) {
        // Default to QR_CODE if no format given.
        format = null;
        if (formatString != null) {
            try {
                format = BarcodeFormat.valueOf(formatString);
            } catch (IllegalArgumentException iae) {
                // Ignore it then
            }
        }
        if (format == null || format == BarcodeFormat.QR_CODE) {
            this.format = BarcodeFormat.QR_CODE;
            encodeQRCodeContents(data, bundle, type);
        } else if (data != null && data.length() > 0) {
            contents = data;
            displayContents = data;
            title = "Text";
        }
        return contents != null && contents.length() > 0;
    }

    private void encodeQRCodeContents(String data, Bundle bundle, String type) {
        if (type.equals(Contents.Type.TEXT)) {
            if (data != null && data.length() > 0) {
                contents = data;
                displayContents = data;
                title = "Text";
            }
        }else if (type.equals(Contents.Type.EMAIL)) {
            data = trim(data);
            if (data != null) {
                String email=bundle.getString("Email");
                String subject=bundle.getString("Subject");
                String content=bundle.getString("Content");
                if (!email.equals("") && !subject.equals("") && !content.equals("")) {
                    contents = type+"   Mail To : " + email+"  Subject :  "+subject+" Content  : "+content;
                    displayContents = data;
                    title = "E-Mail";
                }
            }
        } else if (type.equals(Contents.Type.PHONE)) {
            data = trim(data);
            if (data != null) {
                String name=bundle.getString("Name");
                String number=bundle.getString("Number");
                if (!name.equals("") && !number.equals("")) {
                    contents = type +"   Name : "+name+"\n  Number : "+number;
                    displayContents = PhoneNumberUtils.formatNumber(data);
                    title = "Phone";
                }
            }
        } else if (type.equals(Contents.Type.WIFI)) {
            data = trim(data);
            if (data != null) {
                String ssid=bundle.getString("SSID");
                String password=bundle.getString("Password");
                String type1=bundle.getString("Type");
                if (!ssid.equals("") && !password.equals("") && !type1.equals("")) {
                    contents = type+"  SSID : " + ssid+"  Password :  "+password+" Network Type  : "+type1;
                    displayContents = data;
                    title = "WIFI";
                }
            }
        }else if (type.equals(Contents.Type.ADDRESS)) {
            data = trim(data);
            if (data != null) {
                String name=bundle.getString("Name");
                String company=bundle.getString("Company");
                String job=bundle.getString("Job");
                String address=bundle.getString("Address");
                String phone=bundle.getString("Phone");
                String email=bundle.getString("Email");
                String website=bundle.getString("Website");
                if (!name.equals("") && !company.equals("") && !job.equals("")&& !address.equals("") && !phone.equals("")
                        && !email.equals("") && !website.equals("")) {
                    contents = type+"  Name : " + name+"  Company :  "+company+" Job Title  : "+job
                            +"  Address :  "+address+" Phone  : "+phone+"  Email :  "+email+" Website  : "+website;
                    displayContents = data;
                    title = "ADDRESS";
                }
            }
        }else if (type.equals(Contents.Type.PRODUCT)) {
            data = trim(data);
            if (data != null) {
                String name=bundle.getString("Name");
                String code=bundle.getString("Code");
                String price=bundle.getString("Price");
                if (!name.equals("") && !code.equals("") && !price.equals("")) {
                    contents = type+"  Product : " + name+"  Product Code :  "+code+" Price  : "+price;
                    displayContents = data;
                    title = "SMS";
                }
            }
        } else if (type.equals(Contents.Type.SMS)) {
            data = trim(data);
            if (data != null) {
                String phone=bundle.getString("Phone");
                String content=bundle.getString("Content");
                String name=bundle.getString("Name");
                if (!phone.equals("") && !content.equals("") && !name.equals("")) {
                    contents = type+"  SMS To: " + phone+"  Content :  "+content+" Name  : "+name;
                    displayContents = data;
                    title = "SMS";
                }
            }
        } else if (type.equals(Contents.Type.CALENDER)) {
            data = trim(data);
            if (data != null) {
                String title1=bundle.getString("Title");
                String description=bundle.getString("Description");
                String start=bundle.getString("Start");
                String end=bundle.getString("End");
                if (!start.equals("") && !end.equals("")) {
                    contents = type+"  Title :  "+title1+" Description  : "+description+"  Start Date :  "+start+" End Date  : "+end;
                    displayContents = data;
                    title = "CALENDER";
                }
            }
        }  else if (type.equals(Contents.Type.BOOK)) {
            data = trim(data);
            if (data != null) {
                String name=bundle.getString("Name");
                String code=bundle.getString("Code");
                String price=bundle.getString("Price");
                if (!name.equals("") && !code.equals("") && !price.equals("")) {
                    contents = type+"   Book Name : " + name+"  Book Code :  "+code+" Book Price  : "+price;
                    displayContents = data;
                    title = "BOOK";
                }
            }
        } else if (type.equals(Contents.Type.LOCATION)) {
            if (bundle != null) {
                // These must use Bundle.getFloat(), not getDouble(), it's part of the API.
                float latitude = bundle.getFloat("Latitude");
                float longitude = bundle.getFloat("Longitude");
                String address=bundle.getString("Address");
                if (latitude != Float.MAX_VALUE && longitude != Float.MAX_VALUE) {
                    contents = type+"  Adress Name : "+address+"  Latitude : "+latitude  +"  Longitude : "+ longitude;
                    displayContents = latitude + "," + longitude;
                    title = "Location";
                }
            }
        }else if (type.equals(Contents.Type.URL_KEY)) {
            if (bundle != null) {
                String name=bundle.getString("Name");
                String url=bundle.getString("URL");
                if (!name.equals("") && !url.equals("")) {
                    contents = type +"   Name : "+name+"  URL : "+url;
                    displayContents = name + "," + url;
                    title = "Website";
                }
            }
        }
    }

    public Bitmap encodeAsBitmap() throws WriterException {
        if (!encoded) return null;

        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contents);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result = writer.encode(contents, format, dimension, dimension, hints);
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        // All are 0, or black, by default
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : color;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) { return "UTF-8"; }
        }
        return null;
    }

    private static String trim(String s) {
        if (s == null) { return null; }
        String result = s.trim();
        return result.length() == 0 ? null : result;
    }

    private static String escapeMECARD(String input) {
        if (input == null || (input.indexOf(':') < 0 && input.indexOf(';') < 0)) { return input; }
        int length = input.length();
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            if (c == ':' || c == ';') {
                result.append('\\');
            }
            result.append(c);
        }
        return result.toString();
    }
}
