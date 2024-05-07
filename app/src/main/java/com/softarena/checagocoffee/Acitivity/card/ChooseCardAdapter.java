package com.softarena.checagocoffee.Acitivity.card;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserResponseModel;
import com.softarena.checagocoffee.Helper.GlobalClass;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;
import com.softarena.checagocoffee.Rest.ApiInterface;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseCardAdapter extends RecyclerView.Adapter< ChooseCardAdapter.AllReviewViewHolder> {
    private List<AllCardsModel> allCardsModelList;
    Activity context;
    private int selectedPosition = -1;
    private OnItemClick mCallback;

    public ChooseCardAdapter(List<AllCardsModel> allCardsModelList, Activity context,OnItemClick listener) {
        this.allCardsModelList = allCardsModelList;
        this.context = context;
        this.mCallback = listener;

    }

    @NonNull
    @Override
    public AllReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_add_card, parent, false);
        return new AllReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AllReviewViewHolder holder, final int position) {
        holder.checkBox.setChecked(selectedPosition == position);

        holder.name.setText(allCardsModelList.get(position).getCardHolderName());
        holder.number.setText(allCardsModelList.get(position).getCardNumber());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onClick(holder.getAdapterPosition());
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });
        holder.deleteimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCard(context,position);
            }
        });

        if (selectedPosition==position){
            holder.checkBox.setChecked(true);
        }
        else {
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return allCardsModelList.size();
    }

    public class AllReviewViewHolder extends RecyclerView.ViewHolder {
        ImageView image,deleteimg;
        TextView title, name, number;
        CheckBox checkBox;
        public AllReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profile_image);
            deleteimg = itemView.findViewById(R.id.deleteimg);
            checkBox = itemView.findViewById(R.id.checkBox);
            title = itemView.findViewById(R.id.tv_cardName);
            name = itemView.findViewById(R.id.tv_name);
            number = itemView.findViewById(R.id.tv_number);


        }

    }
    public interface OnItemClick {
        void onClick (int value);
    }
    public void deleteCard(final Activity context, final int pos) {
        GlobalClass.showLoading(context, context.getResources().getString(R.string.please_wait));
        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        final String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);
        final String userid = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        AddcardRequestModel addcardRequestModel = new AddcardRequestModel();
        addcardRequestModel.setUser_id(userid);
        addcardRequestModel.setCard_id(allCardsModelList.get(pos).getCardId());


        Call<UserResponseModel> call = apiService.deleteCard(accessToken,addcardRequestModel);

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
                            allCardsModelList.remove(pos);
                            notifyDataSetChanged();

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


