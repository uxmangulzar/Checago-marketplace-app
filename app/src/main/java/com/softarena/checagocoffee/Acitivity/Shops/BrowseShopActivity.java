package com.softarena.checagocoffee.Acitivity.Shops;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.softarena.checagocoffee.Helper.GlobalClass;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;
import com.softarena.checagocoffee.Rest.ApiInterface;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import im.delight.android.location.SimpleLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseShopActivity extends AppCompatActivity {
    ImageView img_back;
    private RecyclerView rlview_shopnow;
    List<AllShop> shopNowLIstModelList;
    private ShopNowBrowseAdapter shopNowBrowseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_shop);
        img_back = findViewById(R.id.img_back);
        rlview_shopnow = findViewById(R.id.rlview_shopnow);
        rlview_shopnow.setLayoutManager(new LinearLayoutManager(this));
        shopNowLIstModelList = new ArrayList<>();
        shopNowBrowseAdapter=new ShopNowBrowseAdapter(shopNowLIstModelList,this,getIntent().getStringExtra("cat"));
         rlview_shopnow.setAdapter(shopNowBrowseAdapter);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getAllShopdata(this,getIntent().getStringExtra("cat"));
    }
    private void getAllShopdata(final Activity context, String cat) {
        GlobalClass.showLoading(context, getString(R.string.please_wait));

        String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);
        String user_id = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        ShopRequestModel shopRequestModel = new ShopRequestModel();
        shopRequestModel.setLimit("100");
        shopRequestModel.setPage("1");
        shopRequestModel.setUser_id(user_id);
        shopRequestModel.setU_type("user");
        shopRequestModel.setCat_id(cat);
        SimpleLocation location = new SimpleLocation(BrowseShopActivity.this);
        shopRequestModel.setUser_lat(SessionManager.getStringPref(HelperKeys.USER_LAT, context));
        shopRequestModel.setUser_lng(SessionManager.getStringPref(HelperKeys.USER_LON, context));

//        shopRequestModel.setUser_lat(String.valueOf(location.getLatitude()));
//        shopRequestModel.setUser_lng(String.valueOf(location.getLongitude()));

        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        Call<ShopResponseModel> call = apiService
                .getAllshopbycat(accessToken, shopRequestModel);
        Log.d("Response", "URL==" + call.request().url());
        call.enqueue(new Callback<ShopResponseModel>() {
            @Override
            public void onResponse(Call<ShopResponseModel> call,
                                   Response<ShopResponseModel> response) {

                GlobalClass.dismissLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().isError()) {
                            shopNowLIstModelList.clear();
                            if (response.body().getAllShops()!=null){
                                shopNowLIstModelList.addAll(response.body().getAllShops());
                                shopNowBrowseAdapter.notifyDataSetChanged();



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
