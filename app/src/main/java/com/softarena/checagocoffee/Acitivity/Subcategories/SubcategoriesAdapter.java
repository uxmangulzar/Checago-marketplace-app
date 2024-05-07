package com.softarena.checagocoffee.Acitivity.Subcategories;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.softarena.checagocoffee.Acitivity.Products.SavounirProductActivity;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class SubcategoriesAdapter extends RecyclerView.Adapter<SubcategoriesAdapter.SeeFoodTypeViewHolder> {
    private List<AllSubcategoryResponseModel> subcategoryResponseModelList;
    Context context;
    public SubcategoriesAdapter(List<AllSubcategoryResponseModel> subcategoryResponseModelList, Context context)
    {
        this.subcategoryResponseModelList = subcategoryResponseModelList;
        this.context =  context;
    }
    @NonNull
    @Override
    public SeeFoodTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_food_type,parent,false);
        return new SeeFoodTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeeFoodTypeViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, SavounirProductActivity.class);
                intent.putExtra("subcat_id",subcategoryResponseModelList.get(position).getSubcategoryId());
                intent.putExtra("subcat",subcategoryResponseModelList.get(position).getSubcategoryName());
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(APIClient.BASE_URL+subcategoryResponseModelList.get(position).getSubcategoryImage()).into(holder.img_foodType);

        holder.foodName.setText(subcategoryResponseModelList.get(position).getSubcategoryName());

    }

    @Override
    public int getItemCount() {
        return subcategoryResponseModelList.size();
    }

    public class SeeFoodTypeViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_foodType;
        TextView foodName;
        RatingBar browseShopRatingBar;
        public SeeFoodTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            img_foodType = itemView.findViewById(R.id.img);
            foodName = itemView.findViewById(R.id.foodName);

        }
    }
}
