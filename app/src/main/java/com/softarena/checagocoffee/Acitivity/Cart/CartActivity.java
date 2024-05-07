package com.softarena.checagocoffee.Acitivity.Cart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserRequestModel;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserResponseModel;
import com.softarena.checagocoffee.Acitivity.Home.MainMenuDrawerActivity;
import com.softarena.checagocoffee.Acitivity.Signin.SelectLocationActivity;
import com.softarena.checagocoffee.Acitivity.card.ChooseCardActivity;
import com.softarena.checagocoffee.Helper.DatabaseHelper;
import com.softarena.checagocoffee.Helper.GlobalClass;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.Acitivity.Products.AllProduct;
import com.softarena.checagocoffee.Acitivity.Ingredients.IngTypeRequestModel;
import com.softarena.checagocoffee.Acitivity.Ingredients.IngrediantItemModel;
import com.softarena.checagocoffee.Acitivity.Orders.OrderRequestModel;
import com.softarena.checagocoffee.Acitivity.Ingredients.ProductIngRequestModel;
import com.softarena.checagocoffee.Acitivity.Products.ProductRequestModel;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;
import com.softarena.checagocoffee.Rest.ApiInterface;

import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private ImageView img_back;
    private RecyclerView rl_orderSummary;
    private Button btn_placeorder;
    private TextView tv_total,tv_Address,tv_ShopPhone,tvshipcharges,tv_ShopName,tv_subtotal,tvservice,tv_shipAddress,tvtax;
    private CartAdapter cartAdapter;
    List<AllProduct> productList;
    double finalPrice=0;
    ConstraintLayout ln34;
    private DatabaseHelper databaseHelper;
    double totalWeight=0;
    private List<AllProduct> allProductsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        productList=new ArrayList<>();
        final ScrollView main = (ScrollView) findViewById(R.id.scrollView);

        main.post(new Runnable() {

            public void run() {

                main.scrollTo(0,0);
            }
        });
        initView();
        onClick();




        databaseHelper=new DatabaseHelper(getApplicationContext());
        allProductsList=databaseHelper.getAllData();
        productList.addAll(allProductsList);
        tv_ShopName.setText(allProductsList.get(0).getAllShop().getShopName());
        tv_Address.setText(allProductsList.get(0).getAllShop().getShopAddress());
        tv_shipAddress.setText(allProductsList.get(0).getAllShop().getShopAddress());

        for (int i=0;i<allProductsList.size();i++){
            double price=0;
            for (int j=0;j<allProductsList.get(i).getIngredientAll().size();j++){
                price=Double.parseDouble(allProductsList.get(i).getIngredientAll().get(j).getIngredientPrice())+price;
            }
            if (allProductsList.get(0).getProduct_cat_id()==2){
                totalWeight=totalWeight+(Double.parseDouble(allProductsList.get(i).getProduct_weight())*Integer.parseInt(allProductsList.get(i).getProductQuantity()));
            }

           finalPrice=finalPrice+Double.parseDouble(allProductsList.get(i).getProductQuantity())*
                   (Double.parseDouble(allProductsList.get(i).getProductSizes().get(0).getProductPrice())+price);
        }
        DecimalFormat form = new DecimalFormat("#.00");
        finalPrice= Double.parseDouble(form.format(finalPrice));
        tv_subtotal.setText("ZAR"+String.format("%.2f", finalPrice));
        tvservice.setText("ZAR"+String.format("%.2f", (finalPrice/100)*Double.parseDouble(allProductsList.get(0).getProduct_service_fee())));
        double totalpr = finalPrice + (finalPrice/100)*Double.parseDouble(allProductsList.get(0).getProduct_service_fee());
        tvtax.setText("ZAR"+String.format("%.2f", (finalPrice/100)*Double.parseDouble("10.25")));
        totalpr=totalpr+((finalPrice/100)*Double.parseDouble("10.25"));
        tv_total.setText("ZAR"+String.format("%.2f", totalpr));

        rl_orderSummary.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter=new CartAdapter(productList,this,tv_total,finalPrice,tv_subtotal,ln34,tvshipcharges,totalWeight,tvservice,tvtax);
        rl_orderSummary.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
