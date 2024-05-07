package com.softarena.checagocoffee.Acitivity.Cart;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserRequestModel;
import com.softarena.checagocoffee.Acitivity.Signin.Model.UserResponseModel;
import com.softarena.checagocoffee.Helper.DatabaseHelper;
import com.softarena.checagocoffee.Acitivity.Products.AllProduct;
import com.softarena.checagocoffee.Helper.GlobalClass;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;
import com.softarena.checagocoffee.Rest.ApiInterface;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.OrderSummaryViewHolder> {
    Activity  context;
    List<AllProduct> productList;
    DatabaseHelper databaseHelper;
    TextView tv_total,tv_subtotal;
    double finalPricePrev;
    private AllProduct allProduct;
    ConstraintLayout ln34;
    TextView tvshipcharges,tvservice,tvtax;
    double totalWeight;
    public CartAdapter(List<AllProduct> productList, Activity context, TextView tv_total, double finalPricePrev, TextView tvSubtotal, ConstraintLayout ln34, TextView tvshipcharges, double totalWeight,TextView tvservice,TextView tv_tax)
    {
       this.productList = productList;
       this.context =  context;
       this.ln34 =  ln34;
       this.tvservice =  tvservice;
       this.totalWeight =  totalWeight;
       this.tvshipcharges =  tvshipcharges;
       this.tv_total =  tv_total;
       this.tv_subtotal =  tvSubtotal;
       this.finalPricePrev =  finalPricePrev;
       this.tvtax =  tv_tax;
       databaseHelper=new DatabaseHelper(context);

    }

    @NonNull
    @Override
    public OrderSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_ordersummary,parent,false);
        return new OrderSummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSummaryViewHolder holder, final int position) {
        allProduct=productList.get(position);
        Glide.with(context).load(APIClient.BASE_URL+productList.get(position).getProductImage()).into(holder.img_orderSummary_item);
        holder.tv_orderitem_name.setText(productList.get(position).getProductName());
        holder.tv_orderItem_size.setText(productList.get(position).getProductSizes().get(0).getProductSizeName());


        String ingItems="";
        double ingPrice=0;
        if (productList.get(position).getIngredientAll().size()!=0){
            for (int i=0;i<productList.get(position).getIngredientAll().size();i++){

                ingItems=productList.get(position).getIngredientAll().get(i).getIngredientName()+",\n"+ingItems;
                ingPrice=ingPrice+Double.parseDouble(productList.get(position).getIngredientAll().get(i).getIngredientPrice());
            }

            holder.tv_orderflavor_item.setText(ingItems);
            double finalprice=ingPrice+Double.parseDouble(productList.get(position).getProductSizes().get(0).getProductPrice());
            holder.tv_orderitem_price.setText(productList.get(position).getProductQuantity()+" x ZAR"+String.format("%.2f", finalprice));
        }else {
            holder.tv_orderitem_price.setText(productList.get(position).getProductQuantity()+" x ZAR"+String.format("%.2f", Double.parseDouble(productList.get(position).getProductSizes().get(0).getProductPrice())));
        }
        holder.deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseHelper.removeProductonproductId(productList.get(position).getProductId());
                productList.remove(position);
                notifyItemRemoved(position);
                double ingPrice=0;
                for (int i=0;i<allProduct.getIngredientAll().size();i++){

                    ingPrice=ingPrice+Double.parseDouble(allProduct.getIngredientAll().get(i).getIngredientPrice());
                }
                double finalprice=ingPrice+Double.parseDouble(allProduct.getProductSizes().get(0).getProductPrice());

                finalPricePrev=finalPricePrev-(Double.parseDouble(allProduct.getProductQuantity())*finalprice);

                DecimalFormat form = new DecimalFormat("#.00");
                finalPricePrev= Double.parseDouble(form.format(finalPricePrev));
                tvservice.setText("ZAR"+String.format("%.2f", (finalPricePrev/100)*Double.parseDouble(allProduct.getProduct_service_fee())));
                tv_subtotal.setText("ZAR"+String.format("%.2f", finalPricePrev));
                double totalpr = finalPricePrev + (finalPricePrev/100)*Double.parseDouble(allProduct.getProduct_service_fee());

                tvtax.setText("ZAR"+String.format("%.2f", (finalPricePrev/100)*Double.parseDouble("10.25")));
                totalpr=totalpr+((finalPricePrev/100)*Double.parseDouble("10.25"));
                tv_total.setText("ZAR"+String.format("%.2f", totalpr));
                if (productList.size()==0){
                    ((Activity) context).finish();
                }else if (allProduct.getProduct_cat_id()==2){
                    totalWeight=totalWeight-(Double.parseDouble(allProduct.getProduct_weight())*Integer.parseInt(allProduct.getProductQuantity()));
                    getShippingrates(context,allProduct,totalWeight,totalpr);
                }
            }
        });


    }
    public void getShippingrates(final Activity context,AllProduct allProduct,double totalWeight,final double totalpr) {

        GlobalClass.showLoading(context, context.getResources().getString(R.string.please_wait));
        final String userid = SessionManager.getStringPref(HelperKeys.USER_ID, context);
        final String accessToken = SessionManager.getStringPref(HelperKeys.User_Access_Token, context);

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
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class OrderSummaryViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_orderSummary_item;
        public View deleteLayout;
        TextView tv_orderitem_name,tv_orderItem_size,tv_orderflavor_item,tv_orderitem_price;
        public OrderSummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            img_orderSummary_item = itemView.findViewById(R.id.img_orderSummary_item);
            tv_orderitem_name = itemView.findViewById(R.id.tv_orderitem_name);
            tv_orderItem_size = itemView.findViewById(R.id.tv_orderItem_size);
            tv_orderflavor_item =  itemView.findViewById(R.id.tv_orderflavor_item);
            tv_orderitem_price = itemView.findViewById(R.id.tv_orderitem);
            deleteLayout = itemView.findViewById(R.id.delete_layout);

        }
    }
}
