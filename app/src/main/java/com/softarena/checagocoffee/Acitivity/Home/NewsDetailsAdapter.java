package com.softarena.checagocoffee.Acitivity.Home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.softarena.checagocoffee.Acitivity.WebView.News1Activity;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.R;
import com.softarena.checagocoffee.Rest.APIClient;

import java.util.List;


public class NewsDetailsAdapter extends RecyclerView.Adapter<NewsDetailsAdapter.NotificationViewHolder> {
    private List<AllNews> allNewsList;
    Context context;
    public NewsDetailsAdapter(List<AllNews> allNewsList, Context context)
    {
        this.allNewsList = allNewsList;
        this.context = context;
    }
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_news_details,parent,false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, final int position) {
        holder.tv2.setText(allNewsList.get(position).getNewsName());
        Glide.with(context).load(APIClient.BASE_URL+allNewsList.get(position).getNewsImage()).into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allNewsList.get(position)!=null){
                    Intent intent=new Intent(context, News1Activity.class);
                    intent.putExtra("link",allNewsList.get(position).getNewsLink());
                    context.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return allNewsList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView tv2;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tv2 = itemView.findViewById(R.id.tv2);
            img = itemView.findViewById(R.id.img);

        }

    }
}
