package com.softarena.checagocoffee.Acitivity.Products;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.softarena.checagocoffee.Acitivity.Cart.CartActivity;
import com.softarena.checagocoffee.Acitivity.Ingredients.IngredientAdapter;
import com.softarena.checagocoffee.Acitivity.Shops.AllShop;
import com.softarena.checagocoffee.Acitivity.Sizes.SizesAdapter;
import com.softarena.checagocoffee.Acitivity.SuggestedItem.SuggestedItemsAdapter;
import com.softarena.checagocoffee.Helper.DatabaseHelper;
import com.softarena.checagocoffee.Helper.GlobalClass;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.Acitivity.Ingredients.IngrediantItemModel;
import com.softarena.checagocoffee.Acitivity.Ingredients.IngrediantsModel;
import com.softarena.checagocoffee.Acitivity.Sizes.ProductSize;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;
import com.softarena.checagocoffee.Rest.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import im.delight.android.location.SimpleLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopproductDetailActivity extends AppCompatActivity implements SizesAdapter.OnItemClick, IngredientAdapter.OnIngredientItemSelect ,SuggestedItemsAdapter.OnItemClick{
    int minteger = 1;
    Button btn_placeOrder;
    RecyclerView rl_suggested_item_detail,recyclerView_ingredients;
    ImageView img_cart,btn_decrease,img_browse_shop_itemss;
    List<IngrediantsModel> ingredientList;
    RecyclerView recyclerView_size;
    TextView tv_stockInfo,tv_product_name,tv_product_price,tv_product_desc,estimatetravel,tvorder1;
    private AllProduct allProduct;
    private String finalDuration="";
    List<AllProduct> suggestedproductList;
    private List<ProductSize> sizeModelList1;
    private SizesAdapter sizesAdapter;
    private SuggestedItemsAdapter suggestedItemsAdapter;
    private IngredientAdapter ingredientAdapter;
    AllShop allShop;
    int allquantityAvailable=0;
    double price=0;
    double originalprice=0;
    double originalIngprice=0;
    private ProductSize sizeselectedModel;
    List<IngrediantItemModel> ingrediantItemModelFinalList;
    private DatabaseHelper databaseHelper;
    RelativeLayout prnt;
    private TextView cart_items,tv_detailsold;
    private int sizeLastposition=0;


    @Override
    protected void onResume() {
        super.onResume();
        cart_items.setText(databaseHelper.getItemsincart()+"");
        suggestedItemsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_souvenir_shop_item_detail);
         databaseHelper=new DatabaseHelper(getApplicationContext());

        allShop= (AllShop) getIntent().getSerializableExtra("shopmodel");
        sizeselectedModel=new ProductSize();
        allProduct=new AllProduct();
        suggestedproductList=new ArrayList<>();
        ingrediantItemModelFinalList=new ArrayList<>();
        initViews();
        onClicks();
        tv_detailsold.setText(allShop.getShopName());
        rl_suggested_item_detail = findViewById(R.id.rl_suggested_item_detail);
        rl_suggested_item_detail.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        suggestedItemsAdapter=new SuggestedItemsAdapter(suggestedproductList,this,allShop,this);
        rl_suggested_item_detail.setAdapter(suggestedItemsAdapter);



        recyclerView_size = findViewById(R.id.recycler_size);
        recyclerView_size.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        sizeModelList1 = new ArrayList<>();
        sizesAdapter=new SizesAdapter(this,sizeModelList1,this);
        recyclerView_size.setAdapter(sizesAdapter);

        recyclerView_ingredients = findViewById(R.id.recycler_customizeCheckB);
        ingredientList = new ArrayList<>();
        recyclerView_ingredients.setLayoutManager(new LinearLayoutManager(this));
        ingredientAdapter=new IngredientAdapter(ingredientList,this,this);
        recyclerView_ingredients.setAdapter(ingredientAdapter);
        getProductByproductId(this);
    }
    public void initViews()
    {
        cart_items = findViewById(R.id.cart_items);
        tv_detailsold = findViewById(R.id.tv_detailsold);
        btn_placeOrder = findViewById(R.id.btn_placeorder);
        tvorder1 = findViewById(R.id.tvorder1);
        prnt = findViewById(R.id.prnt);
        estimatetravel = findViewById(R.id.estimatetravel);
        btn_decrease = findViewById(R.id.decrease);
        rl_suggested_item_detail = findViewById(R.id.rl_suggested_item_detail);
        img_cart =  findViewById(R.id.img_cart);
        tv_stockInfo = findViewById(R.id.tv_stockInfo);
        img_browse_shop_itemss = findViewById(R.id.img_browse_shop_itemss);
        tv_product_name = findViewById(R.id.tv_product_name);
        tv_product_price = findViewById(R.id.tv_product_price);
        tv_product_desc = findViewById(R.id.tv_product_desc);

    }
    public void onClicks()

    {

        btn_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (minteger == 1) {
                    btn_decrease.setEnabled(false);
                } else {
                    minteger = minteger - 1;
                    display(minteger);
                }
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AllProduct> allProductsList=databaseHelper.getAllData();
                if (allProductsList.size()!=0){
                    Intent intent = new Intent(ShopproductDetailActivity.this, CartActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(ShopproductDetailActivity.this, "cart is empty", Toast.LENGTH_SHORT).show();
                }


            }
        });
        btn_placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper=new DatabaseHelper(getApplicationContext());
                Boolean isAddAllowed=databaseHelper.shopAlreadyaddedIncart(String.valueOf(allShop.getShopId()),String.valueOf(allProduct.getProduct_cat_id()));
                if (isAddAllowed){
                    Boolean added=databaseHelper.saveProduct(allProduct,ingrediantItemModelFinalList,allShop,String.valueOf(minteger),sizeselectedModel);
                    if (added){
                        Toast.makeText(ShopproductDetailActivity.this, "Product added to cart successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ShopproductDetailActivity.this, CartActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(ShopproductDetailActivity.this, "Product Already added in cart", Toast.LENGTH_SHORT).show();
                    }
                }else {


                    Snackbar.make(prnt, "You can only order from One Shop at a time.By selecting this product/shop your previous cart will be cleared.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    deleteDatabase("chicagocoffee");
                                    databaseHelper=new DatabaseHelper(getApplicationContext());
                                    Boolean added=databaseHelper.saveProduct(allProduct,ingrediantItemModelFinalList,allShop,String.valueOf(minteger),sizeselectedModel);
                                    if (added){
                                        Toast.makeText(ShopproductDetailActivity.this, "Product added to cart successfully", Toast.LENGTH_SHORT).show();

                                        finish();
                                    }
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();
                }


            }
        });

    }
    public void increaseInteger(View view)
    {
        if (minteger<allquantityAvailable){
            minteger = minteger + 1;
            display(minteger);
            if (minteger>1){
                btn_decrease.setEnabled(true);
            }
        }else {
            Toast.makeText(this, "Only "+allquantityAvailable+" are available for now", Toast.LENGTH_SHORT).show();
        }


    }
    private void display(int number)
    {
        price=(originalprice+originalIngprice) * number;
        DecimalFormat form = new DecimalFormat("#.00");
        price= Double.parseDouble(form.format(price));
        TextView displayInteger = (TextView) findViewById(R.id.integer_number);
        displayInteger.setText("" + number);
        tv_product_price.setText("ZAR"+price);
    }
    public void onBackPress(View view )
    {
        onBackPressed();
    }

    private void getProductByproductId(final Activity context) {
        GlobalClass.showLoading(context, getString(R.string.please_wait));

        String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);
        String user_id = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        ProductRequestModel productRequestModel = new ProductRequestModel();
        productRequestModel.setUser_id(user_id);
        productRequestModel.setProduct_id(getIntent().getStringExtra("prodid"));

        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        Call<ProductsResponseModel> call = apiService
                .getProductByProductId(accessToken, productRequestModel);
        Log.d("Response", "URL==" + call.request().url());
        call.enqueue(new Callback<ProductsResponseModel>() {
            @Override
            public void onResponse(Call<ProductsResponseModel> call,
                                   Response<ProductsResponseModel> response) {

                GlobalClass.dismissLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().isError()) {
                            if (response.body().getAllProducts()!=null){
                                allProduct =response.body().getAllProducts().get(0);
                                Glide.with(context).load(APIClient.BASE_URL+allProduct.getProductImage()).into(img_browse_shop_itemss);
                                tv_product_name.setText(allProduct.getProductName());
                                tv_product_desc.setText(allProduct.getProductDescription());


                                allquantityAvailable=Integer.parseInt(allProduct.getProductQuantity());
                                originalprice=Double.parseDouble(allProduct.getProductSizes().get(0).getProductPrice());
                                sizeselectedModel=allProduct.getProductSizes().get(0);

                                tv_product_price.setText("ZAR"+allProduct.getProductSizes().get(0).getProductPrice());
                                sizeModelList1.clear();
                                sizeModelList1.addAll(allProduct.getProductSizes());
                                sizesAdapter.notifyDataSetChanged();
                                if (allProduct.getProductIngredients()!=null && allProduct.getProductIngredients().size()!=0){
                                    ingredientList.addAll(allProduct.getProductIngredients());
                                    ingredientAdapter.notifyDataSetChanged();
                                }
                                SimpleLocation location = new SimpleLocation(ShopproductDetailActivity.this);

//                                String lon = String.valueOf(location.getLongitude());
//                                String lat = String.valueOf(location.getLatitude());
                                String lon = SessionManager.getStringPref(HelperKeys.USER_LON, getApplicationContext());
                                String lat = SessionManager.getStringPref(HelperKeys.USER_LAT, getApplicationContext());
                                if (allProduct.getProduct_cat_id()==1){

                                    getDistance(Double.parseDouble(allShop.getShopLat()),Double.parseDouble(allShop.getShopLng()),Double.valueOf(lat),Double.valueOf(lon));
                                    estimatetravel.setText(finalDuration);
                                }
                                else {
                                    tvorder1.setText("Estimated Shipping :");
                                    estimatetravel.setText("7-10 business days");
                                }

                                getAllSuggestedProducts(context);

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
            public void onFailure(Call<ProductsResponseModel> call, Throwable t) {
                // Log error here since request failed
                Log.e("Response", "onFailure" + t.toString());
                Toast.makeText(context, "Server down", Toast.LENGTH_LONG).show();

                GlobalClass.dismissLoading();
            }
        });
    }
    public String getDistance(final double lat1, final double lon1, final double lat2, final double lon2){
        final String[] parsedDistance = new String[1];
        final String[] response = new String[1];
        Thread thread=new Thread(new Runnable() {
            @SuppressLint("DefaultLocale")
            @Override
            public void run() {
                try {
                    URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=" + lat1 + "," + lon1 + "&destination=" + lat2 + "," + lon2 + "&sensor=false&units=metric&mode=driving&key=AIzaSyA7EEM4okrppdhrxpJ48oUiN9g3rZGDPgc");
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    response[0] = org.apache.commons.io.IOUtils.toString(in, "UTF-8");

                    JSONObject jsonObject = new JSONObject(response[0]);
                    JSONArray array = jsonObject.getJSONArray("routes");
                    JSONObject routes = array.getJSONObject(0);
                    JSONArray legs = routes.getJSONArray("legs");
                    JSONObject steps = legs.getJSONObject(0);
                    JSONObject duration = steps.getJSONObject("duration");
                    finalDuration =duration.getString("text");

                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        try {

            thread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return parsedDistance[0];
    }
    private void getAllSuggestedProducts(final Activity context) {
        GlobalClass.showLoading(context, getString(R.string.please_wait));

        String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);
        String user_id = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        ProductRequestModel productRequestModel = new ProductRequestModel();
        productRequestModel.setLimit("100");
        productRequestModel.setPage("1");
        productRequestModel.setUser_id(user_id);
        productRequestModel.setProduct_id(allProduct.getProductId());

        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        Call<ProductsResponseModel> call = apiService
                .getSuggestedProducts(accessToken, productRequestModel);
        Log.d("Response", "URL==" + call.request().url());
        call.enqueue(new Callback<ProductsResponseModel>() {
            @Override
            public void onResponse(Call<ProductsResponseModel> call,
                                   Response<ProductsResponseModel> response) {

                GlobalClass.dismissLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().isError()) {
                            suggestedproductList.clear();
                            if (response.body().getAllProducts()!=null&&response.body().getAllProducts().size()!=0){
                                suggestedproductList.addAll(response.body().getAllProducts());
                                findViewById(R.id.tvsuggested).setVisibility(View.VISIBLE);
                            }
                            suggestedItemsAdapter.notifyDataSetChanged();

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

    @Override
    public void onClick(int value) {
        sizeLastposition=value;
        sizeselectedModel=sizeModelList1.get(value);
        tv_product_price.setText("ZAR"+sizeModelList1.get(value).getProductPrice());
        minteger=1;
        originalprice=Double.parseDouble(sizeModelList1.get(value).getProductPrice());
        display(1);
    }

    @Override
    public void onIngredientItemSelectClick(IngrediantItemModel ingrediantItemModel) {
        ingrediantItemModelFinalList=mycontains(ingrediantItemModelFinalList,ingrediantItemModel.getIngredientId(),ingrediantItemModel);


        originalIngprice=0;
        if (ingrediantItemModelFinalList.size()==0){
            tv_product_price.setText("ZAR"+sizeModelList1.get(sizeLastposition).getProductPrice());
            minteger=1;
            originalprice=Double.parseDouble(sizeModelList1.get(sizeLastposition).getProductPrice());
            display(1);
        }
        for (int i=0;i<ingrediantItemModelFinalList.size();i++){
        originalIngprice=originalIngprice+Double.parseDouble(ingrediantItemModelFinalList.get(i).getIngredientPrice());
        display(minteger);
        }
    }
    List<IngrediantItemModel> mycontains(List<IngrediantItemModel> list, String s,IngrediantItemModel ingrediantItemModel) {
        int removed=0;
        List<IngrediantItemModel> ingrediantItemModels=new ArrayList<>();
        ingrediantItemModels.addAll(list);
        for (IngrediantItemModel item : list) {
            if (item.getIngredientId().equals(s)) {
                ingrediantItemModels.remove(item);
                removed=1;
                return ingrediantItemModels;
            }
        }
        ingrediantItemModels.add(ingrediantItemModel);
        return ingrediantItemModels;
    }

    @Override
    public void onSuggestedClick(int position) {
        if (Integer.parseInt(suggestedproductList.get(position).getProductQuantity())>0){
            databaseHelper=new DatabaseHelper(getApplicationContext());
            Boolean isAddAllowed=databaseHelper.shopAlreadyaddedIncart(String.valueOf(allShop.getShopId()),String.valueOf(allProduct.getProduct_cat_id()));
            if (isAddAllowed){
                Boolean added=databaseHelper.saveProduct(allProduct,ingrediantItemModelFinalList,allShop,String.valueOf(minteger),sizeselectedModel);
                if (added){
                    Toast.makeText(ShopproductDetailActivity.this, "Product added to cart successfully", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(ShopproductDetailActivity.this, ShopproductDetailActivity.class);
                    intent.putExtra("prodid",suggestedproductList.get(position).getProductId());
                    intent.putExtra("model",suggestedproductList.get(position).getProductId());
                    intent.putExtra("shopmodel",allShop);
                    startActivity(intent);
                }else {
                    //Toast.makeText(ShopproductDetailActivity.this, "Product Already added in cart", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(ShopproductDetailActivity.this, ShopproductDetailActivity.class);
                    intent.putExtra("prodid",suggestedproductList.get(position).getProductId());
                    intent.putExtra("model",suggestedproductList.get(position).getProductId());
                    intent.putExtra("shopmodel",allShop);
                    startActivity(intent);
                }
            }else {
                Snackbar.make(prnt, "By selecting this product/shop your previous cart will be cleared.", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                deleteDatabase("chicagocoffee");
                                databaseHelper=new DatabaseHelper(getApplicationContext());
                                Boolean added=databaseHelper.saveProduct(allProduct,ingrediantItemModelFinalList,allShop,String.valueOf(minteger),sizeselectedModel);
                                if (added){
                                    Toast.makeText(ShopproductDetailActivity.this, "Product added to cart successfully", Toast.LENGTH_SHORT).show();

                                    finish();
                                }
                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();


            }


        }else {
            Toast.makeText(ShopproductDetailActivity.this, "Item out of stock try another time", Toast.LENGTH_SHORT).show();
        }
    }
}