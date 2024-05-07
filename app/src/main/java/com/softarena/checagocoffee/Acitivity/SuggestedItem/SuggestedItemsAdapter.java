package com.softarena.checagocoffee.Acitivity.SuggestedItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.softarena.checagocoffee.Acitivity.Products.AllProduct;
import com.softarena.checagocoffee.Acitivity.Shops.AllShop;
import com.softarena.checagocoffee.Helper.DatabaseHelper;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SuggestedItemsAdapter extends RecyclerView.Adapter<SuggestedItemsAdapter.SuggestedItemsViewHolder> {
    Context context;
    List<AllProduct> allProductList;
    AllShop allShop;
    DatabaseHelper databaseHelper;
    private OnItemClick mCallback;
    public SuggestedItemsAdapter(List<AllProduct> allProductList, Context context, AllShop allShop, OnItemClick listener)
    {
        this.allShop=new AllShop();
        this.allProductList = allProductList;
        this.allShop = allShop;
        this.mCallback = listener;
        this.context =  context;
        databaseHelper=new DatabaseHelper(context);

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public SuggestedItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=  layoutInflater.inflate(R.layout.item_suggestion_list,parent,false);
        return new SuggestedItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestedItemsViewHolder holder, final int position) {

        holder.tv_suggested_item_name.setText(allProductList.get(position).getProductName());
        holder.tv_suggested_item_price.setText("ZAR"+allProductList.get(position).getProductSizes().get(0).getProductPrice()+" for "+allProductList.get(position).getProductSizes().get(0).getProductSizeName());
        Glide.with(context).load(APIClient.BASE_URL+allProductList.get(position).getProductImage()).into(holder.img_suggessted_item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onSuggestedClick(position);

                }


        });
        holder.img_suggessted_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onSuggestedClick(position);

                }


        });
        int prodCount=databaseHelper.getProductItemsincart(allProductList.get(position).getProductId());
        if (prodCount>0){
            holder.prod_items.setText(prodCount+"");
            holder.prod_items.setVisibility(View.VISIBLE);
        }else {
            holder.prod_items.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return allProductList.size();
    }

    public class SuggestedItemsViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_suggessted_item;
        TextView tv_suggested_item_name,tv_suggested_item_price,prod_items;
        public SuggestedItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            img_suggessted_item = itemView.findViewById(R.id.img_suggested_items);
            prod_items = itemView.findViewById(R.id.prod_items);

            tv_suggested_item_name = itemView.findViewById(R.id.tv_suggested_item_name);
            tv_suggested_item_price = itemView.findViewById(R.id.tv_suggested_item_price);

        }

    }
    public interface OnItemClick {
        void onSuggestedClick(int value);
    }
}
