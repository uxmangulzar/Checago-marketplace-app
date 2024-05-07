package com.softarena.checagocoffee.Acitivity.Reviews;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.softarena.checagocoffee.Acitivity.Home.MainMenuDrawerActivity;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserResponseModel;
import com.softarena.checagocoffee.Helper.GlobalClass;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.Acitivity.Orders.OrderRequestModel;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;
import com.softarena.checagocoffee.Rest.ApiInterface;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingActivity extends AppCompatActivity {
    Button btn_giveRating;
    RatingBar ratingBar;
    TextView tv_ratingComment;
    private String orderid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        if (getIntent()!=null){
            orderid=getIntent().getStringExtra("orderid");
        }
        btn_giveRating = findViewById(R.id.btn_giveRating);
        tv_ratingComment = findViewById(R.id.tv_ratingComment);
        ratingBar = findViewById(R.id.ratingBar);
        btn_giveRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ratingBar.getRating()>0&&!tv_ratingComment.getText().toString().equals("")){
                    addOrderRatings(RatingActivity.this);
                }
            }
        });

    }
    public void onBackPress(View view)
    {
        onBackPressed();
    }
    public void addOrderRatings(final Activity context) {
        GlobalClass.showLoading(context, context.getResources().getString(R.string.please_wait));
        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        final String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, getApplicationContext());
        final String userid = SessionManager.getStringPref(HelperKeys.USER_ID, getApplicationContext());
        OrderRequestModel orderRequestModel = new OrderRequestModel();
        orderRequestModel.setUser_id(userid);
        orderRequestModel.setOrder_id(orderid);
        orderRequestModel.setComment(tv_ratingComment.getText().toString());
        orderRequestModel.setRating(String.valueOf(ratingBar.getRating()));


        Call<UserResponseModel> call = apiService.addOrderRating(accessToken,orderRequestModel);

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
