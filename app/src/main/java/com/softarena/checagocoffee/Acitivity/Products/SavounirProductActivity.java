package com.softarena.checagocoffee.Acitivity.Products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavounirProductActivity extends AppCompatActivity {
    RecyclerView rlview_prods;
    TextView prodname;
    ImageView img_back;
    List<AllProduct> allProductList;
    private ProductSavounirAdapter productSavounirAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        productSavounirAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savounir_product);
        rlview_prods=findViewById(R.id.rlview_prods);
        prodname=findViewById(R.id.prodname);
        img_back = findViewById(R.id.img_back);
        allProductList = new ArrayList<>();
        rlview_prods.setLayoutManager(new GridLayoutManager( SavounirProductActivity.this,2));
        productSavounirAdapter=new ProductSavounirAdapter(allProductList,this);
        rlview_prods.setAdapter(productSavounirAdapter);
        if (getIntent()!=null){
            prodname.setText(getIntent().getStringExtra("subcat"));
        }

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getAllSavProducts(this);
    }
    private void getAllSavProducts(final Activity context) {
        GlobalClass.showLoading(context, getString(R.string.please_wait));

        String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);
        String user_id = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        ProductRequestModel productRequestModel = new ProductRequestModel();
        productRequestModel.setLimit("100");
        productRequestModel.setPage("1");
        productRequestModel.setUser_id(user_id);
        productRequestModel.setU_type("user");
        productRequestModel.setSub_cat_id(getIntent().getStringExtra("subcat_id"));

        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        Call<ProductsResponseModel> call = apiService
                .getProductsBysubcat(accessToken, productRequestModel);
        Log.d("Response", "URL==" + call.request().url());
        call.enqueue(new Callback<ProductsResponseModel>() {
            @Override
            public void onResponse(Call<ProductsResponseModel> call,
                                   Response<ProductsResponseModel> response) {

                GlobalClass.dismissLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().isError()) {
                            allProductList.clear();
                            if (response.body().getAllProducts()!=null&&response.body().getAllProducts().size()!=0){
                                allProductList.addAll(response.body().getAllProducts());
                            }
                            productSavounirAdapter.notifyDataSetChanged();

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