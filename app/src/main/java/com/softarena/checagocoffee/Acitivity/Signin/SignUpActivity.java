package com.softarena.checagocoffee.Acitivity.Signin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.softarena.checagocoffee.Acitivity.Signin.Model.FilesModel;
import com.softarena.checagocoffee.Acitivity.Signin.Model.SignUpRequestModel;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserResponseModel;
import com.softarena.checagocoffee.Helper.GlobalClass;
import com.softarena.checagocoffee.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.softarena.checagocoffee.Rest.APIClient;
import com.softarena.checagocoffee.Rest.ApiInterface;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    private TextView tv_signIn;
    private EditText et_fullName,et_emailAddress,et_password
            ,et_cPassword,et_phoneNumber,et_address,et_creditCardNumber,ed_shoppostalcode
            ,ed_shopcity,ed_shopstate,
            et_expiryDate,et_cvv;
    private static final int NEWLOCATION_REQUEST_CODE = 210;
    private Button btn_signUp;
    ImageView profile_image;
    private Uri filePathUri;
    private static final int LOCATION_REQUEST_CODE = 200;
    private String[] locationPermission;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private String city="";
    private String state="";
    private String zip_code="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        locationPermission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        if (checkLocationPermission()) {
            //permission is already granted
        } else {
            // permission is not granted yet
            requestLocationPermission();
        }
        //calling methods
        initViews();
        onClickListners();

    }
    public void initViews()
    {
        tv_signIn = findViewById(R.id.tv_signIn);
        et_fullName = findViewById(R.id.signup_FullName);
        et_emailAddress = findViewById(R.id.signup_email);
        et_password = findViewById(R.id.signup_password);
        et_cPassword = findViewById(R.id.signup_cPassword);
        et_phoneNumber = findViewById(R.id.signup_phoneNumber);
        et_address = findViewById(R.id.signup_address);
        et_creditCardNumber = findViewById(R.id.signup_card);
        et_expiryDate = findViewById(R.id.signup_expiryDate);
        et_cvv = findViewById(R.id.signup_cvv);
        ed_shoppostalcode = findViewById(R.id.ed_shoppostalcode);
        ed_shopcity = findViewById(R.id.ed_shopcity);
        ed_shopstate = findViewById(R.id.ed_shopstate);
        btn_signUp = findViewById(R.id.btn_signUp);
        profile_image = findViewById(R.id.profile_image);

    }


    //making variable for validations
    private  String fullName, emailAddress, password, cPassword, phoneNumber, address,
            creditCardNumber, expiryDate, cvv;
    public void validations()
    {
        boolean check=false;

        fullName = et_fullName.getText().toString().trim();
        emailAddress = et_emailAddress.getText().toString().trim();
        password = et_password.getText().toString().trim();
        cPassword = et_cPassword.getText().toString().trim();
        phoneNumber = et_phoneNumber.getText().toString().trim();
        address = et_address.getText().toString().trim();
        creditCardNumber = et_creditCardNumber.getText().toString().trim();
        expiryDate = et_expiryDate.getText().toString().trim();
        cvv = et_cvv.getText().toString().trim();
        //validate data here
        if (TextUtils.isEmpty(fullName)) {
            check=true;
            et_fullName.setError("Please Enter your Name"); }
        if (fullName.length()<3)
        {
            check=true;
            et_fullName.setError("Please Enter Name");
            return;
        }
        if (TextUtils.isEmpty(phoneNumber)||phoneNumber.length()<10) {
            check=true;
            et_phoneNumber.setError("Please Enter Phone Number");
            return;
        }
        if (phoneNumber.length()<9)
        {
            check=true;
            et_phoneNumber.setError("Please Enter a valid Phone Number with at least 10 digits");
            return;
        }
         if(!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches())
        {
            check=true;
              et_emailAddress.setError("Email Address is Invalid");
            return;
        }

         if (!password.equals(cPassword))
        {
            check=true;
            et_cPassword.setError("Password Does not matches....");
            return;
        }
        if (latitude == 0.0 || longitude == 0.0) {
            check=true;
            Toast.makeText(this, "Please select location.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (filePathUri != null) {
            if (!check){

                uploadFile(this, filePathUri.getPath(), "profile");
            }

        } else {
            Toast.makeText(this, "Please Insert Image", Toast.LENGTH_SHORT).show();
        }


    }
    private void uploadFile(final Activity context, String path, String type) {

        GlobalClass.showLoading(context, getString(R.string.please_wait));

        // Map is used to multipart the file using okhttp3.RequestBody

        List<MultipartBody.Part> partsList = new ArrayList<>();
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), new File(path));
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file[]", new File(path).getName(), requestBody);
        partsList.add(fileToUpload);

        RequestBody type1 = RequestBody.create(MediaType.parse("text/plain"), type);

        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        Call<UserResponseModel> call = apiService
                .uploadFile(partsList, type1);

        call.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse
                    (Call<UserResponseModel> call, retrofit2.Response<UserResponseModel> response) {
                GlobalClass.dismissLoading();
                UserResponseModel serverResponse = response.body();
                if (serverResponse != null) {
                    sendData(context);

                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
            }

            @Override
            public void onFailure(Call<UserResponseModel> call, Throwable t) {
                GlobalClass.dismissLoading();
                Toast.makeText(context, "check internet", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void sendData(final Activity context) {
        GlobalClass.showLoading(context, getString(R.string.please_wait));
        SignUpRequestModel signUpRequestModel = new SignUpRequestModel();
        signUpRequestModel.setUser_name(et_fullName.getText().toString());
        signUpRequestModel.setUser_email(et_emailAddress.getText().toString());
        signUpRequestModel.setUser_password(et_password.getText().toString());
        signUpRequestModel.setAddress(et_address.getText().toString());
        signUpRequestModel.setPhone_number(et_phoneNumber.getText().toString());
        signUpRequestModel.setCity(ed_shopcity.getText().toString());
        signUpRequestModel.setState(ed_shopstate.getText().toString());
        signUpRequestModel.setZip_code(ed_shoppostalcode.getText().toString());
        signUpRequestModel.setUser_type("user");
        //Cuurent location of user

        signUpRequestModel.setLat(String.valueOf(latitude));
        signUpRequestModel.setLng(String.valueOf(longitude));




        List<FilesModel> profileFiles = new ArrayList<>();
        profileFiles.add(new FilesModel(new File(filePathUri.getPath()).getName()));
        signUpRequestModel.setFiles(profileFiles);


        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        Call<UserResponseModel> call = apiService
                .signUpUser(signUpRequestModel);
        Log.d("Response", "URL==" + call.request().url());
        call.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(Call<UserResponseModel> call,
                                   Response<UserResponseModel> response) {

                GlobalClass.dismissLoading();


                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        UserResponseModel responseModel = response.body();
                        if (responseModel.getStatus().equals("200")) {
                            Toast.makeText(context, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(context, ""+responseModel.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        String errorMsg = jObjError.getString("message");
                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponseModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("Response", "onFailure" + t.toString());
                Toast.makeText(context, "Server down", Toast.LENGTH_SHORT).show();

                GlobalClass.dismissLoading();
            }
        });
    }
    public void onClickListners()
    {
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  onImage();
            }
        });
        tv_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validations();
            }
        });
        et_fullName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_fullName.setBackground(getDrawable(R.drawable.edit_text_with_stroke));
                et_emailAddress.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_password.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_cPassword.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_phoneNumber.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_address.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_creditCardNumber.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_expiryDate.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_cvv.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                return false;
            }
        });
        et_address.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    et_fullName.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                    et_address.setBackground(getDrawable(R.drawable.edit_text_with_stroke));
                    et_password.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                    et_cPassword.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                    et_phoneNumber.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                    et_emailAddress.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                    et_creditCardNumber.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                    et_expiryDate.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                    et_cvv.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                    Intent intent = new Intent(SignUpActivity.this, SelectLocationActivity.class);
                    startActivityForResult(intent, NEWLOCATION_REQUEST_CODE);
                    return true;
                }
                return false;
            }
        });
        et_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_fullName.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_emailAddress.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_password.setBackground(getDrawable(R.drawable.edit_text_with_stroke));
                et_cPassword.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_phoneNumber.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_address.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_creditCardNumber.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_expiryDate.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_cvv.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                return false;
            }
        });
        et_cPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_fullName.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_emailAddress.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_password.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_cPassword.setBackground(getDrawable(R.drawable.edit_text_with_stroke));
                et_phoneNumber.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_address.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_creditCardNumber.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_expiryDate.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_cvv.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                return false;
            }
        });
        et_phoneNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_fullName.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_emailAddress.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_password.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_cPassword.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_phoneNumber.setBackground(getDrawable(R.drawable.edit_text_with_stroke));
                et_address.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_creditCardNumber.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_expiryDate.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_cvv.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                return false;
            }
        });
        et_emailAddress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_fullName.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_address.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_password.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_cPassword.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_phoneNumber.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_emailAddress.setBackground(getDrawable(R.drawable.edit_text_with_stroke));
                et_creditCardNumber.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_expiryDate.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_cvv.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                return false;
            }
        });
        et_creditCardNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_fullName.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_emailAddress.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_password.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_cPassword.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_phoneNumber.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_address.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_creditCardNumber.setBackground(getDrawable(R.drawable.edit_text_with_stroke));
                et_expiryDate.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_cvv.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                return false;
            }
        });
        et_expiryDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_fullName.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_emailAddress.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_password.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_cPassword.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_phoneNumber.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_address.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_creditCardNumber.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_expiryDate.setBackground(getDrawable(R.drawable.edit_text_with_stroke));
                et_cvv.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                return false;
            }
        });
        et_cvv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                et_fullName.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_emailAddress.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_password.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_cPassword.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_phoneNumber.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_address.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_creditCardNumber.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_expiryDate.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                et_cvv.setBackground(getDrawable(R.drawable.edit_text_with_stroke));
                return false;
            }
        });
    }
    public void onImage() {

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        CropImage.activity().start( SignUpActivity.this);

                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            new AlertDialog.Builder(SignUpActivity.this)
                                    .setTitle("Permission required")
                                    .setMessage("Grant Permision to access the Gallery")
                                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.fromParts("package", "", null));
                                            startActivityForResult(intent, 50);
                                        }
                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                if (result != null) {
                    filePathUri = result.getUri();
                    Log.i("Tah",filePathUri.toString());
                    profile_image.setImageURI(filePathUri);
                }else {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                }

            }
        }
        if (requestCode == NEWLOCATION_REQUEST_CODE) {
            if (!data.getStringExtra("lat").equals("")){
                et_address.setText(data.getStringExtra("locationselected"));
                latitude=Double.parseDouble(data.getStringExtra("lat"));
                longitude=Double.parseDouble(data.getStringExtra("lon"));
                city=data.getStringExtra("city");
                state=data.getStringExtra("state");
                zip_code=data.getStringExtra("zip_code");
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    city = addresses.get(0).getLocality();
                    state = addresses.get(0).getAdminArea();
                    if (zip_code.equals("")){

                        zip_code = addresses.get(0).getPostalCode();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ed_shoppostalcode.setText(zip_code);
                ed_shopcity.setText(city);
                ed_shopstate.setText(state);
            }

        }
    }
    private boolean checkLocationPermission() {
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        return result;
    }

    // method to request location permission
    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, locationPermission, LOCATION_REQUEST_CODE);
    }
    public void onBackPressed(View view)
    {
        onBackPressed();
    }
    //method for checking location permission is granted or not

}
