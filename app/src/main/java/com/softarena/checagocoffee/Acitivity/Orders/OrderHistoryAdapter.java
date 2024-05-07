package com.softarena.checagocoffee.Acitivity.Orders;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.softarena.checagocoffee.Acitivity.Reviews.RatingActivity;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserRequestModel;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserResponseModel;
import com.softarena.checagocoffee.Helper.GlobalClass;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;
import com.softarena.checagocoffee.Rest.ApiInterface;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {
    private List<OrderResponsemodel> orderHistoryModelList;
    Activity context;
    public OrderHistoryAdapter(List<OrderResponsemodel> orderHistoryModelList,Activity context)
    {
        this.orderHistoryModelList = orderHistoryModelList;
        this.context = context;
    }
    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_orderhistory,parent,false);
        return new OrderHistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, final int position) {
        holder.tv_orderHistoryStatus.setText(orderHistoryModelList.get(position).getOrderStatus());
        if (orderHistoryModelList.get(position).getOrderStatus().equals("Rejected")){
            holder.tv_orderHistoryRejection.setVisibility(View.VISIBLE);
            holder.rejecttv.setVisibility(View.VISIBLE);
            holder.tvrejectmain.setVisibility(View.VISIBLE);
            holder.tv_orderHistoryRejection.setText(orderHistoryModelList.get(position).getOrder_rejection());
        }else {
            holder.tv_orderHistoryRejection.setVisibility(View.GONE);
            holder.rejecttv.setVisibility(View.GONE);
            holder.tvrejectmain.setVisibility(View.GONE);
        }
        if (orderHistoryModelList.get(position).getOrder_tracking_id()!=null){
            holder.tracking.setVisibility(View.VISIBLE);
            holder.line1.setVisibility(View.VISIBLE);
            holder.trackno.setText(orderHistoryModelList.get(position).getOrder_tracking_id());
        }else {
            holder.line1.setVisibility(View.GONE);
            holder.tracking.setVisibility(View.GONE);
        }
        if (orderHistoryModelList.get(position).getOrder_label_id()!=null){
            holder.label.setVisibility(View.VISIBLE);
            holder.line2.setVisibility(View.VISIBLE);
            holder.lableno.setText(orderHistoryModelList.get(position).getOrder_label_id());
        }else {
            holder.label.setVisibility(View.GONE);
            holder.line2.setVisibility(View.GONE);

        }
        if (!orderHistoryModelList.get(position).getOrder_service_fee().equals("0")){

            double val=Double.parseDouble(orderHistoryModelList.get(position).getOrderPrice())+Double.parseDouble(orderHistoryModelList.get(position).getOrder_service_fee())+Double.parseDouble(orderHistoryModelList.get(position).getOrder_tax());
            if (orderHistoryModelList.get(position).getOrder_shipment_charges()!=null&&Double.valueOf(orderHistoryModelList.get(position).getOrder_shipment_charges())!=0){
                holder.tv_orderHistoryTotal.setText("ZAR"+String.format("%.2f",val)+"\n(ZAR"+orderHistoryModelList.get(position).getOrder_service_fee()+" Service Fee + "+"ZAR"+orderHistoryModelList.get(position).getOrder_shipment_charges()+" Shipping Fee + ZAR"+orderHistoryModelList.get(position).getOrder_tax()+" Tax Inc.)");

            }else {
                holder.tv_orderHistoryTotal.setText("ZAR"+String.format("%.2f",val)+"\n(ZAR"+orderHistoryModelList.get(position).getOrder_service_fee()+" Service Fee + ZAR"+orderHistoryModelList.get(position).getOrder_tax()+" Tax Inc.)");

            }

        }else {
            holder.tv_orderHistoryTotal.setText("ZAR"+orderHistoryModelList.get(position).getOrderPrice());

        }
        holder.tv_orderHistoryOrderDate.setText(orderHistoryModelList.get(position).getOrderCreatedAt());
        holder.tv_orderHistoryShopAddress.setText(orderHistoryModelList.get(position).getShopAddress());
        holder.tv_orderHistoryShopPhone.setText(orderHistoryModelList.get(position).getShopPhone());
        holder.tv_orderHistoryOrderreview.setText(orderHistoryModelList.get(position).getOrderRatingComment());
        holder.tv_orderHistoryShopName.setText(orderHistoryModelList.get(position).getShopName());
        if (orderHistoryModelList.get(position).getOrderRating()!=null){
            holder.ratingBarOrderHistory.setRating(Float.valueOf(orderHistoryModelList.get(position).getOrderRating()));
            holder.addrating.setVisibility(View.GONE);
            holder.showline.setVisibility(View.GONE);
        }else if (orderHistoryModelList.get(position).getOrderStatus().equals("Completed")&&
                orderHistoryModelList.get(position).getOrderRating()==null){
            holder.addrating.setVisibility(View.VISIBLE);
            holder.showline.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, OrderHistoryProdcutItemActivity.class);
                intent.putExtra("order_id",orderHistoryModelList.get(position).getOrderId());
                context.startActivity(intent);
            }
        });
        holder.addrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (
                            orderHistoryModelList.get(position).getOrderStatus().equals("Completed")&&
                            orderHistoryModelList.get(position).getOrderRating()==null
                    ){
                    Intent intent=new Intent(context, RatingActivity.class);
                    intent.putExtra("orderid",String.valueOf(orderHistoryModelList.get(position).getOrderId()));
                    context.startActivity(intent);
                }
            }
        });
        holder.tracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderHistoryModelList.get(position).getOrder_tracking_id()!=null){

                    trackOrder(context,orderHistoryModelList.get(position).getOrder_tracking_id());
                }
            }
        });
    }
    public void trackOrder(final Activity context, String track) {
        GlobalClass.showLoading(context, context.getResources().getString(R.string.please_wait));
        ApiInterface apiService = APIClient.getRetrofitInstance().create(ApiInterface.class);
        final String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);
        final String id = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        UserRequestModel userRequestModel = new UserRequestModel();