//        if (allProductsList.get(0).getProduct_cat_id()==2) {
            findViewById(R.id.shiping).setVisibility(View.VISIBLE);
            findViewById(R.id.linship).setVisibility(View.VISIBLE);
//            getShippingrates(this,allProductsList.get(0),totalpr);
        ln34.setVisibility(View.VISIBLE);
            tvshipcharges.setText("ZAR"+"5");
            tv_total.setText("ZAR"+String.format("%.2f", totalpr+Double.parseDouble("5")));
//        }
//        else {
//            findViewById(R.id.shiping).setVisibility(View.GONE);
//            findViewById(R.id.linship).setVisibility(View.GONE);
//        }

    }
    public void getShippingrates(final Activity context, AllProduct allProduct, final double totalpr) {

        GlobalClass.showLoading(context, context.getResources().getString(R.string.please_wait));
        final String userid = SessionManager.getStringPref(HelperKeys.USER_ID, getApplicationContext());
        final String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, getApplicationContext());

        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        UserRequestModel userRequestModel = new UserRequestModel();
        userRequestModel.setUser_id(userid);
        userRequestModel.setShop_id(allProduct.getProductShopId());
        userRequestModel.setWeight(String.valueOf(totalWeight));
        userRequestModel.setWeight_unit(allProduct.getProduct_weight_unit());



        Call<UserResponseModel> call = apiService.getShipingrates(accessToken,userRequestModel);

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

                            ln34.setVisibility(View.VISIBLE);
                            tvshipcharges.setText("ZAR"+response.body().getResult());
                            tv_total.setText("ZAR"+String.format("%.2f", totalpr+Double.parseDouble(response.body().getResult())));
                        } else {
                            Toast.makeText(context,
                                    "Unable to find rates" + "",
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
    public void initView()
    {
        tv_ShopPhone = findViewById(R.id.tv_ShopPhone);
        tvshipcharges = findViewById(R.id.tvshipcharges);
        tv_shipAddress = findViewById(R.id.tv_shipAddress);
        tvservice = findViewById(R.id.tvservice);
        tvtax = findViewById(R.id.tvtax);
        tv_Address = findViewById(R.id.tv_Address);
        ln34=findViewById(R.id.ln34);
        tv_ShopName = findViewById(R.id.tv_ShopName);
        img_back = findViewById(R.id.img_back);
        btn_placeorder = findViewById(R.id.btn_placeorder);
        rl_orderSummary = findViewById(R.id.rl_orderSummary);

        tv_subtotal = findViewById(R.id.tv_subtotal);
        tv_total = findViewById(R.id.tv_total);
        String s = SessionManager.getStringPref(HelperKeys.SHIP_ADDRESS, getApplicationContext());
        tv_shipAddress.setText(s);
    }
    public void onClick()
    {
        btn_placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), ChooseCardActivity.class);
                startActivityForResult(intent,134);
            }
        });
        tv_shipAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectLocationActivity.class);
                startActivityForResult(intent, 210);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 134) {
            if (data.getStringExtra("cardid")!=null&&!data.getStringExtra("cardid").equals("")){
                addOrder(CartActivity.this,data.getStringExtra("cardid"));
            }

        }
        if (requestCode == 210) {
            if (!data.getStringExtra("lat").equals("")){
                tv_shipAddress.setText(data.getStringExtra("locationselected"));
                double latitude = Double.parseDouble(data.getStringExtra("lat"));
                double longitude = Double.parseDouble(data.getStringExtra("lon"));
                String city = data.getStringExtra("city");
                String state = data.getStringExtra("state");
                String zip_code = data.getStringExtra("zip_code");
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
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

                updateShipment(CartActivity.this,String.valueOf(latitude),String.valueOf(longitude),zip_code);

            }

        }
    }


    public void updateShipment(final Activity context,String lat,String lng,String zip) {

        GlobalClass.showLoading(context, context.getResources().getString(R.string.please_wait));
        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);
        String user_id = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        UserRequestModel userRequestModel = new UserRequestModel();
        userRequestModel.setUser_id(user_id);
        userRequestModel.setShip_address(tv_shipAddress.getText().toString());
        userRequestModel.setShip_lat(lat);
        userRequestModel.setShip_lng(lng);
        userRequestModel.setShip_zip_code(zip);




        Call<UserResponseModel> call = apiService.updateCustomerShipment(accessToken,userRequestModel);

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

                            SessionManager.putStringPref(HelperKeys.SHIP_ADDRESS,  tv_shipAddress.getText().toString(), context);

                        } else {
                            Toast.makeText(context,
                                    "INVALID Address" + "",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "INVALID Address", Toast.LENGTH_SHORT).show();
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
    public void onBackPress(View view)
    {
        onBackPressed();
    }
    public void addOrder(final Activity context, String cardid) {
        GlobalClass.showLoading(context, context.getResources().getString(R.string.please_wait));
        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        final String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, getApplicationContext());
        final String userid = SessionManager.getStringPref(HelperKeys.USER_ID, getApplicationContext());
        OrderRequestModel orderRequestModel = new OrderRequestModel();
        orderRequestModel.setUser_id(userid);
        String input3 = tvtax.getText().toString().replace("ZAR", "");
        orderRequestModel.setTax(input3);
        String input2 = tvservice.getText().toString().replace("ZAR", "");

        orderRequestModel.setService_fee(input2);
        String input = tv_total.getText().toString().replace("ZAR", "");
        if (allProductsList.get(0).getProduct_cat_id()==2){

            String shipment = tvshipcharges.getText().toString().replace("ZAR", "");
            orderRequestModel.setShipment_fee(shipment);
        }else {
            orderRequestModel.setShipment_fee("0");

        }

        orderRequestModel.setAmount(input);
        orderRequestModel.setStripe_card_id(cardid);
        orderRequestModel.setOrder_date(databaseHelper.getDateTime());
        orderRequestModel.setWeight_unit(allProductsList.get(0).getProduct_weight_unit());
        orderRequestModel.setShop_id(String.valueOf(productList.get(0).getAllShop().getShopId()));
        List<ProductRequestModel> productRequestModelList=new ArrayList<>();
        for (int i=0;i<productList.size();i++){
            ProductRequestModel productRequestModel=new ProductRequestModel();
            productRequestModel.setProduct_id(productList.get(i).getProductId());
            productRequestModel.setProduct_qty(productList.get(i).getProductQuantity());
            productRequestModel.setProduct_size_id(productList.get(i).getProductSizes().get(0).getProductSizeId());
            List<IngTypeRequestModel> ingTypeRequestModelsList=new ArrayList<>();
            for (int j=0;j<productList.get(i).getIngredientAll().size();j++){
                IngrediantItemModel ingrediantItemModel =productList.get(i).getIngredientAll().get(j);
                IngTypeRequestModel ingTypeRequestModel=new IngTypeRequestModel();
                ingTypeRequestModel.setIng_type_id(ingrediantItemModel.getProductIngredientTypeId());
                List<ProductIngRequestModel> productIngRequestModelList=new ArrayList<>();
                productIngRequestModelList.add(new ProductIngRequestModel(ingrediantItemModel.getIngredientId()));
                ingTypeRequestModel.setProduct_ingredients(productIngRequestModelList);
                ingTypeRequestModelsList.add(ingTypeRequestModel);
            }
            productRequestModel.setProduct_ing_types(ingTypeRequestModelsList);
            productRequestModelList.add(productRequestModel);
        }
        orderRequestModel.setProducts(productRequestModelList);

        Call<UserResponseModel> call = apiService.addNewOrder(accessToken,orderRequestModel);

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
                            deleteDatabase("chicagocoffee");
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
                        Toast.makeText(context, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
