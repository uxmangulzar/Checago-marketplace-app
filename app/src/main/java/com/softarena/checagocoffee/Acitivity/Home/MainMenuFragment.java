package com.softarena.checagocoffee.Acitivity.Home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.softarena.checagocoffee.Acitivity.News.NewsActivity;
import com.softarena.checagocoffee.Acitivity.Products.AllProduct;
import com.softarena.checagocoffee.Acitivity.Shops.AllShop;
import com.softarena.checagocoffee.Acitivity.Shops.BrowseShopActivity;
import com.softarena.checagocoffee.Acitivity.Shops.ShopsNearMeAdapter;
import com.softarena.checagocoffee.Acitivity.Signin.LoginActivity;
import com.softarena.checagocoffee.Acitivity.Subcategories.SubcategoriesActivity;
import com.softarena.checagocoffee.Acitivity.Products.ProductsNearMeAdapter;
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


public class MainMenuFragment extends Fragment{

    private RecyclerView recycler_coffeeNearYou,recyclerView_souvenirs;
    private List<AllProduct> shopNearSouveniorModelList;
    private List<AllShop> shopNearFoodModelList;
    private TextView tv_seeMoreShopsNear,seeMoreSouvenir,tv_seeMoreNews,tvsouvenir,tvfood,address;
    private ProductsNearMeAdapter shopNearMeSouveniorAdapter;
    private ShopsNearMeAdapter shopNearMefoodAdapter;
    private HomeData homeData;



    @Override
    public void onResume() {
        super.onResume();
        shopNearMeSouveniorAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        /*initi Views*/
        homeData=new HomeData();
        address = view.findViewById(R.id.address);
        tvsouvenir = view.findViewById(R.id.tvsouvenir);

        tvfood = view.findViewById(R.id.tvfood);
        tv_seeMoreShopsNear = view.findViewById(R.id.tv_seeMoreShopNear);
        seeMoreSouvenir = view.findViewById(R.id.seeMoreSouvenirs);
        tv_seeMoreNews = view.findViewById(R.id.seeMoreNews);
        final String userAddress = SessionManager.getStringPref(HelperKeys.USER_ADDRESS, getActivity());
        address.setText(userAddress);
        seeMoreSouvenir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homeData.getAllCategories()!=null) {
                    Intent intent = new Intent(getContext(), SubcategoriesActivity.class);
                    intent.putExtra("catId", String.valueOf(homeData.getAllCategories().get(1).getCategoryId()));
                    intent.putExtra("catname", String.valueOf(homeData.getAllCategories().get(1).getCategoryName()));
                    startActivity(intent);
                }
            }
        });
        tv_seeMoreShopsNear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homeData.getAllCategories()!=null){
                    Intent intent = new Intent(getContext(), BrowseShopActivity.class);
                    intent.putExtra("cat",String.valueOf(homeData.getAllCategories().get(0).getCategoryId()));
                    startActivity(intent);

                }

            }
        });




        /*Init recycelr view id*/
        recycler_coffeeNearYou = view.findViewById(R.id.recycler_coffeeNearYou);
        shopNearFoodModelList = new ArrayList<>();
        recycler_coffeeNearYou.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        shopNearMefoodAdapter=new ShopsNearMeAdapter(shopNearFoodModelList,getContext(),"");
        recycler_coffeeNearYou.setAdapter(shopNearMefoodAdapter);


       /*init reccycler view id souvenir*/
        recyclerView_souvenirs = view.findViewById(R.id.recycler_souvenir);
        shopNearSouveniorModelList = new ArrayList<>();
        recyclerView_souvenirs.setLayoutManager(new GridLayoutManager(getContext(), 2));
        shopNearMeSouveniorAdapter=new ProductsNearMeAdapter(shopNearSouveniorModelList,getContext(),"");
        recyclerView_souvenirs.setAdapter(shopNearMeSouveniorAdapter);






        getHomedata(getActivity());
        return  view;
    }


    private void getHomedata(final Activity context) {
        GlobalClass.showLoading(context, getString(R.string.please_wait));

        String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);
        String user_id = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        HomeRequestModel homeRequestModel = new HomeRequestModel();
        homeRequestModel.setUser_id(user_id);
        SimpleLocation location = new SimpleLocation(getActivity());
        homeRequestModel.setUser_lat(SessionManager.getStringPref(HelperKeys.USER_LAT, context));
        homeRequestModel.setUser_lng(SessionManager.getStringPref(HelperKeys.USER_LON, context));

