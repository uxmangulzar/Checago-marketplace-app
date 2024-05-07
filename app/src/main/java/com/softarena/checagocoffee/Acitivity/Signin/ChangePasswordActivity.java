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
import android.widget.Toast;

import com.google.gson.Gson;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserRequestModel;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserResponseModel;
import com.softarena.checagocoffee.Helper.GlobalClass;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;
import com.softarena.checagocoffee.Rest.ApiInterface;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText ed_changepass,ed_change_Cpass;
    Button btn_savePassword;
    private String userId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        userId=getIntent().getStringExtra("user_id");
        initViews();
        ed_changepass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ed_change_Cpass.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                ed_changepass.setBackground(getDrawable(R.drawable.edit_text_with_stroke));

                return false;
            }
        });
        ed_change_Cpass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ed_change_Cpass.setBackground(getDrawable(R.drawable.edit_text_with_stroke));
                ed_changepass.setBackground(getDrawable(R.drawable.edit_text_without_strock));
                return false;
            }
        });
        btn_savePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ed_change_Cpass.getText().toString().equals("")){
                    if (ed_change_Cpass.getText().toString().equals(ed_change_Cpass.getText().toString())){
                        updatePass(ChangePasswordActivity.this);
                    }
                }
            }
        });
    }
    public void initViews()
    {
        ed_changepass = findViewById(R.id.change_password);
        ed_change_Cpass = findViewById(R.id.change_cpassword);
        btn_savePassword = findViewById(R.id.btn_savePassword);
    }
    public void updatePass(final Activity context) {

        GlobalClass.showLoading(context, context.getResources().getString(R.string.please_wait));
        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        UserRequestModel userRequestModel = new UserRequestModel();
        userRequestModel.setUser_id(userId);
        userRequestModel.setNew_password(ed_changepass.getText().toString());



        Call<UserResponseModel> call = apiService.updatePass(userRequestModel);

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
                                    modelSigninReceive.getResult() + "",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, LoginActivity.class);
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