//        setting parameters
        userRequestModel.setUser_id(id);

        userRequestModel.setU_type("user");
        userRequestModel.setTracking_number(track);

        Call<UserResponseModel> call = apiService.trackOrder(accessToken,userRequestModel);

        Log.d("Response", "URL==" + call.request().url());
        call.enqueue(new Callback<UserResponseModel>() {
            @Override
            public void onResponse(Call<UserResponseModel> call, Response<UserResponseModel> response) {
                int statusCode = response.code();
                Log.e("main", "apt " + response.body());

                GlobalClass.dismissLoading();

                if (response.isSuccessful()) {
                    Log.d("Success", new Gson().toJson(response.body()));
                    UserResponseModel modelSigninReceive = response.body();
                    if (modelSigninReceive.getStatus_description()!=null){
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.dialog_tracking);
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        lp.gravity = Gravity.CENTER;
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.getWindow().setAttributes(lp);
                        TextView tv_ok = dialog.findViewById(R.id.tv_okDriverArrived);
                        TextView tv_2 = dialog.findViewById(R.id.tv_2);
                        TextView tv_3 = dialog.findViewById(R.id.tv_3);
                        TextView tv_4 = dialog.findViewById(R.id.tv_4);
                        TextView tv_5 = dialog.findViewById(R.id.tv_5);
                        tv_2.setText("STATUS : "+modelSigninReceive.getStatus_description());
                        tv_3.setText("DESCRIPTION : "+modelSigninReceive.getCarrier_status_description());
                        if (modelSigninReceive.getEstimated_delivery_date()!=null){

                            tv_4.setText("ESTIMATED DELIVERY : "+modelSigninReceive.getEstimated_delivery_date());
                        }
                        if (modelSigninReceive.getActual_delivery_date()!=null){

                            tv_5.setText("ACTUAL DELIVERY : "+modelSigninReceive.getActual_delivery_date());
                        }
                        tv_ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }else {
                        Toast.makeText(context
                                , "Not Data found yet", Toast.LENGTH_SHORT).show();
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
    @Override
    public int getItemCount() {
        return orderHistoryModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_orderHistoryTotal,tv_orderHistoryStatus
                ,tv_orderHistoryOrderreview,lableno,line1,line2,trackno,rejecttv,tvrejectmain,tv_orderHistoryRejection,tv_orderHistoryOrderDate,showline,tv_orderHistoryShopAddress,tv_orderHistoryShopPhone,tv_orderHistoryShopName;
        RatingBar ratingBarOrderHistory;
        LinearLayout addrating,label,tracking;
        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_orderHistoryStatus =itemView.findViewById(R.id.tv_orderHistoryStatus);
            trackno =itemView.findViewById(R.id.trackno);
            line1 =itemView.findViewById(R.id.line1);
            line2 =itemView.findViewById(R.id.line2);
            label =itemView.findViewById(R.id.label);
            tracking =itemView.findViewById(R.id.tracking);
            lableno =itemView.findViewById(R.id.lableno);
            rejecttv =itemView.findViewById(R.id.rejecttv);
            tv_orderHistoryRejection = itemView.findViewById(R.id.tv_orderHistoryRejection);
            tvrejectmain = itemView.findViewById(R.id.tvrejectmain);
            tv_orderHistoryShopName = itemView.findViewById(R.id.tv_orderHistoryShopName);
            tv_orderHistoryShopPhone = itemView.findViewById(R.id.tv_orderHistoryShopPhone);
            tv_orderHistoryShopAddress = itemView.findViewById(R.id.tv_orderHistoryShopAddress);
            showline = itemView.findViewById(R.id.showline);
            addrating = itemView.findViewById(R.id.addrating);
            tv_orderHistoryOrderDate = itemView.findViewById(R.id.tv_orderHistoryOrderDate);
            tv_orderHistoryOrderreview = itemView.findViewById(R.id.tv_orderHistoryOrderreview);
            tv_orderHistoryTotal = itemView.findViewById(R.id.tv_orderHistoryTotal);
            ratingBarOrderHistory = itemView.findViewById(R.id.ratingBarOrderHistory);

        }

    }
}
