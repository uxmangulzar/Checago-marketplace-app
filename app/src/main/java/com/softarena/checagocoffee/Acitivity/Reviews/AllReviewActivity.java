package com.softarena.checagocoffee.Acitivity.Reviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.softarena.checagocoffee.Helper.GlobalClass;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.Acitivity.Shops.ShopRequestModel;
import com.softarena.checagocoffee.Acitivity.Shops.ShopResponseModel;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;
import com.softarena.checagocoffee.Rest.ApiInterface;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllReviewActivity extends AppCompatActivity {
    RecyclerView recyclerView_allReview;
    private List<ShopAllReview> allReviewModelList;
    private AllReviewAdapter allReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_review);
        recyclerView_allReview = findViewById(R.id.recycler_allReview);
        allReviewModelList = new ArrayList<>();
        recyclerView_allReview.setLayoutManager(new LinearLayoutManager(AllReviewActivity.this));
        allReviewAdapter=new AllReviewAdapter(allReviewModelList,AllReviewActivity.this);
        recyclerView_allReview.setAdapter(allReviewAdapter);
        getAllShopReviewsdata(this);
    }
    public void onBackPress(View view)
    {
        onBackPressed();
    }
    private void getAllShopReviewsdata(final Activity context) {
        GlobalClass.showLoading(context, getString(R.string.please_wait));

        String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);
        String user_id = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        ShopRequestModel shopRequestModel = new ShopRequestModel();
        shopRequestModel.setLimit("100");
        shopRequestModel.setPage("1");
        shopRequestModel.setUser_id(user_id);
        shopRequestModel.setShop_id(getIntent().getStringExtra("shopid"));

        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        Call<ShopResponseModel> call = apiService
                .getAllshopReviews(accessToken, shopRequestModel);
        Log.d("Response", "URL==" + call.request().url());
        call.enqueue(new Callback<ShopResponseModel>() {
            @Override
            public void onResponse(Call<ShopResponseModel> call,
                                   Response<ShopResponseModel> response) {

                GlobalClass.dismissLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().isError()) {
                            allReviewModelList.clear();
                            if (response.body().getShopAllReviews()!=null){
                                allReviewModelList.addAll(response.body().getShopAllReviews());
                                allReviewAdapter.notifyDataSetChanged();



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
            public void onFailure(Call<ShopResponseModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("Response", "onFailure" + t.toString());
                Toast.makeText(context, "Server down", Toast.LENGTH_LONG).show();

                GlobalClass.dismissLoading();
            }
        });
    }
}