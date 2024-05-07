package com.softarena.checagocoffee.Acitivity.Subcategories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class SubcategoriesActivity extends AppCompatActivity {
    RecyclerView recycler_foodtypes;
    List<AllSubcategoryResponseModel> subcategoryResponseModelList;
    ImageView img_back;
    TextView type;
    private String catId,catname="";
    private SubcategoriesAdapter subcategoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more_food);

        recycler_foodtypes = findViewById(R.id.recycler_foodtypes);
        type = findViewById(R.id.type);
        if (getIntent()!=null){
            catId=getIntent().getStringExtra("catId");
            catname=getIntent().getStringExtra("catname");
            type.setText(""+catname);
        }
        img_back = findViewById(R.id.img_back);
        recycler_foodtypes.setLayoutManager(new LinearLayoutManager(this));
        subcategoryResponseModelList = new ArrayList<>();
        subcategoriesAdapter=new SubcategoriesAdapter(subcategoryResponseModelList,this);
        recycler_foodtypes.setAdapter(subcategoriesAdapter);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getSubCategoryData(this,catId);
    }
    private void getSubCategoryData(final Activity context, String cat_id) {
        GlobalClass.showLoading(context, getString(R.string.please_wait));

        String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);
        String user_id = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        SubCategoryRequestModel subCategoryRequestModel = new SubCategoryRequestModel();
        subCategoryRequestModel.setLimit("100");
        subCategoryRequestModel.setPage("1");
        subCategoryRequestModel.setUser_id(user_id);
        subCategoryRequestModel.setCat_id(cat_id);

        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        Call<GetAllSubCategoryResponseModel> call = apiService
                .getAllSubCategories(accessToken, subCategoryRequestModel);
        Log.d("Response", "URL==" + call.request().url());
        call.enqueue(new Callback<GetAllSubCategoryResponseModel>() {
            @Override
            public void onResponse(Call<GetAllSubCategoryResponseModel> call,
                                   Response<GetAllSubCategoryResponseModel> response) {

                GlobalClass.dismissLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().isError()) {
                            subcategoryResponseModelList.clear();
                            if (response.body().getAllSubcategoryResponseModelList()!=null){
                                subcategoryResponseModelList.addAll(response.body().getAllSubcategoryResponseModelList());
                                subcategoriesAdapter.notifyDataSetChanged();



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
            public void onFailure(Call<GetAllSubCategoryResponseModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("Response", "onFailure" + t.toString());
                Toast.makeText(context, "Server down", Toast.LENGTH_LONG).show();

                GlobalClass.dismissLoading();
            }
        });
    }
}