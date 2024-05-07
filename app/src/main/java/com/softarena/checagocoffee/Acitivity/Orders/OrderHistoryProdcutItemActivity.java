package com.softarena.checagocoffee.Acitivity.Orders;

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
import com.softarena.checagocoffee.Acitivity.Products.AllProduct;
import com.softarena.checagocoffee.Acitivity.Products.ProductsResponseModel;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;
import com.softarena.checagocoffee.Rest.ApiInterface;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryProdcutItemActivity extends AppCompatActivity {
    RecyclerView recycler_orderItemList;
    List<AllProduct> manageOrderListDetailItemModelList;
    private String order_id="";
    private OrderHistoryDetailItemAdapter orderHistoryDetailItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_prodcut_item);
        if (getIntent()!=null){
            order_id=getIntent().getStringExtra("order_id");
        }
        recycler_orderItemList = findViewById(R.id.recycler_orderItemList);
        recycler_orderItemList.setLayoutManager(new LinearLayoutManager(OrderHistoryProdcutItemActivity.this));
        manageOrderListDetailItemModelList = new ArrayList<>();

        orderHistoryDetailItemAdapter=new OrderHistoryDetailItemAdapter(OrderHistoryProdcutItemActivity.this, manageOrderListDetailItemModelList);
        recycler_orderItemList.setAdapter(orderHistoryDetailItemAdapter);
        initViews();
        getAllOrderProducts(this);
    }
    public void initViews()
    {
        recycler_orderItemList = findViewById(R.id.recycler_orderItemList);
    }
    public void onBackPress(View view)
    {
        onBackPressed();
    }

    private void getAllOrderProducts(final Activity context) {
        GlobalClass.showLoading(context, getString(R.string.please_wait));

        String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);
        String user_id = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        OrderRequestModel orderRequestModel = new OrderRequestModel();
        orderRequestModel.setLimit("100");
        orderRequestModel.setPage("1");
        orderRequestModel.setUser_id(user_id);
        orderRequestModel.setOrder_id(order_id);

        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        Call<ProductsResponseModel> call = apiService
                .getOrderproducts(accessToken, orderRequestModel);
        Log.d("Response", "URL==" + call.request().url());
        call.enqueue(new Callback<ProductsResponseModel>() {
            @Override
            public void onResponse(Call<ProductsResponseModel> call,
                                   Response<ProductsResponseModel> response) {

                GlobalClass.dismissLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().isError()) {
                            manageOrderListDetailItemModelList.clear();
                            if (response.body().getOrder_products()!=null){
                                for (int i=0;i<response.body().getOrder_products().size();i++){
                                    AllProduct allProduct=new AllProduct();
                                    allProduct=response.body().getOrder_products().get(i);
                                    allProduct.setExpanded(false);
                                    manageOrderListDetailItemModelList.add(allProduct);
                                }
                            }
                            orderHistoryDetailItemAdapter.notifyDataSetChanged();

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
            public void onFailure(Call<ProductsResponseModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("Response", "onFailure" + t.toString());
                Toast.makeText(context, "Server down", Toast.LENGTH_LONG).show();

                GlobalClass.dismissLoading();
            }
        });
    }
}