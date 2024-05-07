package com.softarena.checagocoffee.Acitivity.card;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softarena.checagocoffee.Acitivity.Shops.ShopRequestModel;
import com.softarena.checagocoffee.Acitivity.Shops.ShopResponseModel;
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

public class ChooseCardActivity extends AppCompatActivity implements ChooseCardAdapter.OnItemClick{
    RecyclerView recycler_chooseCards;
    List<AllCardsModel> allCardsModelList;
    ImageView img_add, img_close, img_select;
    TextView tv_no,tv_yes;
    Dialog dialog2,dialogOrderRequest,dialogTimeOut;
    private ChooseCardAdapter chooseCardAdapter;
    private String selectedcardId="";

    @Override
    protected void onResume() {
        super.onResume();
        getAllcardsData(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_card);

        initViews();
        onClicks();

        allCardsModelList = new ArrayList<>();
        recycler_chooseCards.setLayoutManager(new LinearLayoutManager( ChooseCardActivity.this));
        chooseCardAdapter=new ChooseCardAdapter(allCardsModelList, this,this);
        recycler_chooseCards.setAdapter(chooseCardAdapter);

        getAllcardsData(this);

    }

    public void initViews()
    {
        recycler_chooseCards = findViewById(R.id.recycler_chooseCards);
        img_add = findViewById(R.id.img_addCard);
        img_close = findViewById(R.id.img_close);
        img_select = findViewById(R.id.img_select);
    }




    public void onClicks()
    {
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseCardActivity.this, AddCard2Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up, R.anim.no_animation);
            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
                onBackPressed();
            }
        });
        img_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedcardId.equals("")){
                    final Dialog dialog = new Dialog(ChooseCardActivity.this);
                    dialog2 = new Dialog( ChooseCardActivity.this);
                    dialog.setContentView(R.layout.dialog_place_order);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    lp.gravity = Gravity.CENTER;
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.getWindow().setAttributes(lp);
                    TextView tv_no = dialog.findViewById(R.id.tv_no);
                    tv_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    tv_yes = dialog.findViewById(R.id.tv_yes);
                    tv_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            Intent intent = getIntent();
                            intent.putExtra("cardid", selectedcardId);
                            setResult(134, intent);
                            finish();


                        }
                    });
                    dialog.show();
                }


            }
        });
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.no_animation, R.anim.slide_down);
        Intent intent = getIntent();
        intent.putExtra("cardid", selectedcardId);
        setResult(135, intent);
        finish();
    }
    private void getAllcardsData(final Activity context) {
        GlobalClass.showLoading(context, getString(R.string.please_wait));

        String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);
        String user_id = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        ShopRequestModel shopRequestModel = new ShopRequestModel();
        shopRequestModel.setLimit("100");
        shopRequestModel.setPage("1");
        shopRequestModel.setUser_id(user_id);

        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        Call<ShopResponseModel> call = apiService
                .getcardsByuserId(accessToken, shopRequestModel);
        Log.d("Response", "URL==" + call.request().url());
        call.enqueue(new Callback<ShopResponseModel>() {
            @Override
            public void onResponse(Call<ShopResponseModel> call,
                                   Response<ShopResponseModel> response) {

                GlobalClass.dismissLoading();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!response.body().isError()) {
                            allCardsModelList.clear();
                            if (response.body().getUser_cards()!=null){
                                allCardsModelList.addAll(response.body().getUser_cards());
                                chooseCardAdapter.notifyDataSetChanged();



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
    public void onClick(int value) {
         selectedcardId = allCardsModelList.get(value).getCardStripeCardId();
    }
}