//        homeRequestModel.setUser_lat(String.valueOf(location.getLatitude()));
//        homeRequestModel.setUser_lng(String.valueOf(location.getLongitude()));
        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        Call<HomeMainResponsedata> call = apiService
                .getHomeData(accessToken,homeRequestModel);
        Log.d("Response", "URL==" + call.request().url());
        call.enqueue(new Callback<HomeMainResponsedata>() {
            @Override
            public void onResponse(Call<HomeMainResponsedata> call,
                                   Response<HomeMainResponsedata> response) {

                GlobalClass.dismissLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().isError()) {
                            if (!response.body().getUser_status().equals("Active")){
                                Toast.makeText(getActivity(), "Your account is blocked", Toast.LENGTH_SHORT).show();
                                context.deleteDatabase("chicagocoffee");
                                SessionManager.putStringPref(HelperKeys.USER_NAME, "", getActivity());
                                SessionManager.putStringPref(HelperKeys.User_Access_Token, "", getActivity());
                                SessionManager.putStringPref(HelperKeys.USER_ID, "", getActivity());
                                SessionManager.putStringPref(HelperKeys.User_Type, "", getActivity());
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                context.finish();
                            }else {
                                if (response.body().getHomeScreenData()!=null){
                                    homeData=response.body().getHomeScreenData().get(0);
                                    if (homeData.getUser_ship_address()!=null){

                                        SessionManager.putStringPref(HelperKeys.SHIP_ADDRESS,  homeData.getUser_ship_address(), context);

                                    }


                                    shopNearMefoodAdapter=new ShopsNearMeAdapter(shopNearFoodModelList,getContext(),String.valueOf(homeData.getAllCategories().get(0).getCategoryId()));
                                    recycler_coffeeNearYou.setAdapter(shopNearMefoodAdapter);
                                    shopNearMeSouveniorAdapter=new ProductsNearMeAdapter(shopNearSouveniorModelList,getContext(),String.valueOf(homeData.getAllCategories().get(1).getCategoryId()));
                                    recyclerView_souvenirs.setAdapter(shopNearMeSouveniorAdapter);



                                    if (homeData.getAllCategories().get(0).getAllShops().size()==0){
                                        tvfood.setVisibility(View.GONE);
                                        tv_seeMoreShopsNear.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), "No Nearby Shops Found", Toast.LENGTH_SHORT).show();
                                    }
                                    if (homeData.getAllCategories().get(1).getAllProducts().size()==0){
                                        tvsouvenir.setVisibility(View.GONE);
                                        seeMoreSouvenir.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), "No Nearby Souvenirs Found", Toast.LENGTH_SHORT).show();

                                    }
                                    shopNearFoodModelList.addAll(homeData.getAllCategories().get(0).getAllShops());
                                    shopNearSouveniorModelList.addAll(homeData.getAllCategories().get(1).getAllProducts());
                                    shopNearMefoodAdapter.notifyDataSetChanged();
                                    shopNearMeSouveniorAdapter.notifyDataSetChanged();


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
            public void onFailure(Call<HomeMainResponsedata> call, Throwable t) {
                // Log error here since request failed
                Log.e("Response", "onFailure" + t.toString());
                Toast.makeText(context, "Server down", Toast.LENGTH_LONG).show();

                GlobalClass.dismissLoading();
            }
        });
    }


}