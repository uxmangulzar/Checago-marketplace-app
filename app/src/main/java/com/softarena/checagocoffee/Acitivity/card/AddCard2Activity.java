package com.softarena.checagocoffee.Acitivity.card;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserResponseModel;
import com.softarena.checagocoffee.Helper.GlobalClass;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;
import com.softarena.checagocoffee.Rest.ApiInterface;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCard2Activity extends AppCompatActivity {
    ImageView img_back;
    EditText ed_cardHolderName,ed_cardNumber,ed_expirationDate,ed_cvv;
    TextView tvSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card2);
        initViews();
        onClicks();

    }
    public void initViews()
    {
        ed_cardHolderName = findViewById(R.id.ed_cardHolderName);
        ed_cardNumber = findViewById(R.id.ed_cardNumber);
        ed_expirationDate = findViewById(R.id.ed_expirationDate);
        img_back = findViewById(R.id.img_back);
        ed_cvv =findViewById(R.id.ed_cvv);
        tvSave = findViewById(R.id.tvSave);
        ed_expirationDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 2) {
                    if(start==2 && before==1 && !s.toString().contains("/")){
                        ed_expirationDate.setText(""+s.toString().charAt(0));
                        ed_expirationDate.setSelection(1);
                    }
                    else {
                        ed_expirationDate.setText(s + "/");
                        ed_expirationDate.setSelection(3);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed_cardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String initial = s.toString();
                // remove all non-digits characters
                String processed = initial.replaceAll("\\D", "");

                // insert a space after all groups of 4 digits that are followed by another digit
                processed = processed.replaceAll("(\\d{4})(?=\\d)(?=\\d)(?=\\d)", "$1 ");

                //Remove the listener
                ed_cardNumber.removeTextChangedListener(this);

                int index = ed_cardNumber.getSelectionEnd();

                if (index == 5 || index == 10 || index == 15)
                    if (count > before)
                        index++;
                    else
                        index--;

                //Assign processed text
                ed_cardNumber.setText(processed);

                try {
                    ed_cardNumber.setSelection(index);
                } catch (Exception e) {
                    e.printStackTrace();
                    ed_cardNumber.setSelection(s.length() - 1);
                }
                //Give back the listener
                ed_cardNumber.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void onClicks()
    {
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.no_animation,R.anim.slide_down);
                onBackPressed();
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (
                         !ed_cardHolderName.getText().toString().equals("")&&
                         !ed_cardNumber.getText().toString().equals("")&&
                         !ed_expirationDate.getText().toString().equals("")&&
                         !ed_cvv.getText().toString().equals("")
                ){
                    addCard(AddCard2Activity.this);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.no_animation,R.anim.slide_down);
    }
    public void addCard(final Activity context) {
        GlobalClass.showLoading(context, context.getResources().getString(R.string.please_wait));
        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        final String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, getApplicationContext());
        final String userid = SessionManager.getStringPref(HelperKeys.USER_ID, getApplicationContext());
        AddcardRequestModel addcardRequestModel = new AddcardRequestModel();
        addcardRequestModel.setUser_id(userid);
        addcardRequestModel.setHolder_name(ed_cardHolderName.getText().toString());
        String input = ed_cardNumber.getText().toString().replace(" ", "");
        addcardRequestModel.setCard_number(input);
        addcardRequestModel.setCvv(ed_cvv.getText().toString());
        addcardRequestModel.setExpiry(ed_expirationDate.getText().toString());


        Call<UserResponseModel> call = apiService.addCard(accessToken,addcardRequestModel);

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
                            finish();

                        } else {

                        }
                    } else {
                        Toast.makeText(context, "Check card details", Toast.LENGTH_SHORT).show();
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