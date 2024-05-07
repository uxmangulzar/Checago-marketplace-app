package com.softarena.checagocoffee.Acitivity.Shops;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.softarena.checagocoffee.Acitivity.Reviews.AllReviewActivity;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShopNowBrowseAdapter extends RecyclerView.Adapter<ShopNowBrowseAdapter.BrowseShopViewHolder> {
    private List<AllShop> shopNowLIstModelList;
    Context context;
    String cat_id;
    public ShopNowBrowseAdapter( List<AllShop> shopNowLIstModelList,Context context,String cat_id)
    {
        this.shopNowLIstModelList = shopNowLIstModelList;
        this.context = context;
        this.cat_id = cat_id;
    }
    @NonNull
    @Override
    public BrowseShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_community_board,parent,false);
        return new BrowseShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrowseShopViewHolder holder, final int position) {
        holder.tv_shop_name.setText(shopNowLIstModelList.get(position).getShopName());
        holder.tv_shop_address.setText(shopNowLIstModelList.get(position).getShopAddress());
        Glide.with(context).load(APIClient.BASE_URL+shopNowLIstModelList.get(position).getShopImage()).into(holder.img_shopImage);
        holder.tv_shop_distance.setText(shopNowLIstModelList.get(position).getShop_distance()+" miles");
        holder.tv_shop_description.setText(shopNowLIstModelList.get(position).getShopDescription());
        holder.tv_ratingBar.setText("("+Float.valueOf(shopNowLIstModelList.get(position).getShop_total_rating())+")");
         holder.rl_browseShopItem.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent= new Intent(context, BrowseShopItemActivity.class);
                 intent.putExtra("model",shopNowLIstModelList.get(position));
                 intent.putExtra("cat_id",cat_id);
                 context.startActivity(intent);
             }
         });
         holder.tv_allReview.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(context, AllReviewActivity.class);
                 intent.putExtra("shopid",String.valueOf(shopNowLIstModelList.get(position).getShopId()));
                 context.startActivity(intent);
             }
         });
//         holder.tv_shop_distance.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                         Uri.parse("google.navigation:q="+shopNowLIstModelList.get(position).getShopAddress()));
//                 context.startActivity(intent);
//             }
//         });
    }

    @Override
    public int getItemCount() {
        return shopNowLIstModelList.size();
    }

    public class BrowseShopViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_browse_item,img_shopImage;
        LinearLayout ln_rightArrow;
        RelativeLayout rl_browseShopItem;
        RatingBar ratingBar;
        TextView tv_shop_name,tv_shop_description
                ,tv_shop_address,tv_shop_distance,tv_allReview,tv_ratingBar;

        public BrowseShopViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_shop_description = itemView.findViewById(R.id.tv_shop_description);
            tv_shop_address = itemView.findViewById(R.id.tv_shop_address);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            tv_allReview = itemView.findViewById(R.id.tv_allReview);
            tv_ratingBar = itemView.findViewById(R.id.tv_ratingBar);
            tv_shop_distance = itemView.findViewById(R.id.tv_shop_distance);
            img_shopImage = itemView.findViewById(R.id.img_shopImage);
            tv_shop_name = itemView.findViewById(R.id.tv_shopName);

            ln_rightArrow = itemView.findViewById(R.id.ln_rightArrow);
            rl_browseShopItem = itemView.findViewById(R.id.rl_browseshopitem);
        }
    }
}
