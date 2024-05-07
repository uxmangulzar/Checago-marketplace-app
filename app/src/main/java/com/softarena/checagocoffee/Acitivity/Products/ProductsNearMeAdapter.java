package com.softarena.checagocoffee.Acitivity.Products;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.softarena.checagocoffee.Helper.DatabaseHelper;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class ProductsNearMeAdapter extends RecyclerView.Adapter<ProductsNearMeAdapter.ShopNearMeViewHolder> {
    private final DatabaseHelper databaseHelper;
    private List<AllProduct> shopNearModelList;
    Context context;
    String catId;
    public ProductsNearMeAdapter(List<AllProduct> shopNearModelList, Context context, String catId)
    {
        this.shopNearModelList = shopNearModelList;
        this.context =  context;
        this.catId =  catId;
        databaseHelper=new DatabaseHelper(context);
    }
    @NonNull
    @Override
    public ShopNearMeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_shop_near_main,parent,false);
        return new ShopNearMeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopNearMeViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, ShopproductDetailActivity.class);
                intent.putExtra("prodid",shopNearModelList.get(position).getProductId());
                intent.putExtra("model",shopNearModelList.get(position));
                intent.putExtra("shopmodel",shopNearModelList.get(position).getShop_details().get(0));
                context.startActivity(intent);
            }
        });
        holder.img_shopNear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, ShopproductDetailActivity.class);
                intent.putExtra("prodid",shopNearModelList.get(position).getProductId());
                intent.putExtra("model",shopNearModelList.get(position));
                intent.putExtra("shopmodel",shopNearModelList.get(position).getShop_details().get(0));
                context.startActivity(intent);
            }
        });

        Glide.with(context).load(APIClient.BASE_URL+shopNearModelList.get(position).getProductImage()).into(holder.img_shopNear);
        holder.tv_shopNearName.setText(shopNearModelList.get(position).getProductName());
        int prodCount=databaseHelper.getProductItemsincart(shopNearModelList.get(position).getProductId());
        if (prodCount>0){
            holder.prod_items.setText(prodCount+"");
            holder.prod_items.setVisibility(View.VISIBLE);
        }else {
            holder.prod_items.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return shopNearModelList.size();
    }

    public class ShopNearMeViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_shopNear;
        TextView tv_shopNearName,prod_items;
        RatingBar browseShopRatingBar;
        CardView wholecard;
        public ShopNearMeViewHolder(@NonNull View itemView) {
            super(itemView);
            img_shopNear = itemView.findViewById(R.id.img_shopNearMe);
            wholecard = itemView.findViewById(R.id.wholecard);
            prod_items = itemView.findViewById(R.id.prod_items);
            tv_shopNearName = itemView.findViewById(R.id.tv_ShopNearMeName);

        }
    }
}
