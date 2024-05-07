package com.softarena.checagocoffee.Acitivity.Profile;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.softarena.checagocoffee.Acitivity.Signin.Model.FilesModel;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserRequestModel;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserResponseModel;
import com.softarena.checagocoffee.Acitivity.Signin.SelectLocationActivity;
import com.softarena.checagocoffee.Acitivity.Home.MainMenuDrawerActivity;
import com.softarena.checagocoffee.Helper.GlobalClass;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.Acitivity.Signin.UsermainResponseModel;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class EditProfileFragment extends Fragment {

    ImageView profile_Image;
    private Uri filePathUri=null;
    EditText ed_changeName,ed_changeEmail,ed_changePassword,ed_postalcode
            ,ed_city,ed_state,ed_changeCpassword,ed_changeBankingInfo,ed_changePhoneNumber,ed_changeAddress;
    private double latitude=0.0;
    private double longitude=0.0;
    Button btn_update;
    private UserResponseModel userResponseModel;
    private String city="";
    private String state="";
    private String zip_code="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        userResponseModel=new UserResponseModel();
        profile_Image = view.findViewById(R.id.profile_image);
        btn_update = view.findViewById(R.id.btn_update);
        ed_changeName = view.findViewById(R.id.ed_changeName);
        ed_changeEmail = view.findViewById(R.id.ed_changeEmail);
        ed_changePassword = view.findViewById(R.id.ed_changePassword);
        ed_changeCpassword = view.findViewById(R.id.ed_changeCpassword);
        ed_postalcode = view.findViewById(R.id.ed_postalcode);
        ed_city = view.findViewById(R.id.ed_city);
        ed_state = view.findViewById(R.id.ed_state);
        ed_changeBankingInfo = view.findViewById(R.id.ed_changeBankingInfo);
        ed_changePhoneNumber = view.findViewById(R.id.ed_changePhoneNumber);
        ed_changeAddress = view.findViewById(R.id.ed_changeAddress);



        profile_Image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                onImage();
            }
        });

       onClicks();
        getAllUserdata(getActivity());
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validationCheck();
            }
        });
         return view;
    }
    private void validationCheck() {
        String fullName = ed_changeName.getText().toString().trim();
        String phoneNumber = ed_changePhoneNumber.getText().toString().trim();
        String address = ed_changeAddress.getText().toString().trim();

        //validate data here
        if (TextUtils.isEmpty(fullName)) {
            ed_changeName.setError("Please Enter Name.....");
            return;
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            ed_changePhoneNumber.setError("Please Enter phone....");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            ed_changeAddress.setError("Please Enter address....");
            return;

        }


        if (filePathUri!=null){
            uploadFile(getActivity(), filePathUri.getPath(), "profile");

        }else {
            sendData(getActivity());
        }




    }
    public void onClicks()
    {
        ed_changeName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                ed_changeName.setBackground(getActivity().getDrawable(R.drawable.edit_text_with_stroke));
                ed_changeEmail.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changePassword.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeCpassword.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changePhoneNumber.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeAddress.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeBankingInfo.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                return false;
            }
        });
        ed_changeEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ed_changeName.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeEmail.setBackground(getActivity().getDrawable(R.drawable.edit_text_with_stroke));
                ed_changePassword.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeCpassword.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changePhoneNumber.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeAddress.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeBankingInfo.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                return false;
            }
        });
        ed_changePassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ed_changeName.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeEmail.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changePassword.setBackground(getActivity().getDrawable(R.drawable.edit_text_with_stroke));
                ed_changeCpassword.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changePhoneNumber.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeAddress.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeBankingInfo.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                return false;
            }
        });
        ed_changeCpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ed_changeName.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeEmail.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changePassword.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeCpassword.setBackground(getActivity().getDrawable(R.drawable.edit_text_with_stroke));
                ed_changePhoneNumber.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeAddress.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeBankingInfo.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                return false;
            }
        });
        ed_changeBankingInfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ed_changeName.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeEmail.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changePassword.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeCpassword.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changePhoneNumber.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeAddress.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeBankingInfo.setBackground(getActivity().getDrawable(R.drawable.edit_text_with_stroke));
                return false;
            }
        });
        ed_changePhoneNumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ed_changeName.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeEmail.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changePassword.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeCpassword.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changePhoneNumber.setBackground(getActivity().getDrawable(R.drawable.edit_text_with_stroke));
                ed_changeAddress.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeBankingInfo.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                return false;
            }
        });
        ed_changeAddress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                Intent intent = new Intent(getActivity(), SelectLocationActivity.class);
                startActivityForResult(intent, 210);
                ed_changeName.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeEmail.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changePassword.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeCpassword.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changePhoneNumber.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                ed_changeAddress.setBackground(getActivity().getDrawable(R.drawable.edit_text_with_stroke));
                ed_changeBankingInfo.setBackground(getActivity().getDrawable(R.drawable.edit_text_without_strock));
                    return true;
                }
                return false;
            }
        });
    }
    public void onImage() {

        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        CropImage.activity().start( getContext(),EditProfileFragment.this);

                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            new AlertDialog.Builder(getActivity())
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                if (result != null) {
                    filePathUri = result.getUri();
                    Log.i("Tah",filePathUri.toString());
                    profile_Image.setImageURI(filePathUri);
                }else {
                    Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                }

            }
        }
        if (requestCode == 210) {
            if (!data.getStringExtra("lat").equals("")){
                ed_changeAddress.setText(data.getStringExtra("locationselected"));
                latitude=Double.parseDouble(data.getStringExtra("lat"));
                longitude=Double.parseDouble(data.getStringExtra("lon"));
                city=data.getStringExtra("city");
                state=data.getStringExtra("state");
                zip_code=data.getStringExtra("zip_code");
                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
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

                ed_postalcode.setText(zip_code);
                ed_city.setText(city);
                ed_state.setText(state);
            }

        }
    }
    private void getAllUserdata(final Activity context) {
        GlobalClass.showLoading(context, getString(R.string.please_wait));

        String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);
        String user_id = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        UserRequestModel userRequestModel = new UserRequestModel();
        userRequestModel.setUser_id(user_id);

        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        Call<UsermainResponseModel> call = apiService
                .getUserByuserId(accessToken, userRequestModel);
        Log.d("Response", "URL==" + call.request().url());
        call.enqueue(new Callback<UsermainResponseModel>() {
            @Override
            public void onResponse(Call<UsermainResponseModel> call,
                                   Response<UsermainResponseModel> response) {

                GlobalClass.dismissLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().isError()) {
                            if (response.body().getCustomer_details()!=null){
                                userResponseModel =response.body().getCustomer_details().get(0);
                                String zip_code1 = userResponseModel.getUser_zip_code();
                                String city1 = userResponseModel.getUser_city();
                                String state1 = userResponseModel.getUser_state();
                                ed_postalcode.setText(zip_code1);
                                ed_city.setText(city1);
                                ed_state.setText(state1);
                                ed_changeName.setText(userResponseModel.getuSERNAME());
                                ed_changeEmail.setText(userResponseModel.getuSEREMAIL());
                                ed_changePhoneNumber.setText(userResponseModel.getUserPhone());
                                ed_changeAddress.setText(userResponseModel.getUSER_ADDRESS());
                                Glide.with(context).load(APIClient.BASE_URL+userResponseModel.getuSERIMAGE()).into(profile_Image);
                                latitude=Float.valueOf(userResponseModel.getUSER_LAT());
                                longitude=Float.valueOf(userResponseModel.getUSER_LNG());

                            }


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
            public void onFailure(Call<UsermainResponseModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("Response", "onFailure" + t.toString());
                Toast.makeText(context, "Server down", Toast.LENGTH_LONG).show();

                GlobalClass.dismissLoading();
            }
        });
    }
    private void uploadFile(final Activity context, String path, final String type) {

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

                    sendData(getActivity());

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
        String user_id = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        UpdateUserRequestModel updateShopRequestModel = new UpdateUserRequestModel();
        updateShopRequestModel.setUser_id(user_id);
        updateShopRequestModel.setLat(String.valueOf(latitude));
        updateShopRequestModel.setLng(String.valueOf(longitude));
        updateShopRequestModel.setUser_name(ed_changeName.getText().toString());
        updateShopRequestModel.setAddress(ed_changeAddress.getText().toString());
        updateShopRequestModel.setPhone_number(ed_changePhoneNumber.getText().toString());
        updateShopRequestModel.setCity(ed_city.getText().toString());
        updateShopRequestModel.setState(ed_state.getText().toString());
        updateShopRequestModel.setZip_code(ed_postalcode.getText().toString());



        if (filePathUri!=null){
            List<FilesModel> profileFiles = new ArrayList<>();
            profileFiles.add(new FilesModel(new File(filePathUri.getPath()).getName()));
            updateShopRequestModel.setFiles(profileFiles);
        }else {
            String[] parts = userResponseModel.getuSERIMAGE().split("/");
            final String fileName = parts[parts.length-1];

            List<FilesModel> profileFiles = new ArrayList<>();
            profileFiles.add(new FilesModel(fileName));
            updateShopRequestModel.setFiles(profileFiles);
        }


        final String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);


        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        Call<UserResponseModel> call = apiService
                .updateCustomerById(accessToken,updateShopRequestModel);
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
                            Toast.makeText(context, "User Updated Successfully", Toast.LENGTH_SHORT).show();
                            SessionManager.putStringPref(HelperKeys.USER_LAT, String.valueOf(latitude), context);
                            SessionManager.putStringPref(HelperKeys.USER_LON, String.valueOf(longitude), context);
                            SessionManager.putStringPref(HelperKeys.USER_ADDRESS, ed_changeAddress.getText().toString(), context);
                            SessionManager.putStringPref(HelperKeys.USER_NAME,  ed_changeName.getText().toString(), context);

                            Intent intent = new Intent(context, MainMenuDrawerActivity.class);
                            startActivity(intent);
                            getActivity().finish();
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

}