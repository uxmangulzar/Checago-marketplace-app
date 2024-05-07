package com.softarena.checagocoffee.Acitivity.Shops;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.softarena.checagocoffee.Acitivity.Cart.CartActivity;
import com.softarena.checagocoffee.Acitivity.Products.ShopProductsAdapter;
import com.softarena.checagocoffee.Helper.DatabaseHelper;
import com.softarena.checagocoffee.Helper.GlobalClass;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.Acitivity.Products.AllProduct;
import com.softarena.checagocoffee.Acitivity.Subcategories.AllSubcategoryResponseModel;
import com.softarena.checagocoffee.Acitivity.Products.ProductRequestModel;
import com.softarena.checagocoffee.Acitivity.Products.ProductsResponseModel;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;
import com.softarena.checagocoffee.Rest.ApiInterface;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseShopItemActivity extends AppCompatActivity {
    private RecyclerView rl_browseshopitem;
    ImageView img_back,img_cart,img_shopImage;
    List<AllProduct> productList;
    private TextView tv_shop_distance,tv_shopDay,tv_shopTime,shopname;
    private Spinner spinner_subcats;
    AllShop allShop;
    private ArrayAdapter subcatsAdapter;
    private List<String> subcatList;
    private String selectedsubcatId="";
    private ArrayList<AllSubcategoryResponseModel> allSubcategoryResponseModelList;
    private ShopProductsAdapter browseShopItemAdapter;
    String shopStatus="opened";
    private DatabaseHelper databaseHelper;
    private TextView cart_items;

    @Override
    protected void onResume() {
        super.onResume();
        cart_items.setText(databaseHelper.getItemsincart()+"");
        browseShopItemAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_shop_item);
        databaseHelper=new DatabaseHelper(getApplicationContext());

        initViews();
        final ScrollView main = (ScrollView) findViewById(R.id.scrollView);
        cart_items.setText(databaseHelper.getItemsincart()+"");
        main.post(new Runnable() {

            public void run() {

                main.scrollTo(0,0);
            }
        });

        allShop=new AllShop();
        if (getIntent()!=null){
            allShop= (AllShop) getIntent().getSerializableExtra("model");
        }
        subcatList=new ArrayList<>();
        allSubcategoryResponseModelList=new ArrayList<>();
        rl_browseshopitem.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        browseShopItemAdapter=new ShopProductsAdapter(productList,this,allShop, shopStatus);
        browseShopItemAdapter.setHasStableIds(true);
        rl_browseshopitem.setAdapter(browseShopItemAdapter);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AllProduct> allProductsList=databaseHelper.getAllData();
                if (allProductsList.size()!=0){
                    Intent intent = new Intent(BrowseShopItemActivity.this, CartActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(BrowseShopItemActivity.this, "cart is empty", Toast.LENGTH_SHORT).show();
                }

            }
        });
        getshopByshopId(this);
        getAllProducts(BrowseShopItemActivity.this);
        tv_shop_distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q="+allShop.getShopAddress()));
                startActivity(intent);
            }
        });

    }
    public void initViews()
    {
        img_shopImage = findViewById(R.id.img_shopImage);
        cart_items = findViewById(R.id.cart_items);
        tv_shop_distance = findViewById(R.id.tv_shop_distance);
        img_back = findViewById(R.id.img_back);
        shopname = findViewById(R.id.shopname);
        img_cart = findViewById(R.id.img_cart);
        rl_browseshopitem = findViewById(R.id.rl_browseshopitem);
        tv_shopDay = findViewById(R.id.tv_shopDay);
        tv_shopTime= findViewById(R.id.tv_shopTime);
        spinner_subcats = findViewById(R.id.spinner_subcats);

        rl_browseshopitem.setNestedScrollingEnabled(false);


    }
    private void getshopByshopId(final Activity context) {

        GlobalClass.showLoading(context, getString(R.string.please_wait));
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);
        String user_id = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        final ShopRequestModel shopRequestModel = new ShopRequestModel();
        shopRequestModel.setLimit("100");
        shopRequestModel.setPage("1");
        shopRequestModel.setUser_id(user_id);
        shopRequestModel.setShop_day(dayOfTheWeek);
        shopRequestModel.setShop_id(String.valueOf(allShop.getShopId()));

        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        Call<ShopResponseModel> call = apiService
                .getShopbyShopId(accessToken, shopRequestModel);
        Log.d("Response", "URL==" + call.request().url());
        call.enqueue(new Callback<ShopResponseModel>() {
            @Override
            public void onResponse(Call<ShopResponseModel> call,
                                   Response<ShopResponseModel> response) {

                GlobalClass.dismissLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        allSubcategoryResponseModelList.clear();
                            if (response.body().getShopDetails()!=null){

//                                allSubcategoryResponseModelList.addAll(response.body().getShopDetails().get(0).getShopAllSubcats());
//                                //    selectCategoryAdapter.notifyDataSetChanged();
//
//                                for (int i=0;i<allSubcategoryResponseModelList.size();i++) {
//
//                                    subcatList.add(allSubcategoryResponseModelList.get(i).getSubcategoryName());
//
//                                }
//
//
//                                subcatsAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,subcatList);
//                                subcatsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                spinner_subcats.setAdapter(subcatsAdapter);
//                                // spinSubcats.setSelection(0);
//                                selectedsubcatId=allSubcategoryResponseModelList.get(0).getSubcategoryId();
                                allShop=response.body().getShopDetails().get(0);


                                Glide.with(context).load(APIClient.BASE_URL+allShop.getShopImage()).into(img_shopImage);
                                shopname.setText(allShop.getShopName());
                                tv_shop_distance.setText(allShop.getShopAddress());
                                if (allShop.getShopTiming()!=null&&allShop.getShopTiming().size()!=0){

                                    tv_shopTime.setText(allShop.getShopTiming().get(0).getShopTimeOpening()+" - "+allShop.getShopTiming().get(0).getShopTimeClosing());

                                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                                    Date date1 = null;
                                    try {
                                        date1 = sdf.parse(allShop.getShopTiming().get(0).getShopTimeClosing());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                          Date date3 = null;
                                    try {
                                        date3 = sdf.parse(allShop.getShopTiming().get(0).getShopTimeOpening());
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                     SimpleDateFormat dateFormat = new SimpleDateFormat(
                                "hh:mm a", Locale.getDefault());
                                    Date date11 = new Date();

                                    Date date2 = null;
                                    try {
                                        date2 = sdf.parse(dateFormat.format(date11));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }



                                    Calendar cal1 = Calendar.getInstance();
                                    Calendar cal2 = Calendar.getInstance();
                                    Calendar cal3 = Calendar.getInstance();
                                    cal1.setTime(date1);
                                    cal2.setTime(date2);
                                    cal3.setTime(date3);



                                    if (cal1.after(cal2)||cal1.equals(cal2)) {
                                        if (cal2.after(cal3)||cal3.equals(cal2)){

                                            tv_shopDay.setText("Open Now");
                                            shopStatus="opened";
                                        }else {
                                            tv_shopDay.setText("Shop is closed");
                                            shopStatus="closed";
                                        }
                                    }else {
                                        tv_shopDay.setText("Shop is closed");
                                        shopStatus="closed";
                                    }


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

    private void getAllProducts(final Activity context) {
        GlobalClass.showLoading(context, getString(R.string.please_wait));

        String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);
        String user_id = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        ProductRequestModel productRequestModel = new ProductRequestModel();
        productRequestModel.setLimit("100");
        productRequestModel.setPage("1");
        productRequestModel.setUser_id(user_id);
        productRequestModel.setCat_id(getIntent().getStringExtra("cat_id"));
        productRequestModel.setU_type("user");
        productRequestModel.setShop_id(String.valueOf(allShop.getShopId()));

        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        Call<ProductsResponseModel> call = apiService
                .getShopProducts(accessToken, productRequestModel);
        Log.d("Response", "URL==" + call.request().url());
        call.enqueue(new Callback<ProductsResponseModel>() {
            @Override
            public void onResponse(Call<ProductsResponseModel> call,
                                   Response<ProductsResponseModel> response) {

                GlobalClass.dismissLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().isError()) {
                            productList.clear();
                            browseShopItemAdapter=new ShopProductsAdapter(productList,context,allShop,shopStatus);
                            rl_browseshopitem.setAdapter(browseShopItemAdapter);
                            if (response.body().getAllProducts()!=null){
                                productList.addAll(response.body().getAllProducts());


                            }
                            browseShopItemAdapter.notifyDataSetChanged();

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
