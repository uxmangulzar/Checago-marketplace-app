package com.softarena.checagocoffee.Acitivity.Orders;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import com.softarena.checagocoffee.Acitivity.Category.AllCategoryResponseModel;
import com.softarena.checagocoffee.Acitivity.Category.CategoryRequestModel;
import com.softarena.checagocoffee.Acitivity.Category.GetAllCategoryResponseModel;
import com.softarena.checagocoffee.Helper.GlobalClass;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
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


public class OrderHistoryActivity extends AppCompatActivity implements SelectCategoryAdapter.OnItemClick{
    RecyclerView recyclerView_orderHistory;
    private List<OrderResponsemodel> orderHistoryModelList;
    private OrderHistoryAdapter orderHistoryAdapter;
    RecyclerView recycler_selectCategory;
    List<AllCategoryResponseModel> allCategoryResponseModelList;
    private SelectCategoryAdapter selectCategoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_order_history);
        allCategoryResponseModelList=new ArrayList<>();
        recycler_selectCategory = findViewById(R.id.recycler_btnselectCategory);
        recycler_selectCategory.setLayoutManager(new LinearLayoutManager(OrderHistoryActivity.this, LinearLayoutManager.HORIZONTAL, false));
        selectCategoryAdapter = new SelectCategoryAdapter(allCategoryResponseModelList, OrderHistoryActivity.this,this);
        recycler_selectCategory.setAdapter(selectCategoryAdapter);

        recyclerView_orderHistory = findViewById(R.id.recycler_orderHistoryItem);
        orderHistoryModelList = new ArrayList<>();
        recyclerView_orderHistory.setLayoutManager(new LinearLayoutManager(OrderHistoryActivity.this));
        orderHistoryAdapter=new OrderHistoryAdapter(orderHistoryModelList, OrderHistoryActivity.this);
        recyclerView_orderHistory.setAdapter(orderHistoryAdapter);
        getCategoryData(OrderHistoryActivity.this);
    }

    private void getAllShopOrders(final Activity context, String value) {
        GlobalClass.showLoading(context, getString(R.string.please_wait));

        String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);
        String user_id = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        OrderRequestModel orderRequestModel = new OrderRequestModel();
        orderRequestModel.setLimit("100");
        orderRequestModel.setPage("1");
        orderRequestModel.setCat_id(value);
        orderRequestModel.setU_type("user");
        orderRequestModel.setUser_id(user_id);

        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        Call<ShopResponseModel> call = apiService
                .getAllcustomerOrders(accessToken, orderRequestModel);
        Log.d("Response", "URL==" + call.request().url());
        call.enqueue(new Callback<ShopResponseModel>() {
            @Override
            public void onResponse(Call<ShopResponseModel> call,
                                   Response<ShopResponseModel> response) {

                GlobalClass.dismissLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().isError()) {
                            orderHistoryModelList.clear();
                            if (response.body().getAll_customer_orders()!=null){
                                orderHistoryModelList.addAll(response.body().getAll_customer_orders());
                                orderHistoryAdapter.notifyDataSetChanged();



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

    @Override
    public void onClick(String value) {
        getAllShopOrders(OrderHistoryActivity.this,value);
    }
    private void getCategoryData(final Activity context) {
        GlobalClass.showLoading(context, getString(R.string.please_wait));

        String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);
        String user_id = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        CategoryRequestModel categoryRequestModel = new CategoryRequestModel();
        categoryRequestModel.setLimit("100");
        categoryRequestModel.setPage("1");
        categoryRequestModel.setUser_id(user_id);

        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        Call<GetAllCategoryResponseModel> call = apiService
                .getAllCategories(accessToken, categoryRequestModel);
        Log.d("Response", "URL==" + call.request().url());
        call.enqueue(new Callback<GetAllCategoryResponseModel>() {
            @Override
            public void onResponse(Call<GetAllCategoryResponseModel> call,
                                   Response<GetAllCategoryResponseModel> response) {

                GlobalClass.dismissLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().isError()) {
                            if (response.body().getAllCategoryResponseModelList()!=null){
                                allCategoryResponseModelList.addAll(response.body().getAllCategoryResponseModelList());
                                selectCategoryAdapter.notifyDataSetChanged();
                                getAllShopOrders(context,response.body().getAllCategoryResponseModelList().get(0).getCategoryId());

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
            public void onFailure(Call<GetAllCategoryResponseModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("Response", "onFailure" + t.toString());
                Toast.makeText(context, "Server down", Toast.LENGTH_LONG).show();

                GlobalClass.dismissLoading();
            }
        });
    }

}