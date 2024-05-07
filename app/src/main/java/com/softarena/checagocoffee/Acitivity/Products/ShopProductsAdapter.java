package com.softarena.checagocoffee.Acitivity.Products;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.softarena.checagocoffee.Acitivity.Shops.AllShop;
import com.softarena.checagocoffee.Helper.DatabaseHelper;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShopProductsAdapter extends RecyclerView.Adapter<ShopProductsAdapter.BrowseShopeItemViewHolder> {
    private List<AllProduct> browseShopItemModelList;
    Context context;
    String shopStatus;
    AllShop allShop;
    DatabaseHelper databaseHelper;
    public ShopProductsAdapter(List<AllProduct> browseShopItemModelList, Context context, AllShop allShop, String shopStatus)
    {
        this.browseShopItemModelList = browseShopItemModelList;
        this.context =  context;
        this.shopStatus =  shopStatus;
        this.allShop =  allShop;
        databaseHelper=new DatabaseHelper(context);
    }
    @NonNull
    @Override
    public BrowseShopeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.items_browse_shop,parent,false);
        return new BrowseShopeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrowseShopeItemViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ||browseShopItemModelList.get(position).getProduct_cat_id()==2
                if (shopStatus.equals("opened")){
                    if (Integer.parseInt(browseShopItemModelList.get(position).getProductQuantity())>0){
                        Intent intent= new Intent(context, ShopproductDetailActivity.class);
                        intent.putExtra("prodid",browseShopItemModelList.get(position).getProductId());
                        intent.putExtra("model",browseShopItemModelList.get(position));
                        intent.putExtra("shopmodel",allShop);
                        context.startActivity(intent);
                    }else {
                        Toast.makeText(context, "Item out of stock try another time", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(context, "Shop is closed", Toast.LENGTH_SHORT).show();
                }

            }
        });
        holder.tv_foodName.setText(browseShopItemModelList.get(position).getProductName());
        if (browseShopItemModelList.get(position).getProductSizes().size()!=0){
            holder.tv_stockInfo.setText(browseShopItemModelList.get(position).getProductSizes().size()+" Sizes Available");
            holder.tv_foodPrice.setText("ZAR"+browseShopItemModelList.get(position).getProductSizes().get(0).getProductPrice());

        }
        Glide.with(context).load(APIClient.BASE_URL+browseShopItemModelList.get(position).getProductImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_browse_shop_itemss);
        int prodCount=databaseHelper.getProductItemsincart(browseShopItemModelList.get(position).getProductId());
        if (prodCount>0){
            holder.prod_items.setText(prodCount+"");
            holder.prod_items.setVisibility(View.VISIBLE);
        }else {
            holder.prod_items.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return browseShopItemModelList.size();
    }

    public class BrowseShopeItemViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_browse_shop_itemss;
        TextView tv_foodName,tv_foodPrice,tv_stockInfo,prod_items;
        RatingBar browseShopRatingBar;
        public BrowseShopeItemViewHolder(@NonNull View itemView) {
            super(itemView);
            prod_items = itemView.findViewById(R.id.prod_items);
            browseShopRatingBar = itemView.findViewById(R.id.browseShopRatingBar);
            tv_foodName = itemView.findViewById(R.id.tv_foodName);
            tv_foodPrice = itemView.findViewById(R.id.tv_foodPrice);
            tv_stockInfo = itemView.findViewById(R.id.tv_stockInfo);

            img_browse_shop_itemss = itemView.findViewById(R.id.img_browse_shop_itemss);
        }
    }
}
