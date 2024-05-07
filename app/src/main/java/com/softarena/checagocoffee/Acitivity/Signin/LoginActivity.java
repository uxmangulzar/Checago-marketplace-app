package com.softarena.checagocoffee.Acitivity.Signin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserRequestModel;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserResponseModel;
import com.softarena.checagocoffee.Acitivity.Home.MainMenuDrawerActivity;
import com.softarena.checagocoffee.Helper.GlobalClass;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;
import com.softarena.checagocoffee.Rest.ApiInterface;
import com.softarena.checagocoffee.Validations.ValidationChecks;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private TextView tv_signUp,tv_frogot_password;
    private Button btn_login;
    EditText login_email,login_password;
    ValidationChecks validationChecks = new ValidationChecks();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login);
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);

        login_email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                login_email.setBackground(getDrawable(R.drawable.edit_text_with_stroke));
                login_password.setBackground(getDrawable(R.drawable.edit_text_without_strock));

                return false;
            }
        });
        login_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                login_email.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                login_password.setBackground(getDrawable(R.drawable.edit_text_with_stroke));
                return false;
            }
        });

        tv_signUp= findViewById(R.id.tv_signUp);
        tv_frogot_password = findViewById(R.id.tv_frogot_password);
        tv_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        tv_frogot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
            }
        });


    }
    public void dashBoardLogin(View view) {
        validationCheck();

    }
    private void validationCheck() {

        if ((validationChecks.validateAnyName(login_email, "Please Enter Email"))
                && (validationChecks.validateEmail(login_email, "Please Enter Valid Email"))
                && (validationChecks.validateAnyName(login_password, "Please Enter Password"))
        ) {

            loginUser(this, login_email.getText().toString(), login_password.getText().toString(), "user");

        } else {
        }


    }
    public void loginUser(final Activity context, String email, String password, String type) {
        GlobalClass.showLoading(context, context.getResources().getString(R.string.please_wait));
        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        final String fcmToken = SessionManager.getStringPref(HelperKeys.FCM_Token, getApplicationContext());
        UserRequestModel modelSigninSending = new UserRequestModel(email, password, fcmToken, type);
//        setting parameters
        modelSigninSending.setUser_email(email);
        modelSigninSending.setFcm_token(fcmToken);
        modelSigninSending.setLogin_from("");
        modelSigninSending.setFrom_app("");
        modelSigninSending.setUser_password(password);
        modelSigninSending.setUser_type(type);
        Call<UserResponseModel> call = apiService.loginUser(modelSigninSending);

        Log.d("Response", "URL==" + call.request().url());
        call.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(Call<UserResponseModel> call, Response<UserResponseModel> response) {
                int statusCode = response.code();
                Log.e("main", "apt " + response.body());

                GlobalClass.dismissLoading();

                if (response.isSuccessful()) {
                    Log.d("Success", new Gson().toJson(response.body()));
                    if (response.body().getStatus().equals("200")) {
                        UserResponseModel modelSigninReceive = response.body();
                        if (response.body().getStatus().equals("200")) {
                            Toast.makeText(context,
                                    modelSigninReceive.getMessage() + "",
                                    Toast.LENGTH_SHORT).show();
                            SessionManager.putStringPref(HelperKeys.USER_LAT, modelSigninReceive.getUSER_LAT(), LoginActivity.this);
                            SessionManager.putStringPref(HelperKeys.USER_LON, modelSigninReceive.getUSER_LNG(), LoginActivity.this);
                            SessionManager.putStringPref(HelperKeys.USER_ADDRESS, modelSigninReceive.getUSER_ADDRESS(), LoginActivity.this);
                            SessionManager.putStringPref(HelperKeys.USER_NAME, modelSigninReceive.getuSERNAME(), LoginActivity.this);
                            SessionManager.putStringPref(HelperKeys.User_Access_Token, modelSigninReceive.getjWT(), LoginActivity.this);
                            SessionManager.putStringPref(HelperKeys.USER_ID, String.valueOf(modelSigninReceive.getuSERID()), LoginActivity.this);
                            SessionManager.putStringPref(HelperKeys.FCM_Token, fcmToken, LoginActivity.this);
                            SessionManager.putStringPref(HelperKeys.User_Type, modelSigninReceive.getuSERTYPE(), LoginActivity.this);
                            Intent intent = new Intent(context, MainMenuDrawerActivity.class);
                            context.startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(context,
                                    "User not found" + "",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "INVALID CREDENTIALS", Toast.LENGTH_SHORT).show();
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
