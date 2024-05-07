package com.softarena.checagocoffee.Acitivity.Reviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class AllReviewAdapter extends RecyclerView.Adapter<AllReviewAdapter.AllReviewViewHolder> {
    private List<ShopAllReview> allReviewModelList;
    Context context;
    public AllReviewAdapter(List<ShopAllReview> allReviewModelList,Context context)
    {
        this.allReviewModelList = allReviewModelList;
        this.context = context;
    }
    @NonNull
    @Override
    public AllReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_all_reviews,parent,false);
        return new AllReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllReviewViewHolder holder, int position) {
        holder.tv_reviewName.setText(allReviewModelList.get(position).getUserName());
        holder.tv_reviewDate.setText(allReviewModelList.get(position).getShopReviewDate());
        holder.tv_reviewDescription.setText(allReviewModelList.get(position).getShopReviewComment());
        Glide.with(context).load(APIClient.BASE_URL+allReviewModelList.get(position).getUserImage()).into(holder.img_reviewerProfile);
        holder.allReviewRatingBar.setRating(Float.valueOf(allReviewModelList.get(position).getShopReviewRating()));
        holder.ratingshow.setText("("+allReviewModelList.get(position).getShopReviewRating()+")");

    }

    @Override
    public int getItemCount() {
        return allReviewModelList.size();
    }

    public class AllReviewViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_reviewerProfile;
        TextView tv_reviewName
                ,tv_reviewDate,tv_reviewDescription,ratingshow;
        RatingBar allReviewRatingBar;
        public AllReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            img_reviewerProfile = itemView.findViewById(R.id.img_reviewProfile);
            allReviewRatingBar = itemView.findViewById(R.id.allReviewRatingBar);
            ratingshow = itemView.findViewById(R.id.ratingshow);
            tv_reviewName = itemView.findViewById(R.id.tv_reviewName);
            tv_reviewDate = itemView.findViewById(R.id.tv_date);
            tv_reviewDescription = itemView.findViewById(R.id.tv_reviewDescription);

        }

    }
}